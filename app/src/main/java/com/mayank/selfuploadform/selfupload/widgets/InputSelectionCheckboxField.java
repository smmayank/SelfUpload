package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.util.AttributeSet;

public class InputSelectionCheckboxField extends InputSelectionField {
  public InputSelectionCheckboxField(Context context) {
    super(context);
  }

  public InputSelectionCheckboxField(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public InputSelectionCheckboxField(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected int measureChildren(int width) {
    int height = super.measureChildren(width);
    return height;
  }

  @Override
  protected void layoutChildren(int l, int t, int r, int b) {
    super.layoutChildren(l, t, r, b);
  }
}
