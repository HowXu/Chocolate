package me.imflowow.tritium.utils.events;

import tritium.api.utils.event.api.events.Event;

public class ReachEvent implements Event {
	private double placeRange;
	private double attackRange;

	public ReachEvent(double placeRange, double attackRange) {
		this.placeRange = placeRange;
		this.attackRange = attackRange;
	}

	public double getPlaceRange() {
		return placeRange;
	}

	public void setPlaceRange(double placeRange) {
		this.placeRange = placeRange;
	}

	public double getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(double attackRange) {
		this.attackRange = attackRange;
	}

}
