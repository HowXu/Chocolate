package me.imflowow.tritium.core.modules;

import me.imflowow.tritium.core.modules.utils.DamageParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.world.World;
import tritium.api.module.Module;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.LivingUpdateEvent;
import tritium.api.utils.event.events.TickEvent;

public class DamageDealt extends Module {

	public DamageDealt() {
		super("DamageDealt", "Display Damage Dealt");
	}

	@EventTarget
	public void onLivingUpdate(LivingUpdateEvent e) {
		EntityLivingBase entity = e.getEntity();

		if (!entity.worldObj.isRemote) {
			return;
		}

		int currentHealth = (int) Math.ceil(entity.getHealth());

		if (entity.entityHealth != -1) {
			int entityHealth = entity.entityHealth;

			if (entityHealth != currentHealth) {
				displayParticle(entity, (int) entityHealth - currentHealth);
			}
		}

		entity.entityHealth = currentHealth;
	}
	private void displayParticle(Entity entity, int damage) {
		if (damage == 0) {
			return;
		}

		World world = entity.worldObj;
		double motionX = world.rand.nextGaussian() * 0.02;
		double motionY = 0.5f;
		double motionZ = world.rand.nextGaussian() * 0.02;
		DamageParticle damageIndicator = new DamageParticle(damage, world, entity.posX, entity.posY + entity.height, entity.posZ, motionX, motionY, motionZ);
		mc.effectRenderer.addEffect(damageIndicator);
	}
}
