package completablefuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description  组合事件操作
 * @create 2021-05-24 11:33
 **/
public class CompletableFutureDemo02 {

    /**
     * A 执行完成之后通知 B开始执行，B无法获取到A的结果
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testThenRun() throws ExecutionException, InterruptedException {

        // 创建异步任务，返回future  one
        CompletableFuture<String> one = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                // 休眠2秒模拟计算任务
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("one thread name : " + Thread.currentThread().getName() + " 执行完成");
                return "我是 one 的结果";
            }
        });

        // 在one 的基础上添加事件，当one执行完成之后调用该事件，并返回新的future two
        CompletableFuture<Void> two = one.thenRun(new Runnable() {
            @Override
            public void run() {
                // 休眠2秒模拟计算任务
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("two thread name : " + Thread.currentThread().getName() + " 执行完成");
            }
        });

        // 同步等待two的执行完成，整个执行链为，先one -> two -> 返回
        System.out.println(two.get());
    }


    /**
     * A 执行完成之后通知 B开始执行，A 的结果会传递给 B，但是B执行之后没有结果返回
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testThenAccept() throws ExecutionException, InterruptedException {
        // 创建异步任务，返回future  one
        CompletableFuture<String> one = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                // 休眠2秒模拟计算任务
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("one thread name : " + Thread.currentThread().getName() + " 执行完成");
                return "我是 one 的结果";
            }
        });

        // 在one 的基础上添加事件，当one执行完成之后调用该事件，并返回新的future two
        CompletableFuture<Void> two = one.thenAccept(new Consumer<String>() {

            /**
             * @param s one 执行的结果会传递过来
             */
            @Override
            public void accept(String s) {
                System.out.println("two任务重accept接收到了one执行完成后的结果 ：" + s);
                // 休眠2秒模拟计算任务
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("two thread name : " + Thread.currentThread().getName() + " 执行完成");
            }
        });

        // 同步等待two的执行完成，整个执行链为，先one -> two -> 返回
        System.out.println(two.get());
    }


    /**
     * A 执行完成之后通知 B开始执行，A 的结果会传递给 B，B也返回执行结果数据
     */
    @Test
    public void testThenApply() throws ExecutionException, InterruptedException {
        // 创建异步任务，返回future  one
        CompletableFuture<String> one = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                // 休眠2秒模拟计算任务
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("one thread name : " + Thread.currentThread().getName() + " 执行完成");
                return "我是 one 的结果";
            }
        });

        // 在one 的基础上添加事件，当one执行完成之后调用该事件，并返回新的future two
        CompletableFuture<String> two = one.thenApply(new Function<String, String>() {
            @Override
            public String apply(String s) {
                System.out.println("two任务重accept接收到了one执行完成后的结果 ：" + s);
                // 休眠2秒模拟计算任务
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("two thread name : " + Thread.currentThread().getName() + " 执行完成");
                return "我是 two 的结果";
            }
        });

        // 同步等待two的执行完成，整个执行链为，先one -> two -> 返回结果
        System.out.println(two.get());
    }



    /**
     * 基于whenComplete设置回调函数，当异步任务执行完毕后进行回调，不会阻塞调用线程
     */
    @Test
    public void testWhenComplete() throws InterruptedException {
        // 1、创建异步任务，返回future  one
        CompletableFuture<String> one = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                // 休眠2秒模拟计算任务
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("one thread name : " + Thread.currentThread().getName() + " 执行完成");
                return "我是 one 的结果";
            }
        });

        // 2、添加回调函数
        one.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                if (throwable == null) {
                    System.out.println("回调，异步执行结果入参：" + s);
                } else {
                    System.out.println("回调，异步执行出现异常：" + throwable.getLocalizedMessage());
                }
            }
        });

        System.out.println("主线后续任务");
        ///3、挂起主线程，等待上面异步线程执行完
        Thread.currentThread().join();
    }
}
