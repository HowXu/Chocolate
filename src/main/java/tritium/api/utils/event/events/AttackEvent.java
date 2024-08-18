package tritium.api.utils.event.events;

import net.minecraft.entity.Entity;
import tritium.api.utils.event.api.events.callables.EventCancellable;

public final class AttackEvent extends EventCancellable {
	private final Entity entity;

	public AttackEvent(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return this.entity;
	}
}
