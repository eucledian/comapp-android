package com.eucledian.comapp.adapter.view;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.eucledian.comapp.R;
import com.eucledian.comapp.dao.SurveyFieldOptionDataSource;
import com.eucledian.comapp.model.SurveyField;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by gustavo on 6/1/16.
 */
@EViewGroup(R.layout.fragment_app_user_survey_form_item)
public class SurveyFieldItemView extends LinearLayout {

    @Bean
    protected SurveyFieldOptionDataSource surveyFieldOptionDataSource;

    @ViewById
    protected TextView surveyFieldNameText;

    @ViewById
    protected EditText surveyFieldEditText;

    @ViewById
    protected Spinner surveyFieldSpinner;

    public SurveyFieldItemView(Context context) {
        super(context);
    }

    public void bind(SurveyField item){
        surveyFieldNameText.setText(item.getName());
        switch (item.getIdentity()){
            case SurveyField.Identity.TEXT:
                surveyFieldEditText.setVisibility(View.VISIBLE);
                break;
            case SurveyField.Identity.SELECT:
                surveyFieldSpinner.setVisibility(View.VISIBLE);
                initSpinner(item);
                break;
        }
    }

    private void initSpinner(SurveyField item){
        ArrayAdapter<String> adapter = surveyFieldOptionDataSource.getArrayAdapter(item.getOptions(), getContext());
        surveyFieldSpinner.setAdapter(adapter);
    }

}
