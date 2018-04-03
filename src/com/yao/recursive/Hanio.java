package com.yao.recursive;

/**
 * 汉诺塔问题就是：有ABC三根柱子，A柱子上从上到下摞了很多体积依次递增的圆盘，如果将圆盘从A移动到C柱子，且依然保持从上到下依次递增。
 */
public class Hanio {
    //只有一个盘子的情况
    public void moveOne(int n, String init, String desti) {
        System.out.println("move:" + n + "from" + init + "to" + desti);
    }

    public void move(int n, String init, String temp, String desti) {
        if (n <= 0) {
            System.out.println("number error");
            return;
        } else if (n == 1) {
            moveOne(n, init, desti);
        } else {
            //首先将上面的（n-1）个盘子从init杆借助desti杆移至temp杆
            move(n - 1, init, desti, temp);
            //然后将编号为n的盘子从init杆移至desti杆
            moveOne(n, init, desti);
            //最后将上面的（n-1）个盘子从temp杆借助init杆移至desti杆
            move(n - 1, temp, init, desti);
        }
    }
}

class HanioApp {
    public static void main(String args[]) {
        Hanio hanio = new Hanio();
        hanio.move(70, "A", "B", "C");
    }
}