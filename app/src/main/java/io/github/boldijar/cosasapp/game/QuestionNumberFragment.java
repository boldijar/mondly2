package io.github.boldijar.cosasapp.game;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.boldijar.cosasapp.R;
import io.github.boldijar.cosasapp.data.Question;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class QuestionNumberFragment extends BaseQuestionFragment {
    private static final String ARG_QUESTION = "question";

    public static QuestionNumberFragment newInstance(Question question) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_QUESTION, question);

        QuestionNumberFragment fragment = new QuestionNumberFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.question_numbers_text)
    TextView mText;
    @BindView(R.id.question_numbers_value)
    TextView mValue;
    private Question mQuestion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question_numbers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        loadArgs();
        loadUi();
    }

    private void loadUi() {
        mText.setText(mQuestion.mText);
        mValue.setText(getCurrentNumber() + "");
    }

    private void loadArgs() {
        mQuestion = getArguments().getParcelable(ARG_QUESTION);
    }

    @OnClick({
            R.id.question_numbers_1, R.id.question_numbers_4, R.id.question_numbers_7,
            R.id.question_numbers_2, R.id.question_numbers_5, R.id.question_numbers_8,
            R.id.question_numbers_3, R.id.question_numbers_6, R.id.question_numbers_9,
            R.id.question_numbers_0
    })
    void clickedNumber(View view) {
        if (!(view instanceof TextView)) {
            return;
        }
        TextView textView = (TextView) view;
        int currentNumber = getCurrentNumber();
        if (currentNumber > 999999) {
            return;
        }
        int newDigit = Integer.parseInt(textView.getText().toString());
        int newNumber = currentNumber * 10 + newDigit;
        mValue.setText(newNumber + "");
    }

    @OnClick(R.id.question_numbers_erase)
    void eraseLastDigit() {
        if (getCurrentNumber() == 0) {
            return;
        }
        int newNumber = getCurrentNumber() / 10;
        mValue.setText(newNumber + "");
    }

    @OnClick(R.id.question_numbers_continue)
    void sendAnswer() {
        answerQuestion(mQuestion, mValue.getText().toString());
    }

    private int getCurrentNumber() {
        try {
            return Integer.parseInt(mValue.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
