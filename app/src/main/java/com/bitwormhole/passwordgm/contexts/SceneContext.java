package com.bitwormhole.passwordgm.contexts;

import com.bitwormhole.passwordgm.data.blocks.SceneBlock;

public class SceneContext extends ContextBase {

    private String label;
    private SceneBlock block;

    public SceneContext(AccountContext _parent) {
        super(_parent, ContextScope.SCENE);
    }


    public SceneBlock getBlock() {
        return block;
    }

    public void setBlock(SceneBlock block) {
        this.block = block;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }
}
