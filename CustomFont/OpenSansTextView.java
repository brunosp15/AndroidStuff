package br.com.walmart.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import br.com.walmart.R;

/**
 * Created by bruno on 1/29/16.
 */
public class OpenSansTextView extends TextView {

    public final static int BOLD = 1;
    public final static int LIGHT = 2;
    public final static int REGULAR = 3;
    public final static int SEMIBOLD = 4;


    public OpenSansTextView(Context context) {
        super(context);
        init(context, null);
    }

    public OpenSansTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public OpenSansTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OpenSansTextView);
        int type = a.getInt(R.styleable.OpenSansTextView_type, 0);
        a.recycle();

        switch (type) {
            case BOLD:
                setBold();
                break;
            case LIGHT:
                setLight();
                break;
            case REGULAR:
                setRegular();
                break;
            case SEMIBOLD:
                setSemibold();
                break;
            default:
                setRegular();
                break;
        }

    }

    public void setBold() {
        setFont("fonts/OpenSans-Bold.ttf");
    }

    public void setLight() {
        setFont("fonts/OpenSans-Light.ttf");
    }

    public void setRegular() {
        setFont("fonts/OpenSans-Regular.ttf");
    }

    public void setSemibold() {
        setFont("fonts/OpenSans-Semibold.ttf");
    }


    private void setFont(String customFont) {
        Typeface customTypeface = TypefaceManager.get(getContext(), customFont);
        setTypeface(customTypeface);
    }
}
