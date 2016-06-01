package com.eucledian.comapp.root.surveys;

import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.eucledian.comapp.R;
import com.eucledian.comapp.adapter.SurveyFieldAdapter;
import com.eucledian.comapp.dao.SurveyDataSource;
import com.eucledian.comapp.dao.SurveyFieldDataSource;
import com.eucledian.comapp.dao.SurveyFieldOptionDataSource;
import com.eucledian.comapp.dao.SurveyFieldValidationDataSource;
import com.eucledian.comapp.model.Survey;
import com.eucledian.comapp.util.views.EnhancedRecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by gustavo on 6/1/16.
 */

@EFragment(R.layout.fragment_app_user_survey_form)
public class AppUserSurveyFormFragment extends Fragment {

    @Bean
    protected SurveyDataSource surveyDataSource;

    @Bean
    protected SurveyFieldAdapter adapter;

    @Bean
    protected SurveyFieldDataSource surveyFieldDataSource;

    @Bean
    protected SurveyFieldOptionDataSource surveyFieldOptionDataSource;

    @Bean
    protected SurveyFieldValidationDataSource surveyFieldValidationDataSource;

    @ViewById
    protected TextView surveyDescriptionText;

    @ViewById
    protected EnhancedRecyclerView surveyFieldList;

    private Survey survey;

    public AppUserSurveyFormFragment(){
        // Required empty public constructor
    }

    @AfterViews
    protected void init(){
        setSurvey(surveyDataSource.fromBundle(getArguments()));
        initUI();
        doQuery();
    }

    private void doQuery(){
        surveyFieldDataSource.open();
        surveyFieldOptionDataSource.open();
        surveyFieldValidationDataSource.open();
        adapter.setItems(surveyFieldDataSource.getElementsBySurvey(survey.getId(), surveyFieldOptionDataSource, surveyFieldValidationDataSource));
        adapter.notifyDataSetChanged();
        surveyFieldDataSource.close();
        surveyFieldOptionDataSource.close();
        surveyFieldValidationDataSource.close();
    }

    private void initUI(){
        surveyDescriptionText.setText(survey.getDescription());
        surveyFieldList.setLayoutManager(new LinearLayoutManager(getActivity()));
        surveyFieldList.setAdapter(adapter);
    }

    @Click
    protected void surveyFormSubmitBtnClicked(){

    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
}
