package org.geekbang.rxjava.space.chapter7;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import org.junit.Test;

import java.util.concurrent.TimeUnit;


/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description
 * @create 2021-06-14 19:32
 **/
public class CombineLatestAndJoinDemo {


    @Test
    public void combineLatestTest(){
        Observable<Integer> odds = Observable.just(1, 3, 5);
        Observable<Integer> evens = Observable.just(2, 4, 6);

        Observable.combineLatest(odds, evens, (i, j) -> {
            System.out.println("i=" + i + "-------j=" + j);
            return i + j;
        }).subscribe(System.out::println);
    }


    @Test
    public void joinTest(){
        Observable<Integer> o1 = Observable.just(1, 3, 5);
        Observable<Integer> o2 = Observable.just(2, 4, 6);

        o1.join(o2, new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                return Observable.just(String.valueOf(integer)).delay(200, TimeUnit.MILLISECONDS);
            }
        }, new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                return Observable.just(String.valueOf(integer)).delay(200, TimeUnit.MILLISECONDS);
            }
        }, new BiFunction<Integer, Integer, String>() {
            @Override
            public String apply(Integer integer, Integer integer2) throws Exception {
                return integer + ":" + integer2;
            }

        }).subscribe((Consumer<String>) s -> System.out.println("onNext:" + s));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
















