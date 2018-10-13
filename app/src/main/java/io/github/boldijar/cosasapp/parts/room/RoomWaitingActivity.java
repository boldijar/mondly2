package io.github.boldijar.cosasapp.parts.room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.boldijar.cosasapp.R;
import io.github.boldijar.cosasapp.base.BaseActivity;
import io.github.boldijar.cosasapp.base.FastAdapter;
import io.github.boldijar.cosasapp.base.Tulbar;
import io.github.boldijar.cosasapp.data.BaseResponse;
import io.github.boldijar.cosasapp.data.MessageType;
import io.github.boldijar.cosasapp.data.Room;
import io.github.boldijar.cosasapp.data.ServerMessage;
import io.github.boldijar.cosasapp.data.User;
import io.github.boldijar.cosasapp.game.GameActivity;
import io.github.boldijar.cosasapp.server.Http;
import io.github.boldijar.cosasapp.util.FirebaseUtils;
import io.github.boldijar.cosasapp.util.Observatorul;
import io.github.boldijar.cosasapp.util.Prefs;
import io.github.boldijar.cosasapp.util.RxUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class RoomWaitingActivity extends BaseActivity {

    private static final String ARG_ROOM_ID = "roomid";
    private static final String ARG_USER_CREATED = "user_created";
    private static final String ARG_ROOM = "room";
    private boolean mUserCreated;

    public static Intent createIntent(Context context, int roomId, boolean userCreated) {
        Intent intent = new Intent(context, RoomWaitingActivity.class);
        intent.putExtra(ARG_ROOM_ID, roomId);
        intent.putExtra(ARG_USER_CREATED, userCreated);
        return intent;
    }

    public static Intent createIntent(Context context, int roomId, boolean userCreated, Room room) {
        Intent intent = new Intent(context, RoomWaitingActivity.class);
        intent.putExtra(ARG_ROOM_ID, roomId);
        intent.putExtra(ARG_USER_CREATED, userCreated);
        intent.putExtra(ARG_ROOM, room);
        return intent;
    }

    @BindView(R.id.room_waiting_input)
    EditText mInput;
    @BindView(R.id.room_waiting_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.room_waiting_stats)
    TextView mStats;
    @BindView(R.id.room_waiting_toolbar)
    Tulbar mTulbar;

    private FastAdapter<User, PersonHolder> mAdapter;
    private int mRoomId;
    private Room mRoom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_waiting);
        mRoomId = getIntent().getIntExtra(ARG_ROOM_ID, -1);
        mUserCreated = getIntent().getBooleanExtra(ARG_USER_CREATED, false);
        try {
            mRoom = getIntent().getParcelableExtra(ARG_ROOM);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (mUserCreated) {
            mStats.setText("You created this room.");
        } else {
            if (mRoom != null && mRoom.mPlayers != null && mRoom.mPlayers.size() != 0) {
                mStats.setText("You just joined this room created by " + mRoom.mPlayers.get(0).mName + ".");
                mAdapter.add(mRoom.mPlayers);
            }

        }
        mTulbar.enableCustomIcon(mUserCreated);
        mTulbar.setCustomIconClickListener(view -> {
            if (mAdapter.getItemCount() == 0) {
                Toast.makeText(RoomWaitingActivity.this, "You can't play alone!", Toast.LENGTH_SHORT).show();
            } else {
                startGame();
            }
        });
    }

    private void startGame() {
        Http.getInstance().getApiService().startGame(mRoomId)
                .compose(RxUtils.applySchedulers())
                .subscribe(new Observatorul<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                        } else {
                            Toast.makeText(RoomWaitingActivity.this, "Start game error.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Toast.makeText(RoomWaitingActivity.this, "Start game error.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Http.getInstance().getApiService().leaveRoom(mRoomId).subscribe(new Observatorul<>());
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
        if (event.mType == MessageType.ROOM_GAME_START) {
            finish();
            event.mGame.mRoomId = mRoomId;
            startActivity(GameActivity.createIntent(this, event.mGame));
        }
        if (event.mType == MessageType.ROOM_CHAT) {
            mStats.setText(mStats.getText() + "\n" + event.mMessage);
        }
    }

    @OnClick(R.id.room_waiting_send)
    void sendMessage() {
        String message = Prefs.getUser().mName + " says: " + mInput.getText().toString();
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n  \"to\": \"/topics/room_" + mRoomId + "\",\n  \"data\": {\n    \"message\": {\n    \t\"room_id\":" + mRoomId + ",\n    \t\"type\":\"room_chat\",\n    \t\"message\":\"" + message + "\"\n    }\n   }\n}");
            Request request = new Request.Builder()
                    .url("https://fcm.googleapis.com/fcm/send")
                    .post(body)
                    .addHeader("Authorization", "key=AAAAhHqnJuY:APA91bFXguBc-o75-aHEktULgLKKVjeSNtFZ0-fWrei-CSPSdmXoNaBx1IvirUNUkvuiDNczEzJYNqUqhyuD3EgAV0Ov1EnfDv7m8QD6E4dbQXMjiknEPZhSpMMJh6iZeXNeBVumZrb-")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "cc86ab08-866f-4b07-ba05-27977a9adb37")
                    .build();

            Response response = client.newCall(request).execute();
        }).compose(RxUtils.applySchedulers())
                .subscribe();
        mInput.setText(null);
    }


}
