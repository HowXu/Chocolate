package me.imflowow.tritium.utils.events;

import tritium.api.utils.event.api.events.callables.EventCancellable;

public class SlowdownEvent extends EventCancellable {

	private Type type;
	private double speed;

	public SlowdownEvent(final Type type,final double speed) {
		this.type = type;
		this.speed = speed;
	}

	public Type getType() {
		return type;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getSpeed() {
		return this.speed;
	}

	public enum Type {
		Item, Sprinting, SoulSand
	}
}
