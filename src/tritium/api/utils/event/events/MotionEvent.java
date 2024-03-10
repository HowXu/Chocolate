package tritium.api.utils.event.events;

import tritium.api.utils.event.api.events.Event;

public class MotionEvent implements Event {
	public double x, y, z;
	public boolean autoMotion;

	public MotionEvent(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	
	public double getX() {
		return x;
	}

	public double getZ() {
		return z;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}
}
