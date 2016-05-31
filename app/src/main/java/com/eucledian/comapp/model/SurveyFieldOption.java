package com.eucledian.comapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by joel on 30/05/16.
 */
public class SurveyFieldOption {

    @JsonIgnore
    private long id;

    private long surveyFieldId;

    private String name;

    private String optionValue;

    public SurveyFieldOption() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("survey_field_id")
    public long getSurveyFieldId() {
        return surveyFieldId;
    }

    public void setSurveyFieldId(long surveyFieldId) {
        this.surveyFieldId = surveyFieldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("option_value")
    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }
}
