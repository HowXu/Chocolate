package me.imflowow.tritium.client.ui.clickgui.commands;

import net.minecraft.client.Minecraft;

public class Command {
    private String label;
    private String[] handles;
    public Minecraft mc = Minecraft.getMinecraft();

    public Command(String label, String[] handles) {
        this.label = label;
        this.handles = handles;
    }

    public String[] getHandles() {
        return handles;
    }

    public void onRun(String[] s) {
    }

    public String getLabel() {
        return this.label;
    }
}
