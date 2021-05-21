package org.geekbang.rxjava.space.chapter2.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import org.junit.Test;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description
 * @create 2021-05-13 16:23
 **/
public class ObservableDemo {


    /**
     * 简单使用
     */
    @Test
    public void demo01(){
        Observable.just("Hello World").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });

        // 简化写法
        Observable.just("Hello World").subscribe(System.out::println);
    }


    /**
     * 重载版本  subscribe(onNext, onError, onComplete）
     */
    @Test
    public void demo02(){

        Observable.just("Hello World").subscribe(
                // 消费每条数据
                new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("处理数据：" + s);
                    }
                },

                // 出现异常时执行
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("出现异常。。。。");
                        System.out.println(throwable);
                    }
                },

                // 完成时调用
                new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("执行完成。。。。");
                    }
                }
        );
    }


    /**
     * subscribe(onN ext,onError, onComplete,onSubscribe）
     */
    @Test
    public void demo03(){
        Observable.just("Hello World").subscribe(
                new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("处理数据：" + s);
                    }
                },

                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("出现异常。。。。");
                        System.out.println(throwable);
                    }
                },

                new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("执行完成。。。。");
                    }
                },

                new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        System.out.println("开始执行。。。。。。。。。。。");
                    }
                }
        );
    }


    /**
     * 在 RxJava 2 中， Observable 不再支持订阅 Subscriber ，而是需要使用 Observer 作为观察者
     */
    @Test
    public void demo04(){
        Observable.just("Hello World").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("开始执行");
            }

            @Override
            public void onNext(String s) {
                System.out.println("消费到一条数据：" + s);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("出现异常" + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("执行完成");
            }
        });
    }


}
