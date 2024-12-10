package com.bitwormhole.passwordgm;

import com.bitwormhole.passwordgm.encoding.pem.PEMBlock;
import com.bitwormhole.passwordgm.encoding.pem.PEMDecoder;
import com.bitwormhole.passwordgm.encoding.pem.PEMDocument;
import com.bitwormhole.passwordgm.encoding.pem.PEMEncoder;

import org.junit.Assert;
import org.junit.Test;

public class PEMCodecTest {

    @Test
    public void encodeAndDecode() {

        PEMBlock b1 = new PEMBlock();
        PEMBlock b2 = new PEMBlock();
        PEMBlock b3 = new PEMBlock();
        b1.setType("");
        b2.setType("foo");
        b3.setType("foo bar");
        b2.setData("abc-def-g".getBytes());
        b3.setData("abcdefghijklmnopqrstuvwxyz_0123456789_~!@#$%^&*()_+{}|<>?_ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes());


        PEMDocument doc1 = new PEMDocument();
        doc1.add(b1, b2, b3);


        String text1 = PEMEncoder.encode(doc1);
        PEMDocument doc2 = PEMDecoder.decode(text1);

        final PEMBlock[] blockList1 = doc1.getBlocks();
        final PEMBlock[] blockList2 = doc2.getBlocks();
        for (int i = 0; i < blockList1.length; ++i) {
            b1 = blockList1[i];
            b2 = blockList2[i];
            check2Blocks(b1, b2);
        }
    }

    private void check2Blocks(PEMBlock b1, PEMBlock b2) {
        Assert.assertEquals(b1.getType(), b2.getType());
        Assert.assertArrayEquals(b1.getData(), b2.getData());
    }
}
