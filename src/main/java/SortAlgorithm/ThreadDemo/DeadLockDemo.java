package SortAlgorithm.ThreadDemo;

/***
 *Auth        : yanxiaojin
 *Date        : Create in 10:07 2021/11/4
 *Description : 死锁案例
 *Version     : 
 ***/

public class DeadLockDemo implements Runnable{
    static Object o1 = new Object();
    static Object o2 = new Object();
    private Integer flag = 1;
    DeadLockDemo(Integer flag){
        this.flag = flag;
    }

    @Override
    public void run() {
        if(flag == 1){
            synchronized (o1){
                try {
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName()+"获取到o1的锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2){
                    System.out.println(Thread.currentThread().getName()+"获取到o2的锁");
                }
            }

        }
        else{
            synchronized (o2){
                try {
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName()+"获取到o2的锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1){
                    System.out.println(Thread.currentThread().getName()+"获取到o1的锁");
                }
            }

        }
    }

    public static void main(String[] args) {
        DeadLockDemo d1 = new DeadLockDemo(1);

        DeadLockDemo d2= new DeadLockDemo(2);
        new Thread(d1).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(d2).start();
    }
}
