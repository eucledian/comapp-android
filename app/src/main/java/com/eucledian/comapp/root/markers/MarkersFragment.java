package com.eucledian.comapp.root.markers;

import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eucledian.comapp.App;
import com.eucledian.comapp.R;
import com.eucledian.comapp.adapter.MarkerAdapter;
import com.eucledian.comapp.adapter.RecyclerItemClicked;
import com.eucledian.comapp.dao.MarkerDataSource;
import com.eucledian.comapp.model.Marker;
import com.eucledian.comapp.util.views.EnhancedRecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EFragment(R.layout.fragment_markers)
public class MarkersFragment extends Fragment implements RecyclerItemClicked{

    @Bean
    protected App app;

    @Bean
    protected MarkerAdapter adapter;

    @Bean
    protected MarkerDataSource dao;

    @ViewById
    protected TextView emptyListText;

    @ViewById
    protected EnhancedRecyclerView markersList;

    @ViewById
    protected ProgressBar markersLoadingView;

    public MarkersFragment() {
        // Required empty public constructor
    }

    @AfterViews
    protected void init() {
        adapter.setOnRecyclerItemClickedListener(this);
        markersList.setEmptyView(emptyListText);
        markersList.setLayoutManager(new LinearLayoutManager(getActivity()));
        markersList.setAdapter(adapter);
        doQuery();
    }

    private void doQuery(){
        loading();
        dao.open();
        ArrayList<Marker> list = dao.getElements();
        dao.close();
        adapter.setItems(list);
        adapter.notifyDataSetChanged();
        loaded();
    }

    private void loading() {
        emptyListText.setVisibility(View.GONE);
        markersLoadingView.setVisibility(View.VISIBLE);
        markersList.setVisibility(View.GONE);
        //activity.fab.setVisibility(View.GONE);
    }

    private void loaded() {
        markersLoadingView.setVisibility(View.GONE);
        markersList.setVisibility(View.VISIBLE);
        //activity.fab.setVisibility(View.VISIBLE);
    }


    @Override
    public void onRecyclerItemClicked(int position) {
        app.startAppUserMarkerActivity(getActivity(), dao.toArgs(adapter.getItem(position)));
    }
}
