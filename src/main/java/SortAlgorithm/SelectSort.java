package SortAlgorithm;

import java.util.Arrays;

/***
 *Auth        : yanxiaojin
 *Date        : Create in 9:39 2021/11/4
 *Description : 选择排序
 *Version     : 
 ***/

public class SelectSort {
    public static int[] selectSort(int[] ints){
        int index, temp;
        for (int i = 0; i < ints.length - 1; i++) {
            index = i;
            for (int j = i + 1; j < ints.length; j++) {
                if(ints[index] > ints[j]){
                    index = j;//寻找此次循环最小索引
                }
            }
            if(index != i){//交换位置
                temp = ints[i];
                ints[i] = ints[index];
                ints[index] = temp;
            }
        }
        return ints;
    }

    public static void main(String[] args) {
        int[] ints = {15,23,1,345,32,25,45,3,10,15,33,11};
        System.out.println(Arrays.toString(selectSort(ints)));
    }
}
