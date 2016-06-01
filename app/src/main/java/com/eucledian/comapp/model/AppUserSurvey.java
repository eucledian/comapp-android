package com.eucledian.comapp.model;

import java.util.ArrayList;

/**
 * Created by gustavo on 5/31/16.
 */
public class AppUserSurvey {

    private long id;
    private long surveyId;
    private ArrayList<AppUserSurveyResponse> responses;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public ArrayList<AppUserSurveyResponse> getResponses() {
        return responses;
    }

    public void setResponses(ArrayList<AppUserSurveyResponse> responses) {
        this.responses = responses;
    }
}
