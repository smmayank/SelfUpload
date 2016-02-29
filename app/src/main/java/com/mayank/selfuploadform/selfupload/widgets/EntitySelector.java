package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by rahulchandnani on 26/02/16
 */
public class EntitySelector extends LinearLayout {

    public EntitySelector(Context context) {
        this(context, null);
    }

    public EntitySelector(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EntitySelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

    }
}
