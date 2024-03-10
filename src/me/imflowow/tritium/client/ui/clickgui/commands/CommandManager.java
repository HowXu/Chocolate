package me.imflowow.tritium.client.ui.clickgui.commands;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import me.imflowow.tritium.client.ui.clickgui.commands.commands.Bind;
import me.imflowow.tritium.client.ui.clickgui.commands.commands.Theme;
import me.imflowow.tritium.core.Tritium;
import tritium.api.manager.MessageManager.MessageType;

public class CommandManager {
	public Map<String, Command> map = new HashMap<>();

	public void initialize() {

		Class[] commands = { Bind.class, Theme.class };

		for (Class class_ : commands) {
			this.register(class_);
		}
	}

	private void register(Class<? extends Command> commandClass) {
		try {
			Command createdCommand = commandClass.newInstance();
			map.put(createdCommand.getLabel().toLowerCase(), createdCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dispatch(String s) {
		String[] command = s.split(" ");
		if (command.length > 0) {
			boolean hasRun = false;
			for (Command c : this.getCommandMap().values()) {
				for (String handle : c.getHandles()) {
					if (handle.equalsIgnoreCase(command[0])) {
						c.onRun(command);
						hasRun = true;
					}
				}
			}
			if (!hasRun) {
				Tritium.instance.getMessagemanager().addMessage("That command does not exist.", MessageType.Error,
						3000);
			}
		} else {
			Tritium.instance.getMessagemanager().addMessage("You can't do it without anything...", MessageType.Error,
					3000);
		}
	}

	public Map<String, Command> getCommandMap() {
		return map;
	}
}
