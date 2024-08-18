package me.imflowow.tritium.core.modules;

import net.minecraft.client.Minecraft;
import tritium.api.module.Module;
import tritium.api.module.value.impl.EnumValue;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.TickEvent;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Perspective extends Module {
	public static boolean perspectiveToggled;
	private static float cameraYaw;
	private static float cameraPitch;
	private static int previousPerspective;
	public static Minecraft mc = Minecraft.getMinecraft();
	static {
		perspectiveToggled = false;
		cameraYaw = 0.0f;
		cameraPitch = 0.0f;
		previousPerspective = 0;
	}
	EnumValue<Mode> click = new EnumValue("Mode", Mode.Hold);

	enum Mode {
		Click, Hold;
	}

	public Perspective() {
		super("Perspective", "You know it is 360° you can be in fov.", false, true);
		super.addValues(click);
	}

	@EventTarget
	public void onTick(TickEvent e) {
		if (mc.thePlayer != null && mc.theWorld != null) {
			if (click.getValue() == Mode.Hold)
				if (!Keyboard.isKeyDown(super.getKeybind())) {
					super.setEnabled(false);
				}
		}

	}

	@Override
	public void onEnable() {

		perspectiveToggled = true;
		cameraYaw = mc.thePlayer.rotationYaw;
		cameraPitch = mc.thePlayer.rotationPitch;
		previousPerspective = mc.gameSettings.thirdPersonView;
		mc.gameSettings.thirdPersonView = 1;

	}

	@Override
	public void onDisable() {
		perspectiveToggled = false;
		mc.gameSettings.thirdPersonView = previousPerspective;
	}

	public static float getCameraYaw() {
		return perspectiveToggled ? cameraYaw : mc.getRenderViewEntity().rotationYaw;
	}

	public static float getCameraPitch() {
		return perspectiveToggled ? cameraPitch : mc.getRenderViewEntity().rotationPitch;
	}

	public static float getCameraPrevYaw() {
		return perspectiveToggled ? cameraYaw : mc.getRenderViewEntity().prevRotationYaw;
	}

	public static float getCameraPrevPitch() {
		return perspectiveToggled ? cameraPitch : mc.getRenderViewEntity().prevRotationPitch;
	}

	public static boolean overrideMouse() {
		if (mc.inGameHasFocus && Display.isActive()) {
			if (!perspectiveToggled) {
				return true;
			}
			mc.mouseHelper.mouseXYChange();
			float f1 = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
			float f2 = f1 * f1 * f1 * 8.0f;
			float f3 = mc.mouseHelper.deltaX * f2;
			float f4 = mc.mouseHelper.deltaY * f2;
			cameraYaw += f3 * 0.15f;
			cameraPitch += f4 * 0.15f;
			if (cameraPitch > 90.0f) {
				cameraPitch = 90.0f;
			}
			if (cameraPitch < -90.0f) {
				cameraPitch = -90.0f;
			}
		}
		return false;
	}
}
