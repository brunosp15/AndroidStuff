package br.com.walmart.communication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import timber.log.Timber;

/**
 * Created by bpinto2 on 8/15/16.
 */
public class Crypto {

    private static final String KEY_PREF = "keyStore";
    private static final String KEY_VALUE = "key";

    public void initKey(Context context) {
        if (getKey(context) == null) {
            try {
                SecretKey key = generateKey();
                String stringSecretKey = Base64.encodeToString(key.getEncoded(), Base64.DEFAULT);
                getPref(context).edit().putString(KEY_VALUE, stringSecretKey).commit();
            } catch (NoSuchAlgorithmException e) {
                Timber.d(e, "initKey");
            }
        }
    }

    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        // Generate a 256-bit key
        final int outputKeyLength = 256;

        SecureRandom secureRandom = new SecureRandom();
        // Do *not* seed secureRandom! Automatically seeded from system entropy.
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(outputKeyLength, secureRandom);
        SecretKey key = keyGenerator.generateKey();

        return key;
    }

    public SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE);
    }

    public String getKey(Context context) {
        return getPref(context).getString(KEY_VALUE, null);
    }

    private SecretKeySpec getSecretKeySpec(Context context) {
        String key = getKey(context);
        if (key == null) {
            return null;
        }

        byte[] keyRaw = Base64.decode(key, Base64.DEFAULT);
        return new SecretKeySpec(keyRaw, "AES");
    }

    public String encrypt(String data, Context context) {
        SecretKeySpec secretKeySpec = getSecretKeySpec(context);
        if (secretKeySpec == null) {
            return data;
        }

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] dataByte = Base64.encode(data.getBytes("UTF-8"), Base64.DEFAULT);
            byte[] encrypted = cipher.doFinal(dataByte);
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            Timber.d(e, "Encrypt Exception");
            return data;
        }
    }

    public String decrypt(String data, Context context) {
        SecretKeySpec secretKeySpec = getSecretKeySpec(context);
        if (secretKeySpec == null) {
            return data;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] dataByte = Base64.decode(data.getBytes("UTF-8"), Base64.DEFAULT);
            byte[] decrypted = cipher.doFinal(dataByte);
            return new String(Base64.decode(decrypted, Base64.DEFAULT));
        } catch (Exception e) {
            Timber.d(e, "Decrypt Exception");
            return data;
        }
    }
}
