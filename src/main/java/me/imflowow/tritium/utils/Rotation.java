package me.imflowow.tritium.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class Rotation {
	float yaw, pitch;

	public Rotation(float yaw, float pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public float getYaw() {
		return this.yaw;
	}

	public float getPitch() {
		return this.pitch;
	}
	
	public void toPlayer(EntityPlayer player) {
		if (Float.isNaN(yaw) || Float.isNaN(pitch))
			return;

		fixedSensitivity(Minecraft.getMinecraft().gameSettings.mouseSensitivity);

		player.rotationYaw = yaw;
		player.rotationPitch = pitch;
	}

	public void fixedSensitivity(Float sensitivity) {
		float f = sensitivity * 0.6F + 0.2F;
		float gcd = f * f * f * 1.2F;

		yaw -= yaw % gcd;
		pitch -= pitch % gcd;
	}
}
