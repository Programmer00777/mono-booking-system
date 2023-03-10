package com.mono.bookingsystem.paymentsystem.entity;

import java.util.List;
import java.util.Random;

public enum Status {
    NEW,
    FAILED,
    DONE;

    private static final List<Status> STATUS_LIST = List.of(values());
    private static final int SIZE = STATUS_LIST.size();
    private static final Random RANDOM = new Random();

    public static Status randomStatus() {
        return STATUS_LIST.get(RANDOM.nextInt(SIZE));
    }
}
