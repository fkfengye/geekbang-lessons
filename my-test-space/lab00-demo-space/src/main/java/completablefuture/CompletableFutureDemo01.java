package completablefuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description  单纯的异步执行
 * @create 2021-05-24 11:33
 **/
public class CompletableFutureDemo01 {

    /**
     * 基于runAsync系列方法实现无返回值的异步计算
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testRunAsync() throws ExecutionException, InterruptedException {
        // 1、创建异步任务，并返回future
        CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {

            @Override
            public void run() {
                // 休眠2秒模拟计算任务
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("执行完成");
            }
        });

        // 2、同步等待异步任务执行结果
        future.get();
    }


    /**
     * 基于supplyAsync系列方法实现有返回值的异步计算
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testSupplyAsync() throws ExecutionException, InterruptedException {
        // 1、创建异步任务，并返回future
        CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {

            @Override
            public String get() {
                // 休眠2秒模拟计算任务
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello, 你好！";
            }

        });

        // 2、同步等待异步任务执行结果
        String result = future.get();
        System.out.println(result);
    }

}
