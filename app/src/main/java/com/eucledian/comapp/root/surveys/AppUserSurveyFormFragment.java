package com.eucledian.comapp.root.surveys;

import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.eucledian.comapp.App;
import com.eucledian.comapp.R;
import com.eucledian.comapp.adapter.SurveyFieldAdapter;
import com.eucledian.comapp.adapter.view.SurveyFieldItemView;
import com.eucledian.comapp.dao.AppUserSurveyDataSource;
import com.eucledian.comapp.dao.AppUserSurveyResponseDataSource;
import com.eucledian.comapp.dao.SurveyDataSource;
import com.eucledian.comapp.dao.SurveyFieldDataSource;
import com.eucledian.comapp.dao.SurveyFieldOptionDataSource;
import com.eucledian.comapp.dao.SurveyFieldValidationDataSource;
import com.eucledian.comapp.model.AppUserSurvey;
import com.eucledian.comapp.model.AppUserSurveyResponse;
import com.eucledian.comapp.model.Survey;
import com.eucledian.comapp.model.SurveyField;
import com.eucledian.comapp.root.RootActivity;
import com.eucledian.comapp.util.FieldValidator;
import com.eucledian.comapp.util.views.EnhancedRecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by gustavo on 6/1/16.
 */

@EFragment(R.layout.fragment_app_user_survey_form)
public class AppUserSurveyFormFragment extends Fragment {

    @Bean
    protected App app;

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

    @Bean
    protected AppUserSurveyDataSource appUserSurveyDataSource;

    @Bean
    protected AppUserSurveyResponseDataSource appUserSurveyResponseDataSource;

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
        boolean isValid = true;
        int count = adapter.getItemCount();
        for(int i=0; i<count; ++i){
            SurveyField surveyField = adapter.getItem(i);
            SurveyFieldItemView view = surveyField.getView();
            String value = view.getValue();
            view.clearErrors();
            FieldValidator validator = new FieldValidator(getActivity(), app.getObjectMapper(), value, surveyField.getValidations());
            ArrayList<String> errors = validator.validate();
            if(!errors.isEmpty()){
                // SET errors
                isValid = false;
                view.setErrors(errors);
            }
            else{
                surveyField.getResponse().setResponse(value);
            }
        }
        if(isValid){
            saveAppUserSurvey();
        }
    }

    private void saveAppUserSurvey(){
        appUserSurveyDataSource.open();
        appUserSurveyResponseDataSource.open();
        AppUserSurvey appUserSurvey = new AppUserSurvey();
        appUserSurvey.setSurveyId(getSurvey().getId());
        long appUserSurveyId = appUserSurveyDataSource.insertElement(appUserSurvey);
        if(appUserSurveyId == -1){
            app.toast(getString(R.string.app_user_survey_save_error));
            appUserSurveyDataSource.close();
            appUserSurveyResponseDataSource.close();
        }
        else{
            saveAppUserResponses(appUserSurveyId);
        }
    }

    private void saveAppUserResponses(long appUserSurveyId){
        boolean isValid = true;
        int count = adapter.getItemCount();
        for (int i=0; i<count; ++i) {
            SurveyField surveyField = adapter.getItem(i);
            AppUserSurveyResponse response = surveyField.getResponse();
            response.setAppUserSurveyId(appUserSurveyId);
            long result = appUserSurveyResponseDataSource.insertElement(response);
            String tmp = "abc";
        }
        app.toast(getString(R.string.app_user_survey_save_success));
        appUserSurveyDataSource.close();
        appUserSurveyResponseDataSource.close();
        RootActivity rootActivity = (RootActivity) getActivity();
        rootActivity.popToRootFragment();
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
}
