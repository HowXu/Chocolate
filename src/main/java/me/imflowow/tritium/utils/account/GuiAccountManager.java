package me.imflowow.tritium.utils.account;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import me.imflowow.tritium.client.ui.utils.button.AnimatedButton;
import me.imflowow.tritium.client.ui.utils.button.SimpleButton;
import me.imflowow.tritium.client.ui.utils.input.GuiFlatInput;
import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.account.utils.Account;
import me.imflowow.tritium.utils.account.utils.AddAltThread;
import me.imflowow.tritium.utils.account.utils.AuthThread;
import me.imflowow.tritium.utils.information.Version.VersionType;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import tritium.api.utils.StringUtils;
import tritium.api.utils.animate.Translate;
import tritium.api.utils.render.Image;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.Rect.RenderType;
import tritium.api.utils.render.Scissor;
import tritium.api.utils.render.clickable.entity.ClickableRect;
import tritium.api.utils.timer.MsTimer;
import tritium.netease.TritiumNetease;

public class GuiAccountManager extends GuiScreen {
	public ArrayList<SimpleButton> buttons = new ArrayList();

	public static HashMap<String, ResourceLocation> avatar = new HashMap();
	private static boolean hasInit = false;

	public static ArrayList<AccountRect> accounts = new ArrayList();

	private ClickableRect accountList;
	private ClickableRect addAccount;
	private ClickableRect netease;
	private ClickableRect multiplayer;

	public static String serverip = "";
	public static String port = "";

	private boolean isOpenAddAccount;

	public static GuiFlatInput mail;
	public static GuiFlatInput password;

	public static MsTimer infotimer = new MsTimer();
	public static String info = "";

	private double tarOffset = 0;
	private double curOffset = 0;

	private Translate animation = new Translate(0, 0);
	private Translate openAnimation = new Translate(0, 0);
	private Scissor scissor;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		new Rect(0, 0, this.width, this.height, new Color(12, 12, 22).getRGB(), RenderType.Position).draw();

//		new Rect(this.width - 22, 4, 18, 18, new Color(33, 33, 42).getRGB(), Rect.RenderType.Expand).draw();
		this.addAccount.draw();
		Tritium.instance.getFontManager().arial32.drawStringWithShadow("+", this.width - 17.5, 6, -1);
		if (Tritium.version.getType() == VersionType.Development) {
			this.netease.draw();
			GlStateManager.color(0.8f, 0, 0);
			new Image(new ResourceLocation("tritium/icons/netease.png"), this.width - 63, 7, 12, 12,
					Image.RenderType.None).draw();
		}

		this.multiplayer.draw();
		new Image(new ResourceLocation("tritium/icons/players.png"), this.width - 41, 7, 12, 12, Image.RenderType.Clear)
				.draw();

//		new Rect(0, 0, 100, this.height, new Color(22, 22, 32).getRGB(), Rect.RenderType.Expand).draw();
		this.accountList.draw();
		if (Mouse.hasWheel()) {
			final int wheel = Mouse.getDWheel();
			if (wheel < 0) {
				this.tarOffset += 24;
				if (this.tarOffset > (accounts.size() - 1) * 24) {
					this.tarOffset = (accounts.size() - 1) * 24;
				}
			} else if (wheel > 0) {
				this.tarOffset -= 24;
				if (this.tarOffset < 0) {
					this.tarOffset = 0;
				}
			}
		}
		this.animation.interpolate(this.tarOffset, 0, 0.3f);
		this.curOffset = this.animation.getX();
		int y = 0;

		for (AccountRect rect : this.accounts) {
			rect.draw(y - curOffset);
			y += 24;
		}

		if (this.isOpenAddAccount) {
			this.openAnimation.interpolate(120, 50, 0.3f);
		} else {
			this.openAnimation.interpolate(0, 0, 0.3f);
		}
		this.scissor.setX(this.width / 2 - this.openAnimation.getX());
		this.scissor.setY(this.height / 2 - this.openAnimation.getY());
		this.scissor.setWidth(this.openAnimation.getX() * 2);
		this.scissor.setHeight(this.openAnimation.getY() * 2);

