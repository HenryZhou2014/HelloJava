package com.reflect;

import com.esotericsoftware.reflectasm.MethodAccess;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectTest {

    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        long now;
        long sum = 0;
        sum = 0;
        now = System.currentTimeMillis();
        ReflectTest t = new ReflectTest();

        for(int i = 0; i<500000; ++i){
            t.setNum(i);
            sum += t.getNum();
        }

        System.out.println("对象直接调用耗时"+(System.currentTimeMillis() - now) + "ms秒，和是" +sum);

        sum = 0;
        Class<?> c = Class.forName("com.reflect.ReflectTest");
        Class<?>[] argsType = new Class[1];
        argsType[0] = int.class;
        for(int i = 0; i<500000; ++i){
            Method m = c.getMethod("setNum", argsType);
            m.invoke(t, i);
            sum += t.getNum();
        }
        System.out.println("标准反射耗时"+(System.currentTimeMillis() - now) + "ms，和是" +sum);

        sum = 0;

//        Class<?> c = Class.forName("test.TestClass");
//        Class<?>[] argsType = new Class[1];
//        argsType[0] = int.class;
        Method m = c.getMethod("setNum", argsType);

        now = System.currentTimeMillis();

        for(int i = 0; i<500000; ++i){
            m.invoke(t, i);
            sum += t.getNum();
        }
        System.out.println("缓存反射耗时"+(System.currentTimeMillis() - now) + "ms，和是" +sum);


        MethodAccess ma = MethodAccess.get(ReflectTest.class);
        int index = ma.getIndex("setNum");
        now = System.currentTimeMillis();
        sum = 0;
        for(int i = 0; i<500000; ++i){
            ma.invoke(t, index, i);
            sum += t.getNum();
        }
        System.out.println("reflectasm反射耗时"+(System.currentTimeMillis() - now) + "ms，和是" +sum);
    }
}
