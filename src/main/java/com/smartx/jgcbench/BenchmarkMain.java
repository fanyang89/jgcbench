package com.smartx.jgcbench;

import java.util.ArrayDeque;
import java.util.Deque;

public class BenchmarkMain {
    private static final int OBJ_SIZE = 1024;
    private static final int LIVE_TARGET_BYTES = 500 * 1024 * 1024;
    private static final int WINDOW = LIVE_TARGET_BYTES / OBJ_SIZE;

    private static final Deque<byte[]> window = new ArrayDeque<>();

    public static void main(String[] args) {
        System.out.println("PID = " + ProcessHandle.current().pid());
        long start = System.nanoTime();
        long cycles = 0;

        while (System.nanoTime() - start <= 60_000_000_000L) {
            for (int i = 0; i < WINDOW; i++) {
                window.addLast(new byte[OBJ_SIZE]);
            }
            for (int i = 0; i < WINDOW; i++) {
                window.removeFirst();
            }
            cycles++;
        }
        System.out.printf("Done, cycles=%d, last window size=%d\n", cycles, window.size());
    }
}
