package org.geekbang.rxjava.space.chapter2.coldandhot;

import com.sun.istack.internal.NotNull;
import io.reactivex.*;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description Hot Observable 和 Cold Observable
 * @create 2021-05-14 14:56
 **/
public class HotAndColdObservable {

    /**
     * Cold Observable
     */
    @Test
    public void test01(){
        Consumer<Long> subscriber1 = new Consumer<Long>() {
            @Override
            public void accept(@NotNull Long aLong) {
                System.out.println("subscriber1:" + aLong);
            }
        };

        Consumer<Long> subscriber2 = new Consumer<Long>() {
            @Override
            public void accept(@NotNull Long aLong) {
                System.out.println("subscriber2:" + aLong);
            }
        };

        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> e) throws Exception {
                Observable.interval(10, TimeUnit.MILLISECONDS, Schedulers.computation())
                        .take(Integer.MAX_VALUE)
                        .subscribe(e::onNext);
            }
        }).observeOn(Schedulers.newThread());


        observable.subscribe((Observer<? super Long>) subscriber1);


    }

}

























