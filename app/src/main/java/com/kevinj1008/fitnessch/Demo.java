package com.kevinj1008.fitnessch;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Demo {

    public static void main(String[] args) {
        //迴圈解法
//        int sum = 0;
//        for (int i = 1; i <= 50; i ++) {
//            System.out.println(sum += i);
//        }
//        System.out.println(sum(50));
        int n = 3;
        int circle = 3;

        for (int i = 0; i < n * circle; i++) {
            if (i == (n * circle) - 1) {
                System.out.print(i % n + "\n");
            } else {
                System.out.print(i % n + ", ");
            }
        }
//                for (int k = 0; k < circle; k++) {
//            for (int i = 0; i < j; i++) {
//                System.out.print(i % j + ", ");
//            }
//        }
        // 牛頓逼近開根求解
        DecimalFormat formatter = new DecimalFormat("#.#");
        System.out.print(formatter.format(sqrt(8)) + "\n");

//        Map<Integer, String> HOSTING = new HashMap<>();
//        HOSTING.put(1, "linode.com");
//        HOSTING.put(2, "heroku.com");
//        HOSTING.put(3, "digitalocean.com");
//        HOSTING.put(4, "aws.amazon.com");
//
//        // Before Java 8
//        String result = "";
//        for (Map.Entry<Integer, String> entry : HOSTING.entrySet()) {
//            if ("aws.amazon.com".equals(entry.getValue())) {
//                result = entry.getValue();
//            }
//        }
//        System.out.println("Before Java 8 : " + result);
//        int m = 4;
//        for (int i = 0; i <= m; i++) {
//            System.out.print(fibo(i));
//        }



    }
    //遞迴解法
    private static int sum(int n) {
        return n < 1 ? 0 : n + sum(n - 1);
//        if (n < 1) {
//            return 0;
//        } else {
//            return n + sum(n - 1);
//        }
    }

    // 牛頓逼近開根求解
    private static double sqrt(double x) {
        double ans = x;

        while(ans*ans-x>1e-10)
            ans=(ans*ans+x)/(2*ans);
        return ans;
    }

    // 費伯納西數列?????????????
//    private static int fibo(int n) {
//        if (n == 1) {
//            return 0;
//        } else if (n == 2) {
//            return 1;
//        }
//        return fibo(n - 1) + fibo(n - 2);
//
//        for (int i = n; i >= 0; i --) {
//            if (i == 0) {
//                return 0;
//            } else if (i == 1) {
//                return 1;
//            } else {
//                return fibo(i - 1) + fibo(i - 2);
//            }
//        }
//    }

}
