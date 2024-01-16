package ru.job4j.concurrent.nonblocking;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CASCountTest {

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        CASCount casc = new CASCount();
        Thread first = new Thread(casc::increment);
        Thread second = new Thread(casc::increment);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(casc.get(), is(2));
    }
}