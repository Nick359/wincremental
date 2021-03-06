package net.wintastic.wincremental.gui.util;

import java.math.BigInteger;

public class GUIHelper {

    private static final String[] suffix = new String[]{"", "k", "M", "G", "T", "P", "E", "Z", "Y"};

    public static String formatNumber(BigInteger n) {
        String suf = "";
        if (n.compareTo(BigInteger.valueOf(999)) > 0) {
            int nDigits = n.toString().length();
            int MAX_LENGTH = 3;
            int p = nDigits % MAX_LENGTH;
            int index = (nDigits - ((nDigits - 1) % MAX_LENGTH)) / MAX_LENGTH;
            if (index < suffix.length)
                suf = suffix[index];
            else
                suf = "e" + (nDigits - 1);
            String s = n.toString().substring(0, MAX_LENGTH);
            s = s.substring(0, p) + (p == 0 ? "" : ",") + s.substring(p, MAX_LENGTH);
            return s + suf;
        }
        return n + suf;
    }
}