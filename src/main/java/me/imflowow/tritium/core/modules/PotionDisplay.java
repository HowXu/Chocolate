package me.imflowow.tritium.core.modules;

import me.imflowow.tritium.core.modules.components.potiondisplay.PotionEntity;
import tritium.api.module.Module;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.module.value.utils.Position;
import tritium.api.module.value.utils.Position.Direction;

public class PotionDisplay extends Module {
	public PositionValue position = new PositionValue("PotionDisplay", new Position(10, 10,true));

	public PotionDisplay() {
		super("PotionDisplay", "Display the potions effect you have on you screen.");
		super.addGuiEntities(new PotionEntity(this.position));
	}

}
