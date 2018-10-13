package io.github.boldijar.cosasapp.parts.room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.boldijar.cosasapp.R;
import io.github.boldijar.cosasapp.base.BaseActivity;
import io.github.boldijar.cosasapp.base.FastAdapter;
import io.github.boldijar.cosasapp.data.MessageType;
import io.github.boldijar.cosasapp.data.ServerMessage;
import io.github.boldijar.cosasapp.data.User;
import io.github.boldijar.cosasapp.server.Http;
import io.github.boldijar.cosasapp.util.FirebaseUtils;
import timber.log.Timber;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class RoomWaitingActivity extends BaseActivity {

    private static final String ARG_ROOM_ID = "roomid";
    private static final String ARG_USER_CREATED = "user_created";
    private boolean mUserCreated;

    public static Intent createIntent(Context context, int roomId, boolean userCreated) {
        Intent intent = new Intent(context, RoomWaitingActivity.class);
        intent.putExtra(ARG_ROOM_ID, roomId);
        intent.putExtra(ARG_USER_CREATED, userCreated);
        return intent;
    }

    @BindView(R.id.room_waiting_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.room_waiting_stats)
    TextView mStats;

    private FastAdapter<User, PersonHolder> mAdapter;
    private int mRoomId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_waiting);
        mRoomId = getIntent().getIntExtra(ARG_ROOM_ID, -1);
        mUserCreated = getIntent().getBooleanExtra(ARG_USER_CREATED, false);
        FirebaseUtils.registerToRoom(mRoomId);
        EventBus.getDefault().register(this);

        ButterKnife.bind(this);
        mAdapter = new FastAdapter<User, PersonHolder>() {
            @Override
            protected AbstractHolder createHolder(ViewGroup parent) {
                return new PersonHolder(parent);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Http.getInstance().getApiService().leaveRoom(mRoomId);
        super.onBackPressed();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ServerMessage event) {
        Timber.d("Got message: %s", event);
        if (mRoomId != event.mRoomId) {
            return;
        }
        if (event.mType == MessageType.ROOM_PLAYER_JOINED) {
            mStats.setText(mStats.getText() + "\n" + event.mUser.mName + " joined the room.");
            mAdapter.addItem(event.mUser);
        }
        if (event.mType == MessageType.ROOM_PLAYER_LEFT) {
            mStats.setText(mStats.getText() + "\n" + event.mUser.mName + " left the room.");
            mAdapter.removeItem(event.mUser);

        }
    }


}
