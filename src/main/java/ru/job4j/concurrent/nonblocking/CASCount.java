package ru.job4j.concurrent.nonblocking;

import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer ref;
        Integer temp;
        do {
            ref = count.get();
            if (ref == null) {
                throw new UnsupportedOperationException("Count is not impl.");
            }
            temp = ref + 1;
        } while (!count.compareAndSet(ref, temp));
    }

    public int get() {
        var rsl = count.get();
        if (rsl == null) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        return rsl;
    }
}
