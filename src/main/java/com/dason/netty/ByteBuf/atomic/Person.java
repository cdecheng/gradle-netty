package com.dason.netty.ByteBuf.atomic;

/**
 * 测试对象person
 *
 * @author chendecheng
 * @since 2020-04-25 11:19
 */
public class Person {

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public volatile int age = 1;

}
