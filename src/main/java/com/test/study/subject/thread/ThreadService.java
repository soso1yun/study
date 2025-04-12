package com.test.study.subject.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ThreadService {

    public void virtualThreadTestSemaphore(int cnt) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Semaphore semaphore = new Semaphore(180000);

        Random random = new Random();
        Calculate calculate = new Calculate();
        ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();

        AtomicInteger atomicInteger = new AtomicInteger();
        AtomicInteger atomicInteger2 = new AtomicInteger();
        AtomicInteger atomicInteger3 = new AtomicInteger();

        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < cnt; i++) {
            semaphore.acquire(3);

            int a = random.nextInt(1000);
            int b = random.nextInt(1000);

            Callable<Integer> runnable1 = () -> {
                try {
                    return atomicInteger.addAndGet(calculate.subtraction(a, b));
                } finally {
                    semaphore.release();
                }
            };

            Callable<Integer> runnable2 = () -> {
                try {
                    return atomicInteger2.addAndGet(calculate.addition(a, b));
                } finally {
                    semaphore.release();
                }
            };

            Callable<Integer> runnable3 = () -> {
                try {
                    return atomicInteger3.addAndGet(calculate.division(a, b));
                } finally {
                    semaphore.release();
                }
            };

            futures.add(service.submit(runnable1));

            futures.add(service.submit(runnable2));
            futures.add(service.submit(runnable3));
        }

        for (Future<Integer> future : futures) {
            try {
                // 작업 완료 시 해당 Future 결과 반환
                future.get();
            } catch (Exception e) {
                log.error("작업 실행 중 오류 발생", e);
            }
        }

        log.info("\nThreadService====== \n subtraction = {}, addition = {}, division = {}\n", atomicInteger.get(), atomicInteger2.get(), atomicInteger3.get());

        stopWatch.stop();


        log.info("totalTimeSeconds = {}\n===========", stopWatch.getTotalTimeSeconds());
    }


    public void virtualThread(int cnt) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Random random = new Random();
        Calculate calculate = new Calculate();
        ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();

        List<Future<Integer>> list = new ArrayList<>();

        for (int i = 0; i < cnt; i++) {
            int a = random.nextInt(1000);
            int b = random.nextInt(1000);

            Callable<Integer> runnable1 = () -> calculate.subtraction(a, b);

            Callable<Integer> runnable2 = () -> calculate.addition(a, b);

            Callable<Integer> runnable3 = () -> calculate.division(a, b);


            list.add(service.submit(runnable1));
            list.add(service.submit(runnable2));
            list.add(service.submit(runnable3));
        }

        try {
            int c = 0;
            int d = 0;
            int e = 0;

            for (int i = 0; i < list.size(); i += 3) {
                c += list.get(i).get();
                d += list.get(i + 1).get();
                e += list.get(i + 2).get();
            }

            log.info("\nThreadService====== \n subtraction = {}, addition = {}, division = {}\n", c, d, e);

        } catch (ExecutionException | InterruptedException e) {
            e.fillInStackTrace();
        }


        stopWatch.stop();


        log.info("totalTimeSeconds = {}\n===========", stopWatch.getTotalTimeSeconds());
    }


    public void thread(int cnt) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Random random = new Random();
        Calculate calculate = new Calculate();
        ExecutorService service = Executors.newCachedThreadPool();

        List<Future<Integer>> list = new ArrayList<>();

        for (int i = 0; i < cnt; i++) {
            int a = random.nextInt(1000);
            int b = random.nextInt(1000);

            Callable<Integer> runnable1 = () -> calculate.subtraction(a, b);

            Callable<Integer> runnable2 = () -> calculate.addition(a, b);

            Callable<Integer> runnable3 = () -> calculate.division(a, b);


            list.add(service.submit(runnable1));
            list.add(service.submit(runnable2));
            list.add(service.submit(runnable3));
        }

        try {
            int c = 0;
            int d = 0;
            int e = 0;

            for (int i = 0; i < list.size(); i += 3) {
                c += list.get(i).get();
                d += list.get(i + 1).get();
                e += list.get(i + 2).get();
            }

            log.info("\nThreadService====== \n subtraction = {}, addition = {}, division = {}\n", c, d, e);

        } catch (ExecutionException | InterruptedException e) {
            e.fillInStackTrace();
        }


        stopWatch.stop();


        log.info("totalTimeSeconds = {}\n===========", stopWatch.getTotalTimeSeconds());
    }

}
