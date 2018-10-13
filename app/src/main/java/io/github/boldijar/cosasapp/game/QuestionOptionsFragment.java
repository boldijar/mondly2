package io.github.boldijar.cosasapp.game;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.boldijar.cosasapp.R;
import io.github.boldijar.cosasapp.base.FastAdapter;
import io.github.boldijar.cosasapp.data.Question;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class QuestionOptionsFragment extends BaseQuestionFragment {

    private static final String ARG_QUESTION = "question";

    public static QuestionOptionsFragment newInstance(Question question) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_QUESTION, question);
        QuestionOptionsFragment fragment = new QuestionOptionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.question_options_text)
    TextView mText;
    @BindView(R.id.question_options_list)
    RecyclerView mRecyclerView;

    private Question mQuestion;
    private FastAdapter<String, QuestionHolder> mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        loadArgs();
        loadUi();
    }

    private void loadUi() {
        mText.setText(mQuestion.mText);

        mAdapter = new FastAdapter<String, QuestionHolder>() {
            @Override
            protected AbstractHolder createHolder(ViewGroup parent) {
                return new QuestionHolder(parent);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.add(mQuestion.mOptions);
    }

    private void loadArgs() {
        mQuestion = getArguments().getParcelable(ARG_QUESTION);
    }

    public class QuestionHolder extends FastAdapter.AbstractHolder<String> {

        @BindView(R.id.option_divider)
        View mDivider;
        @BindView(R.id.option_text)
        TextView mText;

        QuestionHolder(ViewGroup parent) {
            super(parent, R.layout.item_option);
        }

        @Override
        public void bind(String item, int position) {
            mText.setText(item.toUpperCase());
            boolean lastItem = position == mAdapter.getItemCount() - 1;
            mDivider.setVisibility(lastItem ? View.GONE : View.VISIBLE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    answerQuestion(mQuestion, item);
                }
            });
        }
    }
}
