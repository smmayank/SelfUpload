package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mayank.selfuploadform.R;

import java.util.HashMap;

/**
 * Created by rahulchandnani on 25/02/16
 */
public class TagSelectorField extends LinearLayout implements View.OnClickListener {

    private CharSequence[] values;
    private int numOfColumns;
    private int selectedTextColor;
    private int defaultTextColor;
    private Drawable selectedBackground;
    private Drawable defaultBackground;
    private int contentPadding;
    private HashMap<String, TextView> map;
    private TextView selectedTextView;
    private int fontSize;

    public TagSelectorField(Context context) {
        this(context, null);
    }

    public TagSelectorField(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagSelectorField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        this.setOrientation(VERTICAL);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TagSelectorField);
        values = typedArray.getTextArray(R.styleable.TagSelectorField_entries);
        numOfColumns = typedArray.getInt(R.styleable.TagSelectorField_numOfColumns, getResources().getInteger(R
                .integer.default_tag_selector_columns));
        selectedTextColor = typedArray.getColor(R.styleable.TagSelectorField_selectedTextColor, ContextCompat
                .getColor(getContext(), android.R.color.black));
        defaultTextColor = typedArray.getColor(R.styleable.TagSelectorField_defaultTextColor, ContextCompat
                .getColor(getContext(), android.R.color.white));
        selectedBackground = typedArray.getDrawable(R.styleable.TagSelectorField_selectedBackground);
        if (null == selectedBackground) {
            selectedBackground = new ColorDrawable(defaultTextColor);
        }
        defaultBackground = typedArray.getDrawable(R.styleable.TagSelectorField_defaultBackground);
        if (null == defaultBackground) {
            defaultBackground = new ColorDrawable(selectedTextColor);
        }
        contentPadding = typedArray.getDimensionPixelSize(R.styleable.TagSelectorField_contentPadding, getResources()
                .getDimensionPixelSize(R.dimen.dimen_6dp));
        fontSize = typedArray.getDimensionPixelSize(R.styleable.TagSelectorField_textSize, getResources()
                .getDimensionPixelSize(R.dimen.fontsize_12));
        typedArray.recycle();
        map = new HashMap<>();
        this.removeAllViews();
        addToLayout();
    }

    private void addToLayout() {
        if (null != values && 0 != values.length) {
            int rows = (values.length % numOfColumns == 0) ? values.length / numOfColumns :
                    (int) Math.ceil((double) values.length / numOfColumns);
            for (int i = 0; i < rows; i++) {
                addView(getRowLayout());
            }
            for (int i = 0; i < rows; i++) {
                LinearLayout parent = (LinearLayout) getChildAt(i);
                for (int j = 0; j < numOfColumns; j++) {
                    int index = i * numOfColumns + j;
                    TextView textView = getTextView(values[index].toString());
                    parent.addView(textView);
                    map.put(values[index].toString(), textView);
                }
            }
        }
    }

    private TextView getTextView(String text) {
        TextView textView = new TextView(getContext());
        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.leftMargin = contentPadding / 2;
        params.rightMargin = contentPadding / 2;
        params.gravity = Gravity.CENTER;
        textView.setLayoutParams(params);
        textView.setText(text);
        setDefaultState(textView);
        textView.setGravity(Gravity.CENTER);
        textView.setForegroundGravity(Gravity.CENTER);
        textView.setTextSize(fontSize);
        textView.setOnClickListener(this);
        return textView;
    }

    private LinearLayout getRowLayout() {
        LinearLayout layout = new LinearLayout(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.topMargin = contentPadding / 2;
        params.bottomMargin = contentPadding / 2;
        params.topMargin = contentPadding / 2;
        params.bottomMargin = contentPadding / 2;
        layout.setLayoutParams(params);
        layout.setOrientation(HORIZONTAL);
        return layout;
    }

    private void setDefaultState(TextView textView) {
        if (null != textView) {
            textView.setTextColor(defaultTextColor);
            textView.setBackground(defaultBackground);
            textView.setTag(State.DEFAULT);
        }
    }

    private void setSelectedState(TextView textView) {
        if (null != textView) {
            textView.setTextColor(selectedTextColor);
            textView.setBackground(selectedBackground);
            textView.setTag(State.SELECTED);
        }
    }

    public static class State {
        public static final int DEFAULT = 0;
        public static final int SELECTED = 1;
    }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView) {
            TextView textView = (TextView) v;
            int state = (int) v.getTag();
            if (State.DEFAULT == state) {
                setDefaultState(selectedTextView);
                setSelectedState(textView);
                selectedTextView = textView;
            } else {
                setDefaultState(selectedTextView);
                selectedTextView = null;
            }

        }
    }
}
