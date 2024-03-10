package me.imflowow.tritium.client.ui.mainmenu;

import me.imflowow.tritium.client.ui.init.GuiInit;
import me.imflowow.tritium.client.ui.utils.button.FlatMainButton;
import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.globals.ClientConfig;
import me.imflowow.tritium.core.globals.ClientConfig.ThemeType;
import me.imflowow.tritium.utils.account.GuiAccountManager;
import me.imflowow.tritium.utils.information.Author;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import tritium.api.utils.RandomUtils;
import tritium.api.utils.StringUtils;
import tritium.api.utils.render.Circle;
import tritium.api.utils.render.Deconverge;
import tritium.api.utils.render.Image;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.Rect.RenderType;
import tritium.api.utils.render.clickable.entity.ClickableRect;
import tritium.api.utils.render.clickable.entity.ClickableString;
import tritium.api.utils.render.special.LoadingRender;

public class MainMenu extends GuiScreen {

    static LoadingRender loading = new LoadingRender(true);
    ArrayList<FlatMainButton> buttons = new ArrayList();
    ClickableString themeImage;
    ClickableString shutdownImage;
    Deconverge textDeconverge;

    //这里是设置标题
    String TitleText = "Chocolate Minecraft";
    int TitleText_Lenth = Tritium.instance.getFontManager().ariaBoldl48.getStringWidth(TitleText);

    //int title_textDeconverge_height = this.height / 5;
    //int title_textDeconverge_width = this.width / 2 - TitleText_Lenth / 2;



    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        //textDeconverge = new Deconverge(title_textDeconverge_width, title_textDeconverge_height, TitleText_Lenth, 24);
        //这行不能加在drawScreen

        String text;
        int lenth;
        new Rect(0, 0, this.width, this.height, this.getColor(0), RenderType.Position).draw();
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (textDeconverge == null || shutdownImage == null) {// || themeImage == null
            return;
        }


        //这里负责绘制标题和设置位置
        //标题设置位置和抖动特效有关
        //见声明title_textDeconverge_height和title_textDeconverge_width
        Tritium.instance.getFontManager().ariaBoldl48.drawString(TitleText, this.width / 2 - TitleText_Lenth / 2, this.height / 5,
                this.getColor(2));

        //这里负责绘制抖动特效
        if (!mc.gameSettings.ofFastRender) {
            textDeconverge.draw();
            //x轴抖动幅度
            textDeconverge.setConvergeX(RandomUtils.nextFloat(-3, 3), RandomUtils.nextFloat(-3, 3),
                    RandomUtils.nextFloat(-3, 3));
            //y轴抖动幅度
            textDeconverge.setConvergeY(RandomUtils.nextFloat(-3, 3), RandomUtils.nextFloat(-3, 3),
                    RandomUtils.nextFloat(-3, 3));
        }

//		float speed = 6f;
//		float hue = System.currentTimeMillis() % (int) ((1 - speed / 15.0) * 2000);
//		hue /= (int) ((1 - speed / 15.0) * 2000);
//		Color col = Color.getHSBColor(hue, hue, hue);
//		GlStateManager.color(col.getRed() / 255f, col.getGreen() / 255f, col.getBlue() / 255f);
//		new Image(new ResourceLocation("tritium/icons/Tritium_Rainbow.png"), this.width / 2 - 40, this.height / 4, 80,
//				80, Image.RenderType.None).draw();

        this.themeImage.setText(this.getResource(0));
        this.themeImage.setColor(this.getColor(2));
        this.themeImage.draw(false);

        if (mc.isFullScreen()) {
            this.shutdownImage.setColor(this.getColor(2));
            this.shutdownImage.draw(false);
        }

        for (FlatMainButton button : buttons) {
            button.getRect().setColor(this.getColor(1));
            button.draw();
        }

        StringBuilder authors = new StringBuilder();
        for (int index = 0; index < 3; index++) {
            authors.append(Tritium.authors[index].toString());
            if (index == Tritium.authors.length - 1)
                break;
            authors.append(" & ");
        }


        text = Tritium.instance.getLanguagemanager().getTextRender().getText("Contribution by ") + authors.toString();
        //这里插入了作者信息
        //Tritium.instance.getLanguagemanager().getTextRender().getFontRender(SizeType.Size14).drawCenteredString("Minecraft 1.8.9",35, this.height - 7, this.getColor(2));


//		Tritium.instance.getFontManager().chinese18.drawStringWithShadow("你好 Hello Здравствыйте こんにちは 안녕하세요.", 2, 2, -1);

//		new Circle(100,100,100,-1).draw();



