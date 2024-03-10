package tritium.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.imflowow.tritium.core.Tritium;
import net.minecraft.client.Minecraft;
import tritium.api.manager.FontManager;
import tritium.api.manager.MessageManager;
import tritium.api.module.Module;
import tritium.netease.auth.AuthManager;

public class Wrapper {
	public static Wrapper instance;
	public static int delta;
	public static Logger logger = LogManager.getLogger();

	public void init(Wrapper instance) {
		this.instance = instance;
	}

	public boolean isModuleEnable(Module module) {
		Module m = Tritium.instance.getModuleManager().getModule(module.getClass());
		if (m != null) {
			return m.isEnabled();
		}
		return false;
	}
	public Module getModule(Class clz) {
		return Tritium.instance.getModuleManager().getModule(clz);
	}

	public Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}

	public FontManager getFontManager() {
		return Tritium.instance.getFontManager();
	}

	public MessageManager getMessageManager() {
		return Tritium.instance.getMessagemanager();
	}

	public AuthManager getAuthManager() {
		return Tritium.instance.getAuthmanager();
	}

	public String getVersion()
	{
		return Tritium.version.toString();
	}
}
