package com.study.flinkdemo1.arithmetic.Source;


import com.study.flinkdemo1.demoDto.AClass;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.shaded.curator.org.apache.curator.shaded.com.google.common.collect.Lists;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 自定义实现一个支持多并行度的Source
 */
public class MyRichParalleSource extends RichParallelSourceFunction<Long> {
    private long count = 1L;
    private boolean isRunning = true;

    /**
     * 主要的方法
     * 启动一个Source
     * 大部分情况下,都需要在这个run方法中实现一个循环,这样就可以循环产生数据了
     */
    @Override
    public void run(SourceContext<Long> ctx) throws Exception {
        while(isRunning){
            ctx.collect(count);
            count++;
            //每秒产生一条数据
            Thread.sleep(1000);
        }
    }

    /**
     * 取消一个cancel的时候会调用的方法
     */
    @Override
    public void cancel() {
        isRunning = false;
    }
    /**
     * 这个方法只会在最开始的时候被调用一次
     */
    public void open(Configuration parameters) throws Exception{
        System.out.println("open......");
        super.open(parameters);
    }

    /**
     * 实现关闭链接的代码
     */
    public void close() throws Exception{
        super.close();
    }

}
