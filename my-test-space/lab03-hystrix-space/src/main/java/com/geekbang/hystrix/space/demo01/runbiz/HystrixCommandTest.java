package com.geekbang.hystrix.space.demo01.runbiz;

import com.geekbang.hystrix.space.demo01.command.CommandHello;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.Future;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description
 * @create 2021-06-16 11:46
 **/
@Slf4j
public class HystrixCommandTest {

    /**
     * 运行结果：
     *
     * 14:56:03.699 [hystrix-ExampleGroup-1] INFO com.lyyzoo.hystrix.CommandHello - hystrix command execute
     * 14:56:04.206 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - execute result is: hello hystrix
     */
    @Test
    public void test_HystrixCommand_execute() {
        CommandHello command = new CommandHello("ExampleGroup", "hystrix", 500);
        // 同步执行
        String result = command.execute();
        log.info("execute result is: {}", result);
    }

    /**
     * 运行结果：
     *
     * 14:56:19.269 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - do something...
     * 14:56:19.279 [hystrix-ExampleGroup-1] INFO com.lyyzoo.hystrix.CommandHello - hystrix command execute
     * 14:56:19.785 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - queue result is: hello hystrix
     */
    @Test
    public void test_HystrixCommand_queue() throws Exception {
        CommandHello command = new CommandHello("ExampleGroup", "hystrix", 500);
        // 异步执行，返回 Future
        Future<String> future = command.queue();
        log.info("do something...");
        log.info("queue result is: {}", future.get());
    }

    /**
     * 运行结果
     *
     * 14:59:56.748 [hystrix-ExampleGroup-1] INFO com.lyyzoo.hystrix.CommandHello - hystrix command execute
     * 14:59:57.252 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - observe result is: hello hystrix
     */
    @Test
    public void test_HystrixCommand_observe_single() {
        CommandHello command = new CommandHello("ExampleGroup", "hystrix", 500);
        Observable<String> observable = command.observe();
        // 获取请求结果，toBlocking() 是为了同步执行，不加 toBlocking() 就是异步执行
        String result = observable.toBlocking().single();
        log.info("observe result is: {}", result);
    }

    /**
     * 运行结果：
     *
     * 15:00:58.921 [hystrix-ExampleGroup-1] INFO com.lyyzoo.hystrix.CommandHello - hystrix command execute
     * 15:00:59.424 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - subscribe result is: hello hystrix
     * 15:00:59.425 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - completed
     */
    @Test
    public void test_HystrixCommand_observe_subscribe() {
        CommandHello command = new CommandHello("ExampleGroup", "hystrix", 500);
        Observable<String> observable = command.observe();
        // 订阅结果处理
        observable.toBlocking().subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                log.info("completed");
            }

            @Override
            public void onError(Throwable e) {
                log.info("error", e);
            }

            @Override
            public void onNext(String s) {
                log.info("subscribe result is: {}", s);
            }
        });
    }



}
