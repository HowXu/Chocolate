package cn.howxu.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;

import java.io.IOException;

public class GuiMultiplayersInGame extends GuiMultiplayer {
    //一个绘制服务器选择的Gui 这个界面完全来自GuiMultiplayer
    public GuiMultiplayersInGame() {
        super(null);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        /*
        * 设置按钮点击事件，id为1和4的按键为直接加入服务器和连接到服务器
        * 在本界面会触发断开链接 断开连接的处理完全来自GuiMultiplayer
        */
        if(button.id == 1 || button.id == 4){
            disconnect();
        }
        super.actionPerformed(button);
    }

    @Override
    public void connectToSelected() {
        disconnect();
        super.connectToSelected();
    }

    private void disconnect() {
        //GuiMultiplayer的同款处理方法
        if (this.mc.theWorld != null){
            this.mc.theWorld.sendQuittingDisconnectingPacket();
            this.mc.loadWorld(null);
            this.mc.displayGuiScreen(null);
            this.parentScreen = null;
        }
    }
}
