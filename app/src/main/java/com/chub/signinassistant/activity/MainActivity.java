package com.chub.signinassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.chub.signinassistant.R;
import com.chub.signinassistant.bean.ForgetEntity;
import com.chub.signinassistant.bean.ForgetResult;
import com.chub.signinassistant.bean.LoginEntity;
import com.chub.signinassistant.bean.LoginResult;
import com.chub.signinassistant.service.SignInService;
import com.chub.signinassistant.util.DefaultUtil;
import com.chub.signinassistant.util.SPUtils;
import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import static com.chub.signinassistant.util.Config.KEY_ID;
import static com.chub.signinassistant.util.DefaultUtil.MD5;
import static com.chub.signinassistant.util.DefaultUtil.isServiceWork;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                if (!isServiceWork(getApplicationContext(), SignInService.class.getName()))
                    startService(new Intent(this, SignInService.class));
                finish();
                break;
            case R.id.sign_in_log:

                break;
            case R.id.login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
