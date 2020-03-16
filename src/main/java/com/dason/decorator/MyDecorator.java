package com.dason.decorator;

/**
 * 装饰者
 *
 * @author chendecheng
 * @since 2020-03-04 10:27
 */
public class MyDecorator implements Component {

    private Component component;

    public MyDecorator(Component component) {
        this.component = component;
    }

    @Override
    public void doSomeThing() {
        component.doSomeThing();
    }
}
