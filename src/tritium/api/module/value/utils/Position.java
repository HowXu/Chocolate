package tritium.api.module.value.utils;

import tritium.api.utils.render.base.RenderEntity;

public class Position extends RenderEntity {
	private int x;
	private int y;
	private Direction h;
	private Direction v;
	private boolean offset;

	private double scaled;

	public Position(int x, int y, Direction h, Direction v, double scaled, boolean offset) {
		this.x = x;
		this.y = y;
		this.h = h;
		this.v = v;
		this.scaled = scaled;
		this.offset = offset;
	}

	public Position(int x, int y, Direction h, Direction v) {
		this(x, y, h, v, 1.0, false);
	}

	public Position(int x, int y, boolean offset) {
		this(x, y, Direction.Left, Direction.Top, 1.0, offset);
	}

	public Position(int x, int y) {
		this(x, y, Direction.Left, Direction.Top, 1.0, false);
	}
	
	public void setValue(int x, int y, Direction h, Direction v, double scaled)
	{
		this.x = x;
		this.y = y;
		this.h = h;
		this.v = v;
		this.scaled = scaled;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append(x).append(":").append(y).append(":").append(h.toString()).append(":").append(v.toString())
				.append(":").append(scaled).toString();
	}

	public enum Direction {
		Left, Right, Top, Bottom, Middle;
	}

	public int getX(int width) {
		if(!offset)
			return this.getX();
		switch (this.getHorizontalDirection()) {
		case Left:
			return x;
		case Right:
			return (int) this.getScaledWidth() - x - width;
		case Middle:
			if (this.getScaledWidth() / 2 + x < this.getScaledWidth() / 2) {
				return (int) this.getScaledWidth() / 2 + x - width;
			} else {
				return (int) this.getScaledWidth() / 2 + x;
			}
		default:
			return x;
		}
	}

	public int getX() {
		switch (this.getHorizontalDirection()) {
		case Left:
			return x;
		case Right:
			return (int) this.getScaledWidth() - x;
		case Middle:
			if (this.getScaledWidth() / 2 + x < this.getScaledWidth() / 2) {
				return (int) this.getScaledWidth() / 2 + x;
			} else {
				return (int) this.getScaledWidth() / 2 + x;
			}
		default:
			return x;
		}
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY(int height) {
		if(!offset)
			return this.getY();
		switch (this.getVerticalDirection()) {
		case Top:
			return y;
		case Bottom:
			return (int) this.getScaledHeight() - y - height;
		case Middle:
			if (this.getScaledHeight() / 2 + y < this.getScaledHeight() / 2) {
				return (int) this.getScaledHeight() / 2 + y - height;
			} else {
				return (int) this.getScaledHeight() / 2 + y;
			}
		default:
			return y;
		}
	}

	public int getY() {
		switch (this.getVerticalDirection()) {
		case Top:
			return y;
		case Bottom:
			return (int) this.getScaledHeight() - y;
		case Middle:
			if (this.getScaledHeight() / 2 + y < this.getScaledHeight() / 2) {
				return (int) this.getScaledHeight() / 2 + y;
			} else {
				return (int) this.getScaledHeight() / 2 + y;
			}
		default:
			return y;
		}
	}

	public void setY(int y) {
		this.y = y;
	}

	public Direction getHorizontalDirection() {
		return h;
	}

	public void setHorizontalDirection(Direction h) {
		this.h = h;
	}

	public Direction getVerticalDirection() {
		return v;
	}

	public void setVerticalDirection(Direction v) {
		this.v = v;
	}

	public double getScaled() {
		return scaled;
	}

	public void setScaled(double scaled) {
		this.scaled = scaled;
	}

}
