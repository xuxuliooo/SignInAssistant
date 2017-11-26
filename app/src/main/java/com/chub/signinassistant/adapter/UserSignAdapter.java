package com.chub.signinassistant.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chub.signinassistant.R;
import com.chub.signinassistant.base.BaseAdapter;
import com.chub.signinassistant.bean.SignInLogBean;
import com.chub.signinassistant.interfaces.OnDeleteClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.chub.signinassistant.util.Config.getDefaultColor;

/**
 * Description：签到记录适配器
 * Created by Chub on 2017/11/25.
 */
public class UserSignAdapter extends BaseAdapter<SignInLogBean, UserSignAdapter.ViewHolder> {


    /**
     * The On delete click listener.
     */
    private OnDeleteClickListener<SignInLogBean> onDeleteClickListener;

    /**
     * Instantiates a new Base adapter.
     *
     * @param datas the datas
     */
    public UserSignAdapter(List<SignInLogBean> datas) {
        super(datas);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.item_log_layout;
    }

    @Override
    protected ViewHolder onCreateViewHolder(View convertView, int viewType) {
        return new ViewHolder(convertView);
    }

    @Override
    protected void onBindViewHolder(ViewHolder viewHolder, final int position, final SignInLogBean data) {
        viewHolder.tvName.setText(data.getNickName());
        viewHolder.tvPhone.setText(data.getPhone());
        String signTime = "签到时间：" + data.getSignTime();
        viewHolder.tvTime.setText(signTime);
        Picasso.with(viewHolder.ctx)
                .load(data.getUserIcon())
                .error(getDefaultColor(position))
                .placeholder(getDefaultColor(position))
                .config(Bitmap.Config.ARGB_8888)
                .into(viewHolder.iv);
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteClickListener != null) {
                    onDeleteClickListener.onClick(position, data);
                }
            }
        });
    }

    /**
     * Sets on delete click listener.
     *
     * @param onDeleteClickListener the on delete click listener
     */
    public void setOnDeleteClickListener(OnDeleteClickListener<SignInLogBean> onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    /**
     * The type View holder.
     */
    class ViewHolder extends BaseAdapter.ViewHolder {

        /**
         * The Iv.
         */
        AppCompatImageView iv;
        /**
         * The Tv name.
         */
        TextView tvName;
        /**
         * The Tv phone.
         */
        TextView tvPhone;
        /**
         * The Tv time.
         */
        TextView tvTime;
        /**
         * The Delete.
         */
        ImageView delete;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvTime = itemView.findViewById(R.id.tvTime);
            delete = itemView.findViewById(R.id.ivDelete);
        }
    }

}
