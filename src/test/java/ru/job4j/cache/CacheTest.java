package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CacheTest {

    @Test
    public void whenAddTwo() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        assertThat(cache.add(base), is(true));
        assertThat(cache.add(base), is(false));
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        Base newBase = new Base(1, 0);
        cache.add(base);
        cache.update(newBase);
        assertThat(cache.get(1).getVersion(), is(1));
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        cache.delete(base);
        assertThat(cache.add(base), is(true));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateThenException() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        Base newBase = new Base(1, 1);
        cache.add(base);
        cache.update(newBase);
    }
}