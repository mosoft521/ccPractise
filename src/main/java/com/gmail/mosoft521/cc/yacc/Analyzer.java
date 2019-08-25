package com.gmail.mosoft521.cc.yacc;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 主程序 句子分析器
 */
public class Analyzer {

    private ArrayList<AnalyzeProduce> analyzeProduces;
    /**
     * LL（1）文法
     */
    private Grammar ll1Grammar;
    /**
     * 开始符
     */
    private Character startChar;
    /**
     * 分析栈
     */
    private Stack<Character> analyzeStatck;
    /**
     * 剩余输入串
     */
    private String str;
    /**
     * 推导所用产生或匹配
     */
    private String useExp;

    public Analyzer() {
        super();
        analyzeStatck = new Stack<Character>();
        // 结束符进栈
        analyzeStatck.push('#');
    }

    public Grammar getLl1Grammar() {
        return ll1Grammar;
    }

    public void setLl1Grammar(Grammar ll1Grammar) {
        this.ll1Grammar = ll1Grammar;
    }

    public ArrayList<AnalyzeProduce> getAnalyzeProduces() {
        return analyzeProduces;
    }

    public void setAnalyzeProduces(ArrayList<AnalyzeProduce> analyzeProduces) {
        this.analyzeProduces = analyzeProduces;
    }

    public Character getStartChar() {
        return startChar;
    }

    public void setStartChar(Character startChar) {
        this.startChar = startChar;
    }

    public Stack<Character> getAnalyzeStatck() {
        return analyzeStatck;
    }

    public void setAnalyzeStatck(Stack<Character> analyzeStatck) {
        this.analyzeStatck = analyzeStatck;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getUseExp() {
        return useExp;
    }

    public void setUseExp(String useExp) {
        this.useExp = useExp;
    }

    /**
     * 分析
     */
    public void analyze() {
        analyzeProduces = new ArrayList<AnalyzeProduce>();

        // 开始符进栈
        analyzeStatck.push(startChar);

        System.out.println("====================\nLL(1)文法分析过程\n====================");
        System.out.println("开始符:" + startChar);
        System.out.println("序号\t\t符号栈\t\t\t输入串\t\t\t所用产生式");
        int index = 0;
        // 开始分析
        // while (analyzeStatck.peek() != '#' && str.charAt(0) != '#') {
        while (!analyzeStatck.empty()) {
            index++;
            if (analyzeStatck.peek() != str.charAt(0)) {

                // 到分析表中找到这个产生式
                String nowUseExpStr = TextUtil.findUseExp(ll1Grammar.getSelectMap(), analyzeStatck.peek(), str.charAt(0));

                //打印表格注意, 制表符的个数
                if (analyzeStatck.size() == 1) {
                    System.out.println(index + "\t\t" + analyzeStatck.toString() + "\t\t\t\t" + str + "\t\t\t"
                            + analyzeStatck.peek() + "->" + nowUseExpStr);
                } else if (analyzeStatck.size() == 2) {
                    System.out.println(index + "\t\t" + analyzeStatck.toString() + "\t\t\t" + str + "\t\t\t"
                            + analyzeStatck.peek() + "->" + nowUseExpStr);
                } else if (analyzeStatck.size() == 3) {
                    System.out.println(index + "\t\t" + analyzeStatck.toString() + "\t\t" + str + "\t\t\t"
                            + analyzeStatck.peek() + "->" + nowUseExpStr);
                } else {
                    System.out.println(index + "\t\t" + analyzeStatck.toString() + "\t" + str + "\t\t\t"
                            + analyzeStatck.peek() + "->" + nowUseExpStr);
                }

                AnalyzeProduce produce = new AnalyzeProduce();
                produce.setIndex(index);
                produce.setAnalyzeStackStr(analyzeStatck.toString());
                produce.setStr(str);
                if (null == nowUseExpStr) {
                    produce.setUseExpStr("无法匹配!");
                } else {
                    produce.setUseExpStr(analyzeStatck.peek() + "->" + nowUseExpStr);
                }
                analyzeProduces.add(produce);
                // 将之前的分析栈中的栈顶出栈
                analyzeStatck.pop();
                // 将要用到的表达式入栈,反序入栈
                if (null != nowUseExpStr && nowUseExpStr.charAt(0) != 'ε') {
                    for (int j = nowUseExpStr.length() - 1; j >= 0; j--) {
                        char currentChar = nowUseExpStr.charAt(j);
                        analyzeStatck.push(currentChar);
                    }
                }
                continue;
            }
            // 如果可以匹配,分析栈出栈，串去掉一位
            if (analyzeStatck.peek() == str.charAt(0)) {

                if (analyzeStatck.size() == 1) {
                    System.out.println(index + "\t\t" + analyzeStatck.toString() + "\t\t\t\t" + str + "\t\t\t" + "“"
                            + str.charAt(0) + "”匹配");
                } else if (analyzeStatck.size() == 2) {
                    System.out.println(index + "\t\t" + analyzeStatck.toString() + "\t\t\t" + str + "\t\t\t" + "“"
                            + str.charAt(0) + "”匹配");
                } else if (analyzeStatck.size() == 3) {
                    System.out.println(index + "\t\t" + analyzeStatck.toString() + "\t\t" + str + "\t\t\t" + "“"
                            + str.charAt(0) + "”匹配");
                } else {
                    System.out.println(index + "\t\t" + analyzeStatck.toString() + "\t" + str + "\t\t\t" + "“"
                            + str.charAt(0) + "”匹配");
                }

                AnalyzeProduce produce = new AnalyzeProduce();
                produce.setIndex(index);
                produce.setAnalyzeStackStr(analyzeStatck.toString());
                produce.setStr(str);
                produce.setUseExpStr("“" + str.charAt(0) + "”匹配");
                analyzeProduces.add(produce);
                analyzeStatck.pop();
                str = str.substring(1);
                continue;
            }
        }
    }
}