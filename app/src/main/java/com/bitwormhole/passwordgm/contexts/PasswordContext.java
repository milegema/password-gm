package com.bitwormhole.passwordgm.contexts;

import com.bitwormhole.passwordgm.data.blocks.PasscodeBlock;

public class PasswordContext extends ContextBase {

    private int revision;
    private long createdAt;

    private PasscodeBlock block;


    public PasswordContext(SceneContext _parent) {
        super(_parent, ContextScope.CODE);
    }


}
