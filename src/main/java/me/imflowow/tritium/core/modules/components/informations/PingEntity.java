package me.imflowow.tritium.core.modules.components.informations;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.modules.FPSDisplay;
import me.imflowow.tritium.core.modules.PingDisplay;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import tritium.api.module.gui.GuiEntity;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.utils.StringUtils;
import tritium.api.utils.event.events.MouseEvent;
import tritium.api.utils.render.Rect;

public class PingEntity extends GuiEntity {
    private String text;

    public PingEntity(PositionValue position) {
        super(position);
    }

    @Override
    public void init() {
        this.text = " Ping";
    }

    @Override
    public void draw(double x, double y) {
        int ping = 0;

        List<NetworkPlayerInfo> list = GuiPlayerTabOverlay.field_175252_a.sortedCopy(mc.thePlayer.sendQueue.getPlayerInfoMap());

        for (NetworkPlayerInfo info : list)
            if (mc.theWorld.getPlayerEntityByUUID(info.getGameProfile().getId()) == mc.thePlayer)
                ping = info.getResponseTime();

        this.text = "Ping " + ping + "ms";
        int len = StringUtils.getWidth(text, SizeType.Size16) + 3;
        new Rect(x, y, len, 13, this.getModule().backgroundColor.getValue().getColor().getRGB(), Rect.RenderType.Expand).draw();
        StringUtils.drawStringWithShadow(text, x + 1.5, y + 5, this.getModule().textColor.getValue().getColor().getRGB(), SizeType.Size16);
    }

    @Override
    public int getHeight() {
        return 13;
    }

    @Override
    public int getWidth() {
        return StringUtils.getWidth(text, SizeType.Size16) + 3;
    }

    public PingDisplay getModule() {
        return (PingDisplay) Tritium.instance.getModuleManager().getModule(PingDisplay.class);
    }
}
