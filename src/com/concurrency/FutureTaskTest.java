package com.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {

    //可以异步得到执行的结果，可以将大耗时间操作放这里
    private final FutureTask<Object> future =  new FutureTask<Object>(new Callable<Object>() {
        @Override
        public Object call() throws Exception {
            System.out.println("pre load data....");
            return getObj();
        }
    });

    private Object getObj() throws InterruptedException {

        for(int i =0 ;i<500;i++){
            Thread.sleep(100);
            System.out.println("qry data");
        }

        return "Hello Future";
    }

    private final  Thread thread = new Thread(future);

    public void start(){
        thread.start();
    }

    public Object get(){
        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String args[]){
        FutureTaskTest test = new FutureTaskTest();
        System.out.println("start...");
        test.start();
        System.out.println("doSomething pay times 6s.");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("get Data Result !");
        System.out.println(test.get());
    }
}
