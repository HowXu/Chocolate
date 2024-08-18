package me.imflowow.tritium.utils.anticheat.listener;

import java.text.DecimalFormat;

import me.imflowow.tritium.utils.ServerUtils;
import me.imflowow.tritium.utils.anticheat.RayTraceUtil;
import me.imflowow.tritium.utils.events.ReachEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import tritium.api.utils.event.api.EventManager;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.api.types.Priority;
import tritium.api.utils.event.events.AttackEvent;

public class ReachListener {
	Minecraft mc = Minecraft.getMinecraft();
	private int count = 0;

	public ReachListener() {
		EventManager.register(this);
	}

	@EventTarget(Priority.LOWEST)
	public void onAttack(AttackEvent e) {
//		MovingObjectPosition mop = RayTraceUtil.getRotationOver(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
//		if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY
//				&& mop.entityHit.getEntityId() == e.getEntity().getEntityId()) {
//			e.setCancelled(false);
//			Vec3 vec3 = mc.getRenderViewEntity().getPositionEyes(1.0f);
//			double range = mop.hitVec.distanceTo(vec3);
//			System.out.println(range);
//			double maxrange = mc.playerController.isInCreativeMode() ? Double.parseDouble("4.75") : Double.parseDouble("3.0");
//			if (range > maxrange)
//				e.setCancelled(true);
//			return;
//		}
//		System.out.println(11);
//		e.setCancelled(true);
		
		
		if(count >= 10)
		{
			ServerUtils.kickFromServer(new ChatComponentText("[Tritium-X] AntiCheat System has found you are cheating.").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
		}

		double range = RayTraceUtil.getReachDistanceFromEntity(e.getEntity());
		double maxrange = mc.playerController.isInCreativeMode() ? Double.parseDouble("4.75")
				: Double.parseDouble("3.0");
		if (range > maxrange) {
			count++;
			e.setCancelled(true);
		}

		if (range == -1) {
			count++;
			e.setCancelled(true);
		}
	}

	@EventTarget(Priority.LOWEST)
	public void onReach(ReachEvent e) {
		e.setAttackRange(Double.parseDouble("3.0"));
		e.setPlaceRange((double) this.mc.playerController.getBlockReachDistance());
	}

}
