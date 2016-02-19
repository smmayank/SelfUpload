package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.mayank.selfuploadform.R;

public class InputSelectionCheckboxField extends InputSelectionField {
  private CheckBox checkBox;
  private int checkBoxHeight;

  public InputSelectionCheckboxField(Context context) {
    this(context, null);
  }

  public InputSelectionCheckboxField(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    TypedArray a = getContext()
            .obtainStyledAttributes(attrs, R.styleable.InputSelectionCheckboxField, 0, 0);
    CharSequence text = a.getString(R.styleable.InputSelectionCheckboxField_checkboxText);
    a.recycle();
    checkBox = createCheckbox(text);
  }

  private CheckBox createCheckbox(CharSequence text) {
    CheckBox cb = new CheckBox(getContext());
    cb.setText(text);
    cb.setTextColor(getPrimaryTextColor());
    setTextSize(cb, getSecondaryTextSize());
    addView(cb);
    return cb;
  }

  @Override
  protected int measureChildren(int width) {
    int height = super.measureChildren(width);
    checkBoxHeight = measureView(checkBox, width);
    height += checkBoxHeight;
    height += getHorizontalMargin();
    return height;
  }

  @Override
  protected void layoutChildren(int l, int t, int r, int b) {
    int widthDiff = 0;
    checkBox.layout(l + widthDiff, b - checkBoxHeight, r - widthDiff, b);
    b -= getHorizontalMargin();
    b -= checkBoxHeight;
    super.layoutChildren(l, t, r, b);
  }
}
