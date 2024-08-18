package me.imflowow.tritium.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.util.IChatComponent;
public class ServerUtils {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static void kickFromServer(IChatComponent reason) {
        if (mc.getCurrentServerData() != null) {
            mc.theWorld.sendQuittingDisconnectingPacket();
            mc.loadWorld(null);
            mc.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "disconnect.lost", reason));
        }
    }

	public static String getServerIP() {
		return Minecraft.getMinecraft().getCurrentServerData() == null ? null
				: Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase();
	}

	public static boolean isDoMCer() {
		String ip = getServerIP();
		if (ip != null) {
			if (ip.contains("domcer.domcer.com")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isMCAC() {
		String ip = getServerIP();
		if (ip != null) {
			if (ip.contains("join.mchycraft.com")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isEPlus() {
		String ip = getServerIP();
		if (ip != null) {
			if (ip.contains("play.qyzgmc.com")) {
				return true;
			}
		}
		return false;
	}
}
