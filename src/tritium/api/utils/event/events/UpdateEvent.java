package tritium.api.utils.event.events;

import tritium.api.utils.event.api.events.callables.EventCancellable;

public class UpdateEvent extends EventCancellable {
	private boolean onGround;
	private float yaw;
	private float pitch;
	private double y;
	private boolean pre;

	public UpdateEvent(final float yaw, final float pitch, final double y, final boolean onGround, final boolean pre) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.y = y;
		this.onGround = onGround;
		this.pre = pre;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public double getY() {
		return y;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public boolean isPre() {
		return pre;
	}
}
