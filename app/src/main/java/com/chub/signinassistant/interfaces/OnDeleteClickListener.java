package com.chub.signinassistant.interfaces;

/**
 * Description：删除按钮接口
 * Created by Chub on 2017/11/26.
 */

public interface OnDeleteClickListener<T> {
    void onClick(int position, T data);
}
