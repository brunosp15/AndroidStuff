package br.com.torabrasil.app;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;
import java.util.Map;

public class TypeFaceManager {

    private static final Map<String, Typeface> FONT_CACHE = new Hashtable<>();

    public static Typeface get(Context c, String assetPath) {
        synchronized (FONT_CACHE) {
            Typeface result = null;
            if (assetPath != null) {
                if (!FONT_CACHE.containsKey(assetPath)) {
                    try {
                        Typeface t = Typeface.createFromAsset(c.getAssets(), assetPath);
                        FONT_CACHE.put(assetPath, t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                result = FONT_CACHE.get(assetPath);
            }
            return result;
        }
    }
}