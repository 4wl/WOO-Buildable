//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.network.play.client.CPacketUseEntity$Action
 */
package wtf.cattyn.woo.mixin.mixins;

import net.minecraft.network.play.client.CPacketUseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import wtf.cattyn.woo.api.accessor.ICPacketUseEntity;

@Mixin(value={CPacketUseEntity.class})
public abstract class MixinCPacketUseEntity
implements ICPacketUseEntity {
    @Shadow
    protected CPacketUseEntity.Action action;
    @Shadow
    protected int entityId;

    @Override
    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public void setAction(CPacketUseEntity.Action action) {
        this.action = action;
    }
}

