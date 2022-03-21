/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraftforge.fml.common.eventhandler.Cancelable
 */
package wtf.cattyn.woo.api.event;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import wtf.cattyn.woo.api.event.EventStage;
import wtf.cattyn.woo.api.event.Stage;

@Cancelable
public class PushEvent
extends EventStage {
    public Entity entity;
    public double x;
    public double y;
    public double z;
    public boolean airbone;

    public PushEvent(Entity entity, double x, double y, double z, boolean airbone) {
        super(Stage.PRE);
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;
        this.airbone = airbone;
    }

    public PushEvent(Stage stage) {
        super(stage);
    }

    public PushEvent(Stage stage, Entity entity) {
        super(stage);
        this.entity = entity;
    }
}

