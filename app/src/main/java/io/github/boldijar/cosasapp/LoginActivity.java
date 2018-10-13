package io.github.boldijar.cosasapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.boldijar.cosasapp.base.BaseActivity;
import io.github.boldijar.cosasapp.data.LoginBody;
import io.github.boldijar.cosasapp.data.LoginResponse;
import io.github.boldijar.cosasapp.parts.home.HomeActivity;
import io.github.boldijar.cosasapp.server.Http;
import io.github.boldijar.cosasapp.util.Observatorul;
import io.github.boldijar.cosasapp.util.Prefs;
import io.github.boldijar.cosasapp.util.RxUtils;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_email)
    EditText mEmail;
    @BindView(R.id.login_password)
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Prefs.Token.get() != null) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
            return;
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_continue)
    void continueLogin() {
        Http.getInstance().getApiService().login(new LoginBody(mPassword.getText().toString(), mEmail.getText().toString()))
                .compose(RxUtils.applySchedulers())
                .subscribe(new Observatorul<LoginResponse>() {
                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        if (loginResponse.isSuccess()) {
                            Prefs.Token.put(loginResponse.mUser.mToken);
                            Prefs.User.putAsJson(loginResponse.mUser);
                            finish();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        }
                    }
                });

    }
}
