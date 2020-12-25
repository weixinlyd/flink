package com.study.flinkdemo1.arithmetic.testa;

/**
 * flatMap实际是将集合进行flat,map处理
 * 先对数据进行函数处理在进行扁平化
 * 扁平化就是将数处在多级结构的数据处理成同一级
 * flatMap能处理多层集合
 */


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 示例
 */
public class FlatMapDemo {


    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
//添加数据略
        integers.stream().map(i -> i + 1).forEach(System.out::println);
        System.out.println(integers);

        List<List<Integer>> outer = new ArrayList<>();
        List<Integer> inner1 = new ArrayList<>();
        inner1.add(1);
        List<Integer> inner2 = new ArrayList<>();
        inner2.add(2);
        List<Integer> inner3 = new ArrayList<>();
        inner3.add(3);
        List<Integer> inner4 = new ArrayList<>();
        inner4.add(4);
        List<Integer> inner5 = new ArrayList<>();
        inner5.add(5);
        outer.add(inner1);
        outer.add(inner2);
        outer.add(inner3);
        outer.add(inner4);
        outer.add(inner5);
        List<Integer> result = outer.stream().flatMap(inner -> inner.stream().filter(i->i>2).map(i -> i + 1)).collect(Collectors.toList());
        System.out.println(result);



    }
}
