package me.imflowow.tritium.core.modules.components.duelinfo;

import java.util.ArrayList;
import java.util.List;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.modules.DuelInfo;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import tritium.api.module.gui.GuiEntity;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.utils.StringUtils;
import tritium.api.utils.animate.Translate;
import tritium.api.utils.event.events.PacketEvent;
import tritium.api.utils.event.events.TickEvent;
import tritium.api.utils.render.Rect;

public class DuelInfoEntity extends GuiEntity {

	public EntityPlayer target;
	public String text;

	public EntityPlayer lastTarget;
	public EntityPlayer attacker;

	Translate pot = new Translate(0, 0);
	Translate atk = new Translate(0, 0);

	Translate anim = new Translate(0, 0);

	int type;

	int attack;
	int potion;

	int mineattack;
	int minepotion;

	int lastAttack;
	int lastPotion;

	int lastmineattack;
	int lastminepotion;

	public DuelInfoEntity(PositionValue position) {
		super(position);
	}

	@Override
	public void init() {
		this.type = 0;

		attack = 0;
		potion = 0;

		mineattack = 0;
		minepotion = 0;

		lastAttack = 0;
		lastPotion = 0;

		lastmineattack = 0;
		lastminepotion = 0;
	}

	@Override
	public void draw(double x, double y) {
		if (target == null) {
			text = "Target not found...";
		}

		new Rect(x, y, 158, 54, this.getModule().backgroundRectColor.getValue().getColor().getRGB(),
				Rect.RenderType.Expand).draw();

		if (this.getModule().animaiton.getValue()) {
			switch (type) {
			case 0:
				if (anim.getX() == 154 && anim.getY() == 0) {
					type = 1;
				}
				anim.interpolate(154, 0, 0.2f);
				break;
			case 1:
				if (anim.getX() == 154 && anim.getY() == 50) {
					type = 2;
				}
				anim.interpolate(154, 50, 0.2f);
				break;
			case 2:
				if (anim.getX() == 0 && anim.getY() == 50) {
					type = 3;
				}
				anim.interpolate(0, 50, 0.2f);
				break;
			case 3:
				if (anim.getX() == 0 && anim.getY() == 0) {
					type = 0;
				}
				anim.interpolate(0, 0, 0.2f);
				break;
			}
			new Rect(x + anim.getX(), y + anim.getY(), 4, 4,
					this.getModule().animationColor.getValue().getColor().getRGB(), Rect.RenderType.Expand).draw();
			new Rect(x + 154 - anim.getX(), y + 50 - anim.getY(), 4, 4,
					this.getModule().animationColor.getValue().getColor().getRGB(), Rect.RenderType.Expand).draw();
		}

		x += 4;
		y += 4;

		new Rect(x, y, 150, 14, this.getModule().backgroundColor.getValue().getColor().getRGB(), Rect.RenderType.Expand)
				.draw();
		new Rect(x, y, 14, 14, this.getModule().rectColor.getValue().getColor().getRGB(), Rect.RenderType.Expand)
				.draw();
		Tritium.instance.getFontManager().logo22.drawString("u", x + 1.5, y + 4.5,
				this.getModule().textColor.getValue().getColor().getRGB());
		StringUtils.drawCenteredStringWithShadow(text, x + 14 + 136 / 2, y + 5, -1, SizeType.Size16);

		atk.interpolate((((float) attack + 1) / (float) (attack + mineattack + 2)) * 124.0f, 0, 0.3f);
		pot.interpolate((((float) minepotion + 1) / (float) (potion + minepotion + 2)) * 124.0f, 0, 0.3f);

		// TODO Attack

		new Rect(x, y + 16, 150, 14, this.getModule().backgroundColor.getValue().getColor().getRGB(),
				Rect.RenderType.Expand).draw();
		new Rect(x, y + 16, 14, 14, this.getModule().rectColor.getValue().getColor().getRGB(), Rect.RenderType.Expand)
				.draw();
		Tritium.instance.getFontManager().logo22.drawString("h", x + 1.5, y + 4.5 + 16,
				this.getModule().textColor.getValue().getColor().getRGB());

		Tritium.instance.getFontManager().arial12.drawString(String.valueOf(this.attack), x + 20, y + 4 + 16,
				this.getModule().textColor.getValue().getColor().getRGB());
		int len = Tritium.instance.getFontManager().arial12.getStringWidth(String.valueOf(this.mineattack));
		Tritium.instance.getFontManager().arial12.drawString(String.valueOf(this.mineattack), x + 144 - len, y + 4 + 16,
				this.getModule().textColor.getValue().getColor().getRGB());
		Tritium.instance.getFontManager().arial12.drawCenteredString(String.valueOf(this.attack - this.mineattack),
				x + 14 + 136 / 2, y + 4 + 16, this.getModule().textColor.getValue().getColor().getRGB());

		new Rect(x + 20, y + 16 + 8, atk.getX(), 4, this.getModule().selfColor.getValue().getColor().getRGB(),
				Rect.RenderType.Expand).draw();
		new Rect(x + 20 + atk.getX(), y + 16 + 8, 124 - atk.getX(), 4,
				this.getModule().targetColor.getValue().getColor().getRGB(), Rect.RenderType.Expand).draw();
		new Rect(x + 20 + 62 - 0.5, y + 16 + 8, 1, 4, this.getModule().backgroundColor.getValue().getColor().getRGB(),
				Rect.RenderType.Expand).draw();

		// TODO Potion

		new Rect(x, y + 32, 150, 14, this.getModule().backgroundColor.getValue().getColor().getRGB(),
				Rect.RenderType.Expand).draw();
		new Rect(x, y + 32, 14, 14, this.getModule().rectColor.getValue().getColor().getRGB(), Rect.RenderType.Expand)
				.draw();

		Tritium.instance.getFontManager().logo22.drawString("i", x + 1.5, y + 4.5 + 32,
				this.getModule().textColor.getValue().getColor().getRGB());
		Tritium.instance.getFontManager().arial12.drawString(String.valueOf(this.minepotion), x + 20, y + 4 + 32,
				this.getModule().textColor.getValue().getColor().getRGB());
		len = Tritium.instance.getFontManager().arial12.getStringWidth(String.valueOf(this.potion));
		Tritium.instance.getFontManager().arial12.drawString(String.valueOf(this.potion), x + 144 - len, y + 4 + 32,
				this.getModule().textColor.getValue().getColor().getRGB());
		Tritium.instance.getFontManager().arial12.drawCenteredString(String.valueOf(this.minepotion - this.potion),
				x + 14 + 136 / 2, y + 4 + 32, this.getModule().textColor.getValue().getColor().getRGB());

		new Rect(x + 20, y + 32 + 8, pot.getX(), 4, this.getModule().targetColor.getValue().getColor().getRGB(),
				Rect.RenderType.Expand).draw();
		new Rect(x + 20 + pot.getX(), y + 32 + 8, 124 - pot.getX(), 4,
				this.getModule().selfColor.getValue().getColor().getRGB(), Rect.RenderType.Expand).draw();
		new Rect(x + 20 + 62 - 0.5, y + 32 + 8, 1, 4, this.getModule().backgroundColor.getValue().getColor().getRGB(),
				Rect.RenderType.Expand).draw();

//		Tritium.instance.getFontManager().arial16.drawStringWithShadow("Attack:" + attack, x, y + 8, -1);
//		Tritium.instance.getFontManager().arial16.drawStringWithShadow("Potion:" + potion, x, y + 16, -1);
//		Tritium.instance.getFontManager().arial16.drawStringWithShadow("MineAttack:" + mineattack, x, y + 24, -1);
//		Tritium.instance.getFontManager().arial16.drawStringWithShadow("MinePotion:" + minepotion, x, y + 32, -1);

	}

