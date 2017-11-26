package com.chub.signinassistant.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chub.signinassistant.R;
import com.chub.signinassistant.base.BaseActivity;
import com.chub.signinassistant.bean.ForgetEntity;
import com.chub.signinassistant.bean.ForgetResult;
import com.chub.signinassistant.bean.Response;
import com.chub.signinassistant.util.HttpUtil;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Request;

import static com.chub.signinassistant.util.Config.URL_GET_CONFIRM_CODE;
import static com.chub.signinassistant.util.Config.URL_RESET_PW;
import static com.chub.signinassistant.util.DefaultUtil.MD5;

/**
 * Description：重置密码
 * Created by Chub on 2017/11/25.
 */

public class ResetPWActivity extends BaseActivity {

    private static final int MSG_WHAT = 0x12;
    private static final int MAX_TIME = 120;

    private AutoCompleteTextView mAccountView;
    private EditText mCodeView, mPasswordView, mPasswordView2;
    private View mProgressView;

    private View mLoginFormView;
    private Button getCode;

    private boolean isRequest;

    private ForgetEntity forgetEntity;

    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                switch (v.getId()) {
                    case R.id.email:
                        mCodeView.requestFocus();
                        break;
                    case R.id.editCode:
                        mPasswordView.requestFocus();
                        break;
                    case R.id.password:
                        mPasswordView2.requestFocus();
                        break;
                    case R.id.password2:
                        attemptLogin();
                        break;
                }
                return true;
            }
            return false;
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_pw;
    }

    @Override
    protected void init() {
        mProgressView = findViewById(R.id.login_progress);
        mLoginFormView = findViewById(R.id.login_form);
        mAccountView = findViewById(R.id.email);
        mCodeView = findViewById(R.id.editCode);
        mPasswordView = findViewById(R.id.password);
        mPasswordView2 = findViewById(R.id.password2);
        getCode = findViewById(R.id.getCode);

        mAccountView.setOnEditorActionListener(onEditorActionListener);
        mCodeView.setOnEditorActionListener(onEditorActionListener);
        mPasswordView.setOnEditorActionListener(onEditorActionListener);
        mPasswordView2.setOnEditorActionListener(onEditorActionListener);
    }


    private void attemptLogin() {
        if (isRequest) return;

        mAccountView.setError(null);
        mCodeView.setError(null);
        mPasswordView.setError(null);
        mPasswordView2.setError(null);

        String account = mAccountView.getText().toString();
        String code = mCodeView.getText().toString();
        String pw1 = mPasswordView.getText().toString();
        String pw2 = mPasswordView2.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(account)) {
            mAccountView.setError(getString(R.string.error_field_required));
            focusView = mAccountView;
            cancel = true;
        }
        if (TextUtils.isEmpty(code)) {
            mCodeView.setError(getString(R.string.code_null));
            focusView = mCodeView;
            cancel = true;
        }
        if (forgetEntity == null || !TextUtils.equals(code, forgetEntity.getIdentify())) {
            mCodeView.setError(getString(R.string.code_error));
            focusView = mCodeView;
            cancel = true;
        }

        if (TextUtils.isEmpty(pw1)) {
            mPasswordView.setError(getString(R.string.input_pw));
            focusView = mPasswordView;
            cancel = true;
        }
        if (!TextUtils.isEmpty(pw1) && !isPasswordValid(pw1)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(pw2)) {
            mPasswordView2.setError(getString(R.string.input_pw2));
            focusView = mPasswordView2;
            cancel = true;
        }
        if (pw1.length() != pw2.length()) {
            mPasswordView2.setError(getString(R.string.length_no_equal));
            focusView = mPasswordView2;
            cancel = true;
        }
        if (!TextUtils.equals(pw1, pw2)) {
            mPasswordView2.setError(getString(R.string.password_no_equals));
            focusView = mPasswordView2;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            resetPw(pw2);
        }

    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    public void ClickBt(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.getCode:
                getCodeMessage();
                break;
            case R.id.login:
                attemptLogin();
                break;
        }
    }

    private MyHandler myHandler;
    private Timer mTimer;
    private int mTime = MAX_TIME;

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            myHandler.sendEmptyMessage(MSG_WHAT);
        }
    };

    private void getCodeMessage() {
        String phone = mAccountView.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            mAccountView.setError(getString(R.string.error_field_required));
            mAccountView.requestFocus();
            return;
        }
        if (phone.length() != 11 || !phone.startsWith("1")) {
            mAccountView.setError(getString(R.string.phong_leng_error));
            mAccountView.requestFocus();
            return;
        }
        mTimer = new Timer();
        if (myHandler == null)
            myHandler = new MyHandler(this);

        getCode.setEnabled(false);
        mTimer.schedule(timerTask, 0, 1000);

        HttpUtil.get(URL_GET_CONFIRM_CODE + phone, ForgetResult.class, new HttpUtil.OnRequestCallBack<ForgetResult>() {
            @Override
            public void onRequestSuccess(ForgetResult response) {
                if (response.getCode() == 200 || response.getCode() == 929) {
                    forgetEntity = response.getData();
                } else {
                    showSnackBar(R.string.get_code_error);
                }
            }

            @Override
            public void onRequestFailure(Request request, Exception error) {
                showSnackBar(R.string.service_error);
                reset();
            }
        });
    }

    private void reset() {
        if (mTimer != null)
            mTimer.cancel();

        getCode.setText(R.string.re_get);
        mTime = MAX_TIME;
        getCode.setEnabled(true);
    }


    private void resetPw(String pw2) {
        hideInputKeyBoard();
        showProgress(true);
        isRequest = true;
        Map<String, String> params = new HashMap<>();
        params.put("uid", forgetEntity.getUid());
        params.put("pw", MD5(pw2));
        HttpUtil.post(URL_RESET_PW, params, Response.class, new HttpUtil.OnRequestCallBack<Response>() {
            @Override
            public void onRequestSuccess(Response response) {
                showProgress(false);
                if (response.getCode() == 200) {
                    showConfirmDialog(getString(R.string.reset_pw_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                } else {
                    showSnackBar(R.string.reset_pw_error);
                }
            }

            @Override
            public void onRequestFailure(Request request, Exception error) {
                showProgress(false);
                showSnackBar(R.string.service_error);
            }
        });
    }

    private static class MyHandler extends Handler {

        private WeakReference<Activity> mActivity;

        MyHandler(ResetPWActivity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ResetPWActivity pwActivity = (ResetPWActivity) mActivity.get();
            int time = pwActivity.mTime--;
            if (time == 0) {
                pwActivity.reset();
            } else {
                pwActivity.getCode.setText(String.valueOf(time));
            }
        }
    }
}
