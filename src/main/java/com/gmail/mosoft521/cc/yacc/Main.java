package com.gmail.mosoft521.cc.yacc;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * 主程序
 */
public class Main {

    public static void main(String[] args) throws Exception {

        // 第一步：获取 LL(1)文法
        ArrayList<String> gsArray = new ArrayList<String>();
        Grammar grammar = new Grammar();

        //初始化 LL(1), 设定该文法的产生式
        initGs(gsArray);

        grammar.setGsArray(gsArray);
        grammar.getNvNt();
        grammar.initExpressionMaps();
        grammar.getFirst();

        // 设置开始符
        grammar.setS('E');
        grammar.getFollow();
        grammar.getSelect();

        //打印预测分析表, 并保存文件
        grammar.genAnalyzeTable();

        // 创建一个分析器
        Analyzer analyzer = new Analyzer();

        // 设定开始符号
        analyzer.setStartChar('E');
        analyzer.setLl1Grammar(grammar);

        // 待分析的字符串
        analyzer.setStr("i+i*i#");

        // 执行分析, 打印分析步骤, 保存文件
        analyzer.analyze();

    }

    /**
     * 获取非终结符集与终结符集
     *
     * @param gsArray
     * @param nvSet
     * @param ntSet
     */
    private static void getNvNt(ArrayList<String> gsArray, TreeSet<Character> nvSet, TreeSet<Character> ntSet) {
        for (String gsItem : gsArray) {
            String[] nvNtItem = gsItem.split("->");
            String charItemStr = nvNtItem[0];
            char charItem = charItemStr.charAt(0);
            // nv在左边
            nvSet.add(charItem);
        }
        for (String gsItem : gsArray) {
            String[] nvNtItem = gsItem.split("->");
            // nt在右边
            String nvItemStr = nvNtItem[1];
            // 遍历每一个字
            for (int i = 0; i < nvItemStr.length(); i++) {
                char charItem = nvItemStr.charAt(i);
                if (!nvSet.contains(charItem)) {
                    ntSet.add(charItem);
                }
            }
        }

    }

