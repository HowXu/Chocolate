
package me.imflowow.tritium.core.globals;

import java.util.Map;

import org.lwjgl.opengl.GL11;

import me.imflowow.tritium.core.modules.utils.motionblur.MotionBlurResourceManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import tritium.api.module.GlobalModule;
import tritium.api.module.value.impl.EnumValue;
import tritium.api.module.value.impl.NumberValue;
import tritium.api.utils.Printer;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.DisplayFrameEvent;
import tritium.api.utils.event.events.TickEvent;

public class MotionBlur extends GlobalModule {
	public EnumValue<MotionBlurType> type = new EnumValue("Type", MotionBlurType.GL);
	public NumberValue<Double> multiplier = new NumberValue<Double>("FrameMultiplier", 0.5, 0.05, 0.99, 0.01);
	double lastvalue = 0;

	public MotionBlur() {
		super("MotionBlur", "Make your screen look high in frames.");
		super.addValues(type, multiplier);
	}

	@EventTarget
	public void onClientTick(DisplayFrameEvent event) {
		if (type.getValue() != MotionBlurType.GL)
			return;

		final float n = multiplier.getValue().floatValue();
		GL11.glAccum(259, n);
		GL11.glAccum(256, 1.0f - n);
		GL11.glAccum(258, 1.0f);
	}

	@Override
	public void onDisable() {
		if (type.getValue() != MotionBlurType.Shader)
			return;
		mc.entityRenderer.stopUseShader();
	}

	@Override
	public void onEnable() {
		if (type.getValue() != MotionBlurType.Shader)
			return;
		if (domainResourceManagers == null) {
			domainResourceManagers = ((SimpleReloadableResourceManager) mc.getResourceManager()).domainResourceManagers;
		}

		if (!domainResourceManagers.containsKey("motionblur")) {
			domainResourceManagers.put("motionblur", new MotionBlurResourceManager());
		}

		if (this.isFastRenderEnabled()) {
			Printer.sendMessage("Please don't open <MotionBlur> with <FastRender>");
			super.setEnabled(false);
			return;
		}
		lastvalue = multiplier.getValue().intValue();
		this.applyShader();

	}

	private Map domainResourceManagers;

	public boolean isFastRenderEnabled() {
		return mc.gameSettings.ofFastRender;
	}

	public void applyShader() {
		if (type.getValue() != MotionBlurType.Shader)
			return;
		mc.entityRenderer.loadShader(new ResourceLocation("motionblur", "motionblur"));
	}

	@EventTarget
	public void onClientTick(TickEvent event) {
		if (type.getValue() != MotionBlurType.Shader)
			return;
		if (mc.thePlayer != null && mc.theWorld != null) {
			if ((!Minecraft.getMinecraft().entityRenderer.isShaderActive()
					|| (this.lastvalue != this.multiplier.getValue())) && (this.mc.theWorld != null)
					&& !this.isFastRenderEnabled()) {
				this.lastvalue = this.multiplier.getValue();
				this.applyShader();
			}
			if (domainResourceManagers == null) {
				domainResourceManagers = ((SimpleReloadableResourceManager) mc
						.getResourceManager()).domainResourceManagers;
			}

			if (!domainResourceManagers.containsKey("motionblur")) {
				domainResourceManagers.put("motionblur", new MotionBlurResourceManager());
			}
			if (this.isFastRenderEnabled()) {
				Printer.sendMessage("Please don't open <MotionBlur> with <FastRender>");
				super.setEnabled(false);
				return;
			}
		}

	}

	public enum MotionBlurType {
		GL, Shader;
	}
}
