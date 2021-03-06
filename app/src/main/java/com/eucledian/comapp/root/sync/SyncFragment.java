package com.eucledian.comapp.root.sync;

import android.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.eucledian.comapp.ApiClientResponse;
import com.eucledian.comapp.App;
import com.eucledian.comapp.Config;
import com.eucledian.comapp.R;
import com.eucledian.comapp.dao.AppUserMarkerDataSource;
import com.eucledian.comapp.dao.AppUserSurveyDataSource;
import com.eucledian.comapp.dao.AppUserSurveyResponseDataSource;
import com.eucledian.comapp.dao.MarkerDataSource;
import com.eucledian.comapp.dao.SurveyDataSource;
import com.eucledian.comapp.dao.SurveyFieldDataSource;
import com.eucledian.comapp.dao.SurveyFieldOptionDataSource;
import com.eucledian.comapp.dao.SurveyFieldValidationDataSource;
import com.eucledian.comapp.dao.ZoneDataSource;
import com.eucledian.comapp.model.AppUserMarker;
import com.eucledian.comapp.model.AppUserSurvey;
import com.eucledian.comapp.model.Marker;
import com.eucledian.comapp.model.Survey;
import com.eucledian.comapp.model.SurveyField;
import com.eucledian.comapp.model.SurveyFieldOption;
import com.eucledian.comapp.model.SurveyFieldValidation;
import com.eucledian.comapp.model.Zone;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@EFragment(R.layout.fragment_sync)
public class SyncFragment extends Fragment {

    @Bean
    protected App app;

    @Bean
    protected MarkerDataSource markerDataSource;

    @Bean
    protected ZoneDataSource zoneDataSource;

    @Bean
    protected SurveyDataSource surveyDataSource;

    @Bean
    protected SurveyFieldDataSource surveyFieldDataSource;

    @Bean
    protected SurveyFieldOptionDataSource surveyFieldOptionDataSource;

    @Bean
    protected SurveyFieldValidationDataSource surveyFieldValidationDataSource;

    @Bean
    protected AppUserSurveyDataSource appUserSurveyDataSource;

    @Bean
    protected AppUserSurveyResponseDataSource appUserSurveyResponseDataSource;

    @Bean
    protected AppUserMarkerDataSource appUserMarkerDataSource;

    @ViewById
    protected ProgressBar syncLoadingView;

    @ViewById
    protected TextView syncCopy;

    @ViewById
    protected TextView syncStatus;

    @ViewById
    protected Button syncBtn;

    private Iterator<AppUserSurvey> surveyIterator;

    public SyncFragment() {
        // Required empty public constructor
    }

    @Click
    protected void syncBtnClicked() {
        loading();
        syncSurveys();
    }

    private void syncSurveys(){
        appUserSurveyDataSource.open();
        appUserSurveyResponseDataSource.open();
        ArrayList<AppUserSurvey> surveys = appUserSurveyDataSource.getElements(appUserSurveyResponseDataSource);
        appUserSurveyDataSource.close();
        appUserSurveyResponseDataSource.close();
        setSurveyIterator(surveys.iterator());
        syncNextSurvey();
    }

    private void syncNextSurvey(){
        if(getSurveyIterator().hasNext()){
            syncSurvey(getSurveyIterator().next());
        }
        else{
            syncMarkers();
        }
    }

