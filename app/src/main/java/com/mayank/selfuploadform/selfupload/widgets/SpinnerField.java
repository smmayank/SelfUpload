package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.widgets.EntryAdaptor.EntryAdaptorCallback;


public class SpinnerField extends BaseField implements EntryAdaptorCallback<CharSequence> {

  private Spinner spinner;
  private EntryAdaptor<CharSequence> adapter;
  private CharSequence defaultString;

  public SpinnerField(Context context) {
    super(context);
    init(null);
  }

  public SpinnerField(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  public SpinnerField(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SpinnerField, 0, 0);
    CharSequence[] values = a.getTextArray(R.styleable.SpinnerField_entries);
    a.recycle();
    defaultString = getContext().getString(R.string.default_selection);
    createSpinner();
    addEntries(values);
  }

  public void addEntries(CharSequence... values) {
    if (null == values || 0 == values.length) {
      return;
    }
    adapter.addEntries(values);
  }

  private void createSpinner() {
    spinner = new Spinner(getContext());
    spinner.setBackgroundResource(R.drawable.spinner_background);
    adapter = new EntryAdaptor<>(this);
    adapter.setDefaultEntry(defaultString);
    spinner.setAdapter(adapter);
    addView(spinner);
  }

  @Override
  protected int measureChildren(int width) {
    return measureView(spinner, width);
  }

  @Override
  protected void layoutChildren(int l, int t, int r, int b) {
    spinner.layout(l, t, r, b);
  }

  @Override
  public void updateView(View convertView, CharSequence charSequence) {
    TextView t = (TextView) convertView;
    t.setText(charSequence);
  }

  @Override
  public View createView() {
    TextView t = new TextView(getContext());
    t.setBackgroundResource(R.drawable.spinner_entry_background);
    setTextSize(t, getSecondaryTextSize());
    t.setTextColor(getsecondaryTextColor());
    return t;
  }
}
