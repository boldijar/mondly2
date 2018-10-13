package io.github.boldijar.cosasapp.parts.room;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import io.github.boldijar.cosasapp.R;
import io.github.boldijar.cosasapp.base.FastAdapter;
import io.github.boldijar.cosasapp.data.User;

/**
 * TODO: Class description
 *
 * @author Paul
 * @since 2018.10.13
 */
public class PersonHolder extends FastAdapter.AbstractHolder<User> {

    @BindView(R.id.person_image)
    ImageView mImage;

    public PersonHolder(ViewGroup parent) {
        super(parent, R.layout.item_person);
    }

    @Override
    public void bind(User item) {
        Glide.with(itemView.getContext()).load(item.mImage).into(mImage);
    }
}
