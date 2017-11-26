package com.chub.signinassistant.adapter;

import android.view.View;

import com.chub.signinassistant.base.BaseAdapter;

import java.util.List;

/**
 * Description：输入提示适配器
 * Created by Chub on 2017/11/25.
 */

public class UserListAdapter extends BaseAdapter<String, UserListAdapter.ViewHolder> {


    /**
     * Instantiates a new Base adapter.
     *
     * @param datas the datas
     */
    public UserListAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return 0;
    }

    @Override
    protected ViewHolder onCreateViewHolder(View convertView, int viewType) {
        return null;
    }

    @Override
    protected void onBindViewHolder(ViewHolder viewHolder, int position, String data) {

    }

    class ViewHolder extends BaseAdapter.ViewHolder {

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
