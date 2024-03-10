package me.imflowow.tritium.core.globals;

import java.util.List;

import me.imflowow.tritium.core.Tritium;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import tritium.api.module.GlobalModule;
import tritium.api.utils.StringUtils;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.ChatEvent;
import tritium.api.utils.event.events.SendMessageEvent;

public class Translator extends GlobalModule {

	public Translator() {
		super("Translator", "I can speak 24 languages.");
	}

	@EventTarget
	public void onChat(ChatEvent event) {
		event.getChatComponent().appendSibling(
				new ChatComponentText(EnumChatFormatting.GRAY + " [T]").setChatStyle(new ChatStyle()
						.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
								new StringBuilder().insert(0, "/translate ")
										.append(EnumChatFormatting.getTextWithoutFormattingCodes(
												event.getMessage()))
										.toString()))
						.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
								new ChatComponentText(Tritium.instance.getLanguagemanager().getTextRender().getText("Click on this to translate this message."))))));
//		event.setChatComponent(ChatComponent);
//		List<ChatLine> list = mc.ingameGUI.getChatGUI().chatLines;
//		
//		System.out.println(list.get(0).getChatComponent().getFormattedText());
//		mc.ingameGUI.getChatGUI().refreshChat();
	}

	@EventTarget
	public void onSendMessage(SendMessageEvent event) {
		if(event.getMessage().startsWith("/"))
			return;
		if(event.getMessage().startsWith("T"))
		{
			event.setCancelled(true);
			Thread translate = new Thread(() -> {
				C01PacketChatMessage chat = new C01PacketChatMessage(StringUtils.translate(event.getMessage().substring(1)));
				if(mc.thePlayer != null)
				{
					mc.thePlayer.sendQueue.getNetworkManager().sendPacket(chat);;
//					mc.thePlayer.addChatMessage(new ChatComponentText("[Tritium-X] It has been translated.").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
				}
			});
			translate.setDaemon(true);
			translate.start();
		}
	}

}
