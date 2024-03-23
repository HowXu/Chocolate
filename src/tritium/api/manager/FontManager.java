package tritium.api.manager;

import java.awt.Font;

import net.minecraft.client.Minecraft;
import tritium.api.utils.font.MCFontRenderer;

public class FontManager {
    private Minecraft mc = Minecraft.getMinecraft();
    public MCFontRenderer arial10 = new MCFontRenderer(this.fontFromTTF("arial.ttf", 10, Font.PLAIN), true, true);
    public MCFontRenderer arial12 = new MCFontRenderer(this.fontFromTTF("arial.ttf", 12, Font.PLAIN), true, true);
    public MCFontRenderer arial14 = new MCFontRenderer(this.fontFromTTF("arial.ttf", 14, Font.PLAIN), true, true);
    public MCFontRenderer arial16 = new MCFontRenderer(this.fontFromTTF("arial.ttf", 16, Font.PLAIN), true, true);
    public MCFontRenderer arial18 = new MCFontRenderer(this.fontFromTTF("arial.ttf", 18, Font.PLAIN), true, true);
    public MCFontRenderer arial32 = new MCFontRenderer(this.fontFromTTF("arial.ttf", 32, Font.PLAIN), true, true);

    public MCFontRenderer load18 = this.arial18;
    public MCFontRenderer load16 = this.arial16;
    public MCFontRenderer load14 = this.arial14;


    public MCFontRenderer arialBold10 = new MCFontRenderer(this.fontFromTTF("arialBold.ttf", 10, Font.PLAIN), true,
            true);
    public MCFontRenderer arialBold12 = new MCFontRenderer(this.fontFromTTF("arialBold.ttf", 12, Font.PLAIN), true,
            true);
    public MCFontRenderer arialBold14 = new MCFontRenderer(this.fontFromTTF("arialBold.ttf", 14, Font.PLAIN), true,
            true);
    public MCFontRenderer arialBold16 = new MCFontRenderer(this.fontFromTTF("arialBold.ttf", 16, Font.PLAIN), true,
            true);
    public MCFontRenderer arialBold18 = new MCFontRenderer(this.fontFromTTF("arialBold.ttf", 18, Font.PLAIN), true,
            true);
    public MCFontRenderer ariaBoldl48 = new MCFontRenderer(this.fontFromTTF("arialBold.ttf", 48, Font.PLAIN), true,
            true);

    public MCFontRenderer logo12 = new MCFontRenderer(this.fontFromTTF("logo.ttf", 12, Font.PLAIN), true, true);
    public MCFontRenderer logo14 = new MCFontRenderer(this.fontFromTTF("logo.ttf", 14, Font.PLAIN), true, true);
    public MCFontRenderer logo16 = new MCFontRenderer(this.fontFromTTF("logo.ttf", 16, Font.PLAIN), true, true);
    public MCFontRenderer logo18 = new MCFontRenderer(this.fontFromTTF("logo.ttf", 18, Font.PLAIN), true, true);
    public MCFontRenderer logo20 = new MCFontRenderer(this.fontFromTTF("logo.ttf", 20, Font.PLAIN), true, true);
    public MCFontRenderer logo22 = new MCFontRenderer(this.fontFromTTF("logo.ttf", 22, Font.PLAIN), true, true);
    public MCFontRenderer logo24 = new MCFontRenderer(this.fontFromTTF("logo.ttf", 24, Font.PLAIN), true, true);
    public MCFontRenderer logo32 = new MCFontRenderer(this.fontFromTTF("logo.ttf", 32, Font.PLAIN), true, true);
    public MCFontRenderer logo36 = new MCFontRenderer(this.fontFromTTF("logo.ttf", 36, Font.PLAIN), true, true);
    public MCFontRenderer logo42 = new MCFontRenderer(this.fontFromTTF("logo.ttf", 42, Font.PLAIN), true, true);

    //Add Howxu
    public MCFontRenderer load12 = this.load14;

    public void loadUnicodeRender() {
        this.load14 = null;
        this.load16 = null;
        this.load18 = null;
        System.gc();
        this.load14 = new MCFontRenderer(this.fontFromTTF("OPPOSans-Regular.ttf", 14, Font.PLAIN), true, true, true, 0, -1);
        this.load16 = new MCFontRenderer(this.fontFromTTF("OPPOSans-Regular.ttf", 16, Font.PLAIN), true, true, true, 0, -1);
        this.load18 = new MCFontRenderer(this.fontFromTTF("OPPOSans-Regular.ttf", 18, Font.PLAIN), true, true, true, 0, -1);
    }

    public void reloadEnglishRender() {
        this.load14 = null;
        this.load16 = null;
        this.load18 = null;
        System.gc();
        this.load18 = this.arial18;
        this.load16 = this.arial16;
        this.load14 = this.arial14;
    }

    public Font fontFromTTF(String fontLocation, float fontSize, int fontType) {
        Font output = null;
        try {
            output = Font.createFont(fontType,
                    FontManager.class.getResourceAsStream("/assets/minecraft/tritium/fonts/" + fontLocation));
            output = output.deriveFont(fontSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
