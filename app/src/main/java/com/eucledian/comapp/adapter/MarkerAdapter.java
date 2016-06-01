package com.eucledian.comapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.eucledian.comapp.adapter.view.MarkerItemView;
import com.eucledian.comapp.adapter.view.MarkerItemView_;
import com.eucledian.comapp.adapter.view.ViewWrapper;
import com.eucledian.comapp.model.Marker;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by joel on 31/05/16.
 */
@EBean
public class MarkerAdapter extends RecyclerViewAdapterBase<Marker, MarkerItemView> {

    @RootContext
    Context ctx;

    @Override
    protected MarkerItemView onCreateItemView(ViewGroup parent, int viewType) {
        return MarkerItemView_.build(ctx);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<MarkerItemView> holder, final int position) {
        MarkerItemView view = holder.getView();
        Marker item = items.get(position);
        view.bind(item);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnRecyclerItemClicked().onRecyclerItemClicked(position);
            }
        });
    }

}