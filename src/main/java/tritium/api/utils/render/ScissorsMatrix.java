package tritium.api.utils.render;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import tritium.api.utils.render.base.RenderEntity;

public class ScissorsMatrix extends RenderEntity {
	List<Scissor> list = new ArrayList();

	public ScissorsMatrix(double x, double y, double width, double height, double scale) {
		list.add(new Scissor(x, y, width, height, scale));
	}

	public ScissorsMatrix(double x, double y, double width, double height) {
		this(x, y, width, height, 1.0);
	}

	public void push(double x, double y, double width, double height) {
		this.push(x, y, width, height, 1.0);
	}

	public void push(double x, double y, double width, double height, double scale) {
		list.add(new Scissor(x, y, width, height, scale));
		this.doScissor();
	}

	public void pop() {
		if (list.size() > 1) {
			list.remove(list.size() - 1);
		}
		this.doScissor();
	}

	/*
	 * GL11.glEnable(GL11.GL_SCISSOR_TEST); GL11.glDisable(GL11.GL_SCISSOR_TEST);
	 */
	public void doScissor() {
		Scissor last = list.get(list.size() - 1);

		double x = last.getX();
		double y = last.getY();
		double width = last.getWidth();
		double height = last.getHeight();
		double x2 = x + width;
		double y2 = y + height;

		for (Scissor scissor : list) {
			double scissorX = scissor.getX();
			double scissorY = scissor.getY();
			double scissorWidth = scissor.getWidth();
			double scissorHeight = scissor.getHeight();
			double scissorX2 = scissorX + scissorWidth;
			double scissorY2 = scissorY + scissorHeight;

			if (scissorX < x) {
				x = scissorX;
				width -= x - scissorX;
			}

			if (scissorY < y) {
				y = scissorY;
				height -= y - scissorY;
			}

			if (scissorX2 < x2) {
				width -= x2 - scissorX2;
			}

			if (scissorY2 < y2) {
				height -= y2 - scissorY2;
			}
		}

		double scale_ = super.getGuiScaleFactor();

		x = x * scale_;
		y = (super.getScaledHeight() - y) * scale_;
		width = width * scale_ * last.getScale();
		height = height * scale_ * last.getScale();

		GL11.glScissor((int) x, (int) (y - height), (int) width, (int) height);
	}
}
