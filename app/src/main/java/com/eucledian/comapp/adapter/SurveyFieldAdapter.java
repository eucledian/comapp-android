package com.eucledian.comapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eucledian.comapp.adapter.view.SurveyFieldItemView;
import com.eucledian.comapp.adapter.view.SurveyFieldItemView_;
import com.eucledian.comapp.adapter.view.ViewWrapper;
import com.eucledian.comapp.model.SurveyField;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by gustavo on 6/1/16.
 */

@EBean
public class SurveyFieldAdapter extends RecyclerViewAdapterBase<SurveyField, SurveyFieldItemView> {

    @RootContext
    Context ctx;

    @Override
    protected SurveyFieldItemView onCreateItemView(ViewGroup parent, int viewType) {
        return SurveyFieldItemView_.build(ctx);
    }



    @Override
    public void onBindViewHolder(ViewWrapper<SurveyFieldItemView> holder, int position) {
        SurveyFieldItemView view = holder.getView();
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        SurveyField item = items.get(position);
        view.setLayoutParams(lp);
        view.bind(item);
    }
}
