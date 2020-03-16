package com.dason.thrift;

import com.dason.thrift.generated.DataException;
import com.dason.thrift.generated.Person;
import com.dason.thrift.generated.PersonService;
import org.apache.thrift.TException;

/**
 * service,这是提供给server端的方法实现，也就是客户端可以远程rpc调用的
 *
 * @author chendecheng
 * @since 2020-02-19 23:52
 */
public class PersonServiceImpl implements PersonService.Iface {


    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        Person person = new Person();
        person.setUsername(username);
        person.setAge(18);
        person.setMarried(false);
        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("用户名" + person.getUsername());
        System.out.println("年龄" + person.getAge());
        System.out.println("是否结婚" + person.isMarried());
    }
}
