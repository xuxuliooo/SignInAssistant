package com.chub.signinassistant.bean;

import android.text.TextUtils;

/**
 * Description：
 * Created by Chub on 2017/11/25.
 */
public class CheckSignEntity {
    /**
     * signFlg : 1
     */

    private String signFlg;

    /**
     * Gets sign flg.
     *
     * @return the sign flg
     */
    public String getSignFlg() {
        return signFlg;
    }

    /**
     * Sets sign flg.
     *
     * @param signFlg the sign flg
     */
    public void setSignFlg(String signFlg) {
        this.signFlg = signFlg;
    }

    @Override
    public String toString() {
        return "{" +
                "\"signFlg\":" + (signFlg == null ? null : "\"" + signFlg + "\"") +
                '}';
    }

    /**
     * Sign boolean.
     *
     * @return the boolean
     */
    public boolean Sign() {
        return TextUtils.equals("1", signFlg);
    }
}
