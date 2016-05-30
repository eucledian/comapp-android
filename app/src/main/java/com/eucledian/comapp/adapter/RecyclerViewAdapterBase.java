package com.eucledian.comapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.eucledian.comapp.adapter.view.ViewWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joel on 15/01/16.
 */
public abstract class RecyclerViewAdapterBase<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>>{

    protected List<T> items = new ArrayList<>();

    private RecyclerItemClicked onRecyclerItemClicked;

    public void setOnRecyclerItemClickedListener(RecyclerItemClicked listener){ onRecyclerItemClicked = listener; }

    public RecyclerItemClicked getOnRecyclerItemClicked(){ return onRecyclerItemClicked; }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public final ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType));
    }

    public T getItem(int position){ return items.get(position); }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    public boolean addItem(T item){ return items.add(item); }

    public void clear(){ items.clear(); }

    public void setItems(List<T> items){
        this.items = items;
    }

    public T removeItem(int position){ return items.remove(position); }
}
