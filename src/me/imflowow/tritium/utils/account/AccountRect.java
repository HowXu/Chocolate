package me.imflowow.tritium.utils.account;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.account.utils.Account;
import me.imflowow.tritium.utils.account.utils.AuthThread;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import tritium.api.utils.render.Image;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.clickable.entity.ClickableRect;
import tritium.api.utils.timer.MsTimer;

public class AccountRect {
	private Account account;
	private ClickableRect area;
	private MsTimer timer = new MsTimer();

	public AccountRect(Account account) {
		this.account = account;
		this.area = new ClickableRect(0, 0, 100, 24, new Color(33, 33, 42).getRGB(), Rect.RenderType.Expand, () -> {
			if (!timer.sleep(500)) {
				new AuthThread(this.account.getUsername(), this.account.getPassword()).start();
			}
		}, () -> {
		}, () -> {
			this.area.setColor(new Color(44, 44, 52).getRGB());
		}, () -> {
		}, () -> {
			this.area.setColor(new Color(33, 33, 42).getRGB());
		});
		ResourceLocation avatar = GuiAccountManager.avatar.get(this.account.getMask());

	}

	public void draw(double y) {
//		new Rect(0, y, 100, 24, new Color(33, 33, 42).getRGB(), Rect.RenderType.Expand).draw();
		this.area.setY(y);
		this.area.draw();
		ResourceLocation avatar = GuiAccountManager.avatar.get(this.account.getMask());

		if (avatar != null)
			new Image(avatar, 0, y, 24, 24, Image.RenderType.Clear).draw();

		Tritium.instance.getFontManager().arial16.drawString(this.account.getMask(), 28, y + 8, -1);
		Tritium.instance.getFontManager().arial16.drawString("*************", 28, y + 17, -1);
	}


}
