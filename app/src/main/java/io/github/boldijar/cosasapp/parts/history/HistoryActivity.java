package io.github.boldijar.cosasapp.parts.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.boldijar.cosasapp.R;
import io.github.boldijar.cosasapp.base.BaseActivity;
import io.github.boldijar.cosasapp.base.FastAdapter;
import io.github.boldijar.cosasapp.data.HistoryResponse;
import io.github.boldijar.cosasapp.data.RoomHistory;
import io.github.boldijar.cosasapp.game.stats.GameStatsActivity;
import io.github.boldijar.cosasapp.server.Http;
import io.github.boldijar.cosasapp.util.FirebaseUtils;
import io.github.boldijar.cosasapp.util.Observatorul;
import io.github.boldijar.cosasapp.util.RxUtils;

/**
 * TODO: Class description
 *
 * @author Paul
 * @since 2018.10.13
 */
public class HistoryActivity extends BaseActivity {

    @BindView(R.id.history_list)
    RecyclerView mRecyclerView;

    private FastAdapter<RoomHistory, RoomHistoryHolder> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FastAdapter<RoomHistory, RoomHistoryHolder>() {
            @Override
            protected AbstractHolder createHolder(ViewGroup parent) {
                return new RoomHistoryHolder(parent);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        Http.getInstance().getApiService().getHistory()
                .compose(RxUtils.applySchedulers())
                .subscribe(new Observatorul<HistoryResponse>() {
                    @Override
                    public void onNext(HistoryResponse historyResponse) {
                        mAdapter.add(historyResponse.mRoomHistoryList);
                    }
                });
    }

    public class RoomHistoryHolder extends FastAdapter.AbstractHolder<RoomHistory> {
        @BindView(R.id.history_emoji)
        TextView mEmoji;
        @BindView(R.id.history_text)
        TextView mText;

        public RoomHistoryHolder(ViewGroup parent) {
            super(parent, R.layout.item_room_history);
        }

        @Override
        public void bind(RoomHistory item) {
            mEmoji.setText(item.mWinner ? R.string.emoji_won : R.string.emoji_lost);
            String status = item.mWinner ? "Won" : "Lost";
            CharSequence timeAgo = FirebaseUtils.getRelativeTime(item.mUpdatedAt);
            mText.setText(status + " " + timeAgo + " with " + item.mScore + " points");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(GameStatsActivity.createIntent(item.mRoomId, HistoryActivity.this));
                }
            });
        }
    }
}
