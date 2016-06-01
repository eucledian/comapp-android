package com.eucledian.comapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by joel on 30/05/16.
 */
public class SurveyFieldValidation {

    public static final class Identity{
        public static final int PRESENCE = 0;
        public static final int LENGTH = 1;
        public static final int NUMERICALITY = 2;
        public static final int INCLUSION = 3;
        public static final int EXCLUSION = 4;
        public static final int UNIQUENESS = 5;
        public static final int FORMAT = 6;
        public static final int CONFIRMATION = 7;
    }

    private long id;

    private long surveyFieldId;

    private int identity;

    private String validationArgs;

    public SurveyFieldValidation() {
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

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    @JsonProperty("validation_args")
    public String getValidationArgs() {
        return validationArgs;
    }

    public void setValidationArgs(String validationArgs) {
        this.validationArgs = validationArgs;
    }
}
