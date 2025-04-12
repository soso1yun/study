package com.test.study.subject.thread;

import org.springframework.stereotype.Component;

@Component
public class Calculate {

    public int addition(int a, int b) throws InterruptedException{
        Thread.sleep(1000);
        return a + b;
    }

    public int subtraction(int a, int b) throws InterruptedException{
        Thread.sleep(1000);
        return a - b;
    }

    public int division(int a, int b) throws InterruptedException {
        Thread.sleep(1000);
        return a * b;
    }

}
