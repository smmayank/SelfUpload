package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.base.Logger;


public class InputField extends BaseField implements View.OnClickListener, TextWatcher {

    private static final int NO_INPUT_TYPE = -1;
    private static final int DEFAULT_LINES = 1;
    private TextView editor;
    private TextView prefixView;
    private TextView postfixView;
    private boolean isEditable;
    private int descriptorIcon;
    private int inputType;
    private int inputLines;
    private InputFieldInteractionListener listener;
    private boolean hasPrefix;
    private boolean hasPostfix;
    private String postfixString;
    private String prefixString;
    private String hintText;

    public InputField(Context context) {
        super(context);
        init(null);
    }

    public InputField(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public InputField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.InputField, 0, 0);
        isEditable = a.getBoolean(R.styleable.InputField_editable, true);
        descriptorIcon = a.getResourceId(R.styleable.InputField_descriptorIcon, 0);
        inputType = a.getInt(R.styleable.InputField_inputType, NO_INPUT_TYPE);
        inputLines = a.getInt(R.styleable.InputField_inputLines, DEFAULT_LINES);
        prefixString = a.getString(R.styleable.InputField_prefixString);
        postfixString = a.getString(R.styleable.InputField_postfixString);
        hintText = a.getString(R.styleable.InputField_hintText);
        a.recycle();
        hasPrefix = !TextUtils.isEmpty(prefixString);
        hasPostfix = !TextUtils.isEmpty(postfixString);
    }

    protected boolean isPrefixLeftRound() {
        return hasPrefix;
    }

    protected boolean isPostFixRightRound() {
        return hasPostfix;
    }

    private TextView createDescriptor(CharSequence text) {
        TextView e;
        e = new TextView(getContext());
        e.setText(text);
        e.setTextColor(getSecondaryTextColor());
        setTextSize(e, getSecondaryTextSize());
        setViewPadding(e);
        addView(e);
        return e;
    }

    private TextView getEditor() {
        if (null == editor) {
            if (isEditable) {
                editor = new EditText(getContext());
            } else {
                editor = new TextView(getContext());
            }
            editor.setLines(inputLines);
            if (NO_INPUT_TYPE != inputType) {
                editor.setInputType(inputType);
            }
            editor.setTextColor(getSecondaryTextColor());
            editor.setGravity(Gravity.TOP);
            editor.setCompoundDrawablesWithIntrinsicBounds(0, 0, descriptorIcon, 0);
            if (isEditable) {
                editor.addTextChangedListener(this);
            } else {
                editor.setOnClickListener(this);
            }
            editor.setHint(hintText);
            addView(editor);
        }
        setTextSize(editor, getSecondaryTextSize());
        setViewBackground(editor, isEditorLeftRound(), isEditorRightRound());
        setViewPadding(editor);
        return editor;
    }

    protected boolean isEditorLeftRound() {
        return !hasPrefix;
    }

    protected boolean isEditorRightRound() {
        return !hasPostfix;
    }

    public void setInputFieldInteractionListener(InputFieldInteractionListener listener) {
        this.listener = listener;
    }

    @Override
    protected int measureChildren(int width) {
        editor = getEditor();
        prefixView = getPrefixView();
        postfixView = getPostfixView();
        int strokeWidthDiff = getStrokeWidth() >> 1;
        if (isVisible(prefixView)) {
            measureView(prefixView, 0);
            width -= prefixView.getMeasuredWidth();
            width += strokeWidthDiff;
        }
        if (isVisible(postfixView)) {
            measureView(postfixView, 0);
            width -= postfixView.getMeasuredWidth();
            width += strokeWidthDiff;
        }
        return measureView(editor, width);
    }

    private TextView getPrefixView() {
        if (hasPrefix) {
            if (null == prefixView) {
                prefixView = createDescriptor(prefixString);
            }
            setViewBackground(prefixView, isPrefixLeftRound(), false);
        }
        return prefixView;
    }

    private TextView getPostfixView() {
        if (hasPostfix) {
            if (null == postfixView) {
                postfixView = createDescriptor(postfixString);
            }
            setViewBackground(postfixView, false, isPostFixRightRound());
        }
        return postfixView;
    }

    @Override
    protected void layoutChildren(int l, int t, int r, int b) {
        int strokeWidthDiff = getStrokeWidth() >> 1;
        if (isVisible(prefixView)) {
            prefixView.layout(l, t, l + prefixView.getMeasuredWidth() + strokeWidthDiff, b);
            l += prefixView.getMeasuredWidth() - strokeWidthDiff;
        }
        if (isVisible(postfixView)) {
            postfixView.layout(r - postfixView.getMeasuredWidth() - strokeWidthDiff, t, r, b);
            r -= postfixView.getMeasuredWidth() - strokeWidthDiff;
        }
        editor.layout(l, t, r, b);
    }

    @Override
    public void onClick(View v) {
        Logger.logD(this, "Search Clicked");
        sendClickResult();
    }

    private void sendClickResult() {
        if (null != listener && !isEditable) {
            listener.onInputFieldClicked(this);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        setTextSize(editor, TextUtils.isEmpty(s) ? getSecondaryTextSize() : getPrimaryTextSize());
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        sendChangeResult(s);

    }

    private void sendChangeResult(CharSequence text) {
        if (null != listener && isEditable) {
            listener.onInputFieldChanged(this, text);
        }
    }

    public void setText (CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            getEditor().setText(text);
        }

    }

    public void setEnabled (boolean enabled) {
        getEditor().setEnabled(enabled);
    }

    public interface InputFieldInteractionListener {
        void onInputFieldClicked(InputField field);

        void onInputFieldChanged(InputField field, CharSequence text);
    }
}
