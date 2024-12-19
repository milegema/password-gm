package com.bitwormhole.passwordgm.data.repositories.objects;

import com.bitwormhole.passwordgm.data.ids.ObjectID;
import com.bitwormhole.passwordgm.data.repositories.RepositoryContext;

import java.nio.file.Path;

public final class SparseObjectFileLocator {

    private SparseObjectFileLocator() {
    }

    public static Path locate(RepositoryContext ctx, ObjectID id) {
        Path dir = ctx.getLayout().getObjects();
        String[] list = toElements(id);
        StringBuilder builder = new StringBuilder();
        for (String el : list) {
            if (builder.length() > 0) {
                builder.append('/');
            }
            builder.append(el);
        }
        return dir.resolve(builder.toString());
    }

    private static String[] toElements(ObjectID id) {
        String str = id.toString();
        int i = 2;
        String p1 = str.substring(0, i);
        String p2 = str.substring(i);
        return new String[]{p1, p2};
    }
}
