package me.imflowow.tritium.client.ui.clickgui.components.values;

import me.imflowow.tritium.client.ui.clickgui.utils.UIComponent;
import tritium.api.module.value.Value;

public class ValueEntity extends UIComponent {
	Value value;
	
	public ValueEntity(Value value) {
		this.value = value;
		this.init(0, 0);
	}

	@Override
	public void init(double positionX, double positionY) {
	}

	@Override
	public void draw(int mouseX, int mouseY, double positionX, double positionY) {
	}

	protected boolean isClickable() {
		return this.getClickGui().getValueList().isClickable();
	}
	
	protected void setLocked(boolean locked) {
		this.getClickGui().getValueList().setLocked(locked);
	}
	public double getHeight()
	{
		return 0.0;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

}
