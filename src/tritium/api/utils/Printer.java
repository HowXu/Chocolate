package tritium.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Printer {
	public static Minecraft mc = Minecraft.getMinecraft();

	public static void sendMessage(String message) {
		mc.ingameGUI.getChatGUI()
				.printChatMessage(new ChatComponentText(("&e[&cTritium&e] &f" + message).replace("&", "§")));
	}
}