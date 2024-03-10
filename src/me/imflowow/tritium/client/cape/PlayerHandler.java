package me.imflowow.tritium.client.cape;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class PlayerHandler {
	private static HashMap<String, PlayerHandler> instances = new HashMap();
	public boolean hasCapeGlint = false;
	public boolean hasCape = false;
	public String name;
	public boolean hasInfo = false;
	public boolean hasWing = false;
	public WingSettings settings;
	public RenderWings wing;
	
	public PlayerHandler(EntityPlayer player) {
		this.name = player.getName();
		instances.put(this.name, this);
	}

	public static PlayerHandler getFromPlayer(EntityPlayer player) {
		PlayerHandler playerHandler = instances.get(player.getName());
		return playerHandler == null ? new PlayerHandler(player) : playerHandler;
	}

	public void applyCape(BufferedImage cape) {
		BufferedImage capeImage = cape;
		int imageHeight;
		int imageWidth = 64;
		int srcWidth = capeImage.getWidth();
		int srcHeight = capeImage.getHeight();
		for (imageHeight = 32; imageWidth < srcWidth || imageHeight < srcHeight; imageWidth *= 2, imageHeight *= 2) {
		}
		BufferedImage imgNew = new BufferedImage(imageWidth, imageHeight, 2);
		Graphics g = imgNew.getGraphics();
		g.drawImage(capeImage, 0, 0, null);
		g.dispose();
		this.applyTexture(new ResourceLocation("tritium", "capes/" + this.name), imgNew);
		this.hasCape = true;
	}

	public ResourceLocation getCapeLocation() {
		if (this.hasCape) {
			return new ResourceLocation("tritium", "capes/" + this.name);
		}
		return null;
	}

	public void applyWing(BufferedImage wing) {
		this.applyTexture(new ResourceLocation("tritium", "wings/" + this.name), wing);
		this.hasWing = true;
	}

	public ResourceLocation getWingLocation() {
		if (this.hasWing) {
			return new ResourceLocation("tritium", "wings/" + this.name);
		}
		return null;
	}

	private void applyTexture(final ResourceLocation resourceLocation, final BufferedImage bufferedImage) {
		Minecraft.getMinecraft().addScheduledTask(new Runnable() {

			@Override
			public void run() {
				Minecraft.getMinecraft().getTextureManager().loadTexture(resourceLocation,
						new DynamicTexture(bufferedImage));
			}
		});
	}
}
