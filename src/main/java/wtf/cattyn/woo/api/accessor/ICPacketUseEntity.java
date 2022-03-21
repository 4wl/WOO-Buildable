/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.client.CPacketUseEntity$Action
 */
package wtf.cattyn.woo.api.accessor;

import net.minecraft.network.play.client.CPacketUseEntity;

public interface ICPacketUseEntity {
    public void setEntityId(int var1);

    public void setAction(CPacketUseEntity.Action var1);
}

