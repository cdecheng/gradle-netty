package com.dason.thrift;

import com.dason.thrift.generated.Person;
import com.dason.thrift.generated.PersonService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * thrift客户端
 *
 * @author chendecheng
 * @since 2020-02-20 00:17
 */
public class ThriftClient {
    public static void main(String[] args) {
        TTransport tTransport = new TFramedTransport(new TSocket("localhost", 9999), 600);
        TProtocol protocol = new TCompactProtocol(tTransport);
        PersonService.Client client = new PersonService.Client(protocol);

        try {
            tTransport.open();

            Person person = client.getPersonByUsername("zhangzheng");
            System.out.println("用户名"+person.getUsername());
            System.out.println("年纪"+person.getAge());
            System.out.println("是否结婚"+person.isMarried());

            Person person1 = new Person();
            person1.setMarried(true);
            person1.setAge(32);
            person1.setUsername("zhangzhen");

            client.savePerson(person1);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            tTransport.close();
        }

    }
}
