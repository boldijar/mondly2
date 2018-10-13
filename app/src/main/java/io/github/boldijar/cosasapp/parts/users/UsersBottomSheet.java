package io.github.boldijar.cosasapp.parts.users;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import io.github.boldijar.cosasapp.util.RxUtils;
import io.reactivex.disposables.Disposable;

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
}
