package io.github.boldijar.cosasapp.leaderboard;

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
import io.github.boldijar.cosasapp.util.RxUtils;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class LeaderboardActivity extends BaseActivity {

    @BindView(R.id.leaderboard_list)
    RecyclerView mRecyclerView;

    FastAdapter<User, UserLeaderboardHolder> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        ButterKnife.bind(this);
        mAdapter = new FastAdapter<User, UserLeaderboardHolder>() {
            @Override
            protected AbstractHolder createHolder(ViewGroup parent) {
                return new UserLeaderboardHolder(parent);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Http.getInstance().getApiService().getLeaderboard()
                .compose(RxUtils.applySchedulers())
                .subscribe(new Observatorul<GameStatsResponse>() {
                    @Override
                    public void onNext(GameStatsResponse gameStatsResponse) {
                        mAdapter.add(gameStatsResponse.mUsers);
                    }
                });
    }

    public class UserLeaderboardHolder extends FastAdapter.AbstractHolder<User> {

        @BindView(R.id.user_leaderboard_image)
        ImageView mImage;
        @BindView(R.id.user_leaderboard_name)
        TextView mName;

        UserLeaderboardHolder(ViewGroup parent) {
            super(parent, R.layout.item_user_leaderboard);
        }

        @Override
        public void bind(User item, int position) {
            position++;
            mName.setText(position + ". " + item.mName);
            Glide.with(itemView).load(item.mImage).into(mImage);
        }
    }
}
