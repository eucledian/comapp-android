package com.eucledian.comapp.util;

import android.content.Context;

import com.eucledian.comapp.R;
import com.eucledian.comapp.model.SurveyField;
import com.eucledian.comapp.model.SurveyFieldValidation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;

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
            String errorMessage;
            if(StringUtils.isNumeric(getValue())){
                double tmp = Double.parseDouble(getValue());
                if(validationArgs.has("only_integer")){
                    int only_integer = validationArgs.get("only_integer").get("value").asInt();
                    if(only_integer == 1){
                        try{
                            Integer.parseInt(getValue());
                        }catch(NumberFormatException nfe) {
                            errorMessage = getContext().getString(R.string.validation_numericality_only_integer);
                            getErrors().add(errorMessage);
                        }
                    }
                }
                if(validationArgs.has("equal_to")){
                    double equals = validationArgs.get("equal_to").get("value").asDouble();
                    if(tmp != equals){
                        errorMessage = getContext().getString(R.string.validation_numericality_equal);
                        getErrors().add(String.format(errorMessage, equals));
                    }
                }
                if(validationArgs.has("greater_than")){
                    double equals = validationArgs.get("greater_than").get("value").asDouble();
                    if(tmp <= equals){
                        errorMessage = getContext().getString(R.string.validation_numericality_greater_than);
                        getErrors().add(String.format(errorMessage, equals));
                    }
                }
                if(validationArgs.has("greater_than_or_equal_to")){
                    double equals = validationArgs.get("greater_than_or_equal_to").get("value").asDouble();
                    if(tmp < equals){
                        errorMessage = getContext().getString(R.string.validation_numericality_greater_or_equal);
                        getErrors().add(String.format(errorMessage, equals));
                    }
                }
                if(validationArgs.has("less_than")){
                    double equals = validationArgs.get("less_than").get("value").asDouble();
                    if(tmp >= equals){
                        errorMessage = getContext().getString(R.string.validation_numericality_less_than);
                        getErrors().add(String.format(errorMessage, equals));
                    }
                }
                if(validationArgs.has("less_than_or_equal_to")){
                    double equals = validationArgs.get("less_than_or_equal_to").get("value").asDouble();
                    if(tmp > equals){
                        errorMessage = getContext().getString(R.string.validation_numericality_less_or_equal);
                        getErrors().add(String.format(errorMessage, equals));
                    }
                }
                if(validationArgs.has("odd")){
                    int odd = validationArgs.get("odd").get("value").asInt();
                    if(odd == 1 && (tmp % 2 == 0)){
                        errorMessage = getContext().getString(R.string.validation_numericality_odd);
                        getErrors().add(errorMessage);
                    }
                }
                if(validationArgs.has("even")){
                    int even = validationArgs.get("even").get("value").asInt();
                    if(even == 1 && (tmp % 2 != 0)){
                        errorMessage = getContext().getString(R.string.validation_numericality_even);
                        getErrors().add(errorMessage);
                    }
                }
            }else{
                errorMessage = getContext().getString(R.string.validation_numericality);
                getErrors().add(errorMessage);
            }
        }
    }

    private void validateInclusion(JsonNode validationArgs){
        // TODO validateInclusion
    }

    private void validateExclusion(JsonNode validationArgs){
        // TODO validateExclusion
    }

    private void validateFormat(JsonNode validationArgs){
        // TODO validateFormat
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
