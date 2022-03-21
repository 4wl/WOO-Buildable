/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package wtf.cattyn.woo.api.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class MoveEvent
extends Event {
    public double motionX;
    public double motionY;
    public double motionZ;

    public MoveEvent(double motionX, double motionY, double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }
}

