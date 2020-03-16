package com.dason.protobuf1;

import com.dason.protobuf2.DataInfo;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * protobuff 测试demo
 *
 * @author chendecheng
 * @since 2020-02-16 21:32
 */
public class ProtoBufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        com.dason.protobuf2.DataInfo.Student student = com.dason.protobuf2.DataInfo.Student.newBuilder().
                setName("张震").setAge(28).setAddress("广州").build();

        byte[] bytes = student.toByteArray();

        DataInfo.Student student2 = com.dason.protobuf2.DataInfo.Student.parseFrom(bytes);
        System.out.println(student2.getName());
        System.out.println(student2.getAge());
        System.out.println(student2.getAddress());

    }

}
