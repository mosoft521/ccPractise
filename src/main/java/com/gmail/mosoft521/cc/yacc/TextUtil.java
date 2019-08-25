package com.gmail.mosoft521.cc.yacc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 字符工具类
 */
public class TextUtil {

    /**
     * (3)B->aA,=Follow(B)
     *
     * @param nvSet
     * @param itemCharStr
     * @param a
     * @param expressionMap
     */
    public static boolean containsbA(TreeSet<Character> nvSet, String itemCharStr, Character a,
                                     HashMap<Character, ArrayList<String>> expressionMap) {
        String aStr = a.toString();
        String lastStr = itemCharStr.substring(itemCharStr.length() - 1);
        return lastStr.equals(aStr);
    }

    /**
     * 形如 aBb,b=空
     *
     * @param nvSet
     * @param itemCharStr
     * @param a
     * @param expressionMap
     */
    public static boolean containsbAbIsNull(TreeSet<Character> nvSet, String itemCharStr, Character a,
                                            HashMap<Character, ArrayList<String>> expressionMap) {
        String aStr = a.toString();
        if (containsAB(nvSet, itemCharStr, a)) {
            Character alastChar = getAlastChar(itemCharStr, a);
            System.out.println("----------------+++++++++++++++++++--" + expressionMap.toString());
            ArrayList<String> arrayList = expressionMap.get(alastChar);
            if (arrayList.contains("ε")) {
                System.out.println(alastChar + "  contains('ε')" + aStr);
                return true;
            }
        }
        return false;

    }

    /**
     * 是否包含这种的字符串<Br>
     * (2)Ab,=First(b)-ε,直接添加终结符
     *
     * @param ntSet
     * @param itemCharStr
     * @param a
     * @return boolean
     */
    public static boolean containsAb(TreeSet<Character> ntSet, String itemCharStr, Character a) {
        String aStr = a.toString();
        if (itemCharStr.contains(aStr)) {
            int aIndex = itemCharStr.indexOf(aStr);
            String findStr;
            try {
                findStr = itemCharStr.substring(aIndex + 1, aIndex + 2);
            } catch (Exception e) {
                return false;
            }
            return ntSet.contains(findStr.charAt(0));
        } else {
            return false;
        }
    }

    /**
     * 是否包含这种的字符串<Br>
     * (2).2Ab,=First(b)-ε
     *
     * @param nvSet
     * @param itemCharStr
     * @param a
     * @return boolean
     */
    public static boolean containsAB(TreeSet<Character> nvSet, String itemCharStr, Character a) {
        String aStr = a.toString();
        if (itemCharStr.contains(aStr)) {
            int aIndex = itemCharStr.indexOf(aStr);
            String findStr;
            try {
                findStr = itemCharStr.substring(aIndex + 1, aIndex + 2);
            } catch (Exception e) {
                return false;
            }
            return nvSet.contains(findStr.charAt(0));
        } else {
            return false;
        }
    }

    /**
     * 获取 A 后的字符
     *
     * @param itemCharStr
     * @param a
     */
    public static Character getAlastChar(String itemCharStr, Character a) {
        String aStr = a.toString();
        if (itemCharStr.contains(aStr)) {
            int aIndex = itemCharStr.indexOf(aStr);
            String findStr = "";
            try {
                findStr = itemCharStr.substring(aIndex + 1, aIndex + 2);
            } catch (Exception e) {
                return null;
            }
            return findStr.charAt(0);
        }
        return null;
    }

    /**
     * 是否为 ε 开始的
     *
     * @param selectExp
     */
    public static boolean isEmptyStart(String selectExp) {
        char charAt = selectExp.charAt(0);
        return charAt == 'ε';
    }

    /**
     * 是否是终结符开始的
     *
     * @param ntSet
     * @param selectExp
     */
    public static boolean isNtStart(TreeSet<Character> ntSet, String selectExp) {
        char charAt = selectExp.charAt(0);
        return ntSet.contains(charAt);
    }

    /**
     * 是否是非终结符开始的
     *
     * @param nvSet
     * @param selectExp
     * @return
     */
    public static boolean isNvStart(TreeSet<Character> nvSet, String selectExp) {
        char charAt = selectExp.charAt(0);
        return nvSet.contains(charAt);
    }

    /**
     * 查找产生式
     *
     * @param selectMap
     * @param peek      当前 Nv
     * @param charAt    当前字符
     * @return
     */
    public static String findUseExp(TreeMap<Character, HashMap<String, TreeSet<Character>>> selectMap, Character peek,
                                    char charAt) {
        try {
            HashMap<String, TreeSet<Character>> hashMap = selectMap.get(peek);
            Set<String> keySet = hashMap.keySet();
            for (String useExp : keySet) {
                TreeSet<Character> treeSet = hashMap.get(useExp);
                if (treeSet.contains(charAt)) {
                    return useExp;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}