package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.base.Logger;
import com.mayank.selfuploadform.selfupload.widgets.BaseField.BaseEntry;

import java.util.ArrayList;
import java.util.List;

public class SelectionField<T extends BaseEntry> extends BaseField implements View.OnClickListener {

    private static final int FIRST = 0;
    private static final int MIDDLE = 1;
    private static final int LAST = 2;

    private ArrayList<T> entries;
    private SparseArray<View> fieldViews;

    private int fieldWidth;
    private int fieldHeight;
    private SelectionFieldInteractionListener<T> listener;

    public SelectionField(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.baseSelectionFieldStyle);
    }

    public SelectionField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, R.attr.baseSelectionFieldStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        fieldViews = new SparseArray<>();
        entries = new ArrayList<>();
    }

    public void addEntries(List<T> values) {
        if (null == values || values.isEmpty()) {
            return;
        }
        entries.addAll(values);
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
        int length = 0;
        int maxIndex = -1;
        for (int i = 0; i < size; i++) {
            int entryLength = entries.get(i).getEntryText().length();
            if (length <= entryLength) {
                length = entryLength;
                maxIndex = i;
            }
        }
        // make sure the child with maximum length gets measured first
        View view = getFieldView(maxIndex, entries.get(maxIndex).getEntryText());
        fieldHeight = measureView(view, fieldWidth);
        for (int i = 0; i < size; i++) {
            if (i != maxIndex) {
                view = getFieldView(i, entries.get(i).getEntryText());
                measureView(view, fieldWidth, fieldHeight);
            }
        }
        return fieldHeight;
    }

    private View getFieldView(int index, CharSequence text) {
        View view = fieldViews.get(index);
        if (null == view) {
            view = createFieldView(index, text);
        }
        return view;
    }

    private View createFieldView(int index, CharSequence text) {
        TextView textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getSecondaryTextColor());
        textView.setOnClickListener(this);
        setTextSize(textView, getSecondaryTextSize());
        addView(textView);
        setViewPadding(textView);
        textView.setText(text);
        fieldViews.put(index, textView);
        boolean left = false, right = false;
        if (FIRST == index) {
            left = true;
        } else if (index == entries.size() - 1) {
            right = true;
        }
        setViewBackground(textView, left, right);
        return textView;
    }

    @Override
    protected void layoutChildren(int l, int t, int r, int b) {
        if (entries.isEmpty()) {
            return;
        }
        int strokeWidthDiff = getStrokeWidth() >> 1;
        int size = entries.size();
        for (int i = 0; i < size; i++) {
            View view = getFieldView(i, entries.get(i).getEntryText());
            view.layout(l, t, (int) (l + fieldWidth + strokeWidthDiff), t + fieldHeight);
            l += fieldWidth - strokeWidthDiff;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.isSelected()) {
            return;
        }
        int index = fieldViews.indexOfValue(v);
        if (index < 0 || index >= entries.size()) {
            return;
        }
        int size = entries.size();
        for (int i = 0; i < size; i++) {
            if (index != i) {
                fieldViews.get(i).setSelected(false);
            }
        }
        v.setSelected(true);
        Logger.logD(this, "index %d", index);
        sendSelection(index, entries.get(index));
    }

    protected void sendSelection(int index, T entry) {
        if (null != listener) {
            listener.onSelectionFieldSelected(this, index, entry);
        }
    }

    public void setSelectionFieldInteractionListener(SelectionFieldInteractionListener<T> listener) {
        this.listener = listener;
    }

    public interface SelectionFieldInteractionListener<T> {
        void onSelectionFieldSelected(SelectionField field, int index, T entry);
    }
}
