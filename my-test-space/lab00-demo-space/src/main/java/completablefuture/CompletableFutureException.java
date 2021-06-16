package completablefuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description  异常处理
 * @create 2021-05-24 11:33
 **/
public class CompletableFutureException {

    @Test
    public void testException() throws ExecutionException, InterruptedException {
        // 1、构建future对象
        CompletableFuture<String> future = new CompletableFuture<>();

        new Thread(() -> {
            try {
                // 2、主动抛出异常
                if (true) {
                    throw new RuntimeException("出现异常");
                }

                // 3、设置正常执行结果
                future.complete("ok");
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

            System.out.println("------ " + Thread.currentThread().getName() + " ------- set future result");
        }, "thread-1").start();

        // 4、获取执行结果
        System.out.println(future.get());

        System.out.println("执行结束");
    }


    /**
     * completeExceptionally 进行异常处理
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testCompleteExceptionally() throws ExecutionException, InterruptedException {
        // 1、构建future对象
        CompletableFuture<String> future = new CompletableFuture<>();

        new Thread(() -> {
            try {
                // 2、主动抛出异常
                if (true) {
                    throw new RuntimeException("出现异常");
                }

                // 3、设置正常执行结果
                future.complete("ok");
            } catch (RuntimeException e) {
                e.printStackTrace();
                // 4、出现异常，将异常传递给 future
                future.completeExceptionally(e);
            }

            System.out.println("------ " + Thread.currentThread().getName() + " ------- set future result");
        }, "thread-1").start();

        // 5、future 设置出现异常时，返回默认值
        CompletableFuture<String> f = future.exceptionally(e -> "default");

        // 6、等待执行结果
        System.out.println(f.get());

        System.out.println("执行结束");
    }



}
