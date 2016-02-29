package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mayank.selfuploadform.R;

/**
 * Created by rahulchandnani on 29/02/16
 */
public class PapaSpinner extends LinearLayout {

    private static final int TOTAL_WEIGHT = 8;
    private static final int PREFIX_WEIGHT = 1;
    private static final int SPINNER_WEIGHT = 2;
    private static final int POSTFIX_WEIGHT = 1;

    private String prefixText;
    private CharSequence[] spinnerEntries;
    private String hint;
    private String postfixText;
    private int prefixWeight = 0;
    private int centerWeight = 0;
    private int spinnerWeight = 0;
    private int postfixWeight = 0;
    private int prefixTextColor;
    private int postfixTextColor;
    private int hintTextColor;

    public PapaSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PapaSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PapaSpinner);
        prefixText = typedArray.getString(R.styleable.PapaSpinner_prefixText);
        spinnerEntries = typedArray.getTextArray(R.styleable.PapaSpinner_spinnerEntries);
        hint = typedArray.getString(R.styleable.PapaSpinner_hint);
        postfixText = typedArray.getString(R.styleable.PapaSpinner_postfixText);
        prefixTextColor = typedArray.getColor(R.styleable.PapaSpinner_prefixTextColor, ContextCompat.getColor
                (getContext(), R.color.black_54pc));
        postfixTextColor = typedArray.getColor(R.styleable.PapaSpinner_postfixTextColor, ContextCompat.getColor
                (getContext(), R.color.black_54pc));

        typedArray.recycle();
        decideWeights();
        initViews();
    }

    private void initViews() {
        createPrefixView();
    }

    private void createPrefixView() {
        if (0 != prefixWeight) {
            TextView prefixTextView = new TextView(getContext());
            LayoutParams layoutParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
            layoutParams.weight = prefixWeight;
            layoutParams.gravity = Gravity.CENTER;
            
        }
    }

    private void decideWeights() {
        if (null != prefixText) {
            prefixWeight = PREFIX_WEIGHT;
        }
        if (null != postfixText) {
            postfixWeight = POSTFIX_WEIGHT;
        }
        if (null != spinnerEntries) {
            spinnerWeight = SPINNER_WEIGHT;
        }
        centerWeight = TOTAL_WEIGHT - prefixWeight - postfixWeight - spinnerWeight;
    }


}
