package io.github.boldijar.cosasapp.parts.users;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import io.github.boldijar.cosasapp.R;
import io.github.boldijar.cosasapp.base.BaseBottomSheet;
import io.github.boldijar.cosasapp.base.FastAdapter;
import io.github.boldijar.cosasapp.data.User;
import io.github.boldijar.cosasapp.data.UsersResponse;
import io.github.boldijar.cosasapp.server.Http;
import io.github.boldijar.cosasapp.util.Observatorul;
import io.github.boldijar.cosasapp.util.ObservatorulNormal;
import io.github.boldijar.cosasapp.util.Prefs;
import io.github.boldijar.cosasapp.util.RxUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * TODO: Class description
 *
 * @author Paul
 * @since 2018.10.13
 */
public class UsersBottomSheet extends BaseBottomSheet {

    @BindView(R.id.users_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.users_input)
    EditText mInput;
    private FastAdapter<User, UserHolder> mAdapter;
    private Disposable mLastRequest;

    @Override
    protected int getLayoutId() {
        return R.layout.sheet_users;
    }


    @OnTextChanged(R.id.users_input)
    protected void onTextChanged(CharSequence text) {
        loadUsers();
    }

    private void loadUsers() {
        clearLastRequest();
        Http.getInstance().getApiService().getUsers(mInput.getText().toString())
                .compose(RxUtils.applySchedulers())
                .subscribe(new Observatorul<UsersResponse>() {
                    @Override
                    public void onNext(UsersResponse usersResponse) {
                        mAdapter.clear();
                        mAdapter.add(usersResponse.mUsers);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        mLastRequest = d;
                    }
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new FastAdapter<User, UserHolder>() {
            @Override
            protected AbstractHolder createHolder(ViewGroup parent) {
                return new UserHolder(parent, R.layout.item_user);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        loadUsers();

    }

    @Override
    public void onDestroyView() {
        clearLastRequest();
        super.onDestroyView();
    }

    private void clearLastRequest() {
        if (mLastRequest != null && !mLastRequest.isDisposed()) {
            mLastRequest.dispose();
        }
    }

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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    challangeUser(item);
                }
            });

        }
    }

    private void challangeUser(User item) {
        Toast.makeText(getActivity(), "User got a flash challenge! Create a room for him.", Toast.LENGTH_SHORT).show();
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            OkHttpClient client = new OkHttpClient();
            int userId = item.mId;
            MediaType mediaType = MediaType.parse("application/json");
            String name = Prefs.getUser().mName;
            RequestBody body = RequestBody.create(mediaType, "{\n  \"to\": \"/topics/" + userId + "\",\n  \"data\": {\n    \"message\": {\n     \n    \t\"type\":\"challenge\",\n    \t\"challenged_by_name\":\"" + name + "\",\n    \t\"who_was_challenged\":" + userId + ",\n    \t\"question_answered_count\":3\n    }\n   }\n}");
            Request request = new Request.Builder()
                    .url("https://fcm.googleapis.com/fcm/send")
                    .post(body)
                    .addHeader("Authorization", "key=AAAAhHqnJuY:APA91bFXguBc-o75-aHEktULgLKKVjeSNtFZ0-fWrei-CSPSdmXoNaBx1IvirUNUkvuiDNczEzJYNqUqhyuD3EgAV0Ov1EnfDv7m8QD6E4dbQXMjiknEPZhSpMMJh6iZeXNeBVumZrb-")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "549a921a-4f34-4ac6-94cd-b286d54b9032")
                    .build();

            Response response = client.newCall(request).execute();
        })
                .compose(RxUtils.applySchedulers())
                .subscribe(new ObservatorulNormal<>());
        dismiss();
    }
}
