package ThreadsInOrder;

import lombok.Getter;

import java.util.ArrayList;

public class MyThread extends Thread {

    private static ArrayList<Integer> _intList = new ArrayList<>();

    MyThread(Runnable r, String name) {
        super(r, name);
    }
    MyThread(Runnable r) {
        super(r);
    }

    public static void addNumber(int n) {
        _intList.add(n);
    }
    public static boolean contains(int n) {
        return _intList.contains(n);
    }
    public static int howManyNumbers() {
        return  _intList.size();
    }
    public static int getLastNumber() {
        try {
            return _intList.get(_intList.size() - 1);
        } catch (IndexOutOfBoundsException ex) {
            return 0;
        }
    }


}
