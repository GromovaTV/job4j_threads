package ru.job4j.cache;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddTwo() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        assertThat(cache.add(base), is(true));
        assertThat(cache.add(base), is(false));
    }

    @Test
    public void WhenUpdate() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        Base newBase = new Base(1, 0);
        cache.add(base);
        cache.update(newBase);
        assertThat(cache.get(1).getVersion(), is(1));
    }

    @Test
    public void WhenDelete() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        cache.delete(base);
        assertThat(cache.add(base), is(true));
    }

    @Test(expected = OptimisticException.class)
    public void WhenUpdateThenException() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        Base newBase = new Base(1, 1);
        cache.add(base);
        cache.update(newBase);
    }
}