package me.imflowow.tritium.utils.anticheat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import me.imflowow.tritium.utils.Rotation;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.optifine.reflect.Reflector;
public class RayTraceUtil {
	static Minecraft mc = Minecraft.getMinecraft();

    public static double getReachDistanceFromEntity(Entity entity) {

        // How far will ray travel before ending
        double maxSize = 6D;
        // Bounding box of entity
        AxisAlignedBB otherBB = entity.getEntityBoundingBox();
        // This is where people found out that F3+B is not accurate for hitboxes,
        // it makes hitboxes bigger by certain amount
        float collisionBorderSize = entity.getCollisionBorderSize();
        AxisAlignedBB otherHitbox = otherBB.expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
        // Not quite sure what the difference is between these two vectors
        // In actual code where this is taken from, partialTicks is always 1.0
        // So this won't decrease accuracy
        Vec3 eyePos = mc.thePlayer.getPositionEyes(1.0F);
        Vec3 lookPos = mc.thePlayer.getLook(1.0F);
        // Get vector for raycast
        Vec3 adjustedPos = eyePos.addVector(lookPos.xCoord * maxSize, lookPos.yCoord * maxSize, lookPos.zCoord * maxSize);
        MovingObjectPosition movingObjectPosition = otherHitbox.calculateIntercept(eyePos, adjustedPos);
        Vec3 otherEntityVec;
        // This will trigger if hit distance is more than maxSize
        if (movingObjectPosition == null)
            return -1;
        otherEntityVec = movingObjectPosition.hitVec;
        // finally calculate distance between both vectors
        double dist = eyePos.distanceTo(otherEntityVec);

        mc.mcProfiler.endSection();
        return dist;
    }
	
	public static MovingObjectPosition getRotationOver(Rotation rot) {
		return getRotationOver(rot.getYaw(), rot.getPitch());
	}

	public static MovingObjectPosition getRotationOver(Entity entity) {
		float partialTicks = mc.timer.renderPartialTicks;

		if (entity != null && mc.theWorld != null) {
			double d0 = 6.0;
			double d1 = 6.0;
			MovingObjectPosition objectMouseOver = rayTrace(entity);

			Vec3 vec3 = entity.getPositionEyes(partialTicks);
			int i = 3;

			if (objectMouseOver != null) {
				d1 = objectMouseOver.hitVec.distanceTo(vec3);
			}

			Vec3 vec31 = getVectorForRotation(entity.rotationPitch, entity.rotationYaw);
			Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
			Entity targetEntity = null;
			Vec3 vec33 = null;
			float f = 1.0F;
			List<Entity> list = mc.theWorld.getEntitiesInAABBexcluding(entity,
					entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0)
							.expand(f, f, f),
					Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>() {
						@Override
						public boolean apply(Entity p_apply_1_) {
							return p_apply_1_.canBeCollidedWith();
						}
					}));
			double d2 = d1;

			for (int j = 0; j < list.size(); ++j) {
				Entity entity1 = list.get(j);
				float f1 = 0.1f;
				AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1,
						f1);
				MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

