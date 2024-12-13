package com.bitwormhole.passwordgm;

import com.bitwormhole.passwordgm.encoding.blocks.BlockID;
import com.bitwormhole.passwordgm.security.SecurityRandom;

import org.junit.Assert;
import org.junit.Test;

public class BlockIDTest {

    @Test
    public void useBlockID() {

        BlockID id = new BlockID();

        byte[] bin = id.toByteArray();
        SecurityRandom.getRandom().nextBytes(bin);

        id = new BlockID(bin);
        final String str1 = id.toString();
        final byte[] bin1 = id.toByteArray();


        id = new BlockID(str1);
        final String str2 = id.toString();
        final byte[] bin2 = id.toByteArray();

        id = new BlockID(bin1);
        final String str3 = id.toString();
        final byte[] bin3 = id.toByteArray();


        Assert.assertEquals(str1, str2);
        Assert.assertEquals(str1, str3);
        Assert.assertArrayEquals(bin1, bin2);
        Assert.assertArrayEquals(bin1, bin3);

        System.out.println("OK, block id = " + id);
    }
}
