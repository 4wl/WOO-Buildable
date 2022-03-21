/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package wtf.cattyn.woo.api.event;

import net.minecraftforge.fml.common.eventhandler.Event;
import wtf.cattyn.woo.api.event.Stage;

public class EventStage
extends Event {
    private final Stage stage;

    public EventStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }
}

