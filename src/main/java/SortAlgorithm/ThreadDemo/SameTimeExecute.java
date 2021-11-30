package SortAlgorithm.ThreadDemo;

import java.util.concurrent.CountDownLatch;

/***
 *Auth        : yanxiaojin
 *Date        : Create in 10:27 2021/11/4
 *Description : 线程同时执行
 *Version     : 
 ***/

public class SameTimeExecute {


    /**
     * CountDownLatch实现
     */
    private static void CountDownLatchDemo() throws InterruptedException {
        int size = 3;
        CountDownLatch count = new CountDownLatch(1);//设置计数器（倒数）
        for (int i = 0; i < size; i++) {
            new Thread(() ->{
                try {
                    count.await();
                    System.out.println(System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(1000);
        count.countDown();
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchDemo();
    }
}
