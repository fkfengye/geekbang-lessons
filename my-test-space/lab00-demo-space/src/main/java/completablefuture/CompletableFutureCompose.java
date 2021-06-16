package completablefuture;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description  多个CompletableFuture进行组合运算
 * @create 2021-05-24 11:33
 **/
public class CompletableFutureCompose {

    /**
     * 基于thenCompose实现当一个CompletableFuture执行完毕后，执行另外一个CompletableFuture
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testThenCompose() throws ExecutionException, InterruptedException {
        CompletableFuture<String> one = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println("one thread name : " + Thread.currentThread().getName() + " 执行完成");
                return "value1";
            }
        });


        CompletableFuture<String> two = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println("two thread name : " + Thread.currentThread().getName() + " 执行完成");
                return "value2";
            }
        });

        CompletableFuture result = one.thenCompose(r -> two);
        System.out.println(result.get());
    }


    /**
     * 基于thenCombine实现当两个并发运行的CompletableFuture任务都完成后，使用两者的结果作为参数再执行一个异步任务
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testThenCombine() throws ExecutionException, InterruptedException {
        CompletableFuture<String> one = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println("one thread name : " + Thread.currentThread().getName() + " 执行完成");
                return "value1";
            }
        });


        CompletableFuture<String> two = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println("two thread name : " + Thread.currentThread().getName() + " 执行完成");
                return "value2";
            }
        });

        CompletableFuture<String> result = one.thenCombine(two, (r1, r2) -> r1 + r2);
        System.out.println(result.get());
    }


    /**
     * 基于allOf等待多个并发运行的CompletableFuture任务执行完毕
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testAllOf() throws ExecutionException, InterruptedException {
        // 向容器中添加5个 CompletableFuture
        CompletableFuture<String>[] futures = new CompletableFuture[5];

        for (int i = 0; i < futures.length; i++) {
            int count = i;

            futures[i] = CompletableFuture.supplyAsync(new Supplier<String>() {
                @Override
                public String get() {
                    System.out.println("thread name : " + Thread.currentThread().getName() + " 执行完成");
                    return "value" + count;
                }
            });
        }

        System.out.println("开始执行");

        // 执行容器中的所有 future
        CompletableFuture<Void> result = CompletableFuture.allOf(futures);

        // 阻塞等待所有future执行完成
        System.out.println(result.get());

        System.out.println("执行结束");
    }


    /**
     * 基于anyOf 等多个并发运行的CompletableFuture任务中有一个执行完毕就返回
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testAnyOf() throws ExecutionException, InterruptedException {
        // 向容器中添加5个 CompletableFuture
        CompletableFuture<String>[] futures = new CompletableFuture[5];

        for (int i = 0; i < futures.length; i++) {
            int count = i;

            futures[i] = CompletableFuture.supplyAsync(new Supplier<String>() {
                @Override
                public String get() {
                    System.out.println(count + "thread name : " + Thread.currentThread().getName() + " 正在执行");
                    return "value" + count;
                }
            });
        }

        System.out.println("开始执行");

        // 执行容器中的所有 future
        CompletableFuture<Object> result = CompletableFuture.anyOf(futures);

        // 阻塞等待所有future执行完成
        System.out.println(result.get());

        System.out.println("执行结束");
    }


}
