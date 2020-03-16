package com.dason.decorator;

/**
 * 扩展实现1
 *
 * @author chendecheng
 * @since 2020-03-04 10:30
 */
public class UserComponentImpl1 extends MyDecorator {

    public UserComponentImpl1(Component component) {
        super(component);
    }

    @Override
    public void doSomeThing() {
        super.doSomeThing();
        doAnOtherThing();
    }

    public void doAnOtherThing() {
        System.out.println("扩展实现1");
    }

}
