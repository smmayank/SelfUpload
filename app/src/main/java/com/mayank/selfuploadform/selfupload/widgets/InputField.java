package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mayank.selfuploadform.base.Logger;
import com.mayank.selfuploadform.R;


public class InputField extends BaseField implements View.OnClickListener {

  private static final int NO_INPUT_TYPE = -1;
  private static final int DEFAULT_LINES = 1;
  private TextView editor;
  private TextView prefixView;
  private TextView postfixView;
  private boolean isEditable;
  private int descriptorIcon;
  private int inputType;
  private int inputLines;

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
    CharSequence prefixString = a.getString(R.styleable.InputField_prefixString);
    String postfixString = a.getString(R.styleable.InputField_postfixString);
    a.recycle();
    editor = createEditor();
    if (!TextUtils.isEmpty(prefixString)) {
      prefixView = createDescriptor(prefixString, R.drawable.editor_prefix_background);
    }
    if (!TextUtils.isEmpty(postfixString)) {
      postfixView = createDescriptor(postfixString, R.drawable.editor_postfix_background);
    }
  }

  private TextView createDescriptor(CharSequence text, int background) {
    TextView e;
    e = new TextView(getContext());
    e.setText(text);
    e.setTextColor(getsecondaryTextColor());
    setTextSize(e, getSecondaryTextSize());
    e.setBackgroundResource(background);
    addView(e);
    return e;
  }

  private TextView createEditor() {
    TextView e;
    if (isEditable) {
      e = new EditText(getContext());
    } else {
      e = new TextView(getContext());
    }
    e.setLines(inputLines);
    if (NO_INPUT_TYPE != inputType) {
      e.setInputType(inputType);
    }
    e.setTextColor(getsecondaryTextColor());
    setTextSize(e, getSecondaryTextSize());
    e.setBackgroundResource(R.drawable.editor_background);
    e.setGravity(Gravity.TOP);
    e.setCompoundDrawablesWithIntrinsicBounds(0, 0, descriptorIcon, 0);
    if (!isEditable) {
      e.setOnClickListener(this);
    }
    addView(e);
    return e;
  }

  @Override
  protected int measureChildren(int width) {
    if (isVisible(prefixView)) {
      measureView(prefixView, 0);
      width -= prefixView.getMeasuredWidth();
    }
    if (isVisible(postfixView)) {
      measureView(postfixView, 0);
      width -= postfixView.getMeasuredWidth();
    }
    return measureView(editor, width);
  }

  @Override
  protected void layoutChildren(int l, int t, int r, int b) {
    if (isVisible(prefixView)) {
      prefixView.layout(l, t, l + prefixView.getMeasuredWidth(), b);
      l += prefixView.getMeasuredWidth();
    }
    if (isVisible(postfixView)) {
      postfixView.layout(r - postfixView.getMeasuredWidth(), t, r, b);
      r -= postfixView.getMeasuredWidth();
    }
    editor.layout(l, t, r, b);
  }

  @Override
  public void onClick(View v) {
    Logger.logD(this, "Search Clicked");
  }
}
