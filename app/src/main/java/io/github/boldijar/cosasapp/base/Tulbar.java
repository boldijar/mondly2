package io.github.boldijar.cosasapp.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.boldijar.cosasapp.R;

/**
 * @author Paul
 * @since 2018.09.27
 */
public class Tulbar extends ConstraintLayout {

    @BindView(R.id.tulbar_text)
    TextView mText;
    @BindView(R.id.tulbar_back_icon)
    ImageView mBack;
    @BindView(R.id.tulbar_custom_icon)
    ImageView mCustomIcon;

    public Tulbar(Context context) {
        super(context);
        init();
    }

    public Tulbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithAttrs(attrs);
    }

    public Tulbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWithAttrs(attrs);
    }

    public void setText(String text) {
        mText.setText(text);
    }

    private void initWithAttrs(AttributeSet attrs) {
        init();
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Tulbar,
                0, 0);
        try {
            CharSequence text = typedArray.getText(R.styleable.Tulbar_text);
            mText.setText(text);
            int color = typedArray.getColor(R.styleable.Tulbar_color, Color.WHITE);
            setBackgroundColor(color);
            boolean customIconEnabled = typedArray.getBoolean(R.styleable.Tulbar_customIconEnabled, false);
            if (customIconEnabled) {
                mCustomIcon.setVisibility(VISIBLE);
                Drawable drawable = typedArray.getDrawable(R.styleable.Tulbar_customIconDrawable);
                mCustomIcon.setImageDrawable(drawable);
            }
        } finally {
            typedArray.recycle();
        }
    }

    public void setCustomIconClickListener(OnClickListener listener) {
        mCustomIcon.setOnClickListener(listener);
    }

    private void init() {
        inflate(getContext(), R.layout.view_tulbar, this);
        ButterKnife.bind(this);
        findViewById(R.id.tulbar_back_icon).setOnClickListener(view -> {
            if (getContext() instanceof Activity) {
                Activity activity = (Activity) getContext();
                activity.onBackPressed();
            }
        });
    }

    public void setCustomIcon(@DrawableRes int res) {
        mCustomIcon.setImageResource(res);
    }
}
