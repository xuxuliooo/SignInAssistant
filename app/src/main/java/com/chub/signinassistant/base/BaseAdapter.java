package com.chub.signinassistant.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Description：Adapter基类
 * Created by Chub on 2017/11/25.
 *
 * @param <T>  the type parameter
 * @param <VH> the type parameter
 */
public abstract class BaseAdapter<T, VH extends BaseAdapter.ViewHolder> extends android.widget.BaseAdapter {

    /**
     * The Datas.
     */
    protected List<T> datas;

    /**
     * Instantiates a new Base adapter.
     *
     * @param datas the datas
     */
    public BaseAdapter(List<T> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return datas == null ? -1 : position;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH vh;

        int viewType = getItemViewType(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(viewType), parent, false);
            vh = onCreateViewHolder(convertView, viewType);
            convertView.setTag(vh);
        } else {
            //noinspection unchecked
            vh = (VH) convertView.getTag();
        }
        onBindViewHolder(vh, position, datas.get(position));
        return convertView;
    }

    /**
     * Gets item layout.
     *
     * @param viewType the view type
     * @return the item layout
     */
    protected abstract int getItemLayout(int viewType);

    /**
     * On create view holder vh.
     *
     * @param convertView the convert view
     * @param viewType    the view type
     * @return the vh
     */
    protected abstract VH onCreateViewHolder(View convertView, int viewType);

    /**
     * On bind view holder.
     *
     * @param vh       the vh
     * @param position the position
     * @param data     the data
     */
    protected abstract void onBindViewHolder(VH vh, int position, T data);

    /**
     * The type View holder.
     */
    public class ViewHolder {

        /**
         * The Item view.
         */
        public View itemView;
        /**
         * The Context.
         */
        public Context ctx;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(View itemView) {
            this.itemView = itemView;
            this.ctx = itemView.getContext();
        }
    }

}
