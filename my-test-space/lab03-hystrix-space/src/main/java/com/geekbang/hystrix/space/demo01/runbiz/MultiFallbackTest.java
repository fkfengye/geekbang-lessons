package com.geekbang.hystrix.space.demo01.runbiz;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description
 * @create 2021-06-16 12:02
 **/
@Slf4j
public class MultiFallbackTest {


    /**
     * 请求结果：
     * <p>
     * 22:28:46.313 [hystrix-MySqlPool-1] INFO com.lyyzoo.hystrix.CommandMySQL - get data from mysql
     * 22:28:46.319 [main] INFO com.lyyzoo.hystrix.Demo03_HystrixCommand_MultiFallback - result: mysql-number-1
     * 22:28:46.320 [hystrix-MySqlPool-2] INFO com.lyyzoo.hystrix.CommandMySQL - get data from mysql
     * 22:28:46.324 [hystrix-MySqlPool-2] DEBUG com.netflix.hystrix.AbstractCommand - Error executing HystrixCommand.run(). Proceeding to fallback logic ...
     * java.lang.RuntimeException: data not found in mysql
     * at com.lyyzoo.hystrix.CommandMySQL.run(Demo03_HystrixCommand_MultiFallback.java:50)
     * at com.lyyzoo.hystrix.CommandMySQL.run(Demo03_HystrixCommand_MultiFallback.java:29)
     * at com.netflix.hystrix.HystrixCommand$2.call(HystrixCommand.java:302)
     * ......
     * 22:28:46.332 [hystrix-MySqlPool-2] INFO com.lyyzoo.hystrix.CommandMySQL - coming mysql fallback
     * 22:28:46.344 [hystrix-RedisPool-1] INFO com.lyyzoo.hystrix.CommandRedis - get data from redis
     * 22:28:46.344 [main] INFO com.lyyzoo.hystrix.Demo03_HystrixCommand_MultiFallback - result: redis-number-2
     */
    @Test
    public void test_HystrixCommand_multi_fallback() {
        CommandMySQL command = new CommandMySQL("ExampleGroup", 1);
        log.info("result: {}", command.execute());

        CommandMySQL command2 = new CommandMySQL("ExampleGroup", 2);
        log.info("result: {}", command2.execute());
    }


}


@Slf4j
class CommandMySQL extends HystrixCommand<String> {

    private final String group;
    private final Integer id;

    public CommandMySQL(String group, Integer id) {
        super(
                HystrixCommand.Setter
                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey(group))
                        // 指定不同的线程池
                        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("MySqlPool"))
        );
        this.group = group;
        this.id = id;
    }

    @Override
    protected String run() throws Exception {
        log.info("get data from mysql");
        if (id % 2 == 0) {
            throw new RuntimeException("data not found in mysql");
        }
        return "mysql-number-" + id;
    }

    /**
     * 快速失败的降级逻辑
     * @return
     */
    @Override
    protected String getFallback() {
        log.info("coming mysql fallback");
        // 嵌套 Command
        HystrixCommand<String> command = new CommandRedis(group, id);
        return command.execute();
    }
}


@Slf4j
class CommandRedis extends HystrixCommand<String> {
    private final Integer id;

    public CommandRedis(String group, Integer id) {
        super(
                HystrixCommand.Setter
                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey(group))
                        // 指定不同的线程池
                        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("RedisPool"))
        );
        this.id = id;
    }

    @Override
    protected String run() throws Exception {
        log.info("get data from redis");
        return "redis-number-" + id;
    }

    /**
     * 快速失败的降级逻辑
     * @return
     */
    @Override
    protected String getFallback() {
        log.info("coming redis fallback");
        return "error";
    }
}
