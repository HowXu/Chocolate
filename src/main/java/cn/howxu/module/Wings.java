package cn.howxu.module;

import net.minecraft.entity.player.EntityPlayer;
import tritium.api.module.Module;
import tritium.api.module.value.impl.BooleanValue;
import tritium.api.module.value.impl.ColorValue;
import tritium.api.module.value.impl.NumberValue;
import tritium.api.module.value.utils.HSBColor;
import tritium.api.utils.event.api.EventManager;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.api.types.EventType;
import tritium.api.utils.event.events.Render3DEvent;

import java.awt.*;

public class Wings extends Module {

    //按照本端原有的组件设置了可调整属性
    public BooleanValue colored = new BooleanValue("Colored", false); //是否有颜色
    public ColorValue color = new ColorValue("Color", new HSBColor(100, 100, 255,10)); //颜色选择器
    //Color color11 = new Color(100,100,255,10);
    //public NumberValue hue = new NumberValue("Hue",100.0,0.0,100.0,1.0); //颜色选择器
    //public BooleanValue chroma = new BooleanValue("Chroma", false);  //是否彩色 但是Color自带是否为彩色
    public NumberValue<Number> scale = new NumberValue<>("Scale", 100.0, 0.0, 100.0, 1.0); //模型大小

    private RenderWings wings = new RenderWings(this);

    public Wings() {
        super("DragonWings", "Add dragon wings to your character.");
        super.addValues(colored,color,scale); //这里调用super的addValues就可以实现模块有颜色选择等等
    }

    @EventTarget//模块调用必须在Module类
    public void onRenderPlayer(Render3DEvent event) {

        //ELog.log_info("Wings Render3DEvent","call");
        //这个Render3DEvent会被EventManager在net.minecraft.client.renderer.EntityRenderer的tick中被调用
        EntityPlayer player;
        if (event.getEventType() != EventType.POST) {
            //老实说感觉加不加都无所谓
            return;
        }
        player = mc.thePlayer;
        if (!player.isInvisible() && mc.gameSettings.thirdPersonView != 0) // 判断需求是否渲染
        {
            //ELog.log_info("Render3DEvent","begin on " + player.getName() + "  " + event.getPartialTicks());
            wings.renderWings(player, event.getPartialTicks());

            //ELog.log_info("Render3DEvent","finish on " + player.getName() + "  " + event.getPartialTicks());

        }
    }
    //仿照的写的模块描述

    @Override
    public void onEnable() {
        //ELog.log_info("Override","onEnable");
        /*
        if (wings == null) {
            wings = new RenderWings(this);
        }
         */
        //向事件管理器注册 之后会在Minecraft的模型渲染中调用renderWings方法
        EventManager.register(wings);
    }

    @Override

    public void onDisable() {
        //ELog.log_info("Override","onDisable");
        EventManager.unregister(wings);
    }

    public float[] getColors() {
        if (!colored.getValue()) {//选择为无色则返回1.1.1
            return new float[]{1F, 1F, 1F};
        }


        //神经啊用的是自己写的HSBColor我说怎么Hue出不来
        Color setting_color = color.getValue().getColor();

        if (color.rainbow.getValue()){
            return new float[] {
                (System.currentTimeMillis() % (1000L * color.rainbowspeed.getValue().intValue())) / 255.0f,
                setting_color.getGreen() / 255.0f,
                setting_color.getBlue() / 255.0f
            };
        }else {
            return new float[] {
                    setting_color.getRed() / 255.0f,
                    setting_color.getGreen() / 255.0f,
                    setting_color.getBlue() / 255.0f
            };
        }

        //Color apply_color = new Color(255,255,255,0);


        //Color color1 = Color.getHSBColor((color.rainbow.getValue() ? (System.currentTimeMillis() % 1000) / 1000F : 50.0f / 100F), 0.8F, 1F );
        //Color color1 = chroma.getValue() ? Color.getHSBColor((System.currentTimeMillis() % 1000) / 1000F, 0.8F, 1F) : intToColor(color.getValue().getColor().getRGB());

        //return new float[]{color1.getRed() / 255F, color1.getGreen() / 255F, color1.getBlue() / 255F};

        //return new float[]{(color.rainbow.getValue() ? (System.currentTimeMillis() % (1000L * color.rainbowspeed.getValue().intValue())) : setting_color.getRed()) /255.0f,}
    }

    public static Color intToColor(int color) {
        Color c1 = new Color(color);
        return new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), color >> 24 & 255);
    }

}

