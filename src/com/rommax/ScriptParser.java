package com.rommax;

import java.util.*;

public class ScriptParser {

    private static ScriptObject so;

    private static int getValueFrom(int min, int max) {
        Random random = new Random();
        return (min + random.nextInt(max - min + 1));
    }

    private static Stat getStat(String token) {
        String str = null;
        Stat s = new Stat(0, 0);
        str = token;
        if (str.indexOf("|") != -1) {
            StringTokenizer st = new StringTokenizer(str, "|");
            str = st.nextToken();
            String str2 = st.nextToken();
            s.setTimer(Integer.parseInt(str2));
        }

        StringTokenizer st = new StringTokenizer(str, " ");
        try {
            st.nextToken();
            str = st.nextToken();
        } catch (NoSuchElementException ex) {
            return new Stat(0, 0);
        }
        if (str.indexOf("=") == -1 && str.indexOf("_") == -1) {
            s.setCurrent(Integer.parseInt(str));
        } else {
            if (str.indexOf("=") != -1) {
                StringTokenizer st2 = new StringTokenizer(str, "=");
                int num1 = Integer.parseInt(st2.nextToken());
                int num2 = Integer.parseInt(st2.nextToken());
                s.setCurrent(getValueFrom(num1, num2));
            } else {
                if (str.indexOf("_") != -1) {
                    StringTokenizer st2 = new StringTokenizer(str, "_");
                    int num1 = Integer.parseInt(st2.nextToken());
                    int num2 = Integer.parseInt(st2.nextToken());
                    s.setCurrent(num1);
                    s.setMax(num2);
                }
            }
        }

        return s;
    }

    private static String getString(String token) {
        StringTokenizer st = new StringTokenizer(token, " ");
        return st.nextToken();
    }

    private static void setStatAs(Stat a, Stat b) {
        a.setCurrent(b.getCurrent());
        a.setMax(b.getMax());
        a.setTimer(b.getTimer());
    }

    public static ScriptObject parseString(String s) {
        so = new ScriptObject();
        StringTokenizer st = new StringTokenizer(s, "#");
        String str = null;
        String string = null;
        Stat number = null;
        while (st.hasMoreTokens()) {
            str = st.nextToken().trim();
            number = getStat(str);
            string = getString(str);
            switch (string) {
                case "STR_UP":
                    setStatAs(so.STR_UP, number);
                    break;
                case "AGI_UP":
                    setStatAs(so.AGI_UP, number);
                    break;
                case "END_UP":
                    setStatAs(so.END_UP, number);
                    break;
                case "INT_UP":
                    setStatAs(so.INT_UP, number);
                    break;
                case "LUCK_UP":
                    setStatAs(so.LUCK_UP, number);
                    break;
                case "DFIRE":
                    setStatAs(so.DFIRE, number);
                    break;
                case "DCOLD":
                    setStatAs(so.DCOLD, number);
                    break;
                case "DPOISON":
                    setStatAs(so.DPOISON, number);
                    break;
                case "DELEC":
                    setStatAs(so.DELEC, number);
                    break;
                case "DNORMAL":
                    setStatAs(so.DNORMAL, number);
                    break;
                case "RFIRE":
                    setStatAs(so.RFIRE, number);
                    break;
                case "RCOLD":
                    setStatAs(so.RCOLD, number);
                    break;
                case "RPOISON":
                    setStatAs(so.RPOISON, number);
                    break;
                case "RELEC":
                    setStatAs(so.RELEC, number);
                    break;
                case "RNORMAL":
                    setStatAs(so.RNORMAL, number);
                    break;
                case "HEALSELF":
                    setStatAs(so.HEALSELF, number);
                    break;
                case "HEALTIME":
                    setStatAs(so.HEALTIME, number);
                    break;
                case "MANASELF":
                    setStatAs(so.MANASELF, number);
                    break;
                case "MANATIME":
                    setStatAs(so.MANATIME, number);
                    break;
                case "BLINK":
                    so.BLINK = true;
                    break;
                case "TELEPORT":
                    so.TELEPORT = true;
                    break;
                case "IDENTIFY":
                    so.IDENTIFY = true;
                    break;
                case "SHOWALLTRAPS":
                    so.SHOWALLTRAPS = true;
                    break;
                case "OPENSKEY":
                    so.OPENSKEY = true;
                    break;					
                case "SHADOWCOUNT":
                    setStatAs(so.SHADOWCOUNT, number);
                    break;					
                case "WARHIT":
                    setStatAs(so.WARHIT, number);
                    break;					
                case "BESTAGI":
                    setStatAs(so.BESTAGI, number);
                    break;					
                case "RHIT":
                    so.RHIT = true;
                    break;					
                case "BESTHITS":
                    so.BESTHITS = true;
                    break;					
                case "FURY":
                    so.FURY = true;
                    break;					
                case "TWHITS":
                    so.TWHITS = true;
                    break;					
                case "FOVRAD":
                    setStatAs(so.FOVRAD, number);
                    break;
                case "POISONCOUNT":
                    setStatAs(so.POISONCOUNT, number);
                    break;
                case "PARALYZECOUNT":
                    setStatAs(so.PARALYZECOUNT, number);
                    break;
                case "HEALPOISON":
                    setStatAs(so.HEALPOISON, number);
                    break;
                case "SW_UP":
                    setStatAs(so.SW_UP, number);
                    break;
            }
        }
        return so;
    }

    public static void main(String[] args) {
        ScriptObject so = parseString("#RFIRE 10=15|5#");
        System.out.println(so.RFIRE.getCurrent() + "|" + so.RFIRE.getMax() + "|" + so.RFIRE.getTimer());
    }
}
