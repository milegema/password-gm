package com.bitwormhole.passwordgm;

import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.encoding.blocks.BlockTypeSet;

import org.junit.Assert;
import org.junit.Test;

public class BlockTypeTest {

    @Test
    public void useBlockType() {
        BlockType bt1 = BlockType.FooBar;
        BlockType bt2 = null;
        String[] list = new String[]{
                "FooBar",
                "FOOBAR",
                "fooBAR",
                "foobar",
        };
        for (String name : list) {
            bt2 = BlockTypeSet.valueOf(name);
            System.out.println("[BlockType name:" + name + " type:" + bt2 + "]");
            Assert.assertEquals(bt1, bt2);
        }
        System.out.println("[OK]");
    }
}
