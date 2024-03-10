package me.imflowow.tritium.core.globals;

import java.util.ArrayList;
import java.util.List;

import me.imflowow.tritium.core.modules.utils.antispam.SpamCount;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import tritium.api.module.GlobalModule;
import tritium.api.module.value.impl.BooleanValue;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.api.types.Priority;
import tritium.api.utils.event.events.ChatEvent;

public class Chat extends GlobalModule {
	private ArrayList<SpamCount> messages = new ArrayList();

	public BooleanValue clearchat = new BooleanValue("ClearChat", true);
	public BooleanValue antispam = new BooleanValue("AntiSpam", true);
	public BooleanValue animation = new BooleanValue("Animation", true);
	public Chat() {
		super("Chat", "Some changes of chat.");
		super.addValues(clearchat, antispam,animation);
		this.messages = new ArrayList();
	}

	@EventTarget(Priority.LOW)
	public void onChat(ChatEvent event) {
		if (!antispam.getValue()) {
			return;
		}
		IChatComponent mes = event.getChatComponent();
		boolean existsAlready = false;
		for (SpamCount spam : messages) {
			if (!spam.isSame(mes))
				continue;
			existsAlready = true;
			Long currentTime = System.currentTimeMillis();
			if (currentTime - spam.getTime() < 30000L) {
				spam.increaseCounter();
				mes.appendText(EnumChatFormatting.GOLD + " [" + EnumChatFormatting.GRAY + "x"
						+ EnumChatFormatting.RED + spam.getCounter() + EnumChatFormatting.GOLD + "]");
			} else {
				spam.resetCounter();
			}
			spam.setTime(currentTime);
		}
		if (!existsAlready) {
			messages.add(new SpamCount(mes));
		} else {
			List<ChatLine> list = mc.ingameGUI.getChatGUI().chatLines;
			if (list != null) {
				for (int i = 0; i < list.size(); ++i) {
					String mesStr;
					ChatLine line = list.get(i);
					String counterStr = EnumChatFormatting.GOLD + " [" + EnumChatFormatting.GRAY + "x";
					String lineStr = line.getChatComponent().getUnformattedText();
					int lastIndex = lineStr.lastIndexOf(counterStr);
					if (lastIndex > 0) {
						lineStr = lineStr.substring(0, lastIndex);
					}
					if ((lastIndex = (mesStr = mes.getUnformattedText()).lastIndexOf(counterStr)) > 0) {
						mesStr = mesStr.substring(0, lastIndex);
					}
					if (!lineStr.equals(mesStr))
						continue;
					list.remove(line);
				}
			}

			mc.ingameGUI.getChatGUI().refreshChat();
		}
	}
}
