package com.chub.signinassistant.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chub.signinassistant.R;
import com.chub.signinassistant.base.BaseActivity;
import com.chub.signinassistant.bean.LoginEntity;
import com.chub.signinassistant.bean.LoginResult;
import com.chub.signinassistant.util.HttpUtil;
import com.chub.signinassistant.util.UserHelp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Request;

import static android.Manifest.permission.READ_CONTACTS;
import static com.chub.signinassistant.util.Config.URL_LOGIN;
import static com.chub.signinassistant.util.DefaultUtil.MD5;

/**
 * 登录账号并添加到本地
 */
public class LoginActivity extends BaseActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private static final int REQUEST_RESET_PW_CODE = 0x16;

    // UI references.
    private AutoCompleteTextView mAccountView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private boolean isRequest;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
//        populateAutoComplete();

        findViewById(R.id.forget_pw).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hideInputKeyBoard();
                startActivityForResult(new Intent(LoginActivity.this, ResetPWActivity.class), REQUEST_RESET_PW_CODE);
            }
        });

        mAccountView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);

        mAccountView.setOnEditorActionListener(onEditorActionListener);
        mPasswordView.setOnEditorActionListener(onEditorActionListener);

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                if (textView.getId() == R.id.email) {
                    mPasswordView.requestFocus();
                } else {
                    attemptLogin();
                }
                return true;
            }
            return false;
        }
    };

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mAccountView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    }).show();
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (isRequest) {
            return;
        }

        // Reset errors.
        mAccountView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String userName = mAccountView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(userName)) {
            mAccountView.setError(getString(R.string.error_field_required));
            focusView = mAccountView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            hideInputKeyBoard();
            showProgress(true);
            isRequest = true;
            Map<String, String> params = new HashMap<>();
            params.put("uname", "0086" + userName);
            params.put("pw", MD5(password));
            params.put("userFlg", "1");
            HttpUtil.post(URL_LOGIN, params, LoginResult.class, new HttpUtil.OnRequestCallBack<LoginResult>() {
                @Override
                public void onRequestSuccess(LoginResult response) {
                    showProgress(false);
                    if (response.getCode() == 903) {
                        mPasswordView.setError(getString(R.string.error_incorrect_password));
                        mPasswordView.setText("");
                        mPasswordView.requestFocus();
                        showInputKeyBoard(mPasswordView);
                    } else if (response.getCode() == 600) {
                        mAccountView.setError(getString(R.string.no_account));
                        mPasswordView.setText("");
                        mAccountView.setText("");
                        mAccountView.requestFocus();
                        showInputKeyBoard(mAccountView);
                    } else if (response.getCode() == 200) {
                        List<LoginEntity> data = response.getData();
                        if (data != null && !data.isEmpty()) {
                            LoginEntity entity = data.get(0);
                            entity.setLogTime(String.valueOf(System.currentTimeMillis()));
                            UserHelp.getInstance(getApplicationContext()).insert(entity);
                        }
                        showSnackBar(R.string.log_in_ok, R.string.back, new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                    }
                    isRequest = false;
                }

                @Override
                public void onRequestFailure(Request request, Exception error) {
                    showProgress(false);
                    showSnackBar(R.string.service_error);
                    isRequest = false;
                }
            });
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
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

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                ContactsContract.Contacts.CONTENT_URI,
//                new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts._ID},
                null,
                null,
                null,
                null);
//        return new CursorLoader(this,
//                ContactsContract.Contacts.CONTENT_URI,
//                new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts._ID},
//                null,
//                null,
//                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> phone = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
//            phone.add(cursor.getString(0));
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < cursor.getColumnCount(); j++) {
                builder.append(cursor.getColumnName(j))
                        .append("=")
                        .append(cursor.getString(j))
                        .append(";      ");
            }
            phone.add(builder.toString());
            cursor.moveToNext();
        }
        Log.d("LoginActivity", "cursor.getCount():" + cursor.getCount());
        for (String value : phone) {
            Log.e("LoginActivity", value);
        }
//        Log.e("LoginActivity", "i:" + i);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addUsersToAutoComplete(Set<String> users) {
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<>(LoginActivity.this,
//                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RESET_PW_CODE && resultCode == RESULT_OK) {
            mAccountView.requestFocus();
        }
    }
}

