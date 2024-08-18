package tritium.api.utils.render;

import com.google.gson.JsonSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class Blur {

	private static final Minecraft mc = Minecraft.getMinecraft();
	private ResourceLocation resourceLocation;
	private ShaderGroup shaderGroup;
	private Framebuffer framebuffer;

	private int lastFactor;
	private int lastWidth;
	private int lastHeight;
	double x;
	double y;
	double areaWidth;
	double areaHeight;
	int blurStrength;

	public Blur(double x, double y, double areaWidth, double areaHeight, int blurStrength) {
		this.x = x;
		this.y = y;
		this.areaWidth = areaWidth;
		this.areaHeight = areaHeight;
		this.blurStrength = blurStrength;

		this.resourceLocation = new ResourceLocation("tritium/shader/blur.json");
	}

	public final void init() {
		try {
			this.shaderGroup = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(),
					resourceLocation);
			this.shaderGroup.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
			this.framebuffer = shaderGroup.mainFramebuffer;

		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
	}

	private void setBlurStrength(int strength) {
		this.shaderGroup.getShaders().get(0).getShaderManager().getShaderUniform("Radius").set(strength);
		this.shaderGroup.getShaders().get(1).getShaderManager().getShaderUniform("Radius").set(strength);
		this.shaderGroup.getShaders().get(2).getShaderManager().getShaderUniform("Radius").set(strength);
		this.shaderGroup.getShaders().get(3).getShaderManager().getShaderUniform("Radius").set(strength);

	}

	public void draw() {
		final ScaledResolution scaledResolution = new ScaledResolution(mc);

		final int scaleFactor = scaledResolution.getScaleFactor();
		final int width = scaledResolution.getScaledWidth();
		final int height = scaledResolution.getScaledHeight();

		if (sizeHasChanged(scaleFactor, width, height) || framebuffer == null || shaderGroup == null) {
			init();
		}

		this.lastFactor = scaleFactor;
		this.lastWidth = width;
		this.lastHeight = height;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		this.makeScissorBox((int) x, (int) y + 1, (int) x + (int) areaWidth, (int) y + (int) areaHeight - 1);

		framebuffer.bindFramebuffer(true);
		shaderGroup.loadShaderGroup(mc.timer.renderPartialTicks);
		this.setBlurStrength(blurStrength);
		mc.getFramebuffer().bindFramebuffer(false);

		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glPopMatrix();
	}

	private boolean sizeHasChanged(int scaleFactor, int width, int height) {
		return (lastFactor != scaleFactor || lastWidth != width || lastHeight != height);
	}

	public void makeScissorBox(final int x, final int y, final int x2, final int y2) {
		final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		final int factor = scaledResolution.getScaleFactor();
		GL11.glScissor((int) (x * factor), (int) ((scaledResolution.getScaledHeight() - y2) * factor),
				(int) ((x2 - x) * factor), (int) ((y2 - y) * factor));
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getAreaWidth() {
		return areaWidth;
	}

	public void setAreaWidth(double areaWidth) {
		this.areaWidth = areaWidth;
	}

	public double getAreaHeight() {
		return areaHeight;
	}

	public void setAreaHeight(double areaHeight) {
		this.areaHeight = areaHeight;
	}
}
