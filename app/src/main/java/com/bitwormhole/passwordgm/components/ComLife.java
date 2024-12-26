package com.bitwormhole.passwordgm.components;

import com.bitwormhole.passwordgm.boot.BootOrder;

public class ComLife {


    private int order; // 先后顺序:从小到大
    private OnCreate onCreate;
    private OnStart onStart;
    private Loop loop;
    private OnStop onStop;
    private OnDestroy onDestroy;

    public ComLife() {
    }

    public ComLife(ComLife src) {
        if (src != null) {
            this.loop = src.loop;
            this.onCreate = src.onCreate;
            this.onDestroy = src.onDestroy;
            this.onStart = src.onStart;
            this.onStop = src.onStop;
            this.order = src.order;
        }
    }


    public interface Handler {
        void handle(ComLife life) throws Exception;
    }

    public interface Callback {
        void invoke() throws Exception;
    }

    public interface OnCreate extends Callback {
    }

    public interface OnStart extends Callback {
    }

    public interface OnStop extends Callback {
    }

    public interface OnDestroy extends Callback {
    }

    public interface Loop extends Callback {
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setOrder(BootOrder order) {
        this.order = order.ordinal();
    }

    public OnCreate getOnCreate() {
        return onCreate;
    }

    public void setOnCreate(OnCreate onCreate) {
        this.onCreate = onCreate;
    }

    public OnStart getOnStart() {
        return onStart;
    }

    public void setOnStart(OnStart onStart) {
        this.onStart = onStart;
    }

    public Loop getLoop() {
        return loop;
    }

    public void setLoop(Loop loop) {
        this.loop = loop;
    }

    public OnStop getOnStop() {
        return onStop;
    }

    public void setOnStop(OnStop onStop) {
        this.onStop = onStop;
    }

    public OnDestroy getOnDestroy() {
        return onDestroy;
    }

    public void setOnDestroy(OnDestroy onDestroy) {
        this.onDestroy = onDestroy;
    }
}
