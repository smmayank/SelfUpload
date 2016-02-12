package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mayank.selfuploadform.R;


public abstract class BaseField extends ViewGroup {

  private static final boolean DEBUG_LAYOUT = false;

  private static final String MANDATORY_INDICATOR = " *";

  private final ColorStateList secondaryTextColor;
  private final ColorStateList primaryTextColor;
  private final int verticalMargin;
  private final int horizontalMargin;
  private final float primaryTextSize;
  private final float secondaryTextSize;
  private final String headerText;
  private final int contentWidth;
  private boolean headerVisible;
  private boolean mandatory;
  private int headerPosition;


  private TextView header;
  private int headerHeight;
  private int headerWidth;

  public BaseField(Context context) {
    this(context, null);
  }

  public BaseField(Context context, AttributeSet attrs) {
    this(context, attrs, R.attr.baseFieldStyle);
  }

  public BaseField(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BaseField, defStyleAttr,
            R.style.BaseFieldStyle);
    headerText = a.getString(R.styleable.BaseField_headerText);
    headerVisible = a.getBoolean(R.styleable.BaseField_headerVisible, true);
    mandatory = a.getBoolean(R.styleable.BaseField_mandatory, false);
    headerPosition = a.getInt(R.styleable.BaseField_headerPosition, HeaderPosition.TOP);

    verticalMargin = a.getDimensionPixelOffset(R.styleable.BaseField_verticalMargin,
            getResources().getDimensionPixelOffset(R.dimen.dimen_13dp));
    horizontalMargin = a.getDimensionPixelOffset(R.styleable.BaseField_horizontalMargin,
            getResources().getDimensionPixelOffset(R.dimen.dimen_13dp));

    primaryTextColor = a.getColorStateList(R.styleable.BaseField_primaryTextColor);
    primaryTextSize = a.getDimension(R.styleable.BaseField_primaryTextSize,
            getResources().getDimension(R.dimen.fontsize_14));

    secondaryTextColor = a.getColorStateList(R.styleable.BaseField_secondaryTextColor);
    secondaryTextSize = a.getDimension(R.styleable.BaseField_secondaryTextSize,
            getResources().getDimension(R.dimen.fontsize_14));
    contentWidth = a.getDimensionPixelSize(R.styleable.BaseField_contentWidth, getResources()
            .getDimensionPixelOffset(R.dimen.input_selection_field_default_width));
    a.recycle();
    setHeaderVisible(headerVisible);
  }

  public float getSecondaryTextSize() {
    return secondaryTextSize;
  }

  public int getVerticalMargin() {
    return verticalMargin;
  }

  public int getHorizontalMargin() {
    return horizontalMargin;
  }

  public void setHeaderPosition(int headerPosition) {
    this.headerPosition = headerPosition;
    requestLayout();
  }

  public void setHeaderVisible(boolean headerVisible) {
    this.headerVisible = headerVisible;
    if (headerVisible) {
      createHeader();
    }
    setVisibility(header, headerVisible);
    requestLayout();
  }

  public void setMandatory(boolean mandatory) {
    this.mandatory = mandatory;
    requestLayout();
  }

  private void createHeader() {
    if (null == header) {
      header = new TextView(getContext());
      header.setTextColor(primaryTextColor);
      setTextSize(header, primaryTextSize);
      String text = headerText;
      if (mandatory) {
        text += MANDATORY_INDICATOR;
      }
      header.setText(text);
      addView(header);
    }
  }

  protected final void setTextSize(TextView v, float size) {
    if (null == v) {
      return;
    }
    v.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
  }

  protected final boolean isVisible(View v) {
    return null != v && VISIBLE == v.getVisibility();
  }

  protected final void setVisibility(View v, boolean visible) {
    if (null != v) {
      v.setVisibility(visible ? VISIBLE : GONE);
    }
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int layoutWidth = MeasureSpec.getSize(widthMeasureSpec);

    int width = layoutWidth - getPaddingLeft() - getPaddingRight();
    int height = getPaddingTop() + getPaddingBottom();
    int childWidth = width;
    if (isVisible(header)) {
      headerWidth = width;
      if (HeaderPosition.LEFT == headerPosition) {
        headerWidth = width - contentWidth;
        childWidth = contentWidth;
      }
      headerHeight = measureView(header, headerWidth);
      if (HeaderPosition.TOP == headerPosition) {
        height += headerHeight;
        height += verticalMargin;
      }
    }
    height += measureChildren(childWidth);

    setMeasuredDimension(layoutWidth, height);

    // view debug
    if (DEBUG_LAYOUT) {
      setBackgroundColor(Color.GRAY);
      int childCount = getChildCount();
      for (int i = 0; i < childCount; i++) {
        int color;
        switch (i % 3) {
          default:
          case 0: {
            color = Color.GREEN;
            break;
          }
          case 1: {
            color = Color.YELLOW;
            break;
          }
          case 2: {
            color = Color.CYAN;
            break;
          }
        }
        getChildAt(i).setBackgroundColor(color);
      }
    }
  }

  protected abstract int measureChildren(final int width);

  protected final int measureView(View v, int width) {
    measureView(v, width, 0);
    return v.getMeasuredHeight();
  }

  protected final void measureView(View v, int width, int height) {
    int widthSpec = createMeasureSpec(width);
    int heightSpec = createMeasureSpec(height);
    v.measure(widthSpec, heightSpec);
  }

  private int createMeasureSpec(int size) {
    int sizePec;
    if (size > 0) {
      sizePec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
    } else {
      sizePec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
    }
    return sizePec;
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    l = getPaddingLeft();
    t = getPaddingTop();
    r = getMeasuredWidth() - getPaddingRight();
    b = getMeasuredHeight() - getPaddingBottom();

    if (isVisible(header)) {
      if (HeaderPosition.LEFT == headerPosition) {
        int height = b - t;
        int diff = (height - headerHeight) >> 1;
        header.layout(l, t + diff, l + headerWidth, b - diff);
        l += headerWidth;
      } else {
        header.layout(l, t, l + headerWidth, t + headerHeight);
        t += headerHeight;
        t += verticalMargin;
      }
    }
    layoutChildren(l, t, r, b);
  }

  public ColorStateList getsecondaryTextColor() {
    return secondaryTextColor;
  }

  protected abstract void layoutChildren(int l, int t, int r, int b);

  private static final class HeaderPosition {
    private static final int TOP = 0;
    private static final int LEFT = 1;
  }
}
