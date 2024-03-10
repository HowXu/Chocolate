package me.imflowow.tritium.core.modules;

import me.imflowow.tritium.core.modules.components.customscoreboard.CustomScoreboardEntity;
import tritium.api.module.Module;
import tritium.api.module.value.impl.BooleanValue;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.module.value.utils.Position;

public class CustomScoreboard extends Module {

	public PositionValue position = new PositionValue("CustomScoreboard", new Position(20, 20,true));
	public BooleanValue hidden = new BooleanValue("Hidden", false);
	public BooleanValue clearBackground = new BooleanValue("ClearBackground", false);
	public BooleanValue hideRedNumber = new BooleanValue("HideRedNumber", true);

	public CustomScoreboard() {
		super("CustomScoreboard", "You may be with intellectual disabilities if you even don't know what it is.");
		super.addValues(hidden, clearBackground, hideRedNumber);
		super.addGuiEntities(new CustomScoreboardEntity(position));
	}

}
