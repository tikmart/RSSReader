package com.example.tigran.rssreaderapp;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Tigran on 29/01/2017.
 */

public class VerticalSpace extends RecyclerView.ItemDecoration {
    int space;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.bottom = space;
        outRect.right = space;
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space;
        }
    }

    public VerticalSpace(int space) {
        this.space = space;

    }
}
