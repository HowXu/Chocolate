package me.imflowow.tritium.client.ui.init;

import java.awt.Color;

import me.imflowow.tritium.client.ui.mainmenu.MainMenu;
import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.information.Version.VersionType;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import tritium.api.utils.animate.Translate;
import tritium.api.utils.render.Image;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.Rect.RenderType;
import tritium.api.utils.render.clickable.entity.ClickableImage;
import tritium.api.utils.render.clickable.entity.ClickableRect;
import tritium.api.utils.render.special.LoadingRender;

public class GuiInit extends GuiScreen {
    static LoadingRender loading = new LoadingRender(false);

    ClickableImage america;
    ClickableImage china;
    ClickableImage japan;

    Translate america_ = new Translate(0, 0);
    Translate china_ = new Translate(0, 0);
    Translate japan_ = new Translate(0, 0);

    @Override
    public void initGui() {
        //初始化Gui为三个图片设置属性
        this.america = new ClickableImage(new ResourceLocation("tritium/icons/countries/america.png"), this.width / 2 - 170,
                this.height / 2 - 50, 96, 96, Image.RenderType.Clear, () -> {
            this.mc.getSoundHandler()
                    .playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            this.load(0);
        }, () -> {
        }, () -> {
            america_.interpolate(0, 10, .25F);
            this.america.setY(this.height / 2 - 50 - america_.getY());
        }, () -> {
        }, () -> {
            america_.interpolate(0, 0, .25F);
            this.america.setY(this.height / 2 - 50 - america_.getY());
        });

        this.china = new ClickableImage(new ResourceLocation("tritium/icons/countries/china.png"), this.width / 2 - 40,
                this.height / 2 - 50, 96, 96, Image.RenderType.Clear, () -> {
            this.mc.getSoundHandler()
                    .playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            this.load(1);
        }, () -> {
        }, () -> {
            china_.interpolate(0, 10, .25F);
            this.china.setY(this.height / 2 - 50 - china_.getY());
        }, () -> {
        }, () -> {
            china_.interpolate(0, 0, .25F);
            this.china.setY(this.height / 2 - 50 - china_.getY());
        });

        this.japan = new ClickableImage(new ResourceLocation("tritium/icons/countries/japan.png"),
                this.width - this.width / 2 + 90, this.height / 2 - 50, 96, 96, Image.RenderType.Clear, () -> {
            this.mc.getSoundHandler()
                    .playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            this.load(2);
        }, () -> {
        }, () -> {
            japan_.interpolate(0, 10, .25F);
            this.japan.setY(this.height / 2 - 50 - japan_.getY());
        }, () -> {
        }, () -> {
            japan_.interpolate(0, 0, .25F);
            this.japan.setY(this.height / 2 - 50 - japan_.getY());
        });
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        //这一步绘制图形
        //new Rect(0, 0, this.width, this.height, new Color(32, 32, 43).getRGB(), RenderType.Position).draw();
        //开局要求你选择语言 但是我是中国人所以直接跳过这步
        //Tritium.instance.getFontManager().arial16.drawCenteredString("Choose Client Language", this.width / 2, 3, -1);

//		a.draw();

        //这里可以都不要直接展示mc.displayGuiScreen(new GuiMainMenu());
        //this.america.draw();
        //this.china.draw();
        //this.japan.draw();

        //loading.draw();
        //去掉welcome提示
        mc.displayGuiScreen(new GuiMainMenu());
    }

    public void load(int type) {
        String lang = "english";
        switch (type) {
            case 0:
                lang = "english";
                break;
            case 1:
                lang = "chinese";
                break;
            case 2:
                lang = "Japanese";
                break;
        }
        //设置语言之后有很多的破事情，我直接提出来放在别的地方
        Tritium.instance.getLanguagemanager().setLang(lang);
        Tritium.instance.getLanguagemanager().getTextRender().setLang(lang);
        if (!Tritium.instance.getLanguagemanager().getTextRender().isEnglish()) {
            if (Tritium.instance.getLanguagemanager().getTextRender().init()) {
                Tritium.instance.getFontManager().loadUnicodeRender();
            }
        }
        Tritium.instance.getModuleManager().initialize();
        Tritium.instance.getConfigmanager().load();
		if(Tritium.version.getType() == VersionType.Development)
		{
	        Tritium.instance.getAuthmanager().getAuth().loadAuth(Tritium.instance.getAuthmanager().getAuthtype());
		}
        Tritium.instance.getClientListener().init();
        Tritium.initialization = false;
        mc.displayGuiScreen(new GuiMainMenu());
    }

}
