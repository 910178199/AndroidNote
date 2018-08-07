package com.example.superman.expandabletextview.ThreadPoolTest;


import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolMain {

    private static final String TAG = ThreadPoolExecutor.class.getSimpleName();

    /**
     * NewCachedThreadPool
     * 可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
     * 特点：
     * 1.maximumPoolSize（最大线程大小）没有限制，数目为Interger.MAX_VALUE,灵活添加线程
     * 2.如果长时间没有往线程池中提交任务，即如果工作线程空闲了制定的时间（默认1分钟），则该工作线程将自动终止。
     * 终止后，如果在提交新任务，则线程池重新创建一个工作线程。
     * 3.在使用CachedThreadPool时，一定要注意控制任务的数量，否则,由于大量线程同时运行，很可能造成系统瘫痪。
     * 通常用于执行一些生存期很短的异步型任务
     */
    /**
     * 创建一种线程数量不定的线程池，它只有非核心线程，并且最大线程数为Integer.MAX_VALUE
     * 当线程池中的线程都处于活动状态时,线程池会创建新的线程来处理任务，否则就会利用空闲的线程来处理任务
     * 线程池中的线程都有超时的限制，60s，超过60s闲置的线程就会把它回收。比较适合执行大量耗时少的任务
     */
    public static void testNewCachedThreadPool() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            try {
                Thread.sleep(index * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(new Runnable() {
                public void run() {
                    Log.e(TAG, "testNewCachedThreadPool: " + index);
                }
            });
        }
    }


    /**
     * NewFixedThreadPool
     * 1.创建一个指定工作线程数量的线程池，每当提交一个任务就创建一个工作线程，如果工作线程数量达到线程池初始的最大数，
     * 则将提交的任务存入到线程池队列中
     */
    /**
     * 创建一种线程数量固定的线程池，线程处于空闲状态，不会被回收 当所有的线程都处于活动状态时，新的任务会处于等待状态，直到有线程可以空出来。
     * 创建是的线程都是核心线程，且不会被回收，除非线程池关闭。能更快响应外界的请求
     * 核心的线程没有超时限制，队列也没有大小限制。
     */
    public static void testNewFixedThreadPool() {
        //可获得的线程数量
        final int availableProcessors = Runtime.getRuntime().availableProcessors();
        final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(availableProcessors);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "testNewFixedThreadPool: " + index + "  core:" + availableProcessors);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 创建一个单线程化的Executor，即只创建唯一的工作者线程来执行任务，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
     * 如果这个线程异常结束，会有另一个取代它，保证顺序执行。单工作线程最大的特点是可保证顺序地执行各个任务，并且在任意给定的时间不会有多个线程是活动的。
     */
    /**
     * 创建的线程池中只有一个核心线程，他确保所有的任务都在同一个线程中按顺序执行。
     * SingleThreadExecutor目的在于统一所有的外界任务到一个线程中，使得在这些任务之间不需要处理线程同步的问题
     */
    public static void testNewSingleThreadExecutor() {
        final ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            newSingleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "testNewSingleThreadExecutor: " + index);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     *创建一个定长的线程池，而且支持定时的以及周期性的任务执行，支持定时及周期性任务执行。
     */
    /**
     * 创建一种线程池，核心线程数固定，非核心线程数量没有限制，非核心线程闲置会被立即回收
     * 主要用来处理定时任务和具有固定周期的重复任务。
     */
    public static void testNewScheduledThreadPool() {
        //可获得的线程数量
        ScheduledExecutorService newSingleThreadExecutor = Executors.newScheduledThreadPool(5);
        newSingleThreadExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "testNewScheduledThreadPool: delay 5 seconds");
            }
        }, 5, TimeUnit.SECONDS);
    }

}
