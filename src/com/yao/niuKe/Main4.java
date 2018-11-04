package com.yao.niuKe;

import java.util.Scanner;

/**
 * https://blog.csdn.net/yyywww666/article/details/78026404
 *
 * 题目描述
 * 继MIUI8推出手机分身功能之后，MIUI9计划推出一个电话号码分身的功能：首先将电话号码中的每个数字加上8取个位，然后使用对应的大写字母代替 （"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"）， 然后随机打乱这些字母，所生成的字符串即为电话号码对应的分身。
 * 输入描述:
 * 第一行是一个整数T（1 ≤ T ≤ 100)表示测试样例数；接下来T行，每行给定一个分身后的电话号码的分身（长度在3到10000之间）。
 * 输出描述:
 * 输出T行，分别对应输入中每行字符串对应的分身前的最小电话号码（允许前导0）。
 *
 * 输入：
 * 4
 * EIGHT
 * ZEROTWOONE
 * OHWETENRTEO
 * OHEWTIEGTHENRTEO
 *
 * 输出：
 * 0
 * 234
 * 345
 * 0345
 */
public class Main4 {
    public static void Solution(String data){
        int out[] = new int[10];
        StringBuffer db = new StringBuffer(data);
        while (db.toString().contains("G")){
            db.deleteCharAt(db.indexOf("E"));
            db.deleteCharAt(db.indexOf("I"));
            db.deleteCharAt(db.indexOf("G"));
            db.deleteCharAt(db.indexOf("H"));
            db.deleteCharAt(db.indexOf("T"));
            out[0]++;
        }
        while(db.toString().contains("Z")){
            db.deleteCharAt(db.indexOf("Z"));
            db.deleteCharAt(db.indexOf("E"));
            db.deleteCharAt(db.indexOf("R"));
            db.deleteCharAt(db.indexOf("O"));
            out[2]++;
        }
        while(db.toString().contains("W")){
            db.deleteCharAt(db.indexOf("T"));
            db.deleteCharAt(db.indexOf("W"));
            db.deleteCharAt(db.indexOf("O"));
            out[4]++;
        }
        while(db.toString().contains("U")){
            db.deleteCharAt(db.indexOf("F"));
            db.deleteCharAt(db.indexOf("O"));
            db.deleteCharAt(db.indexOf("U"));
            db.deleteCharAt(db.indexOf("R"));
            out[6]++;
        }
        while(db.toString().contains("X")){
            db.deleteCharAt(db.indexOf("S"));
            db.deleteCharAt(db.indexOf("I"));
            db.deleteCharAt(db.indexOf("X"));
            out[8]++;
        }
        while(db.toString().contains("T")){
            db.deleteCharAt(db.indexOf("T"));
            db.deleteCharAt(db.indexOf("H"));
            db.deleteCharAt(db.indexOf("R"));
            db.deleteCharAt(db.indexOf("E"));
            db.deleteCharAt(db.indexOf("E"));
            out[5]++;
        }
        while(db.toString().contains("S")){
            db.deleteCharAt(db.indexOf("S"));
            db.deleteCharAt(db.indexOf("E"));
            db.deleteCharAt(db.indexOf("V"));
            db.deleteCharAt(db.indexOf("E"));
            db.deleteCharAt(db.indexOf("N"));
            out[9]++;
        }
        while(db.toString().contains("O")){
            db.deleteCharAt(db.indexOf("O"));
            db.deleteCharAt(db.indexOf("N"));
            db.deleteCharAt(db.indexOf("E"));
            out[3]++;
        }
        while(db.toString().contains("F")){
            db.deleteCharAt(db.indexOf("F"));
            db.deleteCharAt(db.indexOf("I"));
            db.deleteCharAt(db.indexOf("V"));
            db.deleteCharAt(db.indexOf("E"));
            out[7]++;
        }
        while(db.toString().contains("I")){
            db.deleteCharAt(db.indexOf("N"));
            db.deleteCharAt(db.indexOf("I"));
            db.deleteCharAt(db.indexOf("N"));
            db.deleteCharAt(db.indexOf("E"));
            out[1]++;
        }
        for (int i = 0; i <=9 ; i++) {
            if (out[i]>=1){
                for (int j = 0; j <out[i] ; j++) {
                    System.out.print(i);
                }
            }
        }

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()){
            int num = in.nextInt();
            for (int n=0;n<num;n++){
                String data = in.next();
                Solution(data);
                System.out.println();
            }
        }
    }

}
