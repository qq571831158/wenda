package com.newcoder.wenda;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by apple on 2017/8/29.
 */

class myThread extends Thread {
    private int tid;

    public myThread(int tid) {
        this.tid = tid;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                System.out.println(String.format("%d:%d", tid, i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable{
    private  BlockingQueue<String>q;
    public Consumer(BlockingQueue<String>q ){
        this.q = q;
    }
    @Override
    public void run() {
        try {
            while (true){
                System.out.println(Thread.currentThread().getName()+":"+q.take());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class Producer implements Runnable{
    private  BlockingQueue<String>q;
    public Producer(BlockingQueue<String>q ){
        this.q = q;
    }
    @Override
    public void run() {
        try {
            for (int i = 0;i<100;i++){
                Thread.sleep(1000);
                q.put(String.valueOf(i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

public class MultiThreadTests {

    public static void testThread() {
        for (int i = 0; i < 10; i++) {
            new myThread(i).start();
        }

//        for (int i = 0;i<10;i++){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try{
//                        for (int  i = 0 ;i<10;i++){
//                            Thread.sleep(1000);
//                            System.out.println(String.format("t2:%d",i));
//                        }
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }
    }

    private static Object object = new Object();

    public static void testSynchronized1() {
        synchronized (object) {
            try {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                    System.out.println(String.format("t3:%d", i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void testSynchronized2() {
        synchronized (new Object()) {
            try {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                    System.out.println(String.format("t4:%d", i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void testSynchronized(){
        for (int i = 0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    testSynchronized1();
                    testSynchronized2();
                }
            }).start();
        }
    }

    public static void testBlockingQueue(){
        BlockingQueue<String> q = new ArrayBlockingQueue<String>(10);
        new Thread(new Producer(q)).start();
        new Thread(new Consumer(q),"Consumer1").start();
        new Thread(new Consumer(q),"Consumer2").start();

    }

    private static int userId;
    private static ThreadLocal<Integer>threadLocalUserids = new ThreadLocal<>();
    public static void testThreadLocal(){
        for (int i = 0;i<10;i++){
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        threadLocalUserids.set(finalI);
                        Thread.sleep(1000);
                        System.out.println("ThreadLocal:"+threadLocalUserids.get());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        for (int i = 0;i<10;i++){
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        userId = finalI;
                        Thread.sleep(1000);
                        System.out.println("userid:"+userId);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


    public static void testExcutor(){
//        ExecutorService service = Executors.newSingleThreadExecutor();
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i<10;i++){
                    try {
                        Thread.sleep(1000);
                        System.out.println("Executor1:"+i);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        service.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i<10;i++){
                    try {
                        Thread.sleep(1000);
                        System.out.println("Executor2:"+i);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        service.shutdown();

        while (!service.isTerminated()){
            try {
                Thread.sleep(1000);
                System.out.println("Wait for terminal");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static int counter = 0;
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void testWithoutAtmoic(){
        for (int i = 0 ;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        for (int j = 0;j<1000;j++){
                            counter++;
                            System.out.println(counter);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


    public static void testWithAtmoic(){
        for (int i = 0 ;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        for (int j = 0;j<10;j++){

                            System.out.println(atomicInteger.incrementAndGet());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    public static void testAtomic(){
//        testWithoutAtmoic();
        testWithAtmoic();
    }

    public static void testFuture(){
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Integer>future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
//                Thread.sleep(1000);
                throw new IllegalAccessException("异常");
//                return 1;
            }
        });
        service.shutdown();
        try {
            //System.out.println(future.get());
            System.out.println(future.get(100,TimeUnit.MILLISECONDS));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
//        testThread();
//        testSynchronized();
//        testBlockingQueue();
//        testThreadLocal();
//        testExcutor();
//        testAtomic();
        testFuture();
    }
}
