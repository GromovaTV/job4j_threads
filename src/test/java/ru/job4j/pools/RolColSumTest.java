package ru.job4j.pools;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RolColSumTest {
    int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

    @Test
    public void sum() {
        RolColSum.Sums[] sum = RolColSum.sum(array);
        assertThat(sum[0].getRowSum(), is(6));
        assertThat(sum[1].getRowSum(), is(15));
        assertThat(sum[2].getRowSum(), is(24));
        assertThat(sum[0].getColSum(), is(12));
        assertThat(sum[1].getColSum(), is(15));
        assertThat(sum[2].getColSum(), is(18));
    }

    @Test
    public void asyncSum() throws ExecutionException, InterruptedException{
        RolColSum.Sums[] sum = RolColSum.asyncSum(array);
        assertThat(sum[0].getRowSum(), is(6));
        assertThat(sum[1].getRowSum(), is(15));
        assertThat(sum[2].getRowSum(), is(24));
        assertThat(sum[0].getColSum(), is(12));
        assertThat(sum[1].getColSum(), is(15));
        assertThat(sum[2].getColSum(), is(18));
    }
}