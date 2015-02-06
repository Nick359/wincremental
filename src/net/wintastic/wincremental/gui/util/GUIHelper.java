package net.wintastic.wincremental.gui.util;

public class GUIHelper {

    private static String[] suffix = new String[]{"", "k", "M", "G", "T"};
    private static int MAX_LENGTH = 3;

    public static String formatNumber(int n) {
        String suf = "";
        if (n > 999) {
            double nDigits = Math.floor(Math.log10(n)) + 1;
            int p = (int) nDigits % MAX_LENGTH;
            suf = suffix[((int) nDigits - ((int) nDigits % MAX_LENGTH)) / MAX_LENGTH];
            String s = Integer.toString(n).substring(0, MAX_LENGTH);
            s = s.substring(0, p) + (p == 0 ? "" : ",") + s.substring(p, MAX_LENGTH);
            return s + suf;
        }
        return n + suf;
    }
}