package io.github.boldijar.cosasapp.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.boldijar.cosasapp.R;
import io.github.boldijar.cosasapp.base.BaseActivity;
import io.github.boldijar.cosasapp.base.PersonProgress;
import io.github.boldijar.cosasapp.data.BaseResponse;
import io.github.boldijar.cosasapp.data.Game;
import io.github.boldijar.cosasapp.data.MessageType;
import io.github.boldijar.cosasapp.data.Question;
import io.github.boldijar.cosasapp.data.ServerMessage;
import io.github.boldijar.cosasapp.data.User;
import io.github.boldijar.cosasapp.server.Http;
import io.github.boldijar.cosasapp.util.Observatorul;
import io.github.boldijar.cosasapp.util.ObservatorulNormal;
import io.github.boldijar.cosasapp.util.Prefs;
import io.github.boldijar.cosasapp.util.RxUtils;
import io.reactivex.Observable;
import timber.log.Timber;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class GameActivity extends BaseActivity implements BaseQuestionFragment.BaseQuestionListener {

    private static final String ARG_GAME = "game";
    private static final int GAME_SECONDS = 60;
    @BindView(R.id.game_persons_scroll)
    LinearLayout mLinearLayout;
    @BindView(R.id.game_pager)
    ViewPager mViewPager;
    @BindView(R.id.game_progress)
    ProgressBar mProgressBar;

    private HashMap<User, PersonProgress> mPeopleMap = new HashMap<>();

    private GamePagerAdapter mGamePagerAdapter;
    private Game mGame;
    private int mOwnId;

    public static Intent createIntent(Context context, Game game) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(ARG_GAME, game);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        loadArgs();
        initUi();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initUi() {
        for (User user : mGame.mPlayers) {
            PersonProgress personProgress = new PersonProgress(this);
            personProgress.loadImage(user.mImage);
            personProgress.setProgress(0);
            mLinearLayout.addView(personProgress);
            mPeopleMap.put(user, personProgress);
        }
        mGamePagerAdapter = new GamePagerAdapter(getSupportFragmentManager(), mGame.mQuestions, this);
        mViewPager.setAdapter(mGamePagerAdapter);
        mProgressBar.setMax(60);
        mProgressBar.setProgress(0);
        Observable.interval(1, TimeUnit.SECONDS)
                .compose(RxUtils.applySchedulers())
                .subscribe(new ObservatorulNormal<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        mProgressBar.setProgress(mProgressBar.getProgress() + 1);
                    }
                });
    }

    private void loadArgs() {
        try {
            mGame = getIntent().getParcelableExtra(ARG_GAME);
            mOwnId = Prefs.getUser().mId;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Http.getInstance().getApiService().leaveRoom(mGame.mRoomId).subscribe(new Observatorul<>());
        super.onBackPressed();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ServerMessage event) {
        if (event.mRoomId != mGame.mRoomId) {
            return;
        }
        if (event.mType == MessageType.ROOM_PLAYER_LEFT) {
            handleLeftEvent(event);
        }
        if (event.mType == MessageType.ROOM_PLAYER_UPDATE) {
            handlePlayerUpdate(event);
        }
    }

    private void handlePlayerUpdate(ServerMessage event) {
        PersonProgress personProgress = mPeopleMap.get(event.mUser);
        if (personProgress == null) {
            return;
        }
        int percent = (int) ((100.0 * event.mQuestionAnsweredCount) / mGame.mQuestions.size());
        personProgress.setProgress(percent);
        Timber.d("Got some update for player " + event.mUser.mName + ", progress is now: " + percent + "%");
        if (event.mUser.mId == mOwnId) {
            // we got an update, that we just answered something, move on to next question
            if (mViewPager.getCurrentItem() < mGamePagerAdapter.getCount() - 1) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            } else {
                // wtf? game is over
                Timber.e(new Exception("GAME should be over right now..."));
            }
        }
    }

    private void handleLeftEvent(ServerMessage event) {
        PersonProgress personProgress = mPeopleMap.get(event.mUser);
        if (personProgress == null) {
            return;
        }
        personProgress.setDeadOverlay(true);
    }

    @Override
    public void wannaAnswer(Question question, String answer) {
        Http.getInstance().getApiService().sendAnswer(mGame.mRoomId, question.mId, answer)
                .compose(RxUtils.applySchedulers())
                .subscribe(new Observatorul<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Toast.makeText(GameActivity.this, "Something bad happened. Try again", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
