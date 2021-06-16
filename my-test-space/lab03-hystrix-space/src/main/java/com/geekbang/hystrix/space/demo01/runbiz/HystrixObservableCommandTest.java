package com.geekbang.hystrix.space.demo01.runbiz;

import com.geekbang.hystrix.space.demo01.command.CommandObservableHello;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import rx.Observer;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description
 * @create 2021-06-16 11:53
 **/
@Slf4j
public class HystrixObservableCommandTest {

    /**
     * 运行结果：
     *
     * 15:22:49.306 [main] INFO com.lyyzoo.hystrix.CommandObservableHello - hystrix command execute
     * 15:22:49.316 [main] INFO com.lyyzoo.hystrix.Demo02_HystrixObservableCommand - last result: hello command
     */
    @Test
    public void test_HystrixObservableCommand_observe() {
        CommandObservableHello command = new CommandObservableHello("ExampleGroup");

        String result = command.observe().toBlocking().last();
        log.info("last result: {}", result);
    }

    /**
     * 运行结果：
     *
     * 15:23:08.685 [main] INFO com.lyyzoo.hystrix.CommandObservableHello - hystrix command execute
     * 15:23:08.691 [main] INFO com.lyyzoo.hystrix.Demo02_HystrixObservableCommand - result data: hello world
     * 15:23:08.691 [main] INFO com.lyyzoo.hystrix.Demo02_HystrixObservableCommand - result data: hello hystrix
     * 15:23:08.691 [main] INFO com.lyyzoo.hystrix.Demo02_HystrixObservableCommand - result data: hello command
     * 15:23:08.691 [main] INFO com.lyyzoo.hystrix.Demo02_HystrixObservableCommand - completed
     */
    @Test
    public void test_HystrixObservableCommand_observe_subscribe() {
        CommandObservableHello command = new CommandObservableHello("ExampleGroup");

        command.observe().subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                log.info("completed");
            }

            @Override
            public void onError(Throwable e) {
                log.info("error");
            }

            @Override
            public void onNext(String o) {
                log.info("result data: {}", o);
            }
        });
    }

}
