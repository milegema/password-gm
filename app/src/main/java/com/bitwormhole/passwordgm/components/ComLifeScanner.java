package com.bitwormhole.passwordgm.components;

import com.bitwormhole.passwordgm.contexts.ContextHolder;
import com.bitwormhole.passwordgm.utils.Logs;

import java.util.List;

public class ComLifeScanner {

    public static void scan(ComLifeContext ctx, ContextHolder holder) {

        ComponentManager src = holder.getApp().getComponents();
        List<ComLife> dst = ctx.lives;
        String[] ids = src.listIds();
        int count = 0;

        for (String id : ids) {
            Object obj = src.findById(id, Object.class);
            if (obj instanceof ComLifecycle) {
                ComLifecycle lifecycle = (ComLifecycle) obj;
                dst.add(lifecycle.life());
                count++;
            }
        }

        Logs.debug("ComLifeScanner: find ComLives, count = " + count);
    }
}
