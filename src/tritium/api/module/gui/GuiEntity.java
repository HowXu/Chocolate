package tritium.api.module.gui;

import java.awt.Color;
import java.text.DecimalFormat;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.module.value.utils.Position;
import tritium.api.module.value.utils.Position.Direction;
import tritium.api.utils.StringUtils;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.base.RenderEntity;
import tritium.api.utils.render.clickable.ClickEntity;
import tritium.api.utils.render.utils.MouseBounds.CallType;

public abstract class GuiEntity extends RenderEntity {

	private int mouseX, mouseY;
	private int lastMouseX, lastMouseY;
	private boolean moveable, lock;

	private PositionValue position;
	private ClickEntity clickarea;

	public GuiEntity(PositionValue position) {
		this.position = position;
		this.preInit();
	}

	public final void preInit() {
		this.moveable = false;
		this.clickarea = new ClickEntity(position.getValue().getX(this.getWidth()), position.getValue().getY(this.getHeight()), this.getWidth(),
				this.getHeight(), CallType.Expand, () -> {
					moveable = true;
				}, () -> {
				}, () -> {
				}, () -> {
				}, () -> {
				});
		this.init();
	}

	public final void preDraw() {
		double scaled = this.position.getValue().getScaled();

		GL11.glScaled(scaled, scaled, 1);
		this.draw((position.getValue().getX(this.getWidth()) / scaled), (position.getValue().getY(this.getHeight()) / scaled));
		//从代码实现上这个是上层普适性的draw定位 也就是说不能为每个模块设置默认初始位置
		GL11.glScaled(1 / scaled, 1 / scaled, 1);
	}

	public final boolean onEdit(int mouseX, int mouseY, boolean lock) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.lock = lock;

		if (!Mouse.isButtonDown(0))
			this.moveable = false;
		double scaled = this.position.getValue().getScaled();
		this.clickarea.setX(position.getValue().getX(this.getWidth()));
		this.clickarea.setX1(this.getWidth() * scaled);
		this.clickarea.setY(position.getValue().getY(this.getHeight()));
		this.clickarea.setY1(this.getHeight() * scaled);
		this.clickarea.tick();

		if (this.clickarea.isInArea()) {
			double wheel = Mouse.getDWheel() / 10.0;
			if (wheel > 0) {
					this.position.getValue().setScaled(this.position.getValue().getScaled() + 0.1);
			} else if (wheel < 0) {
				if(this.position.getValue().getScaled() > 0.2)
				{
					this.position.getValue().setScaled(this.position.getValue().getScaled() - 0.1);
				}
			}
		}

		if (moveable && !lock) {
			int x = this.position.getValue().getX();
			int y = this.position.getValue().getY();

			int w = this.lastMouseX - x;
			int h = this.lastMouseY - y;

			int x2 = mouseX - w;
			int y2 = mouseY - h;

			int width = (int) this.getScaledWidth();
			int height = (int) this.getScaledHeight();

			if ((width / 4.0 < mouseX) && (width / 4.0 * 3.0 > mouseX)) {
				this.position.getValue().setHorizontalDirection(Direction.Middle);
				this.position.getValue().setX(x2 - width / 2);
			} else if (width / 4.0 * 3.0 < mouseX) {
				this.position.getValue().setHorizontalDirection(Direction.Right);
				this.position.getValue().setX(width - x2);
			} else {
				this.position.getValue().setHorizontalDirection(Direction.Left);
				this.position.getValue().setX(x2);
			}

			if ((height / 4.0 < mouseY) && (height / 4.0 * 3.0 > mouseY)) {
				this.position.getValue().setVerticalDirection(Direction.Middle);
				this.position.getValue().setY(y2 - height / 2);
			} else if (height / 4.0 * 3.0 < mouseY) {
				this.position.getValue().setVerticalDirection(Direction.Bottom);
				this.position.getValue().setY(height - y2);
			} else {
				this.position.getValue().setVerticalDirection(Direction.Top);
				this.position.getValue().setY(y2);
			}
		}

		this.drawShadow();
		this.preDraw();

		this.lastMouseX = mouseX;
		this.lastMouseY = mouseY;
		return this.moveable || lock;
	}

	public abstract void init();

	public abstract void draw(double x, double y);

	public abstract int getHeight();

	public abstract int getWidth();

	private void drawShadow() {
		double scaled = this.position.getValue().getScaled();
		double x = (position.getValue().getX(this.getWidth()) - 2);
		double y = (position.getValue().getY(this.getHeight()) - 2);
		double width = (this.getWidth()) * scaled;
		double height = (this.getHeight()) * scaled;
		new Rect(x, y, width + 4, height + 4, new Color(48, 48, 48, 120).getRGB(), Rect.RenderType.Expand).draw();
		this.drawOutsideRect(x, y, width + 4, height + 4, 1, new Color(127, 127, 127, 120).getRGB());
		StringUtils.drawStringWithShadow(this.position.getLabel(), x - 1, y + height + 10, -1, SizeType.Size16);
	}

	private void drawOutsideRect(double x, double y, double x2, double y2, double width, int color) {
		this.drawOutsideRect2(x, y, x + x2, y + y2, width, color);
	}

	private void drawOutsideRect2(double x, double y, double x2, double y2, double width, int color) {
		if (x > x2) {
			double i = x;
			x = x2;
			x2 = i;
		}

		if (y > y2) {
			double j = y;
			y = y2;
			y2 = j;
		}

		new Rect(x, y - width, x - width, y2, color, Rect.RenderType.Position).draw();
		new Rect(x, y, x2 + width, y - width, color, Rect.RenderType.Position).draw();
		new Rect(x2, y, x2 + width, y2 + width, color, Rect.RenderType.Position).draw();
		new Rect(x - width, y2, x2, y2 + width, color, Rect.RenderType.Position).draw();
	}

	public PositionValue getPosition() {
		return position;
	}

	public void setPosition(PositionValue position) {
		this.position = position;
	}
}
