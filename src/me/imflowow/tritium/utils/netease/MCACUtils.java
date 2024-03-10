package me.imflowow.tritium.utils.netease;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.imflowow.tritium.utils.ServerUtils;
import me.imflowow.tritium.utils.netease.mcac.AESUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import tritium.api.utils.event.api.EventManager;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.PacketEvent;
import tritium.api.utils.event.events.TickEvent;
import tritium.api.utils.timer.MsTimer;
public class MCACUtils {
	public AESUtil aesutils;

	private MsTimer timer;
	private int type;

	public MCACUtils() {
		EventManager.register(this);
		this.timer = new MsTimer();
		this.type = 0;
	}

	@EventTarget
	public void onPacket(PacketEvent event) {
		if (!ServerUtils.isMCAC()) {
			return;
		}

		if (event.getPacket() instanceof S01PacketJoinGame) {
			S01PacketJoinGame packet = (S01PacketJoinGame) event.getPacket();
			this.type = 0;
			this.timer.reset();
		}

	}

	@EventTarget
	public void onTick(TickEvent event) {
		if (!ServerUtils.isMCAC()) {
			return;
		}
		switch (type) {
		case 0:
			if (this.timer.sleep(1000)) {
				this.aesutils = new AESUtil(5);
				ByteBuf byteBuf = Unpooled.wrappedBuffer("15".getBytes());
				C17PacketCustomPayload c17 = new C17PacketCustomPayload("Seed_", new PacketBuffer(byteBuf));
				Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacketNoEvent(c17);
				type++;
			}

			break;
		case 1:
			if (this.timer.sleep(1000)) {
				String message = "1" + String.valueOf(this.aesutils.AESEncode("5"));
				ByteBuf byteBuf = Unpooled.wrappedBuffer(message.getBytes());
				C17PacketCustomPayload c17 = new C17PacketCustomPayload("HB_", new PacketBuffer(byteBuf));
				Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacketNoEvent(c17);
				type++;
			}
			break;
		case 2:
			if (this.timer.sleep(1000)) {
				String message = "1" + String.valueOf(this.aesutils.AESEncode("6"));
				ByteBuf byteBuf = Unpooled.wrappedBuffer(message.getBytes());
				C17PacketCustomPayload c17 = new C17PacketCustomPayload("HB_", new PacketBuffer(byteBuf));
				Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacketNoEvent(c17);
				type++;
			}
			break;
		}
	}

}
