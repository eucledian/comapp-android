package com.eucledian.comapp.adapter.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eucledian.comapp.R;
import com.eucledian.comapp.model.Survey;
import com.eucledian.comapp.model.Zone;

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
        Zone zone = item.getZone();
        surveyNameText.setText(item.getName());
        if(zone != null){
            surveyZoneText.setText(zone.getName());
        }
    }
}
