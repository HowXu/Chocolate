package tritium.api.utils.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import tritium.api.utils.render.base.RenderEntity;

public class Circle extends RenderEntity {
	double x;
	double y;
	double radius;
	int color;

	public Circle(double x, double y, double radius, int color) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = color;
	}

	public void draw() {
		int sections = 50;
		double dAngle = 2 * Math.PI / sections;

		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);

		float f3 = (float) (color >> 24 & 255) / 255.0F;
		float f = (float) (color >> 16 & 255) / 255.0F;
		float f1 = (float) (color >> 8 & 255) / 255.0F;
		float f2 = (float) (color & 255) / 255.0F;

		for (int i = 0; i < sections; i++) {
			GlStateManager.color(f, f1, f2, f3);
			GL11.glVertex2d(x + (radius * Math.sin((i * dAngle))), y + (radius * Math.cos((i * dAngle))));
		}

		GlStateManager.color(0, 0, 0);

		GL11.glEnd();

		GL11.glPopAttrib();
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

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
}
