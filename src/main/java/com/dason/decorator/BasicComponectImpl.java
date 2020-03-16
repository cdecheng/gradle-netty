package com.dason.decorator;

/**
 * 顶层接口基本实现类
 *
 * @author chendecheng
 * @since 2020-03-04 10:25
 */
public class BasicComponectImpl implements Component {

    @Override
    public void doSomeThing() {
        System.out.println("我是最基本的实现类");
    }
}
