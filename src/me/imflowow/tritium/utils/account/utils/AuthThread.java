package me.imflowow.tritium.utils.account.utils;

import java.net.Proxy;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.account.GuiAccountManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;



public class AuthThread extends Thread {
    private final String password;
    private final String username;
    private Minecraft mc = Minecraft.getMinecraft();

    public AuthThread(String username, String password) {
        super("Alt Login Thread");
        this.username = username;
        this.password = password;
    }

    private Session createSession(String username, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();

            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        } catch (AuthenticationException localAuthenticationException) {
            localAuthenticationException.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
		GuiAccountManager.info = "Logging in...";
		GuiAccountManager.infotimer.reset();
        Session auth = createSession(username, password);
        if (auth == null) {
    		GuiAccountManager.info = "§cLogin failed!";
    		GuiAccountManager.infotimer.reset();
        } else {
    		GuiAccountManager.info =  "§aLogged in.";
    		GuiAccountManager.infotimer.reset();
            mc.setSession(auth);
        }
    }
}
