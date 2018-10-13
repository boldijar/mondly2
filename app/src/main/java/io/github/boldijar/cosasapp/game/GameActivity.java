package io.github.boldijar.cosasapp.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.boldijar.cosasapp.R;
import io.github.boldijar.cosasapp.base.BaseActivity;
import io.github.boldijar.cosasapp.base.PersonProgress;
import io.github.boldijar.cosasapp.data.Question;
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
    @BindView(R.id.game_pager)
    ViewPager mViewPager;

    private Room mRoom;
    private List<Question> mQuestions;
    private HashMap<User, PersonProgress> mPeopleMap = new HashMap<>();

    private GamePagerAdapter mGamePagerAdapter;

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
        mGamePagerAdapter = new GamePagerAdapter(getSupportFragmentManager(), mQuestions);
        mViewPager.setAdapter(mGamePagerAdapter);
    }

    private void loadArgs() {
//        try {
//            mRoom = getIntent().getParcelableExtra(ARG_ROOM);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        String questionsJson = "[{\"id\":1,\"type\":\"multiple\",\"text\":\"What means FTP?\",\"correct_answer\":\"File Transfer Protocol\",\"possible_answers\":[\"File Transfer Protocol\",\"File Tunnel Protocol\",\"Financial Transfer Protocol\",\"None of the answers\"],\"max_score\":20,\"created_at\":null,\"updated_at\":null},{\"id\":2,\"type\":\"multiple\",\"text\":\"Who write the \\\"Moby-Dick\\\" book?\",\"correct_answer\":\"Herman Melville\",\"possible_answers\":[\"Ion Iliescu\",\"David Copefield\",\"Lucian Blaga\",\"Herman Melville\"],\"max_score\":50,\"created_at\":null,\"updated_at\":null},{\"id\":3,\"type\":\"multiple\",\"text\":\"From whom were the American Virgin Islands bought?\",\"correct_answer\":\"Denmark\",\"possible_answers\":[\"UK\",\"Finland\",\"Denmark\",\"France\"],\"max_score\":40,\"created_at\":null,\"updated_at\":null},{\"id\":4,\"type\":\"fix\",\"text\":\"In what year was the first airline flight made in Europe?\",\"correct_answer\":\"1911\",\"possible_answers\":[\"1902\",\"1918\",\"1945\",\"1911\"],\"max_score\":60,\"created_at\":null,\"updated_at\":null},{\"id\":5,\"type\":\"fix\",\"text\":\"In what year was the death penalty, for spying and sabotage, reintroduced in the USSR\",\"correct_answer\":\"1950\",\"possible_answers\":[\"1950\",\"1933\",\"1945\",\"1890\"],\"max_score\":60,\"created_at\":null,\"updated_at\":null},{\"id\":6,\"type\":\"multiple\",\"text\":\"Where was the world's first public underground transport line opened (1863)?\",\"correct_answer\":\"London\",\"possible_answers\":[\"New York\",\"London\",\"Berlin\",\"Paris\"],\"max_score\":90,\"created_at\":null,\"updated_at\":null},{\"id\":7,\"type\":\"multiple\",\"text\":\"Who was crowned in 1547 as a Russian tsar?\",\"correct_answer\":\"Ivan the Terrible\",\"possible_answers\":[\"Stalin\",\"Vasiliovici IV\",\"Ivan the Great\",\"Ivan the Terrible\"],\"max_score\":120,\"created_at\":null,\"updated_at\":null},{\"id\":8,\"type\":\"fix\",\"text\":\"In what year was \\\"The British Museum\\\" opened?\",\"correct_answer\":\"1759\",\"possible_answers\":[\"1759\",\"1795 IV\",\"1823\",\"1888\"],\"max_score\":120,\"created_at\":null,\"updated_at\":null}]";
        Type listType = new TypeToken<ArrayList<Question>>() {
        }.getType();
        mQuestions = new Gson().fromJson(questionsJson, listType);
        mRoom = new Gson().fromJson("{\"players\":[{\"id\":7,\"name\":\"Alex Bajzat\",\"email\":\"alex.bajzat@gmail.com\",\"email_verified_at\":null,\"image\":\"https://i.snag.gy/j0wZTl.jpg\",\"state\":\"not-playing\",\"created_at\":null,\"updated_at\":null},{\"id\":10,\"name\":\"Alexandru Popa\",\"email\":\"alexandru.popa@gmail.com\",\"email_verified_at\":null,\"image\":\"https://i.snag.gy/G2smJd.jpg\",\"state\":\"not-playing\",\"created_at\":null,\"updated_at\":\"2018-10-13 09:18:45\"},{\"id\":1,\"name\":\"Andrei Matea\",\"email\":\"andrei.matea@gmail.com\",\"email_verified_at\":null,\"image\":\"https://i.snag.gy/BP0E5u.jpg\",\"state\":\"not-playing\",\"created_at\":null,\"updated_at\":\"2018-10-13 07:47:13\"}],\"id\":123}", Room.class);

    }
}
