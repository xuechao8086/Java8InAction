package lambdasinaction.xuechao8086.test;

import java.util.Scanner;

/**
 * @author gumi
 * @since 2018/03/20 10:50
 */
public class MinArray {

    /** 请完成下面这个函数，实现题目要求的功能 **/
    /** 当然，你也可以不按照这个模板来作答，完全按照自己的想法来 ^-^  **/
    static long min(int[] from, int[] to) {

        int[] tem=null;
        for(int i=0;i<to.length-1; ++i){
            for(int j=0; j<from.length-1; j++){
                if((to[i]+from[j])>(from[j]+from[j+1]) && Math.abs(to[i]-from[j])>Math.abs(from[j]-from[j+1])){
                    tem[i]=to[i];
                    tem[i+1]=from[j];
                }else {
                    tem[i] = to[i];
                    tem[i+1] = to[i+1];
                }
            }
        }
        long addNumber = 0;
        for (int i=0;i<tem.length;i+=2) {
            addNumber += tem[i]*tem[i+1];
        }

        return addNumber;
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        long res;

        int _from_size = 0;
        _from_size = Integer.parseInt(in.nextLine().trim());
        int[] _from = new int[_from_size];
        int _from_item;
        for(int _from_i = 0; _from_i < _from_size; _from_i++) {
            _from_item = Integer.parseInt(in.nextLine().trim());
            _from[_from_i] = _from_item;
        }

        int _to_size = 0;
        _to_size = Integer.parseInt(in.nextLine().trim());
        int[] _to = new int[_to_size];
        int _to_item;
        for(int _to_i = 0; _to_i < _to_size; _to_i++) {
            _to_item = Integer.parseInt(in.nextLine().trim());
            _to[_to_i] = _to_item;
        }

        res = min(_from, _to);
        System.out.println(String.valueOf(res));

    }
}
