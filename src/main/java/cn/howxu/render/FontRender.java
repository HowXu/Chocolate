package cn.howxu.render;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.language.LangUtils;
import tritium.api.manager.FontManager;

public class FontRender {
    //private static LangUtils textRender = new LangUtils();
    private static FontManager fontManager = new FontManager();

    //写了一个简单的字体渲染器用来给加载界面渲染
    public static void drawOutlineCenteredString(String text, double x, double y, int color, int onlineColor) {
        fontManager.load12.drawOutlineCenteredString(text, x, y, color, onlineColor);
    }
}
