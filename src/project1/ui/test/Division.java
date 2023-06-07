package project1.ui.test;

import java.util.Scanner;

public class Division {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("请输入被除数：");
            int dividend = sc.nextInt();
            System.out.print("请输入除数：");
            int divisor = sc.nextInt();
            int result = dividend / divisor;
            System.out.println("运算结果为：" + result);
        } catch (ArithmeticException e) {
            System.out.println("被除数不能为0！");
        } catch (Exception e) {
            System.out.println("输入数据类型不符！");
        }
        sc.close();
    }
}
