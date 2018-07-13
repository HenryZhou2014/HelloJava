package com.runtime;

public class ShutdownHookTest {

    public static void main(String[] args){
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                System.out.println("exe before JVM exit！");
            }
        });
    }
}
