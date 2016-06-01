package com.eucledian.comapp.model;

/**
 * Created by gustavo on 5/31/16.
 */
public class AppUserSurveyResponse {

    private long id;
    private int appUserSurveyId;
    private long surveyFieldId;
    private String response;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSurveyFieldId() {
        return surveyFieldId;
    }

    public void setSurveyFieldId(long surveyFieldId) {
        this.surveyFieldId = surveyFieldId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getAppUserSurveyId() {
        return appUserSurveyId;
    }

    public void setAppUserSurveyId(int appUserSurveyId) {
        this.appUserSurveyId = appUserSurveyId;
    }
}
