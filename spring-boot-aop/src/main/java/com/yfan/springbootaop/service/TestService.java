package com.yfan.springbootaop.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

@Service
public class TestService {
    @Async
    public void test() throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        ArrayList<CompletableFuture<String>> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(100 + new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return " " + finalI + " ";
            }, threadPool);
            list.add(completableFuture);
        }
        CompletableFuture<Void> all = CompletableFuture.allOf(list.toArray(new CompletableFuture[0]));
        all.join();
        StringBuilder sb = new StringBuilder();
        for (CompletableFuture<String> c : list) {
            sb.append(c.get());
        }
        System.out.println(sb.toString());
    }

}
