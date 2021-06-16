package com.geekbang.hystrix.space.demo01.runbiz;

import com.geekbang.hystrix.space.demo01.command.CommandHello;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description
 * @create 2021-06-16 11:55
 **/
@Slf4j
public class HystrixCommandFallBackTest {

    /**
     * 运行结果：
     *
     * 14:44:43.199 [hystrix-ExampleGroup-1] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - hystrix command execute
     * 14:44:43.203 [hystrix-ExampleGroup-1] DEBUG com.netflix.hystrix.AbstractCommand - Error executing HystrixCommand.run(). Proceeding to fallback logic ...
     * java.lang.RuntimeException: data exception
     * 	at com.lyyzoo.hystrix.Demo01_HystrixCommand$CommandHello.run(Demo01_HystrixCommand.java:75)
     * 	at com.lyyzoo.hystrix.Demo01_HystrixCommand$CommandHello.run(Demo01_HystrixCommand.java:61)
     * 	at com.netflix.hystrix.HystrixCommand$2.call(HystrixCommand.java:302)
     * 	........
     * 14:44:43.210 [hystrix-ExampleGroup-1] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - return fallback data
     * 14:44:43.214 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - result is: error
     */
    @Test
    public void test_HystrixCommand_exception_fallback() {
        CommandHello command = new CommandHello("ExampleGroup", null, 500);
        // 抛出异常，返回降级逻辑中的数据
        String result = command.execute();
        log.info("result is: {}", result);
    }

    /**
     * 运行结果：
     *
     * 14:51:27.114 [hystrix-ExampleGroup-1] INFO com.lyyzoo.hystrix.CommandHello - hystrix command execute
     * 14:51:28.113 [HystrixTimer-1] INFO com.lyyzoo.hystrix.CommandHello - return fallback data
     * 14:51:28.119 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - result is: error
     */
    @Test
    public void test_HystrixCommand_timeout_fallback() {
        CommandHello command = new CommandHello("ExampleGroup", "hystrix", 1500);
        // 请求超时，返回降级逻辑中的数据
        String result = command.execute();
        log.info("result is: {}", result);
    }


}
