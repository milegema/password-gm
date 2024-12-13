package com.bitwormhole.passwordgm;

import com.bitwormhole.passwordgm.security.SecurityRandom;
import com.bitwormhole.passwordgm.utils.DeflateUtils;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class DeflateTest {

    @Test
    public void useDeflate() throws IOException {


        byte[] data1 = new byte[1024];
        SecurityRandom.getRandom().nextBytes(data1);
        for (int i = data1.length / 2; i > 0; --i) {
            data1[i] = 1;
        }


        byte[] z = DeflateUtils.compress(data1);
        byte[] data2 = DeflateUtils.expand(z);


        System.out.println("d1.size=" + data1.length);
        System.out.println(" z.size=" + z.length);
        System.out.println("d2.size=" + data2.length);

        Assert.assertArrayEquals(data1, data2);
    }
}
