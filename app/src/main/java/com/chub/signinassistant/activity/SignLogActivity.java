package com.chub.signinassistant.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chub.signinassistant.R;
import com.chub.signinassistant.adapter.UserSignAdapter;
import com.chub.signinassistant.base.BaseActivity;
import com.chub.signinassistant.bean.SignInLogBean;
import com.chub.signinassistant.interfaces.OnDeleteClickListener;
import com.chub.signinassistant.util.SignHelp;

import java.util.List;

/**
 * Description：
 * Created by Chub on 2017/11/26.
 */

public class SignLogActivity extends BaseActivity implements AdapterView.OnItemClickListener, OnDeleteClickListener<SignInLogBean> {

    private ListView lv;
    private UserSignAdapter adapter;
    private List<SignInLogBean> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_log;
    }

    @Override
    protected void init() {
        lv = findViewById(R.id.lv);
        list = SignHelp.getInstance(getApplicationContext()).findAllLog();
        adapter = new UserSignAdapter(list);
        adapter.setOnDeleteClickListener(this);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        if (list == null || list.isEmpty())
            showConfirmDialog(getString(R.string.no_data), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (lv != null) {
            lv.setAdapter(null);
            lv = null;
        }
        if (adapter != null) {
            adapter.setOnDeleteClickListener(null);
            adapter = null;
        }
        if (list != null) {
            list.clear();
            list = null;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object item = parent.getAdapter().getItem(position);
        if (item instanceof SignInLogBean) {
            SignInLogBean bean = (SignInLogBean) item;
            showSnackBar(bean.getNickName() + ":" + bean.getPhone());
        }
    }

    @Override
    public void onClick(int position, final SignInLogBean data) {
        showAlertDialog(getString(R.string.confirm_delete_data), false, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.remove(data);
                adapter.notifyDataSetChanged();
                SignHelp.getInstance(getApplicationContext()).deleteById(data.getId());
            }
        });
    }
}
