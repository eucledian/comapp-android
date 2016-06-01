package com.eucledian.comapp.root.sync;

import android.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eucledian.comapp.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_sync)
public class SyncFragment extends Fragment {

    @ViewById
    protected ProgressBar syncLoadingView;

    @ViewById
    protected TextView syncCopy;

    @ViewById
    protected TextView syncStatus;

    public SyncFragment() {
        // Required empty public constructor
    }

    @Click
    protected void syncBtnClicked() {
        //TODO sync
    }

    private void loading() {
        syncLoadingView.setVisibility(View.VISIBLE);
    }

    private void loaded() {
        syncLoadingView.setVisibility(View.GONE);
    }

}
