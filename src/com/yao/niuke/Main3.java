package com.yao.niuke;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * 给定一个句子（只包含字母和空格）， 将句子中的单词位置反转，单词用空格分割, 单词之间只有一个空格，前后没有空格。 比如： （1） “hello xiao mi”-> “mi xiao hello”
 */
public class Main3 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        String data=s.nextLine();
        String use[]=data.split(" ", 1000);
        List<String> l=Arrays.asList(use);
        Collections.reverse(l);//对list进行反转
        for(int i=0;i<l.size()-1;i++){
            System.out.print(l.get(i)+" ");
        }
        System.out.print(l.get(l.size()-1));
    }
}