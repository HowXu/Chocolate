package me.imflowow.tritium.core.modules;

import tritium.api.module.Module;
import tritium.api.module.value.impl.BooleanValue;

public class OldAnimations extends Module {
	public BooleanValue sneak = new BooleanValue("Sneak", false);
	public BooleanValue blockPosition = new BooleanValue("BlockPosition", true);
	public BooleanValue itemPosition = new BooleanValue("ItemPosition", true);
	public BooleanValue damageBright = new BooleanValue("DamageBright", true);
	public BooleanValue hurtAnimations = new BooleanValue("HurtAnimations", true);
	public BooleanValue bowPositions = new BooleanValue("BowPositions", true);
	public BooleanValue rodPositions = new BooleanValue("RodPositions", true);
	public BooleanValue bowScale = new BooleanValue("BowScale", true);
	public BooleanValue rodScale = new BooleanValue("RodScale", true);

	public BooleanValue debugRender = new BooleanValue("DebugRender", true);

	public OldAnimations() {
		super("OldAnimations", "Make your animation look like older version.");
		super.addValues(sneak, blockPosition, itemPosition, damageBright, hurtAnimations, bowPositions, rodPositions,
				bowScale, rodScale, debugRender);
		super.setEnabled(true);
	}

	@Override
	public void onDisable() {
		this.setEnabled(true);
	}

	// OldAnimations anim =
	// (OldAnimations)Tritium.instance.getModuleManager().getModule(OldAnimations.class);
}
