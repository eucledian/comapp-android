package com.eucledian.comapp.util;

import android.content.Context;

import com.eucledian.comapp.R;
import com.eucledian.comapp.model.SurveyField;
import com.eucledian.comapp.model.SurveyFieldValidation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by gustavo on 6/1/16.
 */
public class FieldValidator {

    private Context context;
    private ObjectMapper mapper;
    private String value;
    private ArrayList<SurveyFieldValidation> validations;
    private ArrayList<String> errors;

    public FieldValidator(Context context, ObjectMapper mapper, String value, ArrayList<SurveyFieldValidation> validations){
        setContext(context);
        setMapper(mapper);
        setValue(value);
        setValidations(validations);
    }

    public ArrayList<String> validate(){
        setErrors(new ArrayList<String>());
        Iterator<SurveyFieldValidation> it = getValidations().iterator();
        while (it.hasNext()){
            validate(it.next());
        }
        return getErrors();
    }

    private void validate(SurveyFieldValidation validation){
        JsonNode validationArgs = null;
        if(validation.getValidationArgs() != null){
            try {
                validationArgs = getMapper().readTree(validation.getValidationArgs());
            } catch (IOException e) {}
        }
        switch (validation.getIdentity()){
            case SurveyFieldValidation.Identity.PRESENCE:
                validatePresence(validation);
                break;
            case SurveyFieldValidation.Identity.LENGTH:
                validateLength(validationArgs);
                break;
            case SurveyFieldValidation.Identity.NUMERICALITY:
                validateNumericality(validationArgs);
                break;
            case SurveyFieldValidation.Identity.INCLUSION:
                validateInclusion(validationArgs);
                break;
            case SurveyFieldValidation.Identity.EXCLUSION:
                validateExclusion(validationArgs);
                break;
            case SurveyFieldValidation.Identity.FORMAT:
                validateFormat(validationArgs);
                break;
        }
    }

    private void validatePresence(SurveyFieldValidation validation){
        if(getValue() == null || getValue().isEmpty()){
            getErrors().add(getContext().getString(R.string.validation_presence));
        }
    }


    private void validateLength(JsonNode validationArgs){
        if(getValue() != null && validationArgs != null){
            int length = getValue().length();
            if(validationArgs.has("minimum")){
                int minimum = validationArgs.get("minimum").get("value").asInt();
                String errorMessage;
                if(length < minimum){
                    errorMessage = getContext().getString(R.string.validation_length_minimum);
                    getErrors().add(String.format(errorMessage, minimum));
                }
            }
            if(validationArgs.has("maximum")){
                int maximum = validationArgs.get("maximum").get("value").asInt();
                String errorMessage;
                if(length > maximum){
                    errorMessage = getContext().getString(R.string.validation_length_maximum);
                    getErrors().add(String.format(errorMessage, maximum));
                }
            }
            if(validationArgs.has("is")){
                int is = validationArgs.get("is").get("value").asInt();
                String errorMessage;
                if(length != is){
                    errorMessage = getContext().getString(R.string.validation_length_is);
                    getErrors().add(String.format(errorMessage, is));
                }
            }
        }
    }

    private void validateNumericality(JsonNode validationArgs){
        if(getValue() != null && validationArgs != null){

        }
    }

    private void validateInclusion(JsonNode validationArgs){

    }

    private void validateExclusion(JsonNode validationArgs){

    }

    private void validateFormat(JsonNode validationArgs){

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ArrayList<SurveyFieldValidation> getValidations() {
        return validations;
    }

    public void setValidations(ArrayList<SurveyFieldValidation> validations) {
        this.validations = validations;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}
