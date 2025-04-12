package com.test.study;

import com.test.study.subject.thread.ThreadService;
import org.junit.jupiter.api.Test;


/**
 * Thread 사용 없이 진행 할 경우 메서드 1번 실행당 3.02초
 * 1. 1만번 비교
 * virtualThread : 1.3487845초 , thread : 3.0835175초
 * 2. 10만번 비교
 * virtualThread : 11.5601335초 , thread : 11.7386459초
 * 3. 100만번 비교
 * virtualThread : 62.0290874초 , thread : 70.8238051초
 * 결론
 * 100만번 부터는 ZGC로 변경하지 않으면 virtualThread 에서 oom 발생 (semaphore 미사용시)
 * semaphore로 입장을 제한 할경우 (성능에 따른 수치 변경 필요)
 * 100만번 호출시 26.486337초 정도로 빠른 성능
 *
 */
public class ThreadTest {

    @Test
    public void virtualThreadTest(){
        new ThreadService().virtualThread(1000000);
    }

    @Test
    public void virtualThreadTestSemaphoreTest() throws InterruptedException{
        new ThreadService().virtualThreadTestSemaphore(1000000);
    }

    @Test
    public void threadTest(){
        new ThreadService().thread(100000);
    }


}
