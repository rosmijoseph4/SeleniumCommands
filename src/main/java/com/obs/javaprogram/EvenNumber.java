package com.obs.javaprogram;

public class EvenNumber {

    public static void main(String[] args) {
        UtilMethod utilMethod=new UtilMethod();
        boolean status;
        for(int i=1;i<10;i++){
          status= utilMethod.verifyCheckEven(i);
          if(status){
              System.out.println(i);
          }
        }

    }
}
