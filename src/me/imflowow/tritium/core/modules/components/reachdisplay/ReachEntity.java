package me.imflowow.tritium.core.modules.components.reachdisplay;

import java.awt.Color;
import java.text.DecimalFormat;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.modules.ReachDisplay;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import tritium.api.module.gui.GuiEntity;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.utils.StringUtils;
import tritium.api.utils.event.events.AttackEvent;
import tritium.api.utils.render.Rect;

public class ReachEntity extends GuiEntity {
	private long lastAttack;
	private String rangeText;

	public ReachEntity(PositionValue position) {
		super(position);
	}

	@Override
	public void init() {
		lastAttack = 0;
		rangeText = "";
	}

	@Override
	public void draw(double x, double y) {
		if (System.currentTimeMillis() - lastAttack > 2000L) {
			rangeText = "Reach Display";
		}
		int len = StringUtils.getWidth(rangeText, SizeType.Size16) + 3;
		new Rect(x, y, len, 13, this.getModule().backgroundColor.getValue().getColor().getRGB(), Rect.RenderType.Expand).draw();
		StringUtils.drawStringWithShadow(rangeText, x + 1.5, y + 5, this.getModule().textColor.getValue().getColor().getRGB(), SizeType.Size16);
	}

	@Override
	public int getHeight() {
		return 13;
	}

	@Override
	public int getWidth() {
		return StringUtils.getWidth(rangeText, SizeType.Size16) + 3;
	}

	public ReachDisplay getModule() {
		return (ReachDisplay) Tritium.instance.getModuleManager().getModule(ReachDisplay.class);
	}

	public void onAttack(AttackEvent e) {

		if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY
				&& mc.objectMouseOver.entityHit.getEntityId() == e.getEntity().getEntityId()) {
			Vec3 vec3 = mc.getRenderViewEntity().getPositionEyes(1.0f);
			double range = mc.objectMouseOver.hitVec.distanceTo(vec3);
			rangeText = new DecimalFormat("#.##").format(range) + ("Blocks");

		} else {
			rangeText = "Impossible Attack";
			e.setCancelled(true);
		}
		lastAttack = System.currentTimeMillis();
	}

	private int resetAlpha(Color color, int alpha) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha).getRGB();
	}
}
