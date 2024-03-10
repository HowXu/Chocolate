package me.imflowow.tritium.client.cape;

import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import net.minecraft.entity.player.EntityPlayer;
import tritium.api.utils.HttpUtils;
import tritium.api.utils.event.api.EventManager;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.api.types.Priority;
import tritium.api.utils.event.events.EntityJoinWorldEvent;
import tritium.api.utils.event.events.RenderStringEvent;

public class ClientCape {

	ArrayList<ReplacementString> replacelist = new ArrayList();

	public ClientCape() {
		EventManager.register(this);
	}

	@EventTarget
	public void onPlayerJoin(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof EntityPlayer && event.getWorld().isRemote) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			final PlayerHandler playerHandler = PlayerHandler.getFromPlayer(player);
			if (playerHandler == null || playerHandler.hasInfo) {
				return;
			}
			playerHandler.hasInfo = true;
			/*
			Thread playerDownload = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						playerHandler.applyCape(ImageIO.read(new URL(
								"https://gitee.com/LEF-ganga/Tritium/raw/master/Capes/" + player.getName() + ".png")));
						String configs;
						configs = "Glint:[false];";
						configs = HttpUtils
								.get("https://gitee.com/LEF-ganga/Tritium/raw/master/Capes/" + player.getName(), null);
						String type = getValue(configs, "Glint");
						if (type.equals("true"))
							playerHandler.hasCapeGlint = true;
					} catch (Throwable e) {
					}
					try {
						playerHandler.applyWing(ImageIO.read(new URL(
								"https://gitee.com/LEF-ganga/Tritium/raw/master/Wings/" + player.getName() + ".png")));
						playerHandler.settings = new WingSettings();
						playerHandler.settings.location = playerHandler.getWingLocation();
						playerHandler.wing = new RenderWings();
						String configs;
						configs = "Chroma:[false];Scale:[100];";
						configs = HttpUtils
								.get("https://gitee.com/LEF-ganga/Tritium/raw/master/Wings/" + player.getName(), null);
						String chroma = getValue(configs, "Chroma");
						if (chroma.equals("true"))
							playerHandler.settings.chroma = true;
						String scale = getValue(configs, "Scale");
						playerHandler.settings.scale = Integer.parseInt(scale);
					} catch (Throwable e) {
					}
					try {
						String configs;
						configs = "PrefixEnable:[false];Prefix:[];";
						configs = HttpUtils.get(
								"https://gitee.com/LEF-ganga/Tritium/raw/master/PlayerConfigs/" + player.getName(),
								null);
						String prefixEnable = getValue(configs, "PrefixEnable");
						if (prefixEnable.equals("true")) {
							String prefix = getValue(configs, "Prefix").replaceAll("&", "§");
							replacelist.add(new ReplacementString(player.getName(), prefix + player.getName()+ "§7*§r"));
						}

					} catch (Throwable e) {
					}
				}
			});
			playerDownload.setDaemon(true);
			playerDownload.start();
			//为什么要到gitee下载披风和翅膀？
			 */

		}
	}

	@EventTarget(value = Priority.LOWEST)
	public void onRenderString(RenderStringEvent event) {
		String text = event.getText();
		for (ReplacementString str : replacelist) {
			text = text.replaceAll(str.getName(), str.getReplace());
		}
		event.setText(text);
	}

	private static String getValue(String config, String key) {
		return getSubString(config, key + ":[", "];");
	}

	private static String getSubString(String text, String left, String right) {
		String result = "";
		int zLen;
		if (left == null || left.isEmpty()) {
			zLen = 0;
		} else {
			zLen = text.indexOf(left);
			if (zLen > -1) {
				zLen += left.length();
			} else {
				zLen = 0;
			}
		}
		int yLen = text.indexOf(right, zLen);
		if (yLen < 0 || right == null || right.isEmpty()) {
			yLen = text.length();
		}
		result = text.substring(zLen, yLen);
		return result;
	}
}
