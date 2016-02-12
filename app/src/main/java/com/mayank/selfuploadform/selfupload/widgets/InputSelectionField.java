package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.widgets.EntryAdaptor.EntryAdaptorCallback;


public class InputSelectionField extends InputField implements EntryAdaptorCallback<CharSequence> {

  private Spinner spinner;
  private CharSequence defaultString;
  private EntryAdaptor<CharSequence> adapter;
  private int spinnerWidth;

  private int borderWidth;

  public InputSelectionField(Context context) {
    super(context);
    init(null);
  }

  public InputSelectionField(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  public InputSelectionField(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  public void addEntries(CharSequence... values) {
    if (null == values || 0 == values.length) {
      return;
    }
    adapter.addEntries(values);
  }

  private void init(AttributeSet attrs) {
    TypedArray a =
            getContext().obtainStyledAttributes(attrs, R.styleable.InputSelectionField, 0, 0);
    CharSequence[] textArray = a.getTextArray(R.styleable.InputSelectionField_entries);
    spinnerWidth = a.getDimensionPixelSize(R.styleable.InputSelectionField_spinnerWidth,
            getResources().getDimensionPixelOffset(R.dimen.input_selection_field_default_width));
    a.recycle();
    defaultString = getContext().getString(R.string.default_selection);
    borderWidth = getResources().getDimensionPixelSize(R.dimen.dimen_1dp) >> 1;
    createSpinner();
    addEntries(textArray);
  }

  private void createSpinner() {
    spinner = new Spinner(getContext());
    spinner.setBackgroundResource(R.drawable.input_spinner_background);
    adapter = new EntryAdaptor<>(this);
    adapter.setDefaultEntry(defaultString);
    spinner.setAdapter(adapter);
    addView(spinner);
  }

  @Override
  protected int measureChildren(int width) {
    width -= spinnerWidth - borderWidth;
    int spinnerHeight = super.measureChildren(width);
    measureView(spinner, spinnerWidth + borderWidth, spinnerHeight);
    return spinnerHeight;
  }

  @Override
  protected void layoutChildren(int l, int t, int r, int b) {
    spinner.layout(r - spinnerWidth - borderWidth, t, r, b);
    r -= spinnerWidth - borderWidth;
    super.layoutChildren(l, t, r, b);
  }

  @Override
  public void updateView(View convertView, CharSequence charSequence) {
    TextView t = (TextView) convertView;
    t.setText(charSequence);
  }

  @Override
  public View createView() {
    TextView t = new TextView(getContext());
    t.setBackgroundResource(R.drawable.input_spinner_entry_background);
    setTextSize(t, getSecondaryTextSize());
    t.setTextColor(getsecondaryTextColor());
    return t;
  }
}
