package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mayank.selfuploadform.R;

/**
 * Created by rahulchandnani on 26/02/16
 */
public class CountPicker extends LinearLayout implements View.OnClickListener {

    private static final int PREFIX = -1;
    private static final int POSTFIX = 1;

    private static final String TEXT_FORMAT = "%s (%s)";

    private String text;
    private String prefixText;
    private String postfixText;
    private int textColor;
    private int prefixColor;
    private int postfixColor;
    private float textSize;
    private float prefixTextSize;
    private float postfixTextSize;
    private int count;
    private TextView textView;
    private OnNumberChangedListener onNumberChangedListener;

    public CountPicker(Context context) {
        this(context, null);
    }

    public CountPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CountPicker);
        text = typedArray.getString(R.styleable.CountPicker_text);
        prefixText = typedArray.getString(R.styleable.CountPicker_prefixText);
        postfixText = typedArray.getString(R.styleable.CountPicker_postfixText);
        textColor = typedArray.getColor(R.styleable.CountPicker_textColor, ContextCompat.getColor(getContext(), R
                .color.black_54pc));
        prefixColor = typedArray.getColor(R.styleable.CountPicker_prefixColor, ContextCompat.getColor(getContext(),
                R.color.post_call_widget_yes_color));
        postfixColor = typedArray.getColor(R.styleable.CountPicker_postfixColor, ContextCompat.getColor(getContext(),
                R.color.post_call_widget_yes_color));
        textSize = typedArray.getDimension(R.styleable.CountPicker_centerTextSize, getResources()
                .getDimensionPixelSize(R.dimen.font_size_5));
        prefixTextSize = typedArray.getDimension(R.styleable.CountPicker_prefixTextSize, getResources()
                .getDimensionPixelSize(R.dimen.font_size_8));
        postfixTextSize = typedArray.getDimension(R.styleable.CountPicker_postfixTextSize, getResources()
                .getDimensionPixelSize(R.dimen.font_size_8));
        typedArray.recycle();
        initViews();
    }

    private void initViews() {
        this.setOrientation(HORIZONTAL);
        count = 0;
        setupPrefix();
        setupText();
        setupPostfix();
    }

    private void setupPostfix() {
        TextView postfixTextView = new TextView(getContext());
        LayoutParams postfixLayoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        postfixLayoutParams.weight = 1;
        postfixTextView.setLayoutParams(postfixLayoutParams);
        postfixTextView.setText(postfixText);
        postfixTextView.setTextColor(postfixColor);
        postfixTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, postfixTextSize);
        postfixTextView.setGravity(Gravity.CENTER);
        postfixTextView.setOnClickListener(this);
        postfixTextView.setTag(POSTFIX);
        postfixTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.translucent_black_border));
        this.addView(postfixTextView);
    }

    private void setupText() {
        textView = new TextView(getContext());
        LayoutParams textLayoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        textLayoutParams.weight = 3;
        textView.setLayoutParams(textLayoutParams);
        setText();
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(null);
        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.translucent_black_border));
        this.addView(textView);
    }

    private void setupPrefix() {
        TextView prefixTextView = new TextView(getContext());
        LayoutParams prefixLayoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        prefixLayoutParams.weight = 1;
        prefixTextView.setLayoutParams(prefixLayoutParams);
        prefixTextView.setText(prefixText);
        prefixTextView.setTextColor(prefixColor);
        prefixTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, prefixTextSize);
        prefixTextView.setGravity(Gravity.CENTER);
        prefixTextView.setOnClickListener(this);
        prefixTextView.setTag(PREFIX);
        prefixTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.translucent_black_border));
        this.addView(prefixTextView);
    }

    private void setText() {
        String str = String.format(TEXT_FORMAT, text, count);
        Spannable textToSet = new SpannableString(str);
        if (0 != count) {
            textToSet.setSpan(new ForegroundColorSpan(Color.BLACK), 0, str.indexOf("(") - 1,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textToSet.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.chatbuttonsend)),
                    str.indexOf("("), str.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            textView.setText(textToSet);
        } else {
            textView.setTextColor(textColor);
            textView.setText(str);
        }
    }

    public void setOnNumberChangedListener(OnNumberChangedListener onNumberChangedListener) {
        this.onNumberChangedListener = onNumberChangedListener;
    }

    @Override
    public void onClick(View v) {
        int operation = (int) v.getTag();
        if (0 <= count + operation) {
            count += operation;
            setText();
            if (null != onNumberChangedListener) {
                onNumberChangedListener.onNumberChanged(this, count);
            }
        }
    }

    public interface OnNumberChangedListener {

        void onNumberChanged(View view, int count);

    }
}