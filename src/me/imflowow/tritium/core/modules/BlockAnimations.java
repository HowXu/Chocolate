package me.imflowow.tritium.core.modules;

import net.minecraft.client.renderer.GlStateManager;
import tritium.api.module.Module;
import tritium.api.module.value.impl.EnumValue;
import tritium.api.module.value.impl.NumberValue;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.BlockAnimationsEvent;

public class BlockAnimations extends Module {
	public enum AnimationType {
		Normal, Down, Basic, Custom;
	}

	public NumberValue<Integer> SwingSpeed = new NumberValue("SwingSpeed", 6, 0, 32, 1);

	// CustomAnimations

	public NumberValue<Double> AnimRotX = new NumberValue("AnimRotX", 0.0, -360.0, 360.0, -0.1);
	public NumberValue<Double> AnimRotY = new NumberValue("AnimRotY", 0.0, -360.0, 360.0, -0.1);
	public NumberValue<Double> AnimRotZ = new NumberValue("AnimRotZ", 0.0, -360.0, 360.0, -0.1);

	public NumberValue<Double> RotX = new NumberValue("RotX", 0.0, -360.0, 360.0, -0.1);
	public NumberValue<Double> RotY = new NumberValue("RotY", 0.0, -360.0, 360.0, -0.1);
	public NumberValue<Double> RotZ = new NumberValue("RotZ", 0.0, -360.0, 360.0, -0.1);

	public NumberValue<Double> X = new NumberValue("X", 0.0, -1.0, 1.0, -0.01);
	public NumberValue<Double> Y = new NumberValue("Y", 0.0, -1.0, 1.0, -0.01);
	public NumberValue<Double> Z = new NumberValue("Z", 0.0, -1.0, 1.0, -0.01);

	public NumberValue<Double> AnimX = new NumberValue("AnimX", 0.0, -1.0, 1.0, -0.01);
	public NumberValue<Double> AnimY = new NumberValue("AnimY", 0.0, -1.0, 1.0, -0.01);
	public NumberValue<Double> AnimZ = new NumberValue("AnimZ", 0.0, -1.0, 1.0, -0.01);

	public NumberValue<Double> HoldingX = new NumberValue("HoldingX", 0.0, -5.0, 5.0, -0.01);
	public NumberValue<Double> HoldingY = new NumberValue("HoldingY", 0.0, -5.0, 5.0, -0.01);
	public NumberValue<Double> HoldingZ = new NumberValue("HoldingZ", 0.0, -5.0, 5.0, -0.01);

	public EnumValue<AnimationType> mode = new EnumValue("Mode", AnimationType.Normal);

	public BlockAnimations() {
		super("BlockAnimations", "Change your blocking animations");
		super.addValues(this.mode, this.SwingSpeed, this.AnimRotX, this.AnimRotY, this.AnimRotZ, this.RotX, this.RotY,
				this.RotZ, this.X, this.Y, this.Z, this.AnimX, this.AnimY, this.AnimZ, this.HoldingX, this.HoldingY,
				this.HoldingZ);
	}

	@EventTarget
	public void onAnimations(final BlockAnimationsEvent e) {
		if (e.isPre()) {
			final float f = e.getSwingProgress();
			switch (this.mode.getValue()) {
			case Normal:
				GlStateManager.translate(0.0F, 0.1F, 0.0F);
				GlStateManager.rotate(f * -50.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(f * 5.0F, 0.0F, 0.0F, 1.0F);
				break;
			case Down:
				GlStateManager.rotate(f * -80.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(f * -5.0F, 0.0F, 0.0F, 1.0F);
				break;
			case Basic:
				GlStateManager.translate(0, 0.3, 0);
				GlStateManager.rotate(f * -80.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(f * -5.0F, 0.0F, 0.0F, 1.0F);
				break;
			default:
				break;

			}
			GlStateManager.translate(this.Z.getValue(), this.Y.getValue(), this.X.getValue());
			// X,Y,Z
			GlStateManager.translate(f * this.AnimZ.getValue(), f * this.AnimY.getValue(), f * this.AnimX.getValue());
			// XYZ,Animations
			GlStateManager.rotate(this.RotX.getValue().floatValue(), 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(this.RotY.getValue().floatValue(), 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(this.RotZ.getValue().floatValue(), 0.0F, 0.0F, 1.0F);
			// RotateXYZ
			GlStateManager.rotate(f * this.AnimRotX.getValue().floatValue(), 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(f * this.AnimRotY.getValue().floatValue(), 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(f * this.AnimRotZ.getValue().floatValue(), 0.0F, 0.0F, 1.0F);
			// RotateXYZ,Animations
		} else {
			e.getSwingProgress();
			switch (this.mode.getValue()) {
			default:
				e.setSwingProgress(e.getSwingProgress() / 2);
				break;
			}
		}

	}

}
