package io.github.boldijar.cosasapp.game;

import android.support.v4.app.Fragment;

import io.github.boldijar.cosasapp.data.Question;

/**
 * @author Paul
 * @since 2018.10.13
 */
public abstract class BaseQuestionFragment extends Fragment {

    private BaseQuestionListener mListener;

    public void setListener(BaseQuestionListener listener) {
        mListener = listener;
    }

    public void answerQuestion(Question question, String answer) {
        mListener.wannaAnswer(question, answer);
    }

    interface BaseQuestionListener {
        void wannaAnswer(Question question, String answer);
    }
}
