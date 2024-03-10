package net.minecraft.client.gui;

import me.imflowow.tritium.client.ui.init.GuiInit;
import me.imflowow.tritium.client.ui.mainmenu.MainMenu;
import me.imflowow.tritium.core.Tritium;
import tritium.api.Plugin;

public class GuiMainMenu extends GuiScreen {
	public static boolean hasLoadPlugins = false;

	@Override
	public void initGui() {
		if (!hasLoadPlugins) {
			hasLoadPlugins = true;
			for (Plugin plugin : Tritium.instance.getPluginsManager().plugins) {
				plugin.postInit();
			}
		}

		if (Tritium.initialization) {
			mc.displayGuiScreen(new GuiInit());
			//这里会调用初始化Gui，就是让你选语言那个
			//这步可以直接跳 我在初始化阶段直接设置语言
			return;
		}
		mc.displayGuiScreen(new MainMenu());
	}
}
