package com.tododeportes.tododeportesapp.gui.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by GIOVANI on 6/06/2016.
 */
public abstract class AnimatedAdapter<VH extends RecyclerView.ViewHolder, O> extends RecyclerView.Adapter<VH> {
    ArrayList<O> array;

    public O removeItem(int position) {
        final O model = array.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, O model) {
        array.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final O model = array.remove(fromPosition);
        array.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(ArrayList<O> arrayFiltered) {
        applyAndAnimateRemovals(arrayFiltered);
        applyAndAnimateAdditions(arrayFiltered);
        applyAndAnimateMovedItems(arrayFiltered);
    }

    private void applyAndAnimateMovedItems(ArrayList<O> arrayFiltered) {
        for (int toPosition = arrayFiltered.size() - 1; toPosition >= 0; toPosition--) {
            final O model = arrayFiltered.get(toPosition);
            final int fromPosition = array.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    private void applyAndAnimateAdditions(ArrayList<O> arrayFiltered) {
        for (int i = 0, count = arrayFiltered.size(); i < count; i++) {
            final O model = arrayFiltered.get(i);
            if (!array.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateRemovals(ArrayList<O> arrayFiltered) {
        for (int i = array.size() - 1; i >= 0; i--) {
            final O model = array.get(i);
            if (!arrayFiltered.contains(model)) {
                removeItem(i);
            }
        }
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public void setArray(ArrayList<O> array) {
        this.array = new ArrayList<>(array);
    }
}
