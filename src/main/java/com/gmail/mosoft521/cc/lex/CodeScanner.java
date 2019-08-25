package com.gmail.mosoft521.cc.lex;

/**
 * 字符扫描
 */
public class CodeScanner {

    private static String _KEY_WORD_END = "end string of string";
    private int charNum = 0;
    private Word word;

    private char[] input = new char[255];
    private char[] token = new char[255];
    private int p_input = 0;
    private int p_token = 0;

    private char ch;

    /**
     * 关键字数组
     */
    private String[] rwtab = {"int", "if", "while", "do", "return", "break", "continue", _KEY_WORD_END};

    /**
     * 逻辑运算数组
     */
    private String[] logicTab = {"==", ">=", "<=", "!=", _KEY_WORD_END};

    public CodeScanner(char[] input) {
        this.input = input;
    }

    /**
     * 取下一个字符
     *
     * @return
     */
    public char m_getch() {
        if (p_input < input.length) {
            ch = input[p_input];
            p_input++;
        }
        return ch;
    }

    /**
     * 如果是标识符或者空白符就取下一个字符
     */
    public void getbc() {
        while ((ch == ' ' || ch == '\t') && p_input < input.length) {
            ch = input[p_input];
            p_input++;
        }
    }

    /**
     * 把当前字符和原有字符串连接
     */
    public void concat() {
        token[p_token] = ch;
        p_token++;
        token[p_token] = '\0';
    }

    /**
     * 回退一个字符
     */
    public void retract() {
        p_input--;
    }

    /**
     * 判断是否为字母
     *
     * @return boolean
     */
    public boolean isLetter() {
        return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z';
    }

    /**
     * 判断是否为数字
     *
     * @return boolean
     */
    public boolean isDigit() {
        return ch >= '0' && ch <= '9';
    }

    /**
     * 查看 token 中的字符串是否是关键字，是的话返回关键字种别编码，否则返回 2
     *
     * @return
     */
    public int isKey() {
        int i = 0;
        while (rwtab[i].compareTo(_KEY_WORD_END) != 0) {
            if (rwtab[i].compareTo(new String(token).trim()) == 0) {
                return i + 1;
            }
            i++;
        }
        return 2;
    }

    /**
     * 可能是逻辑预算字符
     *
     * @return
     */
    public Boolean isLogicChar() {
        return ch == '>' || ch == '<' || ch == '=' || ch == '!';
    }


    /**
     * 查看 token 中的字符串是否是逻辑运算符，是的话返回关键字种别编码，否则返回 2
     *
     * @return
     */
    public int isLogicTab() {
        int i = 0;
        while (logicTab[i].compareTo(_KEY_WORD_END) != 0) {
            if (logicTab[i].compareTo(new String(token).trim()) == 0) {
                return i + 1;
            }
            i++;
        }
        return 4;
    }

    /**
     * 能够识别换行，单行注释和多行注释的
     * 换行的种别码设置成30
     * 多行注释的种别码设置成31
     *
     * @return
     */
    public Word scan() {
        token = new char[255];
        Word myWord = new Word();
        myWord.setTypeNum(10);
        myWord.setWord("");

        p_token = 0;
        m_getch();
        getbc();
        if (isLetter()) {
            while (isLetter() || isDigit()) {
                concat();
                m_getch();
            }
            retract();
            myWord.setTypeNum(isKey());
            myWord.setWord(new String(token).trim());
            return myWord;
        } else if (isLogicChar()) {
            while (isLogicChar()) {
                concat();
                m_getch();
            }
            retract();
            myWord.setTypeNum(4);
            myWord.setWord(new String(token).trim());
            return myWord;
        } else if (isDigit()) {
            while (isDigit()) {
                concat();
                m_getch();
            }
            retract();
            myWord.setTypeNum(3);
            myWord.setWord(new String(token).trim());
            return myWord;
        } else {
            switch (ch) {
                //5
                case ',':
                    myWord.setTypeNum(5);
                    myWord.setWord(",");
                    return myWord;
                case ';':
                    myWord.setTypeNum(5);
                    myWord.setWord(";");
                    return myWord;
                case '{':
                    myWord.setTypeNum(5);
                    myWord.setWord("{");
                    return myWord;
                case '}':
                    myWord.setTypeNum(5);
                    myWord.setWord("}");
                    return myWord;
                case '(':
                    myWord.setTypeNum(5);
                    myWord.setWord("(");
                    return myWord;
                case ')':
                    myWord.setTypeNum(5);
                    myWord.setWord(")");
                    return myWord;
                //4
                case '=':
                    myWord.setTypeNum(4);
                    myWord.setWord("=");
                    return myWord;
                case '+':
                    myWord.setTypeNum(4);
                    myWord.setWord("+");
                    return myWord;
                case '-':
                    myWord.setTypeNum(4);
                    myWord.setWord("-");
                    return myWord;
                case '*':
                    myWord.setTypeNum(4);
                    myWord.setWord("*");
                    return myWord;
                case '/':
                    myWord.setTypeNum(4);
                    myWord.setWord("/");
                    return myWord;

                case '\n':
                    myWord.setTypeNum(6);
                    myWord.setWord("\\n");
                    return myWord;
                case '#':
                    myWord.setTypeNum(0);
                    myWord.setWord("#");
                    return myWord;
                default:
                    concat();
                    myWord.setTypeNum(-1);
                    myWord.setWord("ERROR INFO: WORD = \"" + new String(token).trim() + "\"");
                    return myWord;
            }
        }
    }
}