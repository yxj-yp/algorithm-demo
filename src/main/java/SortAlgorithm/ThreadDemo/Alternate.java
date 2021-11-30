package SortAlgorithm.ThreadDemo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/***
 *Auth        : yanxiaojin
 *Date        : Create in 10:52 2021/11/4
 *Description : 线程交替执行
 *Version     : 
 ***/

public class Alternate {
    static volatile int flag = 1;

    /**
     * 交替打印a b c
     */
    public static void volDemo(){
        new Thread(()->{
            while (true){
                if(flag == 1){
                    System.out.println("a----"+1);
                    flag = 2;
                }
            }
        }).start();
        new Thread(()->{
            while (true){
                if(flag == 2){
                    System.out.println("b----"+2);
                    flag = 3;
                }
            }
        }).start();
        new Thread(()->{
            while (true){
                if(flag == 3){
                    System.out.println("c----"+3);
                    flag = 1;
                }
            }
        }).start();
    }

    /**
     * 三个线程交替打印 1-100,基于volatile和ReentrantLock
     */
    public static void volDemo1(){
        ReentrantLock lock = new ReentrantLock();
        new Thread(()->{
            while (true){
                if(flag %3 == 1 && flag <= 100){
                    lock.lock();
                    System.out.println("a----"+flag++);
                    lock.unlock();
                }
            }
        }).start();
        new Thread(()->{
            while (true){
                if(flag %3 == 2 && flag <= 100){
                    lock.lock();
                    System.out.println("b----"+flag++);
                    lock.unlock();
                }
            }
        }).start();
        new Thread(()->{
            while (true){
                if(flag %3 == 0 && flag <= 100){
                    lock.lock();
                    System.out.println("c----"+flag++);
                    lock.unlock();
                }
            }
        }).start();
    }

    /**
     * Semaphore控制三个线程交替执行
     */
    public static void semaphoreDemo(){
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(1);
        Semaphore s3 = new Semaphore(1);
        try {
            s2.acquire();
            s3.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            while (true){
                try {
                    s1.acquire();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A");
                s2.release();
            }
        }).start();
        new Thread(()->{
            while (true){
                try {
                    s2.acquire();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("b");
                s3.release();
            }
        }).start();
        new Thread(()->{
            while (true){
                try {
                    s3.acquire();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("c");
                s1.release();
            }
        }).start();

    }

    public static void main(String[] args) {
//        volDemo();
//        volDemo1();
        semaphoreDemo();
    }
}
