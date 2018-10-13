package io.github.boldijar.cosasapp.base;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.boldijar.cosasapp.R;

/**
 * @author Paul
 * @since 2018.09.28
 */
public class PersonProgress extends FrameLayout {

    @BindView(R.id.player_progress_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_image)
    ImageView mImage;

    public PersonProgress(@NonNull Context context) {
        super(context);
        init(null);
    }

    public PersonProgress(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PersonProgress(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.view_player_progress, this);
        ButterKnife.bind(this);

    }

    @SuppressLint("SetTextI18n")
    public void setProgress(int progress) {
        ObjectAnimator animation = ObjectAnimator.ofInt(mProgressBar, "progress", progress);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }
}
