package com.study.flinkdemo1.arithmetic.stream;

import com.study.flinkdemo1.arithmetic.Source.MyNoParalleSource;
import com.study.flinkdemo1.arithmetic.Partition.MyPartition;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 使用自定义的Partition
 */
public class SteamingDemoWithMyParitition {

    public static void main(String[] args) throws Exception {
        //创建flink,流运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setMaxParallelism(9);
        //加载数据源,使用自定义的Partition
        DataStreamSource<Long> text = env.addSource(new MyNoParalleSource());
        //对数据进行转换,把Long类型转成Tuple1类型
        DataStream<Tuple1<Long>> tupleData = text.map(new MapFunction<Long, Tuple1<Long>>() {
            @Override
            public Tuple1<Long> map(Long value) throws Exception {
                return new Tuple1<>(value);
            }
        });
        //分区之后的数据
        DataStream<Tuple1<Long>> partitionData = tupleData.partitionCustom(new MyPartition(),0);
        DataStream<Long> result = partitionData.map(new MapFunction<Tuple1<Long>, Long>() {
            @Override
            public Long map(Tuple1<Long> value) throws Exception {
                System.out.println("当前线程id:"+Thread.currentThread().getId()+"value: "+value);
                return value.getField(0);
            }
        });
        result.print().setParallelism(1);
        env.execute("StreamingDemoWithMyParitition");
    }
}
