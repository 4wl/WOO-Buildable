//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraftforge.client.event.ClientChatEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  org.lwjgl.input.Keyboard
 */
package wtf.cattyn.woo.api.event;

import java.util.Iterator;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import wtf.cattyn.woo.Woo;
import wtf.cattyn.woo.api.command.Command;
import wtf.cattyn.woo.api.command.CommandManager;
import wtf.cattyn.woo.api.event.Render2DEvent;
import wtf.cattyn.woo.api.event.Render3DEvent;
import wtf.cattyn.woo.api.module.Module;
import wtf.cattyn.woo.api.util.Wrapper;

public class WooEvent {
    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent e) {
        if (!Keyboard.getEventKeyState()) {
            return;
        }
        Iterator<Module> iterator = Woo.moduleManager.getModules().iterator();
        while (iterator.hasNext()) {
            Module module = iterator.next();
            if (module.getBind() != Keyboard.getEventKey()) continue;
            module.toggle();
        }
    }

    @SubscribeEvent
    public void onCommand(ClientChatEvent e) {
        String msg = e.getMessage();
        if (!msg.startsWith(Command.prefix)) return;
        CommandManager.runCommand(msg);
        e.setCanceled(true);
    }

    @SubscribeEvent(priority=EventPriority.LOW)
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Text event) {
        if (!event.getType().equals((Object)RenderGameOverlayEvent.ElementType.TEXT)) return;
        ScaledResolution resolution = new ScaledResolution(Wrapper.mc);
        Render2DEvent render2DEvent = new Render2DEvent(event.getPartialTicks(), resolution);
        Woo.moduleManager.onRender2D(render2DEvent);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (event.isCanceled()) {
            return;
        }
        Wrapper.mc.profiler.startSection("Eras");
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.shadeModel((int)7425);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth((float)1.0f);
        Render3DEvent render3dEvent = new Render3DEvent(event.getPartialTicks());
        Woo.moduleManager.onRender3D(render3dEvent);
        GlStateManager.glLineWidth((float)1.0f);
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        GlStateManager.enableCull();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        Wrapper.mc.profiler.endSection();
    }
}

