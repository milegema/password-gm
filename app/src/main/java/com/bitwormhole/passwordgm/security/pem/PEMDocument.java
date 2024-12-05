package com.bitwormhole.passwordgm.security.pem;

import java.util.ArrayList;
import java.util.List;

public class PEMDocument {

    private List<PEMBlock> blocks;

    public PEMDocument() {
        this.blocks = new ArrayList<>();
    }


    public List<PEMBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<PEMBlock> list) {
        if (list == null) {
            return;
        }
        this.blocks = list;
    }
}
