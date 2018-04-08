package com.death.paper.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by sidhantrajora on 06/08/17.
 */

public class StItemDecoration extends RecyclerView.ItemDecoration {

    private int halfSpace;
    private boolean isSource;

    public StItemDecoration(int space, boolean isSource) {
        this.halfSpace = space / 2;
        this.isSource = isSource;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getPaddingLeft() != halfSpace) {
            parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
            parent.setClipToPadding(false);
        }
        if(!isSource) {
            outRect.top = halfSpace;
            outRect.bottom = halfSpace;
            outRect.left = halfSpace;
            outRect.right = halfSpace;
        }else{
            outRect.top = halfSpace;
            outRect.bottom = 0;
            outRect.left = halfSpace;
            outRect.right = halfSpace;
        }
    }
}