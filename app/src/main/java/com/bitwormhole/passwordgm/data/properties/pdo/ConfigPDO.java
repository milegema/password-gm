package com.bitwormhole.passwordgm.data.properties.pdo;

import com.bitwormhole.passwordgm.data.repositories.refs.RefName;

/**
 * Config Properties Document Object
 */
public class ConfigPDO {

    private RefName refsBlocksRoot;
    private RefName refsBlocksApp;
    private RefName refsBlocksUser;

    public ConfigPDO() {
    }

    public RefName getRefsBlocksRoot() {
        return refsBlocksRoot;
    }

    public void setRefsBlocksRoot(RefName refsBlocksRoot) {
        this.refsBlocksRoot = refsBlocksRoot;
    }

    public RefName getRefsBlocksApp() {
        return refsBlocksApp;
    }

    public void setRefsBlocksApp(RefName refsBlocksApp) {
        this.refsBlocksApp = refsBlocksApp;
    }

    public RefName getRefsBlocksUser() {
        return refsBlocksUser;
    }

    public void setRefsBlocksUser(RefName refsBlocksUser) {
        this.refsBlocksUser = refsBlocksUser;
    }
}
