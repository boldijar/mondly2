package io.github.boldijar.cosasapp.parts.room;

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
import io.github.boldijar.cosasapp.data.BaseResponse;
import io.github.boldijar.cosasapp.data.Room;
import io.github.boldijar.cosasapp.data.RoomsResponse;
import io.github.boldijar.cosasapp.data.User;
import io.github.boldijar.cosasapp.server.Http;
import io.github.boldijar.cosasapp.util.FirebaseUtils;
import io.github.boldijar.cosasapp.util.Observatorul;
import io.github.boldijar.cosasapp.util.RxUtils;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class RoomListActivity extends BaseActivity {

    @BindView(R.id.room_list_recycler)
    RecyclerView mRecyclerView;


    private FastAdapter<Room, RoomHolder> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        ButterKnife.bind(this);

        mAdapter = new FastAdapter<Room, RoomHolder>() {
            @Override
            protected AbstractHolder createHolder(ViewGroup parent) {
                return new RoomHolder(parent);
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.clear();
        Http.getInstance().getApiService().getRooms()
                .compose(RxUtils.applySchedulers())
                .subscribe(new Observatorul<RoomsResponse>() {
                    @Override
                    public void onNext(RoomsResponse roomsResponse) {
                        mAdapter.clear();
                        mAdapter.add(roomsResponse.mRooms);
                    }
                });

    }

    public class RoomHolder extends FastAdapter.AbstractHolder<Room> {
        @BindView(R.id.room_text)
        TextView mText;
        @BindView(R.id.room_owner)
        ImageView mImage;

        public RoomHolder(ViewGroup parent) {
            super(parent, R.layout.item_room);

        }

        @Override
        public void bind(Room item) {
            if (item.mPlayers == null || item.mPlayers.size() == 0) {
                return;
            }
            int count = item.mPlayers.size();
            User initiator = item.mPlayers.get(0);
            Glide.with(mImage).load(initiator.mImage).into(mImage);
            CharSequence when = FirebaseUtils.getRelativeTime(item.mCreated);
            mText.setText("Room created by " + initiator.mName + " " + when + ".\nPlayers joined: " + count);
            itemView.setOnClickListener(view -> selectedRoom(item));
        }
    }

    private void selectedRoom(Room item) {
        Http.getInstance().getApiService().joinRoom(item.mId)
                .compose(RxUtils.applySchedulers())
                .subscribe(new Observatorul<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        finish();
                        startActivity(RoomWaitingActivity.createIntent(RoomListActivity.this, item.mId, false, item));
                    }
                });
    }


}
