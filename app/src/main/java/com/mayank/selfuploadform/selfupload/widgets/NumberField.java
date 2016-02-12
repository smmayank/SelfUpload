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
import android.widget.ImageView;
import android.widget.TextView;

import com.mayank.selfuploadform.R;

import java.util.Locale;

public class NumberField extends BaseField implements View.OnClickListener {
  private static final int MIN_VALUE = 0;
  private static final int BRACE_START = '(';
  private int borderWidth;
  private String counterText;
  private ForegroundColorSpan colorSpan;
  private TextView counterView;
  private ImageView leftClicker;
  private ImageView rightClicker;
  private int clickerWidth;
  private int counter;

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
    borderWidth = getResources().getDimensionPixelOffset(R.dimen.dimen_1dp);

    TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.NumberField, 0, 0);
    counterText = a.getString(R.styleable.NumberField_contentText);
    int selectionColor = a.getColor(R.styleable.NumberField_selectionColor,
            ContextCompat.getColor(getContext(), R.color.primary));
    a.recycle();

    colorSpan = new ForegroundColorSpan(selectionColor);
    leftClicker = createIcon(android.R.drawable.ic_delete);
    rightClicker = createIcon(android.R.drawable.ic_input_add);
    createCounter();

    setCounter(MIN_VALUE);
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
  }

  private Spannable createCounterText() {
    String text = String.format(Locale.ENGLISH, "%s (%d)", counterText, this.counter);
    SpannableStringBuilder builder = new SpannableStringBuilder(text);
    int index = text.lastIndexOf(BRACE_START);
    builder.setSpan(colorSpan, index, builder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    return builder;
  }

  private ImageView createIcon(int imgSrc) {
    ImageView imageView = new ImageView(getContext());
    imageView.setImageResource(imgSrc);
    imageView.setBackgroundResource(R.drawable.picker_background);
    imageView.setScaleType(ImageView.ScaleType.CENTER);
    addView(imageView);
    imageView.setOnClickListener(this);
    return imageView;
  }


  private void createCounter() {
    counterView = new TextView(getContext());
    counterView.setTextColor(getsecondaryTextColor());
    setTextSize(counterView, getSecondaryTextSize());
    counterView.setBackgroundResource(R.drawable.picker_background);
    counterView.setGravity(Gravity.CENTER);
    addView(counterView);
  }

  @Override
  protected int measureChildren(int width) {
    int clickerHeight = measureView(leftClicker, 0);
    clickerWidth = leftClicker.getMeasuredWidth();
    measureView(rightClicker, clickerWidth, clickerHeight);
    int counterWidth = width - (clickerWidth << 1);
    measureView(counterView, counterWidth, clickerHeight);
    return clickerHeight;
  }

  @Override
  protected void layoutChildren(int l, int t, int r, int b) {
    leftClicker.layout(l, t, l + clickerWidth, b);
    l += clickerWidth - borderWidth;
    rightClicker.layout(r - clickerWidth, t, r, b);
    r -= clickerWidth - borderWidth;
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
}
