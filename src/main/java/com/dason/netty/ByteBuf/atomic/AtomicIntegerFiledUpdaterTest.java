package com.dason.netty.ByteBuf.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * AtomicIntegerFiledUpdater使用demo
 *
 * @author chendecheng
 * @since 2020-04-25 11:16
 */
public class AtomicIntegerFiledUpdaterTest {

    private static final AtomicIntegerFieldUpdater<Person> refCntUpdater =
            AtomicIntegerFieldUpdater.newUpdater(Person.class, "age");

    public static void main(String[] args) {
//        unAtomicOpration();
        atomicOpration();
    }

    /**
     * 反面案例，非原子操作
     */
    private static void unAtomicOpration() {
        Person person = new Person();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                person.setAge(person.getAge()+1);
                System.out.println(person.getAge());
            });
            thread.start();
        }
    }

    /**
     * 原子操作正面案例
     */
    private static void atomicOpration() {
        Person person = new Person();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(refCntUpdater.getAndIncrement(person));
            });
            thread.start();
        }
    }



}