		this.scissor.doScissor();
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		if (!(this.openAnimation.getX() == 0 || this.openAnimation.getY() == 0)) {
			new Rect(this.width / 2 - 120, this.height / 2 - 50, 240, 100, new Color(22, 22, 32).getRGB(),
					RenderType.Expand).draw();

			this.mail.draw(mouseX, mouseY, partialTicks);
			this.password.draw(mouseX, mouseY, partialTicks);

			for (SimpleButton button : buttons) {
				if (!button.isHidden())
					button.draw();
			}
		}
		GL11.glDisable(GL11.GL_SCISSOR_TEST);

		if (!serverip.equals("") && !port.equals("")) {
			Minecraft.getMinecraft().displayGuiScreen(
					new GuiConnecting(this, Minecraft.getMinecraft(), serverip, Integer.parseInt(port)));
			serverip = "";
			port = "";
		}

		if (infotimer.reach(3000))
			info = "";
		StringUtils.drawCenteredStringWithShadow(info, this.width / 2, this.height - 8, -1, SizeType.Size16);
	}

	@Override
	public void initGui() {
		this.isOpenAddAccount = false;
		this.scissor = new Scissor(0, 0, 0, 0);
		this.accounts.clear();
		for (Account account : Tritium.instance.getAccountManager().getAccounts()) {
			if (!this.hasInit) {
				this.download(account);
			}
			this.accounts.add(new AccountRect(account));
		}
		this.hasInit = true;
		this.accountList = new ClickableRect(0, 0, 100, this.height, new Color(22, 22, 32).getRGB(),
				RenderType.Expand, () -> {
				}, () -> {
				}, () -> {
				}, () -> {
				}, () -> {
				});
		this.addAccount = new ClickableRect(this.width - 22, 4, 18, 18, new Color(33, 33, 42).getRGB(),
				RenderType.Expand, () -> {
					this.isOpenAddAccount = !this.isOpenAddAccount;
				}, () -> {
				}, () -> {
					this.addAccount.setColor(new Color(44, 44, 52).getRGB());
				}, () -> {
				}, () -> {
					this.addAccount.setColor(new Color(33, 33, 42).getRGB());
				});
		this.netease = new ClickableRect(this.width - 66, 4, 18, 18, new Color(33, 33, 42).getRGB(),
				RenderType.Expand, () -> {
					if (Tritium.instance.getAuthmanager().getAuth().socket == null) {
						GuiAccountManager.info = "§cPlease set the mode to \"Netease\" first.";
						GuiAccountManager.infotimer.reset();
					} else {
						Tritium.instance.getAuthmanager().getAuth().socket.disconnect();
						Tritium.instance.getAuthmanager().getAuth().socket = new TritiumNetease();
						Tritium.instance.getAuthmanager().getAuth().socket.send("TritiumNetease|NeteaseServer");
					}
				}, () -> {
				}, () -> {
					this.netease.setColor(new Color(44, 44, 52).getRGB());
				}, () -> {
				}, () -> {
					this.netease.setColor(new Color(33, 33, 42).getRGB());
				});
		this.multiplayer = new ClickableRect(this.width - 44, 4, 18, 18, new Color(33, 33, 42).getRGB(),
				RenderType.Expand, () -> {
					this.mc.displayGuiScreen(new GuiMultiplayer(this));
				}, () -> {
				}, () -> {
					this.multiplayer.setColor(new Color(44, 44, 52).getRGB());
				}, () -> {
				}, () -> {
					this.multiplayer.setColor(new Color(33, 33, 42).getRGB());
				});
		this.mail = new GuiFlatInput("mail", this.width / 2 - 100, this.height / 2 - 30, 200, 12,
				new Color(33, 47, 61).getRGB(), -1, new Color(235, 237, 239).getRGB(), false);
		this.password = new GuiFlatInput("password", this.width / 2 - 100, this.height / 2 - 10, 200, 12,
				new Color(33, 47, 61).getRGB(), -1, new Color(235, 237, 239).getRGB(), true);
		this.buttons.clear();
		this.buttons.add(new AnimatedButton("Login", this.width / 2 - 50, this.height / 2 + 10, 100, 12, -1,
				new Color(235, 237, 239).getRGB(), new Color(27, 38, 49).getRGB(), () -> {
					new AddAltThread(this.mail.getText(), this.password.getText()).start();
				}));
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		this.mail.keyTyped(typedChar, keyCode);
		this.password.keyTyped(typedChar, keyCode);
		super.keyTyped(typedChar, keyCode);
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
