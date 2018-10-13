package io.github.boldijar.cosasapp.game;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import io.github.boldijar.cosasapp.data.Question;
import io.github.boldijar.cosasapp.data.QuestionType;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class GamePagerAdapter extends FragmentPagerAdapter {

    private final List<Question> mQuestions;

    public GamePagerAdapter(FragmentManager fm, List<Question> questions) {
        super(fm);
        mQuestions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        Question question = mQuestions.get(position);
        if (question.mQuestionType == QuestionType.OPTIONS) {
            return QuestionOptionsFragment.newInstance(question);
        }
        return QuestionNumberFragment.newInstance(question);
    }

    @Override
    public int getCount() {
        return mQuestions.size();
    }
}
