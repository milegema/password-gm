package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.errors.PGMErrorCode;
import com.bitwormhole.passwordgm.errors.PGMException;

import java.nio.file.Files;
import java.nio.file.Path;

public class RepositoryLocator {

    private int depthLimit;
    private Path wd;
    private String repoDirName;

    public RepositoryLocator() {
        this.depthLimit = 3;
        this.repoDirName = RepositoryLayout.REGULAR_REPO_FOLDER_NAME;
    }

    public String getRepoDirName() {
        return repoDirName;
    }

    public void setRepoDirName(String repoDirName) {
        this.repoDirName = repoDirName;
    }

    public Path getWd() {
        return wd;
    }

    public void setWd(Path wd) {
        this.wd = wd;
    }

    public int getDepthLimit() {
        return depthLimit;
    }

    public void setDepthLimit(int depthLimit) {
        this.depthLimit = depthLimit;
    }

    public RepositoryLayout locate() {
        final String name = this.repoDirName;
        Path current = this.wd;
        int ttl = this.depthLimit;
        while ((current != null) && (ttl > 0)) {
            Path dot = current.resolve(name);
            if (Files.isDirectory(dot)) {
                RepositoryLayout.Builder rlb = new RepositoryLayout.Builder();
                rlb.setDotPasswordGM(dot);
                return rlb.create();
            }
            current = current.getParent();
            ttl--;
        }
        String msg = "cannot find repository at " + this.wd;
        throw new PGMException(PGMErrorCode.Unknown, msg);
    }


    public static RepositoryLayout locate(Path at) {
        RepositoryLocator rl = new RepositoryLocator();
        rl.setWd(at);
        return rl.locate();
    }
}
