package com.examtest;

public class ExamTest {

    public static void main(String[] args){
        System.out.println(hasNumber("aaeeeeeeeeeeee"));
        System.out.println(hasDoubleChar("abc"));
        System.out.println(printBinStr(159));
    }

    public static String printBinStr(int number){
        if(number>255){
            throw  new IllegalArgumentException("param:"+number);
        }
       String bin= Integer.toBinaryString(number);
       StringBuilder sb = new StringBuilder();
       for(int i =0; i< 8-bin.length();i++){
           sb.append("0");
       }
       return sb.toString()+bin;
    }

    public static boolean hasDoubleChar(String in){
        if(in==null || in.length()<2){
            return false;
        }
        boolean ret=false;
        for(int i=0; i<in.length()-1; i++){
            if(in.charAt(i) == in.charAt(i+1)){
                ret=true;
                break;
            }
        }

        return ret;
    }

    public static boolean hasNumber(String in){

        if(in == null){
            return false;
        }else{
            for(int i=0;i<in.length();i++){
                if(Character.isDigit(in.charAt(i))){
                    return true;
                }
            }
            return false;
        }
    }
}
