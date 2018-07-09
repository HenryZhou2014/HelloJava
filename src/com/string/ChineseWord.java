package com.string;

public class ChineseWord {

    //代码来自HanLP自然语言处理库，git地址：https://github.com/hankcs/HanLP/blob/master/src/main/java/com/hankcs/hanlp/utility/TextUtility.java
    /**
     * 判断某个字符是否为汉字
     *
     * @param c 需要判断的字符
     * @return 是汉字返回true，否则返回false
     */
    public static boolean isChinese(char c)
    {
        String regex = "[\\u4e00-\\u9fa5]";
        System.out.println(String.valueOf(c));
        return String.valueOf(c).matches(regex);
    }

    public static void main(String[] args ){
        System.out.println(isChinese('涛'));
        System.out.println(isChinese2('涛'));
    }

    //来源地址：https://blog.csdn.net/z69183787/article/details/53162069这里考虑进了CJK的扩展字符集
    // GENERAL_PUNCTUATION 判断中文的“号
    // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
    // HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
    private static final boolean isChinese2(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
}