    private void syncSurvey(final AppUserSurvey appUserSurvey){
        Map<String, String> params = new HashMap<>();
        String noticeMessage = getString(R.string.sync_survey);
        noticeMessage = String.format(noticeMessage, appUserSurvey.getId());
        setSyncStatusText(noticeMessage);
        String url = Config.Server.Urls.Surveys.SYNC.replace(":survey_id", String.valueOf(appUserSurvey.getSurveyId()));
        appUserSurveyResponseDataSource.setParams(params, appUserSurvey.getResponses());
        app.post(url, params, new ApiClientResponse() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onLoaded() {
            }

            @Override
            public void onSuccess(ObjectNode response) {
                syncSurveyOnSuccess(response, appUserSurvey);
            }

            @Override
            public void onError(VolleyError e) {
                e.printStackTrace();
                syncSurveyOnError(appUserSurvey);
            }
        }, getActivity());
    }

    private void syncSurveyOnSuccess(ObjectNode response, AppUserSurvey appUserSurvey){
        appUserSurveyDataSource.open();
        appUserSurveyResponseDataSource.open();
        appUserSurveyDataSource.deleteSurvey(appUserSurvey.getId());
        appUserSurveyResponseDataSource.deleteAppUserSurvey(appUserSurvey.getId());
        appUserSurveyDataSource.close();
        appUserSurveyResponseDataSource.close();
        syncNextSurvey();
    }

    private void syncSurveyOnError(AppUserSurvey appUserSurvey){
        String noticeMessage = getString(R.string.sync_survey_error);
        noticeMessage = String.format(noticeMessage, appUserSurvey.getId());
        syncError(noticeMessage);
    }

    private void syncMarkers(){
        appUserMarkerDataSource.open();
        ArrayList<AppUserMarker> elements = appUserMarkerDataSource.getElements();
        appUserMarkerDataSource.close();
        Map<String, String> params = new HashMap<>();
        String noticeMessage = getString(R.string.sync_marker);
        setSyncStatusText(noticeMessage);
        String url = Config.Server.Urls.Markers.SYNC;
        appUserMarkerDataSource.setParams(params, elements);
        app.post(url, params, new ApiClientResponse() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onLoaded() {
            }

            @Override
            public void onSuccess(ObjectNode response) {
                syncMarkersOnSuccess(response);
            }

            @Override
            public void onError(VolleyError e) {
                e.printStackTrace();
                syncMarkersOnError();
            }
        }, getActivity());
    }

    private void syncMarkersOnError(){
        String noticeMessage = getString(R.string.sync_marker_error);
        syncError(noticeMessage);
    }

    private void syncMarkersOnSuccess(ObjectNode response){
        appUserMarkerDataSource.open();
        appUserMarkerDataSource.deleteAllElements();
        appUserMarkerDataSource.close();
        deleteAndFetchMarkers();
    }

    private void deleteAndFetchMarkers(){
        setSyncStatusText(getString(R.string.sync_fetch_markers));
        fetchMarkers();
    }

    private void fetchMarkers() {
        Map<String, String> params = new HashMap<>();
        app.get(Config.Server.Urls.Markers.LIST, params, new ApiClientResponse() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onLoaded() {
            }

            @Override
            public void onSuccess(ObjectNode response) {
                fetchMarkersOnSuccess(response);
            }

            @Override
            public void onError(VolleyError e) {
                e.printStackTrace();
                fetchMarkersOnError();
            }
        }, getActivity());
    }

    private void fetchMarkersOnError(){
        syncError(getString(R.string.sync_fetch_markers_error));
    }

    private void fetchMarkersOnSuccess(ObjectNode response){
        ArrayNode contents = (ArrayNode) response.get("contents");
        markerDataSource.open();
        markerDataSource.deleteAllElements();
        markerDataSource.close();
        try {
            Iterator<JsonNode> it = contents.elements();
            markerDataSource.open();
            while(it.hasNext()) {
                ObjectNode tmp = (ObjectNode) it.next();
                Marker element = markerDataSource.getElement(tmp, app.getObjectMapper());
                markerDataSource.insertElement(element);
            }
            markerDataSource.close();
            deleteAndFetchSurveys();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fetchMarkersOnError();
        }
    }

    private void deleteAndFetchSurveys(){
        setSyncStatusText(getString(R.string.sync_fetch_zones));
        // delete zones
        zoneDataSource.open();
        zoneDataSource.deleteAllElements();
        zoneDataSource.close();
        // delete surveys
        surveyDataSource.open();
        surveyDataSource.deleteAllElements();
        surveyDataSource.close();
        // delete survey fields
        surveyFieldDataSource.open();
        surveyFieldDataSource.deleteAllElements();
        surveyFieldDataSource.close();
        // delete survey field options
        surveyFieldOptionDataSource.open();
        surveyFieldOptionDataSource.deleteAllElements();
        surveyFieldOptionDataSource.close();
        // delete survey field validations
        surveyFieldValidationDataSource.open();
        surveyFieldValidationDataSource.deleteAllElements();
        surveyFieldValidationDataSource.close();
        fetchSurveys();
    }

    private void fetchSurveys() {
        Map<String, String> params = new HashMap<>();
        app.get(Config.Server.Urls.Surveys.LIST, params, new ApiClientResponse() {
            @Override
            public void onLoading() {
            }

            @Override
            public void onLoaded() {
            }

            @Override
            public void onSuccess(ObjectNode response) {
                fetchSurveysOnSuccess(response);
            }

            @Override
            public void onError(VolleyError e) {
                e.printStackTrace();
                syncError(getString(R.string.sync_fetch_zones_error));
            }
        }, getActivity());
    }


    private void fetchSurveysOnSuccess(ObjectNode response){
        ArrayNode contents = (ArrayNode) response.get("contents");
        zoneDataSource.open();
        surveyDataSource.open();
        surveyFieldDataSource.open();
        surveyFieldOptionDataSource.open();
        surveyFieldValidationDataSource.open();
        try {
            Iterator<JsonNode> it = contents.elements();
            while(it.hasNext()) {
                ObjectNode tmp = (ObjectNode) it.next();
                Zone element = zoneDataSource.getElement(tmp, app.getObjectMapper());
                zoneDataSource.insertElement(element);
                insertSurveys((ArrayNode) tmp.get("surveys"));
            }
            syncSuccess();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            syncError(getString(R.string.sync_fetch_zones_error));
        }
        finally {
            zoneDataSource.close();
            surveyDataSource.close();
            surveyFieldDataSource.close();
            surveyFieldOptionDataSource.close();
            surveyFieldValidationDataSource.close();
        }
    }

    private void insertSurveys(ArrayNode contents) throws JsonProcessingException {
        Iterator<JsonNode> it = contents.elements();
        while(it.hasNext()){
            ObjectNode tmp = (ObjectNode) it.next();
            Survey element = surveyDataSource.getElement(tmp, app.getObjectMapper());
            surveyDataSource.insertElement(element);
            insertSurveyFields((ArrayNode) tmp.get("survey_fields"));
        }
    }

    private void insertSurveyFields(ArrayNode contents) throws JsonProcessingException{
        Iterator<JsonNode> it = contents.elements();
        while(it.hasNext()){
            ObjectNode tmp = (ObjectNode) it.next();
            SurveyField element = surveyFieldDataSource.getElement(tmp, app.getObjectMapper());
            surveyFieldDataSource.insertElement(element);
            insertSurveyFieldOptions((ArrayNode) tmp.get("survey_field_options"));
            insertSurveyFieldValidations((ArrayNode) tmp.get("survey_field_validations"));
        }
    }

    private void insertSurveyFieldOptions(ArrayNode contents) throws JsonProcessingException{
        Iterator<JsonNode> it = contents.elements();
        while(it.hasNext()){
            ObjectNode tmp = (ObjectNode) it.next();
            SurveyFieldOption element = surveyFieldOptionDataSource.getElement(tmp, app.getObjectMapper());
            surveyFieldOptionDataSource.insertElement(element);
        }
    }

    private void insertSurveyFieldValidations(ArrayNode contents) throws JsonProcessingException{
        Iterator<JsonNode> it = contents.elements();
        while(it.hasNext()){
            ObjectNode tmp = (ObjectNode) it.next();
            SurveyFieldValidation element = surveyFieldValidationDataSource.getElement(tmp, app.getObjectMapper());
            surveyFieldValidationDataSource.insertElement(element);
        }
    }

    private void syncError(String message){
        app.showLongToast(getActivity(), message);
        loaded();
    }

    private void syncSuccess(){
        app.showToast(getActivity(), getString(R.string.sync_success));
        loaded();
    }

    private void setSyncStatusText(String message){
        syncStatus.setText(message);
    }

    private void loading() {
        syncBtn.setEnabled(false);
        syncLoadingView.setVisibility(View.VISIBLE);
    }

    private void loaded() {
        syncBtn.setEnabled(true);
        syncLoadingView.setVisibility(View.GONE);
        setSyncStatusText(getString(R.string.sync_progress));
    }

    public Iterator<AppUserSurvey> getSurveyIterator() {
        return surveyIterator;
    }

    public void setSurveyIterator(Iterator<AppUserSurvey> surveyIterator) {
        this.surveyIterator = surveyIterator;
    }
}
