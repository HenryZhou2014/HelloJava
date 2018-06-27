package com.sync;

/**
 * synchronized 可重入锁，如果是非重入下面代码会死锁
 */
public class DeadLock {
    public static void main(String args[]){
        new LoggingWidget().doSomething();
    }

}
 class Widget{
    public Widget(){
        System.out.println("Widget");
    }
    public synchronized  void doSomething(){
        System.out.println("Widget doSomething....");
        System.out.println(this);
    }
}
 class LoggingWidget extends  Widget{
    public LoggingWidget(){
        System.out.println("LoggingWidget");
    }

    public synchronized  void doSomething(){
        System.out.println("LoggingWidget doSomething....");
        System.out.println(this);
        super.doSomething();
    }
}