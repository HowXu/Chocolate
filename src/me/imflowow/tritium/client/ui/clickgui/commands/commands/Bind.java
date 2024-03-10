package me.imflowow.tritium.client.ui.clickgui.commands.commands;

import org.lwjgl.input.Keyboard;

import me.imflowow.tritium.client.ui.clickgui.commands.Command;
import me.imflowow.tritium.core.Tritium;
import tritium.api.manager.MessageManager.MessageType;
import tritium.api.module.Module;
import tritium.api.utils.Printer;

public class Bind extends Command {

	public Bind() {
		super("Bind", new String[] { "bind", "b" });
	}

	@Override
	public void onRun(String[] args) {
		if (args.length == 3) {
			String moduleName = args[1];
			Module module = Tritium.instance.getModuleManager().getModule(moduleName);
			if (module != null) {
				if (module.isBindable()) {
					int keyCode = Keyboard.getKeyIndex(args[2].toUpperCase());
					if (keyCode != -1) {
						module.setKeybind(keyCode);
						Tritium.instance.getMessagemanager().addMessage(
								module.getLabel() + " is now bound to \"" + Keyboard.getKeyName(keyCode) + "\".",
								MessageType.Info, 3000);
					} else {
						Tritium.instance.getMessagemanager().addMessage("That is not a valid key code.",
								MessageType.Warnning, 3000);
					}
				} else {
					Tritium.instance.getMessagemanager().addMessage("That module is unbindable.", MessageType.Warnning,
							3000);
				}
			} else {
				Tritium.instance.getMessagemanager().addMessage("That module does not exist.", MessageType.Warnning,
						3000);
			}
		} else {
			Tritium.instance.getMessagemanager().addMessage("Usage: \"bind [module] [key]\"", MessageType.Warnning,
					3000);
		}
	}
}
