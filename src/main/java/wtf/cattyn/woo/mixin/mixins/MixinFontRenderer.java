//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 */
package wtf.cattyn.woo.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import wtf.cattyn.woo.client.modules.misc.NameProtect;

@Mixin(value={FontRenderer.class})
public abstract class MixinFontRenderer {
    private final Minecraft mc = Minecraft.getMinecraft();

    @ModifyVariable(method={"renderString"}, at=@At(value="HEAD"), require=1, ordinal=0)
    private String renderString(String string) {
        String string2;
        if (string == null) {
            return null;
        }
        if (this.mc.player == null) return string;
        if (!StringUtils.contains((CharSequence)string, this.mc.player.getName())) return string;
        if (NameProtect.INSTANCE.isToggled()) {
            string2 = StringUtils.replace(string, this.mc.player.getName(), NameProtect.fakeName);
            return string2;
        }
        string2 = string;
        return string2;
    }

    @ModifyVariable(method={"getStringWidth"}, at=@At(value="HEAD"), require=1, ordinal=0)
    private String getStringWidth(String string) {
        String string2;
        if (string == null) {
            return null;
        }
        if (this.mc.player == null) return string;
        if (!StringUtils.contains((CharSequence)string, this.mc.player.getName())) return string;
        if (NameProtect.INSTANCE.isToggled()) {
            string2 = StringUtils.replace(string, this.mc.player.getName(), NameProtect.fakeName);
            return string2;
        }
        string2 = string;
        return string2;
    }
}

