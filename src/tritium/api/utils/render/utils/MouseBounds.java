package tritium.api.utils.render.utils;

public class MouseBounds {
	double mouseX;
	double mouseY;
	double x;
	double y;
	double x1;
	double y1;
	CallType type;

	public MouseBounds(double mouseX, double mouseY, double x, double y, double x1, double y1, CallType type) {
		super();
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
		this.type = type;
	}

	public boolean isWhthinBounds() {
		switch (type) {
		case Expand:
			return isWhthinBounds(x + x1, y + y1);
		case Position:
			return isWhthinBounds(x1, y1);
		default:
			return false;
		}
	}

	private boolean isWhthinBounds(double x1, double y1) {
		if (x > x1) {
			double i = x;
			x = x1;
			x1 = i;
		}

		if (y > y1) {
			double j = y;
			y = y1;
			y1 = j;
		}
		return (mouseX >= x && mouseX <= x1) && (mouseY >= y && mouseY <= y1);
	}

	public enum CallType {
		Expand, Position;
	}

	public double getMouseX() {
		return mouseX;
	}

	public void setMouseX(double mouseX) {
		this.mouseX = mouseX;
	}

	public double getMouseY() {
		return mouseY;
	}

	public void setMouseY(double mouseY) {
		this.mouseY = mouseY;
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

	public double getX1() {
		return x1;
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(double y1) {
		this.y1 = y1;
	}

	public CallType getType() {
		return type;
	}

	public void setType(CallType type) {
		this.type = type;
	}
}
