package com.example.appanimals.Admin.Adapter;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appanimals.Model.ItemTouchHelperListener;

public class RecyclerViewitemtouchhelperScheduling extends ItemTouchHelper.SimpleCallback {

    private ItemTouchHelperListener listener;
    public RecyclerViewitemtouchhelperScheduling(int dragDirs, int swipeDirs, ItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (listener != null)
        {
            listener.onSwiped(viewHolder);
        }

    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {

        if (viewHolder != null)
        {
            View foregroundView = ((SchedulingAdapter.SchedulingViewHolder)viewHolder).layoutForegroundScheduling;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        View foregroundView = ((SchedulingAdapter.SchedulingViewHolder)viewHolder).layoutForegroundScheduling;
        getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        View foregroundView = ((SchedulingAdapter.SchedulingViewHolder)viewHolder).layoutForegroundScheduling;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View foregroundView = ((SchedulingAdapter.SchedulingViewHolder)viewHolder).layoutForegroundScheduling;
        getDefaultUIUtil().clearView(foregroundView);
    }
}
