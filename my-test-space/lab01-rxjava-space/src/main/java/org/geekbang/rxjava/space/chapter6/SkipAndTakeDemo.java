package org.geekbang.rxjava.space.chapter6;

import io.reactivex.Observable;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description
 * @create 2021-06-14 9:50
 **/
public class SkipAndTakeDemo {

    @Test
    public void skipUntilTest(){
        Observable.intervalRange(1, 9, 0, 1, TimeUnit.MILLISECONDS)
                .skipUntil(Observable.timer(4, TimeUnit.MILLISECONDS))
                .subscribe(System.out::println);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void skipWhileTest(){
//        System.out.println(1 % 2);
        Observable.just(1,2,3,4,5)
                .skipWhile(integer -> integer % 2 > 0)
                .subscribe(System.out::println);
    }

}