				if (axisalignedbb.isVecInside(vec3)) {
					if (d2 >= 0.0D) {
						targetEntity = entity1;
						vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
						d2 = 0.0D;
					}
				} else if (movingobjectposition != null) {
					double d3 = vec3.distanceTo(movingobjectposition.hitVec);

					if (d3 < d2 || d2 == 0.0D) {
						boolean flag1 = false;

						if (Reflector.ForgeEntity_canRiderInteract.exists()) {
							flag1 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract);
						}

						if (!flag1 && entity1 == entity.ridingEntity) {
							if (d2 == 0.0D) {
								targetEntity = entity1;
								vec33 = movingobjectposition.hitVec;
							}
						} else {
							targetEntity = entity1;
							vec33 = movingobjectposition.hitVec;
							d2 = d3;
						}
					}
				}
			}

			if (targetEntity != null && (d2 < d1 || objectMouseOver == null)) {
				objectMouseOver = new MovingObjectPosition(targetEntity, vec33);
			}
			return objectMouseOver;
		}
		return null;
	}

	public static MovingObjectPosition getRotationOver(float yaw, float pitch) {
		float partialTicks = mc.timer.renderPartialTicks;
		Entity entity = mc.getRenderViewEntity();

		if (entity != null && mc.theWorld != null) {
			double d0 = mc.playerController.getBlockReachDistance();
			double d1 = mc.playerController.getBlockReachDistance();
			MovingObjectPosition objectMouseOver = rayTrace(yaw, pitch);

			Vec3 vec3 = entity.getPositionEyes(partialTicks);
			int i = 3;

			if (objectMouseOver != null) {
				d1 = objectMouseOver.hitVec.distanceTo(vec3);
			}

			Vec3 vec31 = getVectorForRotation(pitch, yaw);
			Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
			Entity targetEntity = null;
			Vec3 vec33 = null;
			float f = 1.0F;
			List<Entity> list = mc.theWorld.getEntitiesInAABBexcluding(entity,
					entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0)
							.expand(f, f, f),
					Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>() {
						@Override
						public boolean apply(Entity p_apply_1_) {
							return p_apply_1_.canBeCollidedWith();
						}
					}));
			double d2 = d1;

			for (int j = 0; j < list.size(); ++j) {
				Entity entity1 = list.get(j);
				float f1 = 0.1f;
				AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1,
						f1);
				MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

				if (axisalignedbb.isVecInside(vec3)) {
					if (d2 >= 0.0D) {
						targetEntity = entity1;
						vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
						d2 = 0.0D;
					}
				} else if (movingobjectposition != null) {
					double d3 = vec3.distanceTo(movingobjectposition.hitVec);

					if (d3 < d2 || d2 == 0.0D) {
						boolean flag1 = false;

						if (Reflector.ForgeEntity_canRiderInteract.exists()) {
							flag1 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract);
						}

						if (!flag1 && entity1 == entity.ridingEntity) {
							if (d2 == 0.0D) {
								targetEntity = entity1;
								vec33 = movingobjectposition.hitVec;
							}
						} else {
							targetEntity = entity1;
							vec33 = movingobjectposition.hitVec;
							d2 = d3;
						}
					}
				}
			}

			if (targetEntity != null && (d2 < d1 || objectMouseOver == null)) {
				objectMouseOver = new MovingObjectPosition(targetEntity, vec33);
			}
			return objectMouseOver;
		}
		return null;
	}

	public static MovingObjectPosition rayTrace(Rotation rot) {
		return rayTrace(rot.getYaw(), rot.getPitch());
	}

	public static MovingObjectPosition rayTrace(float yaw, float pitch) {
		double distance = 8.0;
		Vec3 vec3 = mc.getRenderViewEntity().getPositionEyes(1.0f);
		Vec3 vec31 = getVectorForRotation(pitch, yaw);
		Vec3 vec32 = vec3.addVector(vec31.xCoord * distance, vec31.yCoord * distance, vec31.zCoord * distance);
		return mc.theWorld.rayTraceBlocks(vec3, vec32, false, false, true);
	}

	public static MovingObjectPosition rayTrace(Entity entity) {
		double distance = 8.0;
		Vec3 vec3 = entity.getPositionEyes(1.0f);
		Vec3 vec31 = getVectorForRotation(entity.rotationPitch, entity.rotationYaw);
		Vec3 vec32 = vec3.addVector(vec31.xCoord * distance, vec31.yCoord * distance, vec31.zCoord * distance);
		return mc.theWorld.rayTraceBlocks(vec3, vec32, false, false, true);
	}

	private static final Vec3 getVectorForRotation(float pitch, float yaw) {
		float f = MathHelper.cos(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
		float f1 = MathHelper.sin(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
		float f2 = -MathHelper.cos(-pitch * ((float) Math.PI / 180F));
		float f3 = MathHelper.sin(-pitch * ((float) Math.PI / 180F));
		return new Vec3(f1 * f2, f3, f * f2);
	}
}
