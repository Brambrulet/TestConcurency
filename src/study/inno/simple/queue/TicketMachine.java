package study.inno.simple.queue;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TicketMachine {
    private static final int windowsQty = 20;
    private static final Map<Operation, Queue<Integer>> operations = new ConcurrentHashMap<>();
    private static final Random rand = new Random();
    private static int counter = 0;

    static {
        operations.put(Operation.op_1, new ConcurrentLinkedQueue<>());
        operations.put(Operation.op_2, new ConcurrentLinkedQueue<>());
        operations.put(Operation.op_3, new ConcurrentLinkedQueue<>());
        operations.put(Operation.op_4, new ConcurrentLinkedQueue<>());
        operations.put(Operation.op_5, new ConcurrentLinkedQueue<>());
    }

    private final Set<WindowClass> windows = new HashSet<>();

    public TicketMachine() {
        for (int iWindow = 0; iWindow < windowsQty; iWindow++) {
            windows.add(new WindowClass(this, iWindow + 1));
        }

        try {
            new ClientsThread(this).join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        whileLoop:
        while (true) {
            for (Queue<Integer> queue : operations.values()) {
                if (queue.size() > 0) {
                    try {
                        Thread.currentThread().sleep(5);
                        continue whileLoop;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break whileLoop;
                }
            }
        }
    }

    public static Operation getRandomOperation() {
        int opNo = rand.nextInt(operations.size());
        int iOperation = 0;

        for (Operation op : operations.keySet()) {
            if (iOperation == opNo) return op;
            else ++iOperation;
        }

        return null;
    }

    public static void main(String[] args) {
        new TicketMachine();
    }

    public synchronized int getNextNumber() {
        return ++counter;
    }

    public Integer getNextNumberForOperation(Operation operation) {
        return operations.get(operation).poll();
    }


    public void welcomeGuest() {
        int clientNumber = getNextNumber();
        System.out.println("welcome guest: " + clientNumber);
        operations.get(getRandomOperation()).add(clientNumber);
    }
}
