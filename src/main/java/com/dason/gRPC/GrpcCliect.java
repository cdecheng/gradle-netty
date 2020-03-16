package com.dason.gRPC;

import com.dason.protobuf.StreamResponse;
import com.dason.protobuf.StreamResquest;
import com.dason.protobuf.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDate;

/**
 * rpc请求客户端
 *
 * @author chendecheng
 * @since 2020-02-23 21:26
 */
public class GrpcCliect {

    public static void main(String[] args) {

        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8899).
                usePlaintext().build();
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.
                newBlockingStub(managedChannel);
        StudentServiceGrpc.StudentServiceStub stub = StudentServiceGrpc.newStub(managedChannel);

//        System.out.println("----------------------对象-对象----------------------------");
//
//        MyResponse myResponse = blockingStub.getRealNamByUsername(MyRequest.newBuilder().setUsername("你谁呀").build());
//        System.out.println("我是："+myResponse.getRealname());
//
//        System.out.println("----------------------对象--stream---------------------------");
//
//        Iterator<StudentResponse> iterator = blockingStub.getStudentsByAge(
//                StudentRequest.newBuilder().setAge(20).build());
//        while (iterator.hasNext()) {
//            StudentResponse response = iterator.next();
//            System.out.println("返回结果：" + response.getName() + "," + response.getAge() + "," + response.getCity());
//        }

//        System.out.println("---------------------stream-对象-----------------------------");
//
//        //处理返回结果
//        StreamObserver<StudentResponseList> studentResponseListStreamObserver = new StreamObserver<StudentResponseList>() {
//            @Override
//            public void onNext(StudentResponseList value) {
//                value.getStudentResponseList().forEach(studentResponse -> {
//                    System.out.println(studentResponse.getName());
//                    System.out.println(studentResponse.getCity());
//                    System.out.println(studentResponse.getAge());
//                    System.out.println("**********************");
//                });
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                System.out.println("出错了");
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("结束了");
//            }
//        };
//        //发送请求
//        StreamObserver<StudentRequest> studentRequestStreamObserver = stub.getStudentsWrapperByAges(studentResponseListStreamObserver);
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(56).build());
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(58).build());
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(56).build());
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(59).build());
//        studentRequestStreamObserver.onCompleted();
//
//        try {
//            Thread.sleep(50000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println("---------------------stream-stream-----------------------------");

        StreamObserver<StreamResquest> resquestStreamObserver = stub.biTalk(new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse value) {
                System.out.println(value.getResponseInfo());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("出错了");
            }

            @Override
            public void onCompleted() {
                System.out.println("结束了");
            }
        });

        for (int i = 0; i < 10; i++) {
            resquestStreamObserver.onNext(StreamResquest.newBuilder().setRequestInfo(LocalDate.now().toString()).build());
        }

        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
