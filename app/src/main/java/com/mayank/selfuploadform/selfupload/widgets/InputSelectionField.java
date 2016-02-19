package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.widgets.BaseField.BaseEntry;
import com.mayank.selfuploadform.selfupload.widgets.EntryAdaptor.EntryAdaptorCallback;

import java.util.List;


public class InputSelectionField<T extends BaseEntry> extends InputField implements
        EntryAdaptorCallback<T> {

  private static final int LAYER_SIZE = 2;
  private static final int FIRST_LAYER = 0;
  private static final int SECOND_LAYER = 1;

  private Spinner spinner;
  private CharSequence defaultString;
  private EntryAdaptor<T> adapter;
  private int spinnerWidth;
  private float selectionTextSize;
  private int selectionBackgroundColor;

  public InputSelectionField(Context context) {
    this(context, null);
  }

  public InputSelectionField(Context context, AttributeSet attrs) {
    this(context, attrs, R.attr.baseInputFieldStyle);
  }

  public InputSelectionField(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, R.attr.baseInputFieldStyle);
    init(attrs);
  }

  public void addEntries(List<T> values) {
    adapter.addEntries(values);
  }

  private void init(AttributeSet attrs) {
    TypedArray a =
            getContext().obtainStyledAttributes(attrs, R.styleable.InputSelectionField, 0, 0);
    spinnerWidth = a.getDimensionPixelSize(R.styleable.InputSelectionField_spinnerWidth,
            getResources().getDimensionPixelOffset(R.dimen.input_selection_field_default_width));
    selectionTextSize = a.getDimensionPixelSize(R.styleable.InputSelectionField_selectionTextSize,
            getResources().getDimensionPixelOffset(R.dimen.fontsize_12));
    selectionBackgroundColor = a.getColor(R.styleable.InputSelectionField_selectionBackgroundColor,
            ContextCompat.getColor(getContext(), R.color.input_selection_default_background_color));
    a.recycle();
    defaultString = getContext().getString(R.string.spinner_field_default_selection);
    createSpinner();
  }

  @Override
  protected boolean isEditorRightRound() {
    return adapter.isEmpty();
  }

  @Override
  protected boolean isPostFixRightRound() {
    return adapter.isEmpty();
  }

  private void createSpinner() {
    spinner = new Spinner(getContext());
    setViewBackground(spinner, getDrawable());
    adapter = new EntryAdaptor<>(this);
    adapter.setDefaultEntry(defaultString);
    spinner.setAdapter(adapter);
    addView(spinner);
  }

  private Drawable getDrawable() {
    Drawable[] layers = new Drawable[LAYER_SIZE];
    layers[FIRST_LAYER] = createViewBackground(false, true, selectionBackgroundColor);
    Drawable bitmap = ContextCompat.getDrawable(getContext(), R.drawable.drop_down);
    layers[SECOND_LAYER] = bitmap;
    LayerDrawable drawable = new LayerDrawable(layers);
    drawable.setLayerInset(SECOND_LAYER, 0, 0,
            getResources().getDimensionPixelSize(R.dimen.dimen_13dp), 0);
    return drawable;
  }

  @Override
  protected int measureChildren(int width) {
    if (adapter.isEmpty()) {
      return super.measureChildren(width);
    }
    int strokeWidthDiff = getStrokeWidth() >> 1;
    width -= spinnerWidth - strokeWidthDiff;
    int spinnerHeight = super.measureChildren(width);
    measureView(spinner, spinnerWidth + strokeWidthDiff, spinnerHeight);
    return spinnerHeight;
  }

  @Override
  protected void layoutChildren(int l, int t, int r, int b) {
    if (adapter.isEmpty()) {
      super.layoutChildren(l, t, r, b);
      return;
    }
    int strokeWidthDiff = getStrokeWidth() >> 1;
    spinner.layout(r - spinnerWidth - strokeWidthDiff, t, r, b);
    r -= spinnerWidth - strokeWidthDiff;
    super.layoutChildren(l, t, r, b);
  }

  @Override
  public void updateView(View convertView, T entry) {
    TextView t = (TextView) convertView;
    t.setText(entry.getEntryText());
  }

  @Override
  public void updateDefaultView(View convertView, CharSequence defaultString) {
    TextView t = (TextView) convertView;
    t.setText(defaultString);
  }

  @Override
  public View createView() {
    TextView t = new TextView(getContext());
    setViewPadding(t);
    setTextSize(t, selectionTextSize);
    t.setTextColor(getSecondaryTextColor());
    return t;
  }
}
