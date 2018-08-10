package com.dvl.tablayout_materialdesign;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Admin on 15/8/2016.
 */
public class LineEditText extends EditText {
    private Paint mPaint = new Paint();

    public LineEditText(Context context) {
        super(context);
        initPaint();
    }

    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public LineEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
    }

    private void initPaint() {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0x80000000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int left = getLeft();
        int right = getRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int height = getHeight();
        int lineHeight = getLineHeight();
        int count = (height - paddingTop - paddingBottom) / lineHeight;
        int current = getLineCount();
        if(current>count) count=current;

        for (int i = 0; i < count; i++) {
            int baseline = lineHeight * (i + 1) + paddingTop;
            canvas.drawLine(left + paddingLeft, baseline, right - paddingRight, baseline, mPaint);
        }
            super.onDraw(canvas);
        }
    }

