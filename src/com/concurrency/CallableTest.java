package com.concurrency;

import java.util.concurrent.*;

public class CallableTest implements Callable<String>{

    @Override
    public String call() throws Exception {

        Thread.sleep(2000);
        return "I'm exe reulst from Thread";
    }

    public static void main(String[] args){
        FutureTask<String> futureTask = new FutureTask<String>(new CallableTest());
        Executor executor= Executors.newSingleThreadExecutor();
        executor.execute(futureTask);
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ExecutorService exe =  Executors.newFixedThreadPool(100);
        Future<String> future =exe.submit(new CallableTest());
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Future future2 =exe.submit(new RunnableTest());
        try {
            System.out.println("future2:"+future2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class RunnableTest implements  Runnable{

    public void run(){
        System.out.println("I'm runable result");
    }
}
