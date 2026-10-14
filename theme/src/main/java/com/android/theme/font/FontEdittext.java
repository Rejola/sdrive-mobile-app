package com.android.theme.font;

/**
 * Created by svelan on 3/8/2016.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.android.theme.R;


public class FontEdittext extends EditText {

    public FontEdittext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public FontEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public FontEdittext(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyTextView);
            String fontName = a.getString(R.styleable.MyTextView_fontName);
            if (fontName != null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + "BPreplay.otf");
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }

}