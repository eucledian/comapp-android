package com.eucledian.comapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by joel on 30/05/16.
 */
public class SurveyField {

    public ArrayList<SurveyFieldOption> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<SurveyFieldOption> options) {
        this.options = options;
    }

    public ArrayList<SurveyFieldValidation> getValidations() {
        return validations;
    }

    public void setValidations(ArrayList<SurveyFieldValidation> validations) {
        this.validations = validations;
    }

    public static final class Identity{
        public static final int TEXT = 0;
        public static final int SELECT = 4;
    }

    private long id;

    private long surveyId;

    private int position;

    private int dataType;

    private int identity;

    private String name;

    @JsonIgnore
    private ArrayList<SurveyFieldOption> options;

    @JsonIgnore
    private ArrayList<SurveyFieldValidation> validations;

    @JsonProperty("survey_id")
    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @JsonProperty("data_type")
    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
