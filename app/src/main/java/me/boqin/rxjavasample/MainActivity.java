package me.boqin.rxjavasample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        schedulerTest(Schedulers.io());
        //        schedulerTest(Schedulers.single());
    }

    private void schedulerTest(Scheduler scheduler) {

        Observable.just(1)
                .observeOn(scheduler)// [RxCachedThreadScheduler-1,5,main]
                .observeOn(scheduler)// [RxCachedThreadScheduler-2,5,main]
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        Log.d("BQ", "ThreadA:" + Thread.currentThread());
                        return integer * 10;
                    }
                })
                .observeOn(scheduler)// [RxCachedThreadScheduler-3,5,main]
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        Log.d("BQ", "ThreadB:" + Thread.currentThread());

                        return integer + 1;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("BQ", "integer:" + integer);
                    }
                });

    }
}
