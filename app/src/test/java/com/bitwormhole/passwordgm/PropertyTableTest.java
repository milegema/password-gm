package com.bitwormhole.passwordgm;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTableLS;

import org.junit.Assert;
import org.junit.Test;

public class PropertyTableTest {

    @Test
    public void usePTable() {

        PropertyTable pt = PropertyTable.Factory.create();

        pt.put("a", "1");
        pt.put("b", "2");
        pt.put("c", "3");
        pt.put("h.x", "4");
        pt.put("h.y", "5");
        pt.put("h.z", "6");


        String text1 = PropertyTableLS.stringify(pt);
        PropertyTable pt2 = PropertyTableLS.parse(text1);

        String[] ids = pt.names();
        for (String id : ids) {
            String v1 = pt.get(id);
            String v2 = pt2.get(id);
            Assert.assertEquals(v1, v2);
        }
        System.out.println("stringify(PropertyTable) = \n" + text1);
    }
}
