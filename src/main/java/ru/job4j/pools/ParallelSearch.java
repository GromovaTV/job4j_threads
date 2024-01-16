package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T value;

    public ParallelSearch(T[] array, int from, int to, T value) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    @Override
    protected Integer compute() {
        int res = -1;
        if (to - from <= 10) {
            for (int i = from; i <= to; i++) {
                if (array[i].equals(value)) {
                    res = i;
                }
            }
        } else {
            int mid = (to + from) / 2;
            ParallelSearch leftSearch = new ParallelSearch(array, 0, mid, value);
            ParallelSearch rightSearch = new ParallelSearch(array, mid + 1, array.length - 1, value);
            leftSearch.fork();
            rightSearch.fork();
            int leftResult = (int) leftSearch.join();
            int rightResult = (int) rightSearch.join();
            res = leftResult > rightResult ? leftResult : rightResult;

        }
        return res;
    }

    public static <T> int search(T[] array, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        return forkJoinPool.invoke(new ParallelSearch<>(array, 0, array.length - 1, value));
    }

    public static void main(String[] args) {
        Integer[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        System.out.println(search(array, 6));
    }
}
