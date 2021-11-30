import java.util.concurrent.Semaphore;

/***
 *Auth        : yanxiaojin
 *Date        : Create in 9:23 2021/11/12
 *Description : 请实现两个线程，使之交替打印 1 - 100。
 *Version     : 
 ***/

public class ThreadDemo {


    /**
     * 问题2：请实现两个线程，使之交替打印 1 - 100。第一个线程固定输出奇数，第二个线程固定输出偶数。
     */

    /**
     * 思路1：运用volatile关键字的特性，修饰一个flag对象，保证flag值在两个线程中可见
     * 通过判断flag值顺序输出1-100，flag值修改在打印之后，保证了会先打印并修改i的值
     */
    volatile static int flag;
    static int i = 1;
    public static void fun1(){

        new Thread(() ->{
            while (i < 100){
                if(flag == 0){
                    System.out.println(Thread.currentThread().getName()+"——"+i++);
                    flag = 1;
                }
            }
        },"Printer1").start();

        new Thread(() ->{
            while (i <= 100){
                if(flag == 1){
                    System.out.println(Thread.currentThread().getName()+"——"+i++);
                    flag = 0;
                }
            }
        },"Printer2").start();

    }

    /**
     * 思路2：通过工具类Semaphore实现两个线程交替打印
     * 定义两个Semaphore对象，设置许可数为1
     * Printer1执行前输出逻辑前先要获取semaphore1然后释放掉semaphore2的许可
     * Printer2执行前输出逻辑前先要获取semaphore2然后释放掉semaphore1的许可
     */
    static Semaphore semaphore1 = new Semaphore(1);
    static Semaphore semaphore2 = new Semaphore(1);
    static int j = 1;
    public static void fun2(){

        try {
            semaphore2.acquire();//先获取线程Printer2中用的许可，保证线程1先执行输出逻辑
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() ->{
            while (j < 100){
                if(j%2 == 1){
                    try {
                        semaphore1.acquire(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"——"+j++);
                    semaphore2.release(1);
                }
            }
        },"Printer1").start();

        new Thread(() ->{
            while (j <= 100){
                if(j%2 == 0){
                    try {
                        semaphore2.acquire(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"——"+j++);
                    semaphore1.release(1);
                }
            }
        },"Printer2").start();
    }


    /**
     * 思路3：使用synchronized和wait(),notify()实现
     */
    static int x = 1;
    public static void fun3(){
        Object o = new Object();
        new Thread(()->{
            while (x < 100){
                if(x%2 == 1){
                    synchronized (o){
                        o.notify();//唤醒Printer2 获取锁
                        System.out.println(Thread.currentThread().getName()+" "+x++);
                        try{
                            o.wait();//打印之后 等待 释放锁，这时Printer2获取到锁开始执行打印逻辑
                        }catch (Exception e){
                            e.getLocalizedMessage();
                        }
                    }
                }
            }
        },"Printer1").start();

        new Thread(()->{
            while (x <= 100  ){
                if(x%2 == 0){
                    synchronized (o){
                        o.notify();
                        System.out.println(Thread.currentThread().getName()+" "+x++);
                        try{
                            o.wait();
                        }catch (Exception e){
                            e.getLocalizedMessage();
                        }
                    }
                }
            }
        },"Printer2").start();
    }


    public static void main(String[] args) {
        fun3();
    }
}
