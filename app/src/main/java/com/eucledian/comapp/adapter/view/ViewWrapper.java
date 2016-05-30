package com.eucledian.comapp.adapter.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by joel on 15/01/16.
 */
public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder {

    private V view;

    public ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }

    public V getView() {
        return view;
    }
}