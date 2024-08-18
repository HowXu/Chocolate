package me.imflowow.tritium.core.modules;

import net.minecraft.util.EnumParticleTypes;
import tritium.api.module.Module;
import tritium.api.module.value.impl.BooleanValue;
import tritium.api.module.value.impl.NumberValue;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.AttackEvent;

public class MoreParticles extends Module {
	public NumberValue<Integer> crackSize = new NumberValue("CrackSize", 2, 0, 10, 1);
	public BooleanValue crit = new BooleanValue("CritParticle", true);
	public BooleanValue normal = new BooleanValue("NormalParticle", true);

	public MoreParticles() {
		super("MoreParticles", "When you attack a player,there will be more partcles of attacking.");
		super.addValues(this.crackSize, this.crit, this.normal);
	}

	@EventTarget
	public void onAttack(AttackEvent e) {

		for (int index = 0; index < this.crackSize.getValue().intValue(); ++index) {
			if (this.crit.getValue()) {
				this.mc.effectRenderer.emitParticleAtEntity(e.getEntity(), EnumParticleTypes.CRIT);
			}
			if (this.normal.getValue()) {
				this.mc.effectRenderer.emitParticleAtEntity(e.getEntity(), EnumParticleTypes.CRIT_MAGIC);
			}
		}
	}
}
