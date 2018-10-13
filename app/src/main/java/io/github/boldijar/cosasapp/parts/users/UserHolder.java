package io.github.boldijar.cosasapp.parts.users;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import io.github.boldijar.cosasapp.R;
import io.github.boldijar.cosasapp.base.FastAdapter;
import io.github.boldijar.cosasapp.data.User;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class UserHolder extends FastAdapter.AbstractHolder<User> {

    @BindView(R.id.item_user_image)
    ImageView mImage;
    @BindView(R.id.item_user_text)
    TextView mText;

    public UserHolder(ViewGroup parent, int layoutId) {
        super(parent, layoutId);
    }

    @Override
    public void bind(User item) {
        Glide.with(mImage.getContext()).load(item.mImage).into(mImage);
        mText.setText(item.mName);
    }
}
