package com.chub.signinassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chub.signinassistant.R;
import com.chub.signinassistant.base.BaseActivity;
import com.chub.signinassistant.service.SignInService;

import static com.chub.signinassistant.util.DefaultUtil.isServiceWork;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.sign_in_log:

                break;
            case R.id.back:
                if (!isServiceWork(getApplicationContext(), SignInService.class.getName()))
                    startService(new Intent(this, SignInService.class));
                finish();
                break;
        }
    }
}
