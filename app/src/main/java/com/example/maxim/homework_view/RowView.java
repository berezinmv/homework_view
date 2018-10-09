package com.example.maxim.homework_view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;

public class RowView extends LinearLayoutCompat {
    private static final int OFFSET = 20;

    public RowView(Context context) {
        this(context, null);
    }

    public RowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int paddingRight = getPaddingRight();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight;
        int viewWidth = 0;
        int viewHeight = paddingTop;
        int measureState = 0;
        int childCount = getChildCount();

        int widthCursor = 0;
        int maxChildHeight = 0;
        for (int childIndex = 0; childIndex < childCount; childIndex++) {
            View child = getChildAt(childIndex);

            if (child.getVisibility() == GONE) {
                continue;
            }

            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (childWidth + widthCursor + OFFSET > maxWidth) {
                viewHeight += maxChildHeight + OFFSET;
                widthCursor = paddingRight + childWidth + OFFSET;
                maxChildHeight = childHeight;
            } else {
                widthCursor += childWidth + OFFSET;
                maxChildHeight = Math.max(maxChildHeight, childHeight);
                viewWidth = Math.max(viewWidth, widthCursor);
            }
            measureState = combineMeasuredStates(measureState, child.getMeasuredState());
        }

        viewHeight += maxChildHeight + getPaddingBottom();
        viewWidth = Math.max(viewWidth, getSuggestedMinimumWidth());
        viewHeight = Math.max(viewHeight, getSuggestedMinimumHeight());

        setMeasuredDimension(
                resolveSizeAndState(viewWidth, widthMeasureSpec, measureState),
                resolveSizeAndState(viewHeight, heightMeasureSpec, measureState << MEASURED_HEIGHT_STATE_SHIFT)
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int viewWidth = getMeasuredWidth();
        int maxChildWidth = viewWidth - paddingLeft - paddingTop;
        int childCount = getChildCount();

        int maxHeight = paddingTop;
        int heightCursor = paddingTop;
        int widthCursor = paddingRight;
        for (int childIndex = 0; childIndex < childCount; childIndex++) {
            View child = getChildAt(childIndex);

            if (child.getVisibility() == GONE) {
                continue;
            }

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (childWidth + widthCursor + OFFSET > maxChildWidth) {
                heightCursor += maxHeight + OFFSET;
                widthCursor = paddingRight + childWidth + OFFSET;
                maxHeight = childHeight;
            } else {
                widthCursor += childWidth + OFFSET;
                maxHeight = Math.max(maxHeight, childHeight);
            }

            int left = viewWidth - widthCursor + OFFSET;
            int top = heightCursor;
            int right = left + childWidth;
            int bottom = top + childHeight;
            child.layout(left, top, right, bottom);
        }
    }
}
