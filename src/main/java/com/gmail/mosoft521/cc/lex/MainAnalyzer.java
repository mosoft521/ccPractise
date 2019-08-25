package com.gmail.mosoft521.cc.lex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 执行主程序
 */
public class MainAnalyzer {
    private File inputFile;
    private File outputFile;
    private String fileContent;
    private ArrayList<Word> list = new ArrayList<>();

    /**
     * 构造方法
     *
     * @param input
     * @param output
     */
    public MainAnalyzer(String input, String output) {
        //实例化输入文件
        inputFile = new File(input);

        //实例化输出文件
        outputFile = new File(output);
    }

    public static void main(String[] args) {

        //注意输入文件路径/名称必须对, 输出文件可以由程序创建
        MainAnalyzer analyzer = new MainAnalyzer("E:\\ws_ij_alvin\\ccPractise-alvin\\src\\main\\java\\com\\gmail\\mosoft521\\cc\\lex\\input.txt", "E:\\ws_ij_alvin\\ccPractise-alvin\\src\\main\\java\\com\\gmail\\mosoft521\\cc\\lex\\output.txt");

        analyzer.analyze(analyzer.getContent());
    }

    /**
     * 从指定的 txt 文件中读取源程序文件内容
     *
     * @return java.lang.String
     */
    public String getContent() {
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner reader = new Scanner(inputFile)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                stringBuilder.append(line + "\n");
                System.out.println(line);
            }
            System.out.println("Successful reading of files：" + inputFile.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileContent = stringBuilder.toString();
    }

    /**
     * 然后扫描程序，在程序结束前将扫描到的词添加到 list 中
     * 最后把扫描结果保存到指定的文件中
     *
     * @param fileContent
     * @return void
     */
    public void analyze(String fileContent) {
        int over = 1;
        Word word = new Word();

        //调用扫描程序
        CodeScanner scanner = new CodeScanner(fileContent.toCharArray());
        System.out.println("The result:");
        while (over != 0) {
            word = scanner.scan();
            System.out.println("(" + word.getTypeNum() + "," + word.getWord() + ")");
            list.add(word);
            over = word.getTypeNum();
        }
        saveResult();
    }

    /**
     * 将结果写入到到指定文件中
     * 如果文件不存在，则创建一个新的文件
     * 用一个 foreach 循环将 list 中的项变成字符串写入到文件中
     */
    public void saveResult() {

        //创建文件
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        //写入文件
        try (Writer writer = new FileWriter(outputFile)) {
            for (Word word : list) {
                writer.write("(" + word.getTypeNum() + " ," + word.getWord() + ")\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}