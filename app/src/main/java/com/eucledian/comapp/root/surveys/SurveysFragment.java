package com.eucledian.comapp.root.surveys;

import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eucledian.comapp.R;
import com.eucledian.comapp.adapter.RecyclerItemClicked;
import com.eucledian.comapp.adapter.SurveyAdapter;
import com.eucledian.comapp.dao.SurveyDataSource;
import com.eucledian.comapp.dao.ZoneDataSource;
import com.eucledian.comapp.model.Survey;
import com.eucledian.comapp.root.RootActivity;
import com.eucledian.comapp.util.views.EnhancedRecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EFragment(R.layout.fragment_surveys)
public class SurveysFragment extends Fragment implements RecyclerItemClicked {

    @Bean
    protected SurveyAdapter adapter;

    @Bean
    protected SurveyDataSource dao;

    @Bean
    protected ZoneDataSource zoneDataSource;

    @ViewById
    protected TextView emptyListText;

    @ViewById
    protected EnhancedRecyclerView surveysList;

    @ViewById
    protected ProgressBar surveysLoadingView;

    public SurveysFragment() {
        // Required empty public constructor
    }

    @AfterViews
    protected void init() {
        adapter.setOnRecyclerItemClickedListener(this);
        surveysList.setEmptyView(emptyListText);
        surveysList.setLayoutManager(new LinearLayoutManager(getActivity()));
        surveysList.setAdapter(adapter);
        /**activity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceFragment(new NewCardFragment_());
            }
        });*/
        doQuery();
    }

    private void doQuery(){
        loading();
        dao.open();
        zoneDataSource.open();
        ArrayList<Survey> list = dao.getElements(zoneDataSource);
        dao.close();
        zoneDataSource.close();
        adapter.setItems(list);
        adapter.notifyDataSetChanged();
        loaded();
    }

    private void loading() {
        emptyListText.setVisibility(View.GONE);
        surveysLoadingView.setVisibility(View.VISIBLE);
        surveysList.setVisibility(View.GONE);
    }

    private void loaded() {
        surveysLoadingView.setVisibility(View.GONE);
        surveysList.setVisibility(View.VISIBLE);
    }


    @Override
    public void onRecyclerItemClicked(int position) {
        AppUserSurveyFormFragment_ fragment = new AppUserSurveyFormFragment_();
        Survey element = adapter.getItem(position);
        RootActivity activity = (RootActivity) getActivity();

        fragment.setArguments(dao.toArgs(element));
        activity.replaceFragment(fragment);
    }

}
