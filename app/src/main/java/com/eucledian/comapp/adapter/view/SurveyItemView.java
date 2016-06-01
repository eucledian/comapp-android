package com.eucledian.comapp.adapter.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eucledian.comapp.R;
import com.eucledian.comapp.model.Survey;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by joel on 29/05/16.
 */
@EViewGroup(R.layout.fragment_surveys_item)
public class SurveyItemView extends LinearLayout {

    @ViewById
    protected TextView surveyNameText;

    @ViewById
    protected TextView surveyZoneText;

    public SurveyItemView(Context context) {
        super(context);
    }

    public void bind(Survey item){
        surveyNameText.setText(item.getName());
        surveyZoneText.setText("Zone Query");
    }
}
