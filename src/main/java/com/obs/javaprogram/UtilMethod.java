package com.obs.javaprogram;

public class UtilMethod {
    public boolean verifyCheckEven(int number) {
        boolean flag = false;
        int r = number % 2;
        if (r == 0) {
            flag = true;
        }
        return flag;
    }

}
