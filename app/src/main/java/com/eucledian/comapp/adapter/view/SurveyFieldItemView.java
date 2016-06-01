package com.eucledian.comapp.adapter.view;

import android.content.Context;
import android.text.TextUtils;
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

import java.util.ArrayList;

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

    private View inputView;

    @ViewById
    protected Spinner surveyFieldSpinner;

    @ViewById
    protected TextView surveyFieldErrorText;

    public SurveyFieldItemView(Context context) {
        super(context);
    }

    public void bind(SurveyField item){
        item.setView(this);
        surveyFieldNameText.setText(item.getName());
        switch (item.getIdentity()){
            case SurveyField.Identity.TEXT:
                setInputView(surveyFieldEditText);
                surveyFieldEditText.setVisibility(View.VISIBLE);
                break;
            case SurveyField.Identity.SELECT:
                setInputView(surveyFieldSpinner);
                surveyFieldSpinner.setVisibility(View.VISIBLE);
                initSpinner(item);
                break;
        }
    }

    public String getValue(){
        String value = null;
        if(inputView instanceof TextView){
            value = ((TextView) inputView).getText().toString();
        }
        else if(inputView instanceof Spinner){
            Object selectedItem = ((Spinner) inputView).getSelectedItem();
            if(selectedItem != null){
                value = selectedItem.toString();
            }
        }
        return value;
    }

    public void setErrors(ArrayList<String> errors){
        String errorMessage = TextUtils.join(", ", errors);
        surveyFieldErrorText.setText(errorMessage);
    }

    public void clearErrors(){
        surveyFieldErrorText.setText(null);
    }

    private void initSpinner(SurveyField item){
        ArrayAdapter<String> adapter = surveyFieldOptionDataSource.getArrayAdapter(item.getOptions(), getContext());
        surveyFieldSpinner.setAdapter(adapter);
    }

    public View getInputView() {
        return inputView;
    }

    public void setInputView(View inputView) {
        this.inputView = inputView;
    }
}
