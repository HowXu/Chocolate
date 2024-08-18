package tritium.api.module.value.impl;

import tritium.api.module.Module;
import tritium.api.module.gui.GuiEntity;
import tritium.api.module.value.Value;
import tritium.api.module.value.utils.Position;
import tritium.api.module.value.utils.Position.Direction;

public class PositionValue extends Value<Position> {

	public PositionValue(String label, Position value) {
		super(label, value);
	}

	@Override
	public void setValue(String value) {
		String[] split = value.split(":");
		Direction h = Direction.Left;
		if (split[2].equalsIgnoreCase("left")) {
			h = Direction.Left;
		} else if (split[2].equalsIgnoreCase("right")) {
			h = Direction.Right;
		} else if (split[2].equalsIgnoreCase("middle")) {
			h = Direction.Middle;
		}

		Direction v = Direction.Left;
		if (split[3].equalsIgnoreCase("top")) {
			v = Direction.Top;
		} else if (split[3].equalsIgnoreCase("bottom")) {
			v = Direction.Bottom;
		} else if (split[3].equalsIgnoreCase("middle")) {
			v = Direction.Middle;
		}
		this.value.setValue(Integer.parseInt(split[0]), Integer.parseInt(split[1]), h, v,Double.parseDouble(split[4]));
	}

	@Override
	public void init(Module module) {
	}

}
