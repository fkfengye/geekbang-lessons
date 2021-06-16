package com.geekbang.hystrix.space.demo01.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description
 * @create 2021-06-16 11:41
 **/
@Slf4j
public class CommandHello extends HystrixCommand<String> {

    private final String name;

    private final long timeout;


    public CommandHello(String group, String name, long timeout) {
        // 指定命令的分組，同一组使用同一个线程池
        super(HystrixCommandGroupKey.Factory.asKey(group));
        this.name = name;
        this.timeout = timeout;
    }

    /**
     * 要封装的业务请求
     * @return
     * @throws Exception
     */
    @Override
    protected String run() throws Exception {
        log.info("hystrix command execute");
        if (name == null) {
            throw new RuntimeException("data exception");
        }
        Thread.sleep(timeout); // 休眠
        return "hello " + name;
    }


    /**
     * 快速失败的降级逻辑
      * @return
     */
    @Override
    protected String getFallback() {
        log.info("return fallback data");
        return "error";
    }
}