	@Override
	public int getHeight() {
		return 46 + 8;
	}

	@Override
	public int getWidth() {
		return 150 + 8;
	}

	public void onPacket(PacketEvent event) {
		Packet p = event.getPacket();
		if (p instanceof S0EPacketSpawnObject) {
			S0EPacketSpawnObject packet = (S0EPacketSpawnObject) p;
			if (packet.getType() == 73) {
				double x = (double) packet.getX() / 32.0D;
				double y = (double) packet.getY() / 32.0D;
				double z = (double) packet.getZ() / 32.0D;
				double mine = mc.thePlayer.getDistanceSq(x, y, z);
				double attacker = target.getDistanceSq(x, y, z);
				if (mine < attacker) {
					this.lastPotion++;
				} else {
					this.lastminepotion++;
				}

			}

		}
		if (p instanceof S19PacketEntityStatus) {
			S19PacketEntityStatus packet = (S19PacketEntityStatus) p;

			if (packet.getOpCode() == 2) {
				Entity entity = packet.getEntity(mc.theWorld);
				if (entity == mc.thePlayer) {
					this.lastmineattack++;
//					MovingObjectPosition mop = RayTraceUtil.getRotationOver(target);
//					if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
//						Vec3 vec3 = target.getPositionEyes(1.0f);
//						double range = mop.hitVec.distanceTo(vec3);
//						System.out.println(range);
//					}
				} else {
					if (entity instanceof EntityPlayer) {
						this.attacker = (EntityPlayer) entity;
					}
				}
				if (entity == target) {
					this.lastAttack++;
				}
			}

		}
	}

	public void onTick(TickEvent event) {
		List<EntityPlayer> list = this.getEntity(100);
		if (list.size() == 1) {
			for (EntityPlayer entity : list) {
				target = entity;
				text = target.getName();
				if (target == attacker) {
					attack = lastAttack;
					potion = lastPotion;
					mineattack = lastmineattack;
					minepotion = lastminepotion;
				}
				if (lastTarget != target) {
					attack = 0;
					potion = 0;

					mineattack = 0;
					minepotion = 0;

					lastAttack = 0;
					lastPotion = 0;

					lastmineattack = 0;
					lastminepotion = 0;
				}

				lastTarget = target;
			}
		} else {
			lastTarget = target;
			target = null;
			attack = 0;
			potion = 0;

			mineattack = 0;
			minepotion = 0;
		}
	}

	private List<EntityPlayer> getEntity(double distance) {
		List<EntityPlayer> playerEntities = new ArrayList();
		for (EntityPlayer player : this.mc.theWorld.playerEntities) {
			if (player != this.mc.thePlayer) {
				if (player.getDistanceToEntity(this.mc.thePlayer) <= distance) {
					playerEntities.add(player);
				}
			}
		}
		return playerEntities;
	}

	public DuelInfo getModule() {
		return (DuelInfo) Tritium.instance.getModuleManager().getModule(DuelInfo.class);
	}

}
