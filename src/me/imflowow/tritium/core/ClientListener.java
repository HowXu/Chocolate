package me.imflowow.tritium.core;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;

import cn.howxu.ELog;
import org.lwjgl.input.Keyboard;

import me.imflowow.tritium.client.cape.ClientCape;
import me.imflowow.tritium.client.libraries.Libraries;
import me.imflowow.tritium.client.libraries.library.EPlusAntiCheat;
import me.imflowow.tritium.client.libraries.library.StaffModules;
import me.imflowow.tritium.client.ui.clickgui.ClickGui;
import me.imflowow.tritium.utils.Rotation;
import me.imflowow.tritium.utils.anticheat.AntiCheatManager;
import me.imflowow.tritium.utils.itemscroller.ItemScroller;
import me.imflowow.tritium.utils.netease.DoMCerUtils;
import me.imflowow.tritium.utils.netease.EPlusUtils;
import me.imflowow.tritium.utils.netease.MCACUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import tritium.api.utils.StringUtils;
import tritium.api.utils.event.api.EventManager;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.AttackEvent;
import tritium.api.utils.event.events.KeyPressEvent;
import tritium.api.utils.event.events.PacketEvent;
import tritium.api.utils.event.events.SendMessageEvent;
import tritium.netease.TritiumNetease;

public class ClientListener {
	public Minecraft mc = Minecraft.getMinecraft();
	public ClickGui gui = new ClickGui();;
	public Rotation serverRotation = new Rotation(-1, -1);;

	public AntiCheatManager anticheat;

	public ClientCape clientcape;

	public DoMCerUtils domcer;
	public MCACUtils mcac;
	public EPlusUtils eplus;

	public ItemScroller is;

	public TritiumNetease skinhandler;

	public ClientListener() {
		EventManager.register(this);
		this.is = new ItemScroller();
		EventManager.register(is);

		this.anticheat = new AntiCheatManager();
		this.clientcape = new ClientCape();

		this.domcer = new DoMCerUtils();
		this.mcac = new MCACUtils();

	}

	public void init() {
		if (Tritium.instance.getLibrariesmanager().isLoaded(EPlusAntiCheat.class)) {
			this.eplus = new EPlusUtils();
		}
	}

	@EventTarget
	public void onKeyPress(KeyPressEvent event) {
		// System.out.println(event.getKey());
		Tritium.instance.getModuleManager().getModuleMap().values().forEach(m -> {
			if (m.getKeybind() == event.getKey()) {
				m.toggle();
			}
		});

		if (event.getKey() == Keyboard.KEY_RSHIFT) {
			//监听到右键事件后打开一个gui
			//ELog.log_info("Listening KEY_RSHIFT","begin mc.displayGuiScreen(gui);");
			//System.exit(0);
			mc.displayGuiScreen(gui);
			//ELog.log_info("Listening KEY_RSHIFT gui toString",gui.toString());
			//ELog.log_info("Listening KEY_RSHIFT gui.getModuleList toString",gui.getModuleList().toString());
			//ELog.log_info("Listening KEY_RSHIFT","finish mc.displayGuiScreen(gui);");
		}
	}

	@EventTarget
	public void onStaffModules(SendMessageEvent event) {
		if (event.getMessage().equals("qwqhhhfaq")) {
			StaffModules lib = (StaffModules) Tritium.instance.getLibrariesmanager().getLibraries(StaffModules.class);
			if (lib.isLoaded()) {
				lib.unload();
				try {
					lib.classloader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				lib.classloader = null;
				lib.setLoaded(false);
				System.gc();
				File directory = new File(new File(Minecraft.getMinecraft().mcDataDir, "chocolate"), "libraries");
				//存的是什么我也不知道
				File [] files = directory.listFiles();
				if (!directory.exists()) {
					directory.mkdir();
				} else if (files != null) {
					for (final File file : files) {
						file.delete();
					}
				}
			}
		}
	}

	@EventTarget
	public void onSendMessage(SendMessageEvent event) {
		final String message = event.getMessage();
		if (message.startsWith("/")) {
			String s = message.substring(1);
			final String[] command = s.split(" ");
			if (command.length > 0)
			{
				String msg = "";
				for (int index = 1; index < command.length; ++index)
					msg += command[index] + " ";
				switch (command[0]) {
				case "messagecopy":
					event.setCancelled(true);
					this.copy(msg);
					break;
				case "translate":
					event.setCancelled(true);
					this.translate(msg);
					break;
				}
			}


		}
	}

	@EventTarget
	public void onPacket(PacketEvent e) {
		if (e.isSending()) {
			if (e.getPacket() instanceof C03PacketPlayer) {
				C03PacketPlayer packet = (C03PacketPlayer) e.getPacket();

				if (packet.getRotating()) {
					this.serverRotation = new Rotation(packet.getYaw(), packet.getPitch());
				}

			}

			if (e.getPacket() instanceof C02PacketUseEntity) {
				C02PacketUseEntity packet = (C02PacketUseEntity) e.getPacket();
				if (packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
					AttackEvent event = new AttackEvent(packet.getEntityFromWorld(mc.theWorld));
					EventManager.call(event);
					if (event.isCancelled())
						e.setCancelled(true);
				}
			}
		}
	}

	public void copy(String str) {
		StringSelection stsel = new StringSelection(str);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stsel, stsel);
	}

	public void translate(String str)
	{
			Thread translate = new Thread(() -> {
				if(mc.thePlayer != null)
				{
					mc.thePlayer.addChatMessage(new ChatComponentText("[Tritium-X] "+StringUtils.translate(str).trim()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
				}
			});
			translate.setDaemon(true);
			translate.start();
	}

	public ClickGui getClickGui() {
		return this.gui;
	}

	public Rotation getServerRotation() {
		return serverRotation;
	}

	public void setServerRotation(Rotation serverRotation) {
		this.serverRotation = serverRotation;
	}

}
