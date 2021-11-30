package SortAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/***
 *Auth        : yanxiaojin
 *Date        : Create in 20:53 2021/11/3
 *Description : 合并区间，暴力算法
 *Version     : 
 ***/

public class ViolenceSort {

    /**
     * 示例 1：以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
     * 请你合并所有重叠的区间，并返回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间
     *
     * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
     * 输出：[[1,6],[8,10],[15,18]]
     * 解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
     */

    public static int[][] toMerge(int[][] intervals) {
        //将数组按照区间头进行排序
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] > o2[0] ? 1 : o1[0] == o2[0] ? 0 : -1;
            }
        });
        //result存放合并后的数组
        List<int[]> result = new ArrayList<int[]>();
        for (int i = 0; i < intervals.length; i++) {
            if(result.size() == 0){//将第一个区间放入List中
                result.add(intervals[i]);
                continue;
            }
            //第i个区间与result中最后一个元素进行比较
            int[] ints = result.get(result.size() - 1);
            //比较分三种情况

            //1、result中尾元素区间包含当前循环的区间
            if(ints[0] <= intervals[i][1] && ints[1] >= intervals[i][1]){
                continue;//舍弃原数组，进行下一次循环
            }

            //2、两个区间有交集
            if(ints[1] >= intervals[i][0] && ints[1] <= intervals[i][1]){
                ints[1] = intervals[i][1];//合并区间，更新ints的区间上限
                continue;
            }

            //3、两个区间没有交集，直接将该区间加入result
            result.add(intervals[i]);
        }

        return result.toArray(new int[0][]);
    }

    //测试
    public static void main(String[] args) {
        int[][] intervals = {{1,3},{2,6},{8,10},{15,18}};
        int[][] ints = toMerge(intervals);
        System.out.println(Arrays.deepToString(ints));
    }
}
