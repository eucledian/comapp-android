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
import com.eucledian.comapp.dao.MarkerDataSource;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

@EFragment(R.layout.fragment_sync)
public class SyncFragment extends Fragment {

    @Bean
    protected App app;

    @Bean
    protected MarkerDataSource markerDataSource;

    @ViewById
    protected ProgressBar syncLoadingView;

    @ViewById
    protected TextView syncCopy;

    @ViewById
    protected TextView syncStatus;

    @ViewById
    protected Button syncBtn;

    public SyncFragment() {
        // Required empty public constructor
    }

    @Click
    protected void syncBtnClicked() {
        loading();
        deleteAndfetchMarkers();
    }

    private void deleteAndfetchMarkers(){
        /**markerDataSource.open();
        markerDataSource.deleteAllElements();
        markerDataSource.close();*/
    }

    private void fetchMarkers() {
        setSyncStatusText(getString(R.string.sync_fetch_markers));
        Map<String, String> params = new HashMap<>();
        app.post(Config.Server.Urls.Markers.LIST, params, new ApiClientResponse() {
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
                syncError(getString(R.string.sync_fetch_markers_error));
            }
        }, getActivity());
    }

    private void fetchMarkersOnSuccess(ObjectNode response){

    }

    private void syncError(String message){
        app.showLongToast(getActivity(), message);
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

}
