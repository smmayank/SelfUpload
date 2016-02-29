package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mayank.selfuploadform.R;

/**
 * Created by vikas-pc on 26/02/16
 */
public class CustomEditText extends LinearLayout {

    private static final String TAG = "CustomEditText";
    private Context mContext;
    private String hintText;
    private CharSequence[] dropDownMenu;
    private View mView;
    private TextView mTextView;
    private Spinner mSpinner;

    public CustomEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomEditText(Context context) {
        this(context,null);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setOrientation(HORIZONTAL);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.customEditText);
        hintText = attributes.getString(R.styleable.customEditText_hintText);
        dropDownMenu = attributes.getTextArray(R.styleable.customEditText_dropDownMenu);
        attributes.recycle();
        initViews();
        addViewsToLayout();
    }

    private void initViews() {
        mView = new View(mContext);
        mTextView = new TextView(mContext);
        mSpinner = new Spinner(mContext);
    }

    private void addViewsToLayout() {

    }

}
