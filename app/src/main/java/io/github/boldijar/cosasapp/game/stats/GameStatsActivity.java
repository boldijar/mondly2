package io.github.boldijar.cosasapp.game.stats;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.boldijar.cosasapp.R;
import io.github.boldijar.cosasapp.base.BaseActivity;
import io.github.boldijar.cosasapp.base.FastAdapter;
import io.github.boldijar.cosasapp.data.GameStatsResponse;
import io.github.boldijar.cosasapp.data.User;
import io.github.boldijar.cosasapp.server.Http;
import io.github.boldijar.cosasapp.util.Observatorul;
import io.github.boldijar.cosasapp.util.Prefs;
import io.github.boldijar.cosasapp.util.RxUtils;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class GameStatsActivity extends BaseActivity {
    private static final String ARG_ROOM_ID = "room_id";

    public static Intent createIntent(int roomId, Context context) {
        Intent intent = new Intent(context, GameStatsActivity.class);
        intent.putExtra(ARG_ROOM_ID, roomId);
        return intent;
    }

    @BindView(R.id.game_stats_title)
    TextView mTitle;

    @BindView(R.id.game_stats_list)
    RecyclerView mRecyclerView;

    private int mOwnUserId;
    private int mRoomId;

    private FastAdapter<User, UserStatsHolder> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_stats);
        ButterKnife.bind(this);
        loadArgs();
        loadUi();
        Http.getInstance().getApiService().getGameStats(mRoomId)
                .compose(RxUtils.applySchedulers())
                .subscribe(new Observatorul<GameStatsResponse>() {
                    @Override
                    public void onNext(GameStatsResponse response) {
                        gotStats(response);
                    }
                });
    }

    private void loadUi() {
        mAdapter = new FastAdapter<User, UserStatsHolder>() {
            @Override
            protected AbstractHolder createHolder(ViewGroup parent) {
                return new UserStatsHolder(parent);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadArgs() {
        mOwnUserId = Prefs.getUser().mId;
        mRoomId = getIntent().getIntExtra(ARG_ROOM_ID, 0);
    }

    private void gotStats(GameStatsResponse statsResponse) {
        if (statsResponse.mUsers == null || statsResponse.mUsers.size() == 0) {
            return;
        }
        boolean userWon = statsResponse.mUsers.get(0).mId == mOwnUserId;
        mTitle.setText(userWon ? R.string.you_won : R.string.you_lost);
        mAdapter.add(statsResponse.mUsers);
    }

    public class UserStatsHolder extends FastAdapter.AbstractHolder<User> {

        @BindView(R.id.user_stats_image)
        ImageView mImage;
        @BindView(R.id.user_stats_name)
        TextView mName;
        @BindView(R.id.user_stats_image_layout)
        PulsatorLayout mPulsatorLayout;
        @BindView(R.id.user_stats_subtitle)
        TextView mStats;

        public UserStatsHolder(ViewGroup parent) {
            super(parent, R.layout.item_user_stats);
        }

        @Override
        public void bind(User item, int position) {
            if (position == 0 && mOwnUserId == item.mId) {
                // user won
                mPulsatorLayout.setColor(Color.GREEN);
                mPulsatorLayout.start();
            } else if (mOwnUserId == item.mId) {
                mPulsatorLayout.setColor(Color.RED);
                mPulsatorLayout.start();
            } else {
                mPulsatorLayout.stop();
            }
            Glide.with(itemView).load(item.mImage).into(mImage);
            mName.setText(item.mName);
            mStats.setText("Scored " + item.mScore + " points, answered " + item.mQuestionAnsweredCount + "/" + item.mQuestionAnsweredTotal + " questions");
        }
    }
}
