package me.imflowow.tritium.core.modules;

import me.imflowow.tritium.core.Tritium;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.IChatComponent;
import tritium.api.manager.MessageManager.MessageType;
import tritium.api.module.Module;
import tritium.api.module.value.impl.BooleanValue;
import tritium.api.module.value.impl.NumberValue;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.ChatEvent;

public class AutoGG extends Module {
	public NumberValue<Integer> delay = new NumberValue("Delay", 2000, 0, 3000, 250);
	public BooleanValue autoplay = new BooleanValue("AutoPlay", false);

	public AutoGG() {
		super("AutoGG", "When the game has been over on Hypixel,it will send a message\"gg\"");
		super.addValues(delay, autoplay);
	}

	@EventTarget
	public void onChatFrom(final ChatEvent e) {
		for (IChatComponent cc : e.getChatComponent().getSiblings()) {
			final ClickEvent ce = cc.getChatStyle().getChatClickEvent();
			if (ce != null) {
				if ((ce.getAction() == Action.RUN_COMMAND) && ce.getValue().contains("/play")) {
					this.mc.thePlayer.sendChatMessage("gg");
					Tritium.instance.getMessagemanager().addMessage(
							Tritium.instance.getLanguagemanager().getTextRender().getText("You will start the next game in ") + (this.delay.getValue().doubleValue() / 1000.0) + "s",
							MessageType.Info, this.delay.getValue().intValue());
					if (autoplay.getValue()) {
						(new Thread(() -> {
							try {
								Thread.sleep(this.delay.getValue().longValue());
							} catch (final InterruptedException a) {
								a.printStackTrace();
							}
							this.mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(ce.getValue()));
						})).start();
					}
					e.setCancelled(true);
				}
			}
		}
	}
}
