package com.bitwormhole.passwordgm;

import com.bitwormhole.passwordgm.utils.Hex;

import org.junit.Test;

import java.util.Arrays;

public class HexTest {

    @Test
    public void useHex() {

        final byte[] data0 = "hello, this is test for Hex utils".getBytes();
        byte[] data = data0;

        for (int i = 0; i < 3; i++) {
            String str = Hex.stringify(data);
            data = Hex.parse(str);
            System.out.println("str[" + i + "] = " + str);
        }

        if (!Arrays.equals(data, data0)) {
            throw new RuntimeException("data != data0");
        }
    }

    @Test
    public void badHexString() {
        String str = "0123456789abc def ghi+";
        try {
            System.out.println("badHexString():");
            System.out.println("  str1 : " + str);
            Hex.parse(str);
        } catch (Exception e) {
            System.err.println("error: " + e);
        }
    }

    @Test
    public void goodHexString() {
        String str = "01 23  \t 45678  \n 9abc def";
        byte[] data = Hex.parse(str);
        String str2 = Hex.stringify(data);
        System.out.println("goodHexString():");
        System.out.println("  str1 : " + str);
        System.out.println("  str2 : " + str2);
    }
}
