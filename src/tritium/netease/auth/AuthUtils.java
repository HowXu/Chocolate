package tritium.netease.auth;

import java.net.Proxy;
import java.util.UUID;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import me.imflowow.tritium.core.Tritium;
import net.minecraft.client.Minecraft;
import tritium.netease.TritiumNetease;

public class AuthUtils {

	public TritiumNetease socket;

	public MinecraftSessionService bak;

	public void loadAuth(int type) {
		Minecraft mc = Minecraft.getMinecraft();
		if (bak == null) {
			bak = mc.getSessionService();
		}
		if (socket != null) {
			socket.disconnect();
			socket = null;
		}
		switch (type) {
		case 0:
			mc.setSessionService(bak);
			Tritium.instance.getAuthmanager().setAuthtype(0);
			break;
		case 1:
			mc.setSessionService((new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString()))
					.createMinecraftSessionService());
			Tritium.instance.getAuthmanager().setAuthtype(1);
			socket = new TritiumNetease();
			break;
		}

	}

}
