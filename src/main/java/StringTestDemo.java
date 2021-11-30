/***
 *Auth        : yanxiaojin
 *Date        : Create in 8:44 2021/11/12
 *Description : 统计字符串中连续出现最多次数的字符
 *Version     : 
 ***/

public class StringTestDemo {

    /**
     * 问题1：从一个字符串中查找连续出现次数最多的字符，比如：输入字符串为"fasjrosdkskkkkk"，则输出为k
     * 思路：采用for循环遍历字节数组，统计连续的字符以及连续次数，判断是否超过了已统计的最大次数，超过，保存当前字符和次数；时间复杂的为O(n),n为字符串长度
     * 当2个字符连续出现次数相同切为最多时，取先出现的字符串；如果想取后面的字符串，将判断次数最大的表达式改为num >= max
     */
    public static String MaxNumChar(String str){

        if(str.length() == 0) return null;//当参数str为空 直接返回
        char[] chars = str.toCharArray();
        char a = chars[0];//存放存放连续出现最多字符
        char c = chars[0];//存放正在比较的字符串
        int max = 1,num = 1;//max存放连续出现最多字符的次数，num存放统计临时数据
        for (char b : chars) {
            if(b == c){//相等则计数+1
                num ++;
                if(num > max){//当前字符出现次数最大时，设置最大次数，并更新a
                    max = num;
                    a = c;
                }
            } else{//重置num，并将当前循环字符存到c
                num = 1;
                c = b;
            }
        }
        return String.valueOf(a);

    }

    public static void main(String[] args) {
        String s = "fasjjjjjjrosdkskkkkkk";
        System.out.println(MaxNumChar(s));
    }
}
