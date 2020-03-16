package com.dason.gRPC;

import com.dason.protobuf.*;
import io.grpc.stub.StreamObserver;

import java.util.UUID;

/**
 * 测试服务端提供的service方法
 *
 * @author chendecheng
 * @since 2020-02-23 21:05
 */
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    @Override
    public void getRealNamByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("接受的客户端消息：" + request.getUsername());
        responseObserver.onNext(MyResponse.newBuilder().setRealname("叫我张震").build());
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("接受的客户端消息：" + request.getAge());
        responseObserver.onNext(StudentResponse.newBuilder().setAge(18).setCity("广州1").setName("张震1").build());
        responseObserver.onNext(StudentResponse.newBuilder().setAge(19).setCity("广州2").setName("张震2").build());
        responseObserver.onNext(StudentResponse.newBuilder().setAge(20).setCity("广州3").setName("张震3").build());
        responseObserver.onNext(StudentResponse.newBuilder().setAge(21).setCity("广州4").setName("张震4").build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<StudentRequest> getStudentsWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                System.out.println("服务端收到的值："+value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("出错了");
            }

            @Override
            public void onCompleted() {
                StudentResponse studentResponse1 = StudentResponse.newBuilder().setName("掌政1").setAge(67).setCity("dangzhongy1").build();
                StudentResponse studentResponse2 = StudentResponse.newBuilder().setName("掌政3").setAge(67).setCity("dangzhongy23").build();
                StudentResponse studentResponse3 = StudentResponse.newBuilder().setName("掌政5").setAge(67).setCity("dangzhongy3").build();
                StudentResponseList list = StudentResponseList.newBuilder().addStudentResponse(studentResponse1).
                        addStudentResponse(studentResponse2).addStudentResponse(studentResponse3).build();
                responseObserver.onNext(list);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<StreamResquest> biTalk(StreamObserver<StreamResponse> responseObserver) {
        return new StreamObserver<StreamResquest>() {
            @Override
            public void onNext(StreamResquest value) {
                responseObserver.onNext(StreamResponse.newBuilder().setResponseInfo(UUID.randomUUID().toString()).build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("出错了");
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
