package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mayank.selfuploadform.R;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 29/02/16
 */
public class ValueInputField extends LinearLayout implements AdapterView.OnItemSelectedListener, TextWatcher {

    private static final int TOTAL_WEIGHT = 8;
    private static final int PREFIX_WEIGHT = 1;
    private static final int SPINNER_WEIGHT = 2;
    private static final int POSTFIX_WEIGHT = 1;

    private String titleText;
    private String prefixText;
    private String hint;
    private String postfixText;
    private int prefixTextColor;
    private int postfixTextColor;
    private int hintTextColor;
    private int titleTextColor;
    private float prefixSize;
    private float postfixSize;
    private float hintSize;
    private float titleTextSize;

    private int prefixWeight = 0;
    private int centerWeight = 0;
    private int spinnerWeight = 0;
    private int postfixWeight = 0;
    private EditText editText;
    private Drawable borderDrawable;
    private String[] entries;
    private Double[] conversions;
    private Double conversionFactor;
    private ArrayList<ValueChangedListener> valueChangedListeners;
    private LinearLayout holder;
    private Spinner spinner;

    public ValueInputField(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ValueInputField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ValueInputField);
        prefixText = typedArray.getString(R.styleable.ValueInputField_prefix);
        CharSequence[] spinnerEntries = typedArray.getTextArray(R.styleable.ValueInputField_spinnerEntries);
        titleText = typedArray.getString(R.styleable.ValueInputField_titleText);
        hint = typedArray.getString(R.styleable.ValueInputField_hint);
        postfixText = typedArray.getString(R.styleable.ValueInputField_postfix);
        prefixTextColor = typedArray.getColor(R.styleable.ValueInputField_prefixTextColor, ContextCompat.getColor
                (getContext(), R.color.black_54pc));
        postfixTextColor = typedArray.getColor(R.styleable.ValueInputField_postfixTextColor, ContextCompat.getColor
                (getContext(), R.color.black_54pc));
        hintTextColor = typedArray.getColor(R.styleable.ValueInputField_hintTextColor, ContextCompat.getColor
                (getContext(), R.color.black_30_percent_opacity));
        titleTextColor = typedArray.getColor(R.styleable.ValueInputField_titleColor, ContextCompat.getColor
                (getContext(), R.color.black_54pc));
        prefixSize = typedArray.getDimension(R.styleable.ValueInputField_prefixSize, getResources().getDimension(R
                .dimen.font_size_20));
        postfixSize = typedArray.getDimension(R.styleable.ValueInputField_postfixSize, getResources().getDimension(R
                .dimen.font_size_20));
        hintSize = typedArray.getDimension(R.styleable.ValueInputField_hintSize, getResources().getDimension(R
                .dimen.font_size_14));
        titleTextSize = typedArray.getDimension(R.styleable.ValueInputField_titleTextSize, getResources().getDimension
                (R.dimen.font_size_14));
        typedArray.recycle();
        borderDrawable = ContextCompat.getDrawable(getContext(), R.drawable.translucent_black_border);
        createConversionArray(spinnerEntries);
        calculateWeights();
        valueChangedListeners = new ArrayList<>();
        conversionFactor = 1.0;
        initViews();
    }

    private void createConversionArray(CharSequence[] spinnerEntries) {
        if (null != spinnerEntries) {
            try {
                entries = new String[spinnerEntries.length];
                conversions = new Double[spinnerEntries.length];
                int i = 0;
                for (CharSequence str : spinnerEntries) {
                    String[] splitArray = str.toString().split(",");
                    entries[i] = splitArray[0];
                    conversions[i] = Double.parseDouble(splitArray[1]);
                    i++;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void initViews() {
        this.setOrientation(VERTICAL);
        createTitleText();
        createPrefixView();
        createCenterView();
        createSpinnerView();
        createPostfixView();
    }

    private void createTitleText() {
        if (null != titleText) {
            TextView textView = new TextView(getContext());
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(layoutParams);
            textView.setText(titleText);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
            textView.setTextColor(titleTextColor);
            int padding = getResources().getDimensionPixelSize(R.dimen.dimen_8dp);
            textView.setPadding(0, padding, 0, padding);
            this.addView(textView);
        }
        holder = new LinearLayout(getContext());
        holder.setOrientation(HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        layoutParams.height = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        holder.setLayoutParams(layoutParams);
        this.addView(holder);
    }

    public void setValue(Double value, Double conversionFactor) {
        if (null != value && null != conversionFactor) {
            editText.removeTextChangedListener(this);
            editText.setText(String.valueOf(value));
            if (null != conversions) {
                for (int i = 0; i < conversions.length; i++) {
                    if (conversionFactor.equals(conversions[i])) {
                        spinner.setSelection(i);
                        break;
                    }
                }
            }
        }
    }

    private void createPostfixView() {
        if (0 != postfixWeight) {
            TextView postfixTextView = new TextView(getContext());
            LayoutParams layoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            layoutParams.weight = postfixWeight;
            layoutParams.gravity = Gravity.CENTER;
            postfixTextView.setLayoutParams(layoutParams);
            postfixTextView.setText(postfixText);
            postfixTextView.setGravity(Gravity.CENTER);
            postfixTextView.setTextColor(postfixTextColor);
            postfixTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, postfixSize);
            postfixTextView.setBackground(borderDrawable);
            holder.addView(postfixTextView);
        }
    }

    private void createSpinnerView() {
        if (0 != spinnerWeight) {
            spinner = new Spinner(getContext());
            LayoutParams layoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            layoutParams.weight = spinnerWeight;
            layoutParams.gravity = Gravity.CENTER;
            spinner.setLayoutParams(layoutParams);
            spinner.setGravity(Gravity.CENTER);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(getContext(), R.layout.self_upload_spinner_item, R.id.text, entries);
            adapter.setDropDownViewResource(R.layout.self_upload_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
            spinner.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.dark_grey_border));
            holder.addView(spinner);
        }
    }

    private void createCenterView() {
        editText = new EditText(getContext());
        LayoutParams layoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.weight = centerWeight;
        editText.setLayoutParams(layoutParams);
        editText.setHint(hint);
        editText.setHintTextColor(hintTextColor);
        editText.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        editText.setBackground(borderDrawable);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editText.addTextChangedListener(this);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, hintSize);
        editText.setPadding(getResources().getDimensionPixelSize(R.dimen.dimen_6dp), 0, 0, 0);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(3);
        editText.setFilters(filterArray);
        holder.addView(editText);
    }

    private void createPrefixView() {
        if (0 != prefixWeight) {
            TextView prefixTextView = new TextView(getContext());
            LayoutParams layoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            layoutParams.weight = prefixWeight;
            layoutParams.gravity = Gravity.CENTER;
            prefixTextView.setLayoutParams(layoutParams);
            prefixTextView.setText(prefixText);
            prefixTextView.setGravity(Gravity.CENTER);
            prefixTextView.setTextColor(prefixTextColor);
            prefixTextView.setBackground(borderDrawable);
            prefixTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, prefixSize);
            holder.addView(prefixTextView);
        }
    }

    private void calculateWeights() {
        if (null != prefixText) {
            prefixWeight = PREFIX_WEIGHT;
        }
        if (null != postfixText) {
            postfixWeight = POSTFIX_WEIGHT;
        }
        if (null != entries) {
            spinnerWeight = SPINNER_WEIGHT;
        }
        centerWeight = TOTAL_WEIGHT - prefixWeight - postfixWeight - spinnerWeight;
    }

    public void addValueChangedListener(ValueChangedListener valueChangedListener) {
        if (!this.valueChangedListeners.contains(valueChangedListener)) {
            this.valueChangedListeners.add(valueChangedListener);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        conversionFactor = conversions[position];
        calculateConvertedValue();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        conversionFactor = 1.0;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        calculateConvertedValue();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void calculateConvertedValue() {
        if (!TextUtils.isEmpty(editText.getText().toString())) {
            try {
                if (!valueChangedListeners.isEmpty()) {
                    Double value = Double.parseDouble(editText.getText().toString());
                    for (ValueChangedListener valueChangedListener : valueChangedListeners) {
                        valueChangedListener.onValueChanged(this, value, conversionFactor);
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public interface ValueChangedListener {

        void onValueChanged(ValueInputField papaInputField, Double value, Double conversionFactor);

    }
}
