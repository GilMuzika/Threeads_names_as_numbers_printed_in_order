package ThreadsInOrder;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

// https://drive.google.com/file/d/1kJ2VJz8fLd4ey6WJ4NfKhEpEzVS2P16e/view

public class Main {

    private static final ArrayList<Thread> _allThreads = new ArrayList<>();
    private static final Random _rnd = new Random();

    private static boolean isAllBeforeHere(int num) {
        for(int i = num - 1; i >= 1; i--) {
            if(!MyThread.contains(i))
                return false;
        }
        return true;
    }
    private static void whichThreadsAreAlive() {
        for (Thread tr : _allThreads) {
            if(tr.isAlive())
                System.out.printf("Thread %s is alive \n", tr.getName());
        }
    }

    public synchronized static void print_order() throws InterruptedException {
        int numFromName = Integer.parseInt(Thread.currentThread().getName());

        while (!isAllBeforeHere(numFromName) && numFromName != MyThread.getLastNumber()) {
            Main.class.wait();
        }

        Thread.sleep(20);
        MyThread.addNumber(numFromName);
        Main.class.notifyAll();
        System.out.print(Thread.currentThread().getName() + "  ");

    }




    public static void main(String[] args){
        System.out.println("Hello world!");

        Runnable runnable = () -> {
            try {
                Main.print_order();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        int numberOfThreads = 50;

        for (int i = 0; i < numberOfThreads; i++) {
            Thread t = new MyThread(runnable, Integer.toString(i + 1));
            _allThreads.add(t);
        }

        _allThreads.sort((o1, o2) -> 5 - _rnd.nextInt(1, 10));
        System.out.println("The original order:");
        System.out.println(_allThreads.stream().map(x -> x.getName()).collect(Collectors.toList()));
        System.out.print("\n");
        _allThreads.forEach(Thread::start);
        _allThreads.forEach((x) -> {
                try {
                    x.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        System.out.println();
        System.out.println("\nAfter finishing all the threads");



    }
}