        //loading.draw();
        //关闭Welcome通知
    }

    @Override
    public void initGui() {

        super.initGui();

		//这里是添加主题按钮
        this.themeImage = new ClickableString(Tritium.instance.getFontManager().logo42, this.getResource(0), 12, 12 + 3,
                this.getColor(2), () -> {
            ClientConfig config = (ClientConfig) Tritium.instance.getModuleManager()
                    .getModule(ClientConfig.class);
            switch (config.theme.getValue()) {
                case Dark:
                    config.theme.setValue(ThemeType.Light);
                    break;
                case Light:
                    config.theme.setValue(ThemeType.Dark);
                    break;
            }
            this.mc.getSoundHandler()
                    .playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
        }, () -> {
        }, () -> {
        }, () -> {
        }, () -> {
        });

        this.shutdownImage = new ClickableString(Tritium.instance.getFontManager().arial32, "×", this.width - 24, 12,
                this.getColor(2), () -> this.mc.shutdown(), () -> {
        }, () -> {
        }, () -> {
        }, () -> {
        });

        buttons.clear();
        buttons.clear();

		//这里是添加按钮的部分，param是位置的x，y 大小的width和height
        //加上40分之1的缩进会好看一点
        //每个按钮大致是半高度 + 按钮大小 * 上方的按钮个数 + 40分之1的高度差距
        double indent_width = this.width - 80 - (double) this.width / 40;
        //indent 缩进
        buttons.add(new FlatMainButton("Single Player", new ClickableRect(indent_width, (double) this.height / 2 - 30, 80, 30,
                this.getColor(1), Rect.RenderType.Expand, () -> {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
            this.mc.getSoundHandler()
                    .playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));

        }, () -> {
        }, () -> {
        }, () -> {
        }, () -> {
        })));

        buttons.add(new FlatMainButton("Multi Player", new ClickableRect(indent_width, (double) this.height / 2 + (double) this.height / 40, 80, 30,
                this.getColor(1), Rect.RenderType.Expand, () -> {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
            this.mc.getSoundHandler()
                    .playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
        }, () -> {
        }, () -> {
        }, () -> {
        }, () -> {
        })));

        /*

        我不需要切换账号设置所以去掉这个

        buttons.add(new FlatMainButton("Alt Repository", new ClickableRect(this.width / 2, this.height / 2, 80, 30,
                this.getColor(1), Rect.RenderType.Expand, () -> {
            this.mc.displayGuiScreen(new GuiAccountManager());
            this.mc.getSoundHandler()
                    .playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
        }, () -> {
        }, () -> {
        }, () -> {
        }, () -> {
        })));
         */

        buttons.add(new FlatMainButton("Options", new ClickableRect(indent_width, (double) this.height / 2 + 30 + (double) this.height / 40 * 2, 80, 30,
                this.getColor(1), Rect.RenderType.Expand, () -> {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
            this.mc.getSoundHandler()
                    .playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
        }, () -> {
        }, () -> {
        }, () -> {
        }, () -> {
        })));

        //额外添加了一个退出按钮

        buttons.add(new FlatMainButton("Quit", new ClickableRect(indent_width, (double) this.height / 2 + 60 + (double) this.height / 40 * 3, 80, 30,
                this.getColor(1), Rect.RenderType.Expand, () -> {
            this.mc.shutdown();//调用这个方法退出游戏
            this.mc.getSoundHandler()
                    .playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
        }, () -> {
        }, () -> {
        }, () -> {
        }, () -> {
        })));

        String text;
        int lenth;

        text = "Tritium";
        lenth = Tritium.instance.getFontManager().ariaBoldl48.getStringWidth(text);

        //这里变量后赋值才能渲染上去
        ////int title_textDeconverge_height = this.height / 5;
        //    //int title_textDeconverge_width = this.width / 2 - TitleText_Lenth / 2;

        textDeconverge = new Deconverge(this.width / 2 - TitleText_Lenth / 2, this.height / 5, TitleText_Lenth, 24);
    }

    public int getColor(int type) {
        ClientConfig config = (ClientConfig) Tritium.instance.getModuleManager().getModule(ClientConfig.class);
        switch (config.theme.getValue()) {
            case Dark:
                switch (type) {
                    case 0:
                        return new Color(32, 32, 43).getRGB();
                    case 1:
                        return new Color(32, 32, 32).getRGB();
                    case 2:
                        return new Color(255, 255, 255).getRGB();
                    case 3:
                        return new Color(34, 52, 70).getRGB();
                    case 4:
                        return new Color(23, 37, 50).getRGB();
                }
            case Light:
                switch (type) {
                    case 0:
                        return new Color(235, 235, 235).getRGB();
                    case 1:
                        return new Color(246, 246, 246).getRGB();
                    case 2:
                        return new Color(0, 0, 0).getRGB();
                    case 3:
                        return new Color(200, 200, 200).getRGB();
                    case 4:
                        return new Color(215, 215, 215).getRGB();
                }
        }
        return 0;
    }

    public String getResource(int type) {
        ClientConfig config = (ClientConfig) Tritium.instance.getModuleManager().getModule(ClientConfig.class);
        switch (config.theme.getValue()) {
            case Dark:
                switch (type) {
                    case 0:
                        return "d";
                }
            case Light:
                switch (type) {
                    case 0:
                        return "c";
                }
        }
        return "";
    }

}
