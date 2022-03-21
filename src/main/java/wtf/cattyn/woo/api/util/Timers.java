/*
 * Decompiled with CFR 0.152.
 */
package wtf.cattyn.woo.api.util;

public class Timers {
    private long time = -1L;
    private long current = -1L;

    public long time() {
        return System.nanoTime() / 1000000L - this.current;
    }

    public boolean passedMs(long ms) {
        if (this.getMs(System.nanoTime() - this.time) < ms) return false;
        return true;
    }

    public boolean passed(double ms) {
        if (!((double)(System.currentTimeMillis() - this.time) >= ms)) return false;
        return true;
    }

    public final boolean hasReached(long delay) {
        if (System.currentTimeMillis() - this.current < delay) return false;
        return true;
    }

    public Timers reset() {
        this.time = System.nanoTime();
        return this;
    }

    public long getMs(long time) {
        return time / 1000000L;
    }
}

