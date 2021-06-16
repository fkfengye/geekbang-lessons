package com.geekbang.hystrix.space.demo01.command;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Subscriber;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description
 * @create 2021-06-16 11:44
 **/
@Slf4j
public class CommandObservableHello extends HystrixObservableCommand<String> {

    public CommandObservableHello(String group) {
        // 指定命令的分組，同一组使用同一个线程池
        super(HystrixCommandGroupKey.Factory.asKey(group));
    }

    @Override
    protected Observable<String> construct() {
        log.info("hystrix command execute");
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                // 发送多条数据
                subscriber.onNext("hello world");
                subscriber.onNext("hello hystrix");
                subscriber.onNext("hello command");
                subscriber.onCompleted();
            }
        });
    }

    /**
     * 快速失败的降级逻辑
     * @return
     */
    @Override
    protected Observable<String> resumeWithFallback() {
        log.info("return fallback data");
        return Observable.just("error");
    }
}
