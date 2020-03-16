package com.dason.decorator;

/**
 * 客户端
 *
 * @author chendecheng
 * @since 2020-03-04 10:41
 */
public class UserClient {

    public static void main(String[] args) {

        Component component = new UserComponentImpl1(new UserComponentImpl2(new BasicComponectImpl()));
        component.doSomeThing();

        System.out.println("-------------------------------");

        Component component1 = new UserComponentImpl2(new BasicComponectImpl());
        component1.doSomeThing();

        System.out.println("-------------------------------");
        Component component2 = new BasicComponectImpl();
        component2.doSomeThing();


    }

}
