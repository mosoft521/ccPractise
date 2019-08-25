package com.gmail.mosoft521.cc.lex;

/**
 * 表示识别后的词实体类
 */
public class Word {

    /**
     * 种别码
     */
    private int typeNum;

    /**
     * 扫描得到的词
     */
    private String word;

    public int getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(int typeNum) {
        this.typeNum = typeNum;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}