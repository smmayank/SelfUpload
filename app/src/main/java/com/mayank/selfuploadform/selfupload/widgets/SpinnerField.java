package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.widgets.BaseField.BaseEntry;
import com.mayank.selfuploadform.selfupload.widgets.EntryAdaptor.EntryAdaptorCallback;

import java.util.List;


public class SpinnerField<T extends BaseEntry> extends BaseField implements
        EntryAdaptorCallback<T>, AdapterView.OnItemSelectedListener {

  private static final int LAYER_SIZE = 2;
  private static final int FIRST_LAYER = 0;
  private static final int SECOND_LAYER = 1;
  private Spinner spinner;
  private EntryAdaptor<T> adapter;
  private CharSequence defaultString;
  private SpinnerFieldInteractionListener<T> listener;

  public SpinnerField(Context context) {
    this(context, null);
  }

  public SpinnerField(Context context, AttributeSet attrs) {
    this(context, attrs, R.attr.baseSpinnerFieldStyle);
  }

  public SpinnerField(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, R.attr.baseSpinnerFieldStyle);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    defaultString = getContext().getString(R.string.spinner_field_default_selection);
    createSpinner();
  }

  public void addEntries(List<T> values) {
    adapter.addEntries(values);
  }

  private void createSpinner() {
    spinner = new Spinner(getContext());
    setViewBackground(spinner, getDrawable());
    adapter = new EntryAdaptor<>(this);
    adapter.setDefaultEntry(defaultString);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(this);
    addView(spinner);
  }

  private Drawable getDrawable() {
    Drawable[] layers = new Drawable[LAYER_SIZE];
    layers[FIRST_LAYER] = createViewBackground(true, true);
    Drawable bitmap = ContextCompat.getDrawable(getContext(), R.drawable.drop_down);
    layers[SECOND_LAYER] = bitmap;
    LayerDrawable drawable = new LayerDrawable(layers);
    drawable.setLayerInset(SECOND_LAYER, 0, 0,
            getResources().getDimensionPixelSize(R.dimen.dimen_13dp), 0);
    return drawable;
  }

  public void setSpinnerFieldInteractionListener(SpinnerFieldInteractionListener<T> listener) {
    this.listener = listener;
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
  public void updateView(View convertView, T entry) {
    TextView t = (TextView) convertView;
    t.setTextColor(getSecondaryTextColor());
    setTextSize(t, getSecondaryTextSize());
    t.setText(entry.getEntryText());
  }

  @Override
  public void updateDefaultView(View convertView, CharSequence defaultString) {
    TextView t = (TextView) convertView;
    t.setTextColor(getPrimaryTextColor());
    setTextSize(t, getPrimaryTextSize());
    t.setText(defaultString);
  }

  @Override
  public View createView() {
    TextView t = new TextView(getContext());
    setTextSize(t, getSecondaryTextSize());
    t.setPadding(getVerticalMargin(), getHorizontalMargin(), getVerticalMargin(),
            getHorizontalMargin());
    t.setTextColor(getSecondaryTextColor());
    return t;
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    if (EntryAdaptor.FIRST == position) {
      return;
    }
    sendResult(position - 1, adapter.getEntry(position));
  }

  private void sendResult(int i, T item) {
    if (null != listener) {
      listener.onSpinnerFieldSelected(this, i, item);
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
  }

  public interface SpinnerFieldInteractionListener<E> {
    void onSpinnerFieldSelected(SpinnerField field, int index, E entry);
  }
}
