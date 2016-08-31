package br.com.walmart.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.ViewParent;

import br.com.walmart.R;

/**
 * Created by bruno on 1/29/16.
 */
public class OpenSansEditText extends AppCompatEditText {

    public final static int BOLD = 1;
    public final static int LIGHT = 2;
    public final static int REGULAR = 3;
    public final static int SEMIBOLD = 4;


    public OpenSansEditText(Context context) {
        super(context);
        init(context, null);
    }

    public OpenSansEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public OpenSansEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OpenSansEditText);
        int type = a.getInt(R.styleable.OpenSansEditText_type, 0);

        String mask = a.getString(R.styleable.OpenSansEditText_mask);
        String mask2 = a.getString(R.styleable.OpenSansEditText_mask2);
        if (mask != null) {
            addTextChangedListener(Mask.insert(mask, mask2, this));
        }

        final String regex = a.getString(R.styleable.OpenSansEditText_regex);
        setRegex(regex);


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


    public void setError(@StringRes int stringRes) {
        String string = getContext().getString(stringRes);
        setError(string);
    }

    @Override
    public void setError(CharSequence error) {
        super.setError(error);
        ViewParent parent = getParent();
        while (parent != null) {
            if (parent instanceof FloatLabelLayout) {
                ((FloatLabelLayout) parent).setError(error);
                break;
            }

            parent = parent.getParent();
        }
    }

    private void setFont(String customFont) {
        Typeface customTypeface = TypefaceManager.get(getContext(), customFont);
        setTypeface(customTypeface);
    }

    public void setRegex(final String regex) {
        InputFilter[] inputFilter = new InputFilter[]{
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart,
                                               int dend) {
                        if (src.equals("") || regex == null) { // for backspace
                            return src;
                        }
                        if (src.toString().matches(regex)) {
                            return src;
                        }
                        return "";
                    }
                }
        };

        setFilters(inputFilter);
    }
}
