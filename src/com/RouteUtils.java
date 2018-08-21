package com;

import java.util.*;



public class RouteUtils {
    private static String[] northArray = {"103400","100000","102300","102700","102800","101400","100900","101500","102400","102900","103900","102600","103800","100100","101700","101100","101000","103700","101200"};
    private static String[] sourthArray = {"102000","100800","103000","104000","103100","101300","102100","101800","100200","100700","103200","104200","100300","100500","103300","102200","100600","101900","101600","102500"};
    private static int[] sourthArray2 = {102000,100800,103000,104000,103100,101300,102100,101800,100200,100700,103200,104200,100300,100500,103300,102200,100600,101900,101600,102500};

    private static Set<String> northSet = new HashSet<String>(Arrays.asList(northArray));
    private static Set<String> sourthSet = new HashSet<String>(Arrays.asList(sourthArray));
    //private static List<String> northSet = Arrays.asList(northArray);
    //private static List<String> sourthSet = Arrays.asList(sourthArray);
    private final static String SOUTH="S";
    private final static String NORTH="N";

    private static Map<String,String> cacheMap = new HashMap<String,String>();
    static{
        Iterator<String>  itN=northSet.iterator();
        while(itN.hasNext()){
            cacheMap.put(itN.next(),NORTH);
        }

        Iterator<String>  itS=sourthSet.iterator();
        while(itS.hasNext()){
            cacheMap.put(itS.next(),SOUTH);
        }
    }

    public static String route2( String depotCode){
        return cacheMap.get(depotCode);
    }

    public static String route(String depotCode){
//        if (StringUtils.isBlank(depotCode)) {
//            return "ERROR";
//        }
        /*if (sourthSet.contains(depotCode)){
            //System.out.println("南方");
            return "S";
        }
        if (northSet.contains(depotCode)){
            //System.out.println("北方");
            return "N";
        }*/
        return sourthSet.contains(depotCode) ? "S" : "N";
    }
    public static void main(String args[]){
        long start = System.currentTimeMillis();
        Random random = new Random();
        int count=1000000;
        //压测
        for(int i = 0; i<= count; i++) {
            route(sourthArray[random.nextInt(19)]);
        }
        long end = System.currentTimeMillis();
        System.out.println("SET 查询"+count+"个对象 \n共耗费时间："+(end-start)+ "毫秒");


         start = System.currentTimeMillis();
        //压测
        for(int i = 0; i<= count; i++) {
            route2(sourthArray[random.nextInt(19)]);
        }
         end = System.currentTimeMillis();
        System.out.println("MAP 查询"+count+"个对象 \n共耗费时间："+(end-start)+ "毫秒");
    }
}
