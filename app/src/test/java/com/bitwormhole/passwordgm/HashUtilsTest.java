package com.bitwormhole.passwordgm;

import com.bitwormhole.passwordgm.utils.HashUtils;

import org.junit.Assert;
import org.junit.Test;

public class HashUtilsTest {


    @Test
    public void useSum() {

        String raw = "hello\n";
        String sumWant = "5891b5b522d5df086d0ff0b110fbd9d21bb4fc7163af34d08286a2e846f6be03";
        String sumHave = HashUtils.hexSum(raw, HashUtils.SHA256);

        System.out.println("raw string = " + raw);
        Assert.assertEquals(sumWant, sumHave);
    }

}
