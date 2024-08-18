package me.imflowow.tritium.client.ui.clickgui.commands.commands;

import java.io.IOException;

import me.imflowow.tritium.client.manager.utils.Config;
import me.imflowow.tritium.client.ui.clickgui.commands.Command;
import me.imflowow.tritium.core.Tritium;
import tritium.api.manager.MessageManager.MessageType;
import tritium.api.utils.Printer;

public class ConfigCMD extends Command {

	public ConfigCMD() {
		super("Config", new String[] { "c", "config", "configs" });
	}

	@Override
	public void onRun(final String[] s) {
		if (s.length == 3) {
			switch (s[1]) {
			case "list":
				if (!Tritium.instance.getConfigmanager().getConfigs().isEmpty()) {
					Printer.sendMessage("Current Configs:");
					Tritium.instance.getConfigmanager().getConfigs().forEach(cfg -> {
						Printer.sendMessage(cfg.getName());
					});
				} else {
					Printer.sendMessage("You have no saved configs!");
				}
				break;
			case "help":
				Printer.sendMessage("config list - shows all configs.");
				Printer.sendMessage("config create/save configname - saves a config.");
				Printer.sendMessage("config delete/remove configname - removes a config.");
				Printer.sendMessage("config override configname - override a config.");
				Printer.sendMessage("config clear configname - remove all your config.");
				Printer.sendMessage("config load configname - load a config.");
				Printer.sendMessage("config reload configname - reload configmanager.");
				break;
			case "create":
			case "save":
				if (!Tritium.instance.getConfigmanager().isConfig(s[2])) {
					Tritium.instance.getConfigmanager().saveConfig(s[2]);
					Tritium.instance.getConfigmanager().getConfigs().add(new Config(s[2]));
					Printer.sendMessage("Created a config named " + s[2] + "!");
				} else {
					Printer.sendMessage(s[2] + " is already a saved config!");
				}
				break;
			case "delete":
			case "remove":
				if (Tritium.instance.getConfigmanager().isConfig(s[2])) {
					Tritium.instance.getConfigmanager().deleteConfig(s[2]);
					Printer.sendMessage("Deleted the config named " + s[2] + "!");
				} else {
					Printer.sendMessage(s[2] + " is not a saved config!");
				}
				break;
			case "reload":
				Tritium.instance.getConfigmanager().getConfigs().clear();
				Tritium.instance.getConfigmanager().load();
				Printer.sendMessage("Reloaded saved configs. Current number of configs: "
						+ Tritium.instance.getConfigmanager().getConfigs().size() + "!");
				break;
			case "clear":
				try {
					if (!Tritium.instance.getConfigmanager().getConfigs().isEmpty()) {
						Tritium.instance.getConfigmanager().clear();
						Tritium.instance.getConfigmanager().getConfigs().clear();
						Printer.sendMessage("Cleared all saved configs!");
					} else {
						Printer.sendMessage("You have no saved configs!");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "override":
				if (Tritium.instance.getConfigmanager().isConfig(s[2])) {
					Tritium.instance.getConfigmanager().saveConfig(s[2]);
					Printer.sendMessage("Overrode the config named " + s[2] + "!");
				} else {
					Printer.sendMessage(s[2] + " is not a saved config!");
				}
				break;
			case "load":
				if (Tritium.instance.getConfigmanager().isConfig(s[2])) {
					Tritium.instance.getConfigmanager().loadModules(s[2]);
					Printer.sendMessage("Loaded the config named " + s[2] + "!");
				} else {
					Printer.sendMessage(s[2] + " is not a saved config!");
				}
				break;
			}
		} else {
			Printer.sendMessage("config list - shows all configs.");
			Printer.sendMessage("config create/save configname - saves a config.");
			Printer.sendMessage("config delete/remove configname - removes a config.");
			Printer.sendMessage("config override configname - override a config.");
			Printer.sendMessage("config clear configname - remove all your config.");
			Printer.sendMessage("config load configname - load a config.");
			Printer.sendMessage("config reload configname - reload configmanager.");
		}
	}
}
