package me.imflowow.tritium.utils.anticheat.listener;


import me.imflowow.tritium.utils.anticheat.RayTraceUtil;
import me.imflowow.tritium.utils.events.HitBoxEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition;
import tritium.api.utils.event.api.EventManager;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.api.types.Priority;
import tritium.api.utils.event.events.AttackEvent;
public class HitboxListener {
	Minecraft mc = Minecraft.getMinecraft();

	public HitboxListener() {
		EventManager.register(this);
	}

//	@EventTarget(Priority.LOWEST)
//	public void onAttack(AttackEvent e) {
//		MovingObjectPosition mop = RayTraceUtil.getRotationOver(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
//		if (!(mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY
//				&& mop.entityHit.getEntityId() == e.getEntity().getEntityId())) {
//			e.setCancelled(true);
//		}
//	}
//
	@EventTarget(Priority.LOWEST)
	public void onHitBox(HitBoxEvent e) {
		e.setSize(Float.parseFloat("0.1"));
	}
}
