package me.imflowow.tritium.utils.account.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import me.imflowow.tritium.utils.account.GuiAccountManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class Account {
	private String mask;
	private final String username;
	private String password;

	public Account(String username, String password) {
		this(username, password, "");
	}

	public Account(String username, String password, String mask) {
		this.username = username;
		this.password = password;
		this.mask = mask;
	}

	public String getMask() {
		return mask;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
