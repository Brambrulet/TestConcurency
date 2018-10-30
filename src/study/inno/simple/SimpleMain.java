package study.inno.simple;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.UUID.randomUUID;

public class SimpleMain extends Thread {
    private static final boolean concurency = false;
    private static final Set<String> set = newSet();
    private static final int threadsMax = 4;
    private static final int loopTimes = 2500;

    public SimpleMain() {
        start();
    }

    private static Set<String> newSet() {
        return concurency ?
                Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>()) :
                new TreeSet<>();
    }

    public static void main(String[] args) {
        Set<Thread> threads = new HashSet<>();

        for (int iThread = 0; iThread < threadsMax; iThread++) {
            threads.add(new SimpleMain());
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("size: " + set.size());
        System.out.println("must be: " + threadsMax * loopTimes);
        System.out.println("diff: " + (threadsMax * loopTimes - set.size()));
    }

    @Override
    public void run() {
        for (int i = 0; i < loopTimes; ++i) {
            set.add(randomUUID().toString());
        }
    }
}
