package io.github.boldijar.cosasapp.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.boldijar.cosasapp.R;
import io.github.boldijar.cosasapp.base.BaseActivity;
import io.github.boldijar.cosasapp.base.PersonProgress;
import io.github.boldijar.cosasapp.data.Room;
import io.github.boldijar.cosasapp.data.User;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class GameActivity extends BaseActivity {

    private static final String ARG_ROOM = "room";

    @BindView(R.id.game_persons_scroll)
    LinearLayout mLinearLayout;
    private Room mRoom;
    private HashMap<User, PersonProgress> mPeopleMap = new HashMap<>();

    public static Intent createIntent(Context context, Room room) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(ARG_ROOM, room);
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
        for (User user : mRoom.mPlayers) {
            PersonProgress personProgress = new PersonProgress(this);
            personProgress.loadImage(user.mImage);
            personProgress.setProgress(30);
            mLinearLayout.addView(personProgress);
            mPeopleMap.put(user, personProgress);
        }
    }

    private void loadArgs() {
//        try {
//            mRoom = getIntent().getParcelableExtra(ARG_ROOM);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        mRoom = new Gson().fromJson("{\"players\":[{\"id\":7,\"name\":\"Alex Bajzat\",\"email\":\"alex.bajzat@gmail.com\",\"email_verified_at\":null,\"image\":\"https://i.snag.gy/j0wZTl.jpg\",\"state\":\"not-playing\",\"created_at\":null,\"updated_at\":null},{\"id\":10,\"name\":\"Alexandru Popa\",\"email\":\"alexandru.popa@gmail.com\",\"email_verified_at\":null,\"image\":\"https://i.snag.gy/G2smJd.jpg\",\"state\":\"not-playing\",\"created_at\":null,\"updated_at\":\"2018-10-13 09:18:45\"},{\"id\":1,\"name\":\"Andrei Matea\",\"email\":\"andrei.matea@gmail.com\",\"email_verified_at\":null,\"image\":\"https://i.snag.gy/BP0E5u.jpg\",\"state\":\"not-playing\",\"created_at\":null,\"updated_at\":\"2018-10-13 07:47:13\"}],\"id\":123}", Room.class);

    }
}
