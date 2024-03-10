package me.imflowow.tritium.client.ui.clickgui.commands.commands;

import me.imflowow.tritium.client.ui.clickgui.commands.Command;
import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.globals.ClientConfig;
import me.imflowow.tritium.core.globals.ClientConfig.ThemeType;
import tritium.api.manager.MessageManager.MessageType;

public class Theme extends Command {

	public Theme() {
		super("Theme", new String[] { "theme", "dark", "light" });
	}

	@Override
	public void onRun(String[] args) {
		String label = args[0];
		ClientConfig config = (ClientConfig) Tritium.instance.getModuleManager().getModule(ClientConfig.class);
		if (label.equalsIgnoreCase("theme")) {
			switch (config.theme.getValue()) {
			case Dark:
				config.theme.setValue(ThemeType.Light);
				break;
			case Light:
				config.theme.setValue(ThemeType.Dark);
				break;
			}
		} else {
			
			config.theme.setValue(label);
		}
		Tritium.instance.getMessagemanager().addMessage("Theme changed.", MessageType.Info,
				3000);

	}

}
