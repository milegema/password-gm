package com.bitwormhole.passwordgm.contexts;

import com.bitwormhole.passwordgm.data.blocks.AccountBlock;

public class AccountContext extends ContextBase {

    private String username;
    private String description;
    private String nickname;
    private String avatar;
    private AccountBlock block;

    public AccountContext(DomainContext _parent) {
        super(_parent, ContextScope.ACCOUNT);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public AccountBlock getBlock() {
        return block;
    }

    public void setBlock(AccountBlock block) {
        this.block = block;
    }
}
