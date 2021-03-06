package com.eucledian.comapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.eucledian.comapp.adapter.view.SurveyItemView;
import com.eucledian.comapp.adapter.view.SurveyItemView_;
import com.eucledian.comapp.adapter.view.ViewWrapper;
import com.eucledian.comapp.model.Survey;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by joel on 29/05/16.
 */
@EBean
public class SurveyAdapter extends RecyclerViewAdapterBase<Survey, SurveyItemView>{

    @RootContext
    Context ctx;

    @Override
    protected SurveyItemView onCreateItemView(ViewGroup parent, int viewType) {
        return SurveyItemView_.build(ctx);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<SurveyItemView> holder, final int position) {
        SurveyItemView view = holder.getView();
        Survey item = items.get(position);
        view.bind(item);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnRecyclerItemClicked().onRecyclerItemClicked(position);
            }
        });
    }

}
