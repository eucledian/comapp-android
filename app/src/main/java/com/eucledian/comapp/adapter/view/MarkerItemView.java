package com.eucledian.comapp.adapter.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eucledian.comapp.App;
import com.eucledian.comapp.R;
import com.eucledian.comapp.model.Marker;
import com.loopj.android.image.SmartImageView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by joel on 29/05/16.
 */
@EViewGroup(R.layout.fragment_markers_item)
public class MarkerItemView extends LinearLayout {

    @Bean
    protected App app;

    @ViewById
    protected TextView markerNameText;

    @ViewById
    protected SmartImageView markerIcon;

    public MarkerItemView(Context context) {
        super(context);
    }

    public void bind(Marker item){
        markerNameText.setText(item.getName());
        markerIcon.setImageUrl(app.getImageUrl(item.getIconUrl()));
    }
}
