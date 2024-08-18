package me.imflowow.tritium.utils.account.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Proxy;
import java.net.URL;

import javax.imageio.ImageIO;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.account.AccountRect;
import me.imflowow.tritium.utils.account.GuiAccountManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;

public class AddAltThread extends Thread {
    private final String password;
    private final String username;
    private Minecraft mc = Minecraft.getMinecraft();

    public AddAltThread(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private final void checkAndAddAlt(String username, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service
                .createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            Account alt = new Account(username, password, auth.getSelectedProfile().getName());
            Tritium.instance.getAccountManager().getAccounts()
                    .add(alt);
            Tritium.instance.getAccountManager().getAltSaving().saveFile();
            Session session = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
            mc.setSession(session);
    		GuiAccountManager.accounts.clear();
    		for (Account account : Tritium.instance.getAccountManager().getAccounts()) {
    			GuiAccountManager.accounts.add(new AccountRect(account));
    		}
    		GuiAccountManager.info =  "§aLogged in.";
    		GuiAccountManager.infotimer.reset();
    		this.download(alt);
    		
    		GuiAccountManager.mail.setText("");
    		GuiAccountManager.password.setText("");
        } catch (AuthenticationException e) {

    		GuiAccountManager.info = "§cLogin failed!";
    		GuiAccountManager.infotimer.reset();
    		
    		GuiAccountManager.mail.setText("");
    		GuiAccountManager.password.setText("");
            e.printStackTrace();
        }
    }


    @Override
	public void run() {
    	GuiAccountManager.info = "Logging in...";
    	GuiAccountManager.infotimer.reset();
        checkAndAddAlt(username, password);
    }
    
	private void download(Account account) {
		String name = account.getMask();
		ResourceLocation avatar = GuiAccountManager.avatar.get(name);

		if (avatar == null) {
			Thread avatarDownload = new Thread(() -> {
				BufferedImage dynamicAvatar;
				try {
					dynamicAvatar = ImageIO.read(new URL("https://mineskin.de/avatar/" + name));
				} catch (IOException e) {
					dynamicAvatar = null;
				}
				if (dynamicAvatar != null) {
					ResourceLocation rl = new ResourceLocation("tritium/account/" + name);
					applyTexture(rl, dynamicAvatar);
					GuiAccountManager.avatar.put(name, rl);
				}
			});
			avatarDownload.setDaemon(true);
			avatarDownload.start();
		}
	}

	private void applyTexture(final ResourceLocation resourceLocation, final BufferedImage bufferedImage) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			Minecraft.getMinecraft().getTextureManager().loadTexture(resourceLocation,
					new DynamicTexture(bufferedImage));
		});
	}
}