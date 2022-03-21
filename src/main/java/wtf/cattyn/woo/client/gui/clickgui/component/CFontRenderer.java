//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\maywr\Documents\remapping\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  org.lwjgl.opengl.GL11
 */
package wtf.cattyn.woo.client.gui.clickgui.component;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;
import wtf.cattyn.woo.client.gui.clickgui.component.CFont;

public class CFontRenderer
extends CFont {
    protected CFont.CharData[] boldChars = new CFont.CharData[256];
    protected CFont.CharData[] italicChars = new CFont.CharData[256];
    protected CFont.CharData[] boldItalicChars = new CFont.CharData[256];
    private final int[] colorCode = new int[32];
    protected DynamicTexture texBold;
    protected DynamicTexture texItalic;
    protected DynamicTexture texItalicBold;

    public CFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
        super(font, antiAlias, fractionalMetrics);
        this.setupMinecraftColorcodes();
        this.setupBoldItalicIDs();
    }

    public float drawStringWithShadow(String text, double x, double y, int color) {
        float shadowWidth = this.drawString(text, x + 1.0, y + 1.0, color, true);
        return Math.max(shadowWidth, this.drawString(text, x, y, color, false));
    }

    public float drawString(String text, float x, float y, int color) {
        return this.drawString(text, x, y, color, false);
    }

    public float drawCenteredStringWithShadow(String text, float x, float y, int color) {
        return this.drawStringWithShadow(text, x - (float)(this.getStringWidth(text) / 2), y, color);
    }

    public float drawCenteredString(String text, float x, float y, int color) {
        return this.drawString(text, x - (float)(this.getStringWidth(text) / 2), y, color);
    }

    public float drawString(String text, double x, double y, int color, boolean shadow) {
        x -= 1.0;
        y -= 2.0;
        if (text == null) {
            return 0.0f;
        }
        if (color == 0x20FFFFFF) {
            color = 0xFFFFFF;
        }
        if ((color & 0xFC000000) == 0) {
            color |= 0xFF000000;
        }
        if (shadow) {
            color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
        }
        CFont.CharData[] currentData = this.charData;
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        x *= 2.0;
        y *= 2.0;
        GL11.glPushMatrix();
        GlStateManager.scale((double)0.5, (double)0.5, (double)0.5);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc((int)770, (int)771);
        GlStateManager.color((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)alpha);
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture((int)this.tex.getGlTextureId());
        GL11.glBindTexture((int)3553, (int)this.tex.getGlTextureId());
        char[] charArr = text.toCharArray();
        int size = charArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                GL11.glHint((int)3155, (int)4352);
                GL11.glPopMatrix();
                return (float)x / 2.0f;
            }
            char character = charArr[i2];
            if (character == '\u00a7') {
                int colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i2 + 1));
                if (colorIndex < 16) {
                    GlStateManager.bindTexture((int)this.tex.getGlTextureId());
                    currentData = this.charData;
                    if (colorIndex < 0) {
                        colorIndex = 15;
                    }
                    if (shadow) {
                        colorIndex += 16;
                    }
                    int colorcode = this.colorCode[colorIndex];
                    GlStateManager.color((float)((float)(colorcode >> 16 & 0xFF) / 255.0f), (float)((float)(colorcode >> 8 & 0xFF) / 255.0f), (float)((float)(colorcode & 0xFF) / 255.0f), (float)alpha);
                } else if (colorIndex == 17) {
                    GlStateManager.bindTexture((int)this.texBold.getGlTextureId());
                    currentData = this.boldChars;
                } else if (colorIndex == 20) {
                    GlStateManager.bindTexture((int)this.texItalic.getGlTextureId());
                    currentData = this.italicChars;
                } else {
                    GlStateManager.color((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)alpha);
                    GlStateManager.bindTexture((int)this.tex.getGlTextureId());
                    currentData = this.charData;
                }
                ++i2;
            } else if (character < currentData.length) {
                GL11.glBegin((int)4);
                this.drawChar(currentData, character, (float)x, (float)y);
                GL11.glEnd();
                x += (double)(currentData[character].width - 8 + this.charOffset);
            }
            ++i2;
        }
    }

    @Override
    public int getStringWidth(String text) {
        if (text == null) {
            return 0;
        }
        int width = 0;
        CFont.CharData[] currentData = this.charData;
        int size = text.length();
        int i2 = 0;
        while (i2 < size) {
            char character = text.charAt(i2);
            if (character == '\u00a7') {
                ++i2;
            } else if (character < currentData.length) {
                width += currentData[character].width - 8 + this.charOffset;
            }
            ++i2;
        }
        return width / 2;
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        this.setupBoldItalicIDs();
    }

    @Override
    public void setAntiAlias(boolean antiAlias) {
        super.setAntiAlias(antiAlias);
        this.setupBoldItalicIDs();
    }

    @Override
    public void setFractionalMetrics(boolean fractionalMetrics) {
        super.setFractionalMetrics(fractionalMetrics);
        this.setupBoldItalicIDs();
    }

    private void setupBoldItalicIDs() {
        this.texBold = this.setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
        this.texItalic = this.setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
        this.texItalicBold = this.setupTexture(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
    }

    public List<String> wrapWords(String text, double width) {
        ArrayList<String> finalWords = new ArrayList<String>();
        if (!((double)this.getStringWidth(text) > width)) {
            finalWords.add(text);
            return finalWords;
        }
        String[] words = text.split(" ");
        StringBuilder currentWord = new StringBuilder();
        char lastColorCode = '\uffff';
        String[] stringArray = words;
        int n = stringArray.length;
        int n2 = 0;
        while (true) {
            String word;
            if (n2 < n) {
                word = stringArray[n2];
            } else {
                if (currentWord.length() <= 0) return finalWords;
                if ((double)this.getStringWidth(currentWord.toString()) < width) {
                    finalWords.add("\u00a7" + lastColorCode + currentWord + " ");
                    currentWord = new StringBuilder();
                    return finalWords;
                }
                finalWords.addAll(this.formatString(currentWord.toString(), width));
                return finalWords;
            }
            for (int i2 = 0; i2 < word.toCharArray().length; ++i2) {
                char c2 = word.toCharArray()[i2];
                if (c2 != '\u00a7' || i2 >= word.toCharArray().length - 1) continue;
                lastColorCode = word.toCharArray()[i2 + 1];
            }
            StringBuilder stringBuilder = new StringBuilder();
            if ((double)this.getStringWidth(stringBuilder.append((Object)currentWord).append(word).append(" ").toString()) < width) {
                currentWord.append(word).append(" ");
            } else {
                finalWords.add(currentWord.toString());
                currentWord = new StringBuilder("\u00a7" + lastColorCode + word + " ");
            }
            ++n2;
        }
    }

    public List<String> formatString(String string, double width) {
        ArrayList<String> finalWords = new ArrayList<String>();
        StringBuilder currentWord = new StringBuilder();
        char lastColorCode = '\uffff';
        char[] chars = string.toCharArray();
        int i2 = 0;
        while (true) {
            StringBuilder stringBuilder;
            if (i2 >= chars.length) {
                if (currentWord.length() <= 0) return finalWords;
                finalWords.add(currentWord.toString());
                return finalWords;
            }
            char c2 = chars[i2];
            if (c2 == '\u00a7' && i2 < chars.length - 1) {
                lastColorCode = chars[i2 + 1];
            }
            if ((double)this.getStringWidth((stringBuilder = new StringBuilder()).append(currentWord.toString()).append(c2).toString()) < width) {
                currentWord.append(c2);
            } else {
                finalWords.add(currentWord.toString());
                currentWord = new StringBuilder("\u00a7" + lastColorCode + String.valueOf(c2));
            }
            ++i2;
        }
    }

    private void setupMinecraftColorcodes() {
        int index = 0;
        while (index < 32) {
            int noClue = (index >> 3 & 1) * 85;
            int red = (index >> 2 & 1) * 170 + noClue;
            int green = (index >> 1 & 1) * 170 + noClue;
            int blue = (index >> 0 & 1) * 170 + noClue;
            if (index == 6) {
                red += 85;
            }
            if (index >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCode[index] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
            ++index;
        }
    }
}

