package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.mayank.selfuploadform.R;

import java.util.Locale;

public class NumberField extends BaseField implements View.OnClickListener {
  private static final int MIN_VALUE = 0;
  private static final int BRACE_START = '(';
  private static final String LEFT_TEXT = "-";
  private static final String RIGHT_TEXT = "+";
  private String counterText;
  private ForegroundColorSpan colorSpan;
  private TextView counterView;
  private TextView leftClicker;
  private TextView rightClicker;
  private int clickerWidth;
  private int counter;
  private NumberFieldInteractionListener listener;
  private int selectionColor;
  private float clickerTextSize;

  public NumberField(Context context) {
    super(context);
    init(null);
  }

  public NumberField(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  public NumberField(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.NumberField, 0, 0);
    counterText = a.getString(R.styleable.NumberField_contentText);
    selectionColor = a.getColor(R.styleable.NumberField_selectionColor,
            ContextCompat.getColor(getContext(), R.color.primary));
    clickerWidth = a.getDimensionPixelSize(R.styleable.NumberField_clickerWidth, getResources()
            .getDimensionPixelSize(R.dimen.number_filed_cliker_default_width));
    clickerTextSize = a.getDimension(R.styleable.NumberField_clickerTextSize, getResources()
            .getDimension(R.dimen.fontsize_18));
    a.recycle();

    colorSpan = new ForegroundColorSpan(selectionColor);
    leftClicker = createClicker(LEFT_TEXT, true, false);
    rightClicker = createClicker(RIGHT_TEXT, false, true);
    createCounter();

    setCounter(MIN_VALUE);
  }

  private TextView createClicker(String leftText, boolean leftRound, boolean rightRound) {
    TextView t = new TextView(getContext());
    setViewBackground(t, leftRound, rightRound);
    t.setText(leftText);
    t.setTextColor(selectionColor);
    setTextSize(t, clickerTextSize);
    t.setGravity(Gravity.CENTER);
    t.setOnClickListener(this);
    setViewPadding(t);
    addView(t);
    return t;
  }

  public void setOnNumberFieldInteractionListener(NumberFieldInteractionListener listener) {
    this.listener = listener;
  }

  public int getCounter() {
    return counter;
  }

  public void setCounter(int counter) {
    if (counter < MIN_VALUE) {
      return;
    }
    this.counter = counter;
    counterView.setText(createCounterText());
    requestLayout();
    sendResult(counter);
  }

  private void sendResult(int counter) {
    if (null != listener) {
      listener.onNumberSelected(this, counter);
    }
  }

  private Spannable createCounterText() {
    String text = String.format(Locale.ENGLISH, "%s (%d)", counterText, this.counter);
    SpannableStringBuilder builder = new SpannableStringBuilder(text);
    int index = text.lastIndexOf(BRACE_START);
    builder.setSpan(colorSpan, index, builder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    return builder;
  }

  private void createCounter() {
    counterView = new TextView(getContext());
    counterView.setTextColor(getSecondaryTextColor());
    setTextSize(counterView, getSecondaryTextSize());
    setViewBackground(counterView, false, false);
    counterView.setGravity(Gravity.CENTER);
    addView(counterView);
  }

  @Override
  protected int measureChildren(int width) {
    int strokeWidthDiff = getStrokeWidth() >> 1;
    clickerWidth += strokeWidthDiff;
    int clickerHeight = measureView(leftClicker, clickerWidth);
    measureView(rightClicker, clickerWidth, clickerHeight);
    int counterWidth = width - (clickerWidth << 1);
    measureView(counterView, counterWidth, clickerHeight);
    return clickerHeight;
  }

  @Override
  protected void layoutChildren(int l, int t, int r, int b) {
    int strokeWidthDiff = getStrokeWidth() >> 1;
    leftClicker.layout(l, t, l + clickerWidth, b);
    l += clickerWidth - strokeWidthDiff;
    rightClicker.layout(r - clickerWidth, t, r, b);
    r -= clickerWidth - strokeWidthDiff;
    counterView.layout(l, t, r, b);
  }

  @Override
  public void onClick(View v) {
    int curVal = getCounter();
    if (leftClicker.equals(v)) {
      setCounter(--curVal);
    } else {
      setCounter(++curVal);
    }
  }

  public interface NumberFieldInteractionListener {
    public void onNumberSelected(NumberField field, int value);
  }
}
