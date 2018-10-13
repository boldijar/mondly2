package io.github.boldijar.cosasapp.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.boldijar.cosasapp.R;
import io.github.boldijar.cosasapp.base.BaseActivity;
import io.github.boldijar.cosasapp.base.PersonProgress;
import io.github.boldijar.cosasapp.data.Game;
import io.github.boldijar.cosasapp.data.User;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class GameActivity extends BaseActivity {

    private static final String ARG_GAME = "game";

    @BindView(R.id.game_persons_scroll)
    LinearLayout mLinearLayout;
    @BindView(R.id.game_pager)
    ViewPager mViewPager;

    private HashMap<User, PersonProgress> mPeopleMap = new HashMap<>();

    private GamePagerAdapter mGamePagerAdapter;
    private Game mGame;

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

    }

    private void initUi() {
        for (User user : mGame.mPlayers) {
            PersonProgress personProgress = new PersonProgress(this);
            personProgress.loadImage(user.mImage);
            personProgress.setProgress(30);
            mLinearLayout.addView(personProgress);
            mPeopleMap.put(user, personProgress);
        }
        mGamePagerAdapter = new GamePagerAdapter(getSupportFragmentManager(), mGame.mQuestions);
        mViewPager.setAdapter(mGamePagerAdapter);
    }

    private void loadArgs() {
        try {
            mGame = getIntent().getParcelableExtra(ARG_GAME);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
