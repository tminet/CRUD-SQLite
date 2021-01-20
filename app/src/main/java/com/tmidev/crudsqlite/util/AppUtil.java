package com.tmidev.crudsqlite.util;

import android.text.Editable;
import android.text.TextWatcher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AppUtil {

    public static final int TIME_SPLASH = 2000;
    public static final String APP_LOG = "APP_LOG";
    public static final String APP_PREF = "app_pref";

    /**
     * gera md5
     *
     * @param senha pura do usuario
     * @return senha em md5
     */
    public static String gerarMD5Hash(String senha) {
        String retorno = "";
        if (!senha.isEmpty()) {
            try {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.update(senha.getBytes());
                byte[] messageDigest = digest.digest();
                StringBuilder MD5Hash = new StringBuilder();
                for (byte b : messageDigest) {
                    StringBuilder h = new StringBuilder(Integer.toHexString(0xFF & b));
                    while (h.length() < 2)
                        h.insert(0, "0");
                    MD5Hash.append(h);
                }
                return MD5Hash.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return retorno;
    }

    /**
     * data e hora do dispositivo
     */
    public static String currentDateTime() {
        Calendar calendar = Calendar.getInstance();
        String date = DateFormat.getDateInstance().format(calendar.getTime());
        String time = DateFormat.getTimeInstance().format(calendar.getTime());

        return time + " - " + date;
    }

    public static boolean nomePattern(String nome) {
        final String pattern = "([a-zA-Z])+(\\s?[a-zA-Z])+";
        return nome.matches(pattern);
    }

    // listener para verificar quando algo é digitado
    public abstract static class TextChangedListener<T> implements TextWatcher {
        private final T target;

        public TextChangedListener(T target) {
            this.target = target;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            this.onTextChanged(target, s);
        }

        public abstract void onTextChanged(T target, Editable s);
    }

}
