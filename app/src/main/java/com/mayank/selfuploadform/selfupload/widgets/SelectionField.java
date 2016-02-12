package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.mayank.selfuploadform.base.Logger;
import com.mayank.selfuploadform.R;

import java.util.ArrayList;
import java.util.Collections;

public class SelectionField extends BaseField implements View.OnClickListener {

  private float borderWidth;

  private ArrayList<CharSequence> entries;
  private SparseArray<View> fieldViews;

  private int fieldWidth;
  private int fieldHeight;
  private int entryPadding;

  public SelectionField(Context context) {
    super(context);
    init(null);
  }

  public SelectionField(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  public SelectionField(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SelectionField, 0, 0);
    CharSequence[] values = a.getTextArray(R.styleable.SelectionField_entries);
    entryPadding = a.getDimensionPixelSize(R.styleable.SelectionField_entryPadding, 0);
    a.recycle();
    borderWidth = getResources().getDimension(R.dimen.dimen_1dp) / 2;
    fieldViews = new SparseArray<>();
    entries = new ArrayList<>();
    addEntries(values);
  }

  public void addEntries(CharSequence... values) {
    if (null == values || 0 == values.length) {
      return;
    }
    Collections.addAll(entries, values);
    requestLayout();
  }

  public void clearAll() {
    entries.clear();
    requestLayout();
  }

  @Override
  protected int measureChildren(int width) {
    if (entries.isEmpty()) {
      return 0;
    }
    int size = entries.size();
    fieldWidth = width / size;
    fieldHeight = 0;
    for (int i = 0; i < size; i++) {
      View view = getFieldView(i, entries.get(i));
      fieldHeight = measureView(view, fieldWidth);
    }
    return fieldHeight;
  }

  private View getFieldView(int index, CharSequence text) {
    View view = fieldViews.get(index);
    if (null != view) {
      return view;
    }
    return createFieldView(index, text);
  }

  private View createFieldView(int index, CharSequence text) {
    TextView textView = new TextView(getContext());
    textView.setLines(2);
    textView.setGravity(Gravity.CENTER);
    textView.setBackgroundResource(R.drawable.selection_background);
    textView.setTextColor(getsecondaryTextColor());
    textView.setPadding(entryPadding, entryPadding, entryPadding, entryPadding);
    textView.setOnClickListener(this);
    setTextSize(textView, getSecondaryTextSize());
    addView(textView);
    textView.setText(text);
    fieldViews.put(index, textView);
    return textView;
  }

  @Override
  protected void layoutChildren(int l, int t, int r, int b) {
    if (entries.isEmpty()) {
      return;
    }
    int size = entries.size();
    for (int i = 0; i < size; i++) {
      View view = getFieldView(i, entries.get(i));
      view.layout(l, t, (int) (l + fieldWidth + borderWidth), t + fieldHeight);
      l += fieldWidth - borderWidth;
    }
  }

  @Override
  public void onClick(View v) {
    if (v.isSelected()) {
      return;
    }
    int size = entries.size();
    int index = fieldViews.indexOfValue(v);
    for (int i = 0; i < size; i++) {
      if (index != i) {
        fieldViews.get(i).setSelected(false);
      }
    }
    v.setSelected(true);
    Logger.logD(this, "index %d", index);
  }
}