    /**
     * 初始化 LL(1)文法, 设定产生式
     * G'[E]: E→TE'
     * E'→+TE'|ε
     * T→FT'
     * T'→*FT'|ε
     * F→(E)|i
     * 为了方便处理，用：M 代替 E'，N 代表 T'；并展开相同同一非终结符的产生式；不影响含义，可自行再优化
     * G[E]: E→TM
     * M→+TM
     * M→ε
     * T→FN
     * N→*FN
     * N→ε
     * F→(E)
     * F→i
     *
     * @param gsArray
     */
    private static void initGs(ArrayList<String> gsArray) {
        //E' = M
        //T' = N
        gsArray.add("E->TM");
        gsArray.add("M->+TF");
        gsArray.add("M->ε");
        gsArray.add("T->FN");
        gsArray.add("N->*FN");
        gsArray.add("N->ε");
        gsArray.add("F->(E)");
        gsArray.add("F->i");
    }
}
/*
Console Output:
charItem:T
T->FN
E->TM
---------------find AB:TM    T   =First(M)
----------------+++++++++++++++++++--{T=[FN], E=[TM], F=[(E), i], M=[+TF, ε], N=[*FN, ε]}
M  contains('ε')T
tempChar:M  keyE
---------------find tempChar bA: tempChar:ME   TM    T   =Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find Ab:(E)    E   =)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
F->(E)
F->i
M->+TF
---------------find AB:+TF    T   =First(F)
----------------+++++++++++++++++++--{T=[FN], E=[TM], F=[(E), i], M=[+TF, ε], N=[*FN, ε]}
M->ε
N->*FN
N->ε
charItem:N
T->FN
---------------find bA: T   FN    N   =Follow(T)
---------------find AB:TM    T   =First(M)
----------------+++++++++++++++++++--{T=[FN], E=[TM], F=[(E), i], M=[+TF, ε], N=[*FN, ε]}
M  contains('ε')T
tempChar:M  keyE
---------------find tempChar bA: tempChar:ME   TM    T   =Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find Ab:(E)    E   =)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find AB:+TF    T   =First(F)
----------------+++++++++++++++++++--{T=[FN], E=[TM], F=[(E), i], M=[+TF, ε], N=[*FN, ε]}
E->TM
F->(E)
F->i
M->+TF
M->ε
N->*FN
N->ε
charItem:M
T->FN
E->TM
---------------find bA: E   TM    M   =Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find Ab:(E)    E   =)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
F->(E)
F->i
M->+TF
M->ε
N->*FN
N->ε
charItem:F
T->FN
---------------find AB:FN    F   =First(N)
----------------+++++++++++++++++++--{T=[FN], E=[TM], F=[(E), i], M=[+TF, ε], N=[*FN, ε]}
N  contains('ε')F
tempChar:N  keyT
---------------find tempChar bA: tempChar:NT   FN    F   =Follow(T)
---------------find AB:TM    T   =First(M)
----------------+++++++++++++++++++--{T=[FN], E=[TM], F=[(E), i], M=[+TF, ε], N=[*FN, ε]}
M  contains('ε')T
tempChar:M  keyE
---------------find tempChar bA: tempChar:ME   TM    T   =Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find Ab:(E)    E   =)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find AB:+TF    T   =First(F)
----------------+++++++++++++++++++--{T=[FN], E=[TM], F=[(E), i], M=[+TF, ε], N=[*FN, ε]}
E->TM
F->(E)
F->i
M->+TF
---------------find bA: M   +TF    F   =Follow(M)
---------------find bA: E   TM    M   =Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find Ab:(E)    E   =)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
M->ε
N->*FN
---------------find AB:*FN    F   =First(N)
----------------+++++++++++++++++++--{T=[FN], E=[TM], F=[(E), i], M=[+TF, ε], N=[*FN, ε]}
N  contains('ε')F
tempChar:N  keyN
---------------find tempChar bA: tempChar:NN   *FN    F   =Follow(N)
---------------find bA: T   FN    N   =Follow(T)
---------------find AB:TM    T   =First(M)
----------------+++++++++++++++++++--{T=[FN], E=[TM], F=[(E), i], M=[+TF, ε], N=[*FN, ε]}
M  contains('ε')T
tempChar:M  keyE
---------------find tempChar bA: tempChar:ME   TM    T   =Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find Ab:(E)    E   =)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find S:E   ={#}+Follow(E)
---------------find AB:+TF    T   =First(F)
----------------+++++++++++++++++++--{T=[FN], E=[TM], F=[(E), i], M=[+TF, ε], N=[*FN, ε]}
N->ε
charItem:E
T->FN
---------------find S:E   ={#}+Follow(E)
E->TM
---------------find S:E   ={#}+Follow(E)
F->(E)
---------------find S:E   ={#}+Follow(E)
---------------find Ab:(E)    E   =)
F->i
---------------find S:E   ={#}+Follow(E)
M->+TF
---------------find S:E   ={#}+Follow(E)
M->ε
---------------find S:E   ={#}+Follow(E)
N->*FN
---------------find S:E   ={#}+Follow(E)
N->ε
---------------find S:E   ={#}+Follow(E)
====================
预测分析表
====================
表	(		)		*		+		i		#
E	E->TM	空		空		空		E->TM	空
F	F->(E)	空		空		空		F->i	空
M	空		M->ε	空		M->+TF	空		M->ε
N	N->ε	N->ε	N->*FN	N->ε	N->ε	N->ε
T	T->FN	空		空		空		T->FN	空
====================
LL(1)文法分析过程
====================
开始符:E
序号		符号栈			输入串			所用产生式
1		[#, E]			i+i*i#			E->TM
2		[#, M, T]		i+i*i#			T->FN
3		[#, M, N, F]	i+i*i#			F->i
4		[#, M, N, i]	i+i*i#			“i”匹配
5		[#, M, N]		+i*i#			N->ε
6		[#, M]			+i*i#			M->+TF
7		[#, F, T, +]	+i*i#			“+”匹配
8		[#, F, T]		i*i#			T->FN
9		[#, F, N, F]	i*i#			F->i
10		[#, F, N, i]	i*i#			“i”匹配
11		[#, F, N]		*i#			N->*FN
12		[#, F, N, F, *]	*i#			“*”匹配
13		[#, F, N, F]	i#			F->i
14		[#, F, N, i]	i#			“i”匹配
15		[#, F, N]		#			N->ε
16		[#, F]			#			F->null
17		[#]				#			“#”匹配

Process finished with exit code 0
 */