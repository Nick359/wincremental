package net.wintastic.wincremental.gui.util;

import net.wintastic.wincremental.gui.ResourceDisplay;

import java.text.DecimalFormat;

public class GUIHelper {

    private static String[] suffix = new String[]{"", "k", "M", "G", "T"};
    private static int MAX_LENGTH = 3;

    public static String formatNums(double num) {


        String fnum = new DecimalFormat("##0E0").format(num);
        fnum = fnum.replaceAll("E[0-9]", suffix[Character.getNumericValue(fnum.charAt(fnum.length() - 1)) / 3]);
        while (fnum.length() > MAX_LENGTH || fnum.matches("[0-9]+\\.[a-z]")) {
            fnum = fnum.substring(0, fnum.length() - 2) + fnum.substring(fnum.length() - 1);
        }
        return fnum;
    }
}
//    public static String formatNumber(int n) {
//        String suf = "";
//       if (n != 0) {
//           double exponent = Math.floor(Math.log10(n));
//           int virg = (int) exponent % 3;
//           suf = suffix[((int) exponent - ((int) exponent % 3)) / 3];
//           String str = Integer.toString(n);
//           String first3 = str.substring(0, 3);
//           first3 = first3.substring(0, 3-virg) + "," + first3.substring(3-virg,3);
//           return first3 + suf;
//       }
//        else return "0";
//    }
//}