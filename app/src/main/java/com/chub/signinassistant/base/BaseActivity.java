package com.chub.signinassistant.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.chub.signinassistant.R;

/**
 * Description：Activity 基类
 * Created by Chub on 2017/11/25.
 */
public abstract class BaseActivity extends AppCompatActivity {

    static{
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private Snackbar snackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        init();
    }

    /**
     * Gets layout id.
     *
     * @return 获取界面布局 layout id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化
     */
    protected abstract void init();

    /**
     * 打开键盘
     *
     * @param editText the edit text
     */
    protected void showInputKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 隐藏键盘
     */
    protected void hideInputKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * Show alert dialog.
     *
     * @param title          the title
     * @param msg            the msg
     * @param negativeButton the negative button
     * @param positiveButton the positive button
     * @param showNaviBt     the show navi bt
     * @param cancelable     the cancelable
     * @param postListener   the post listener
     */
    public void showAlertDialog(String title, String msg, String negativeButton, String positiveButton, boolean showNaviBt, boolean cancelable, DialogInterface.OnClickListener postListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton(positiveButton, postListener)
                .setCancelable(cancelable)
                .setTitle(title);
        if (showNaviBt) {
            builder.setNegativeButton(negativeButton, null);
        }
        builder.show();
    }

    /**
     * Show alert dialog.
     *
     * @param title          the title
     * @param msg            the msg
     * @param negativeButton the negative button
     * @param positiveButton the positive button
     * @param cancelable     the cancelable
     * @param postListener   the post listener
     */
    public void showAlertDialog(String title, String msg, String negativeButton, String positiveButton, boolean cancelable, DialogInterface.OnClickListener postListener) {
        showAlertDialog(title, msg, negativeButton, positiveButton, true, cancelable, postListener);
    }

    /**
     * Show alert dialog.
     *
     * @param msg            the msg
     * @param negativeButton the negative button
     * @param positiveButton the positive button
     * @param cancelable     the cancelable
     * @param postListener   the post listener
     */
    public void showAlertDialog(String msg, String negativeButton, String positiveButton, boolean cancelable, DialogInterface.OnClickListener postListener) {
        showAlertDialog(getString(R.string.tip), msg, negativeButton, positiveButton, cancelable, postListener);
    }

    /**
     * Show alert dialog.
     *
     * @param msg            the msg
     * @param positiveButton the positive button
     * @param cancelable     the cancelable
     * @param postListener   the post listener
     */
    public void showAlertDialog(String msg, String positiveButton, boolean cancelable, DialogInterface.OnClickListener postListener) {
        showAlertDialog(msg, getString(R.string.cancel), positiveButton, cancelable, postListener);
    }

    /**
     * Show alert dialog.
     *
     * @param msg          the msg
     * @param cancelable   the cancelable
     * @param postListener the post listener
     */
    public void showAlertDialog(String msg, boolean cancelable, DialogInterface.OnClickListener postListener) {
        showAlertDialog(msg, getString(R.string.ok), cancelable, postListener);
    }

    /**
     * Show alert dialog.
     *
     * @param msg          the msg
     * @param postListener the post listener
     */
    public void showAlertDialog(String msg, DialogInterface.OnClickListener postListener) {
        showAlertDialog(msg, false, postListener);
    }

    /**
     * Show confirm dialog.
     *
     * @param msg          the msg
     * @param postListener the post listener
     */
    public void showConfirmDialog(String msg, DialogInterface.OnClickListener postListener) {
        showAlertDialog(getString(R.string.tip), msg, null, getString(R.string.ok), false, false, postListener);
    }

    /**
     * Show snack bar.
     *
     * @param message         the message
     * @param button          the button
     * @param onClickListener the on click listener
     */
    public void showSnackBar(String message, String button, View.OnClickListener onClickListener) {
        if (snackbar == null) {
            snackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content),
                    "",
                    Snackbar.LENGTH_LONG);
        }
        snackbar.setText(message);
        if (onClickListener != null) {
            snackbar.setAction((TextUtils.isEmpty(button) ? getString(R.string.ok) : button), onClickListener);
        }
        snackbar.show();
    }

    /**
     * Show snack bar.
     *
     * @param msgTestResId    the msg test res id
     * @param buttonTextResId the button text res id
     * @param onClickListener the on click listener
     */
    public void showSnackBar(int msgTestResId, int buttonTextResId, View.OnClickListener onClickListener) {
        showSnackBar(getString(msgTestResId), getString(buttonTextResId), onClickListener);
    }

    /**
     * Show snack bar.
     *
     * @param message the message
     */
    public void showSnackBar(String message) {
        showSnackBar(message, null, null);
    }

    /**
     * Show snack bar.
     *
     * @param StrRes the str res
     */
    public void showSnackBar(int StrRes) {
        showSnackBar(getString(StrRes), null, null);
    }

}
