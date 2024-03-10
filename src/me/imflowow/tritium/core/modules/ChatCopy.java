package me.imflowow.tritium.core.modules;

import me.imflowow.tritium.core.Tritium;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import tritium.api.module.Module;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.ChatEvent;

public class ChatCopy extends Module {

	public ChatCopy() {
		super("ChatCopy", "It allowes you to copy chat message when you click \"[C]\"");
	}

	@EventTarget
	public void onChatFrom(ChatEvent e) {
		IChatComponent ChatComponent = e.getChatComponent();

		e.getChatComponent().appendSibling(
				new ChatComponentText(EnumChatFormatting.GRAY + " [C]").setChatStyle(new ChatStyle()
						.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
								new StringBuilder().insert(0, "/messagecopy ")
										.append(EnumChatFormatting.getTextWithoutFormattingCodes(
												e.getMessage()))
										.toString()))
						.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
								new ChatComponentText(Tritium.instance.getLanguagemanager().getTextRender().getText("Click on this to copy this message."))))));
		e.setChatComponent(ChatComponent);
	}

}
