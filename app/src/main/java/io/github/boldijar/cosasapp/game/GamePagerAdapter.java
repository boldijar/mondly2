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
    private final BaseQuestionFragment.BaseQuestionListener mBaseQuestionListener;

    public GamePagerAdapter(FragmentManager fm, List<Question> questions, BaseQuestionFragment.BaseQuestionListener baseQuestionListener) {
        super(fm);
        mQuestions = questions;
        mBaseQuestionListener = baseQuestionListener;
    }

    @Override
    public Fragment getItem(int position) {
        Question question = mQuestions.get(position);
        BaseQuestionFragment fragment;
        if (question.mQuestionType == QuestionType.OPTIONS) {
            fragment = QuestionOptionsFragment.newInstance(question);
        } else {
            fragment = QuestionNumberFragment.newInstance(question);
        }
        fragment.setListener(mBaseQuestionListener);
        return fragment;
    }

    @Override
    public int getCount() {
        return mQuestions.size();
    }
}
