//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 */
package wtf.cattyn.woo.client.gui.clickgui.component;

import java.awt.Color;
import java.util.Random;
import net.minecraft.client.gui.ScaledResolution;
import wtf.cattyn.woo.api.util.RenderUtil;

public class Snow {
    private int x;
    private int y;
    private int fallingSpeed;
    private int size;

    public Snow(int x, int y, int fallingSpeed, int size) {
        this.x = x;
        this.y = y;
        this.fallingSpeed = fallingSpeed;
        this.size = size;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int _y) {
        this.y = _y;
    }

    public void Update(ScaledResolution res) {
        RenderUtil.drawRect(this.getX(), this.getY(), this.getX() + this.size, this.getY() + this.size, new Color(201, 197, 197, 100).getRGB());
        this.setY(this.getY() + this.fallingSpeed);
        if (this.getY() <= res.getScaledHeight() + 10) {
            if (this.getY() >= -10) return;
        }
        this.setY(-10);
        Random rand = new Random();
        this.fallingSpeed = rand.nextInt(10) + 1;
        this.size = rand.nextInt(4) + 1;
    }
}

