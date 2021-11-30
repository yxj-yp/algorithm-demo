package SortAlgorithm;

import java.util.Arrays;

/***
 *Auth        : yanxiaojin
 *Date        : Create in 9:29 2021/11/4
 *Description : 冒泡排序
 *Version     : 
 ***/

public class BubbleSort {
    
    public static int[] bubbleSort(int[] ints){
        for (int i = 0; i < ints.length - 1; i++) {
            for (int j = i + 1; j < ints.length; j++) {
                if(ints[i] > ints[j]){
                    int anInt = ints[i];
                    ints[i] = ints[j];
                    ints[j] = anInt;
                }
            }
        }
        return  ints;
    }

    public static void main(String[] args) {
        int[] ints = {15,23,1,345,32,25,45,3,10,15,33,11};
        System.out.println(Arrays.toString(bubbleSort(ints)));
    }
}
