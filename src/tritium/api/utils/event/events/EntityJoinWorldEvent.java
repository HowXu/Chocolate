package tritium.api.utils.event.events;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import tritium.api.utils.event.api.events.callables.EventCancellable;

public class EntityJoinWorldEvent extends EventCancellable {
	private Entity entity;
	private World world;

	public EntityJoinWorldEvent(Entity entity, World world) {
		super();
		this.entity = entity;
		this.world = world;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

}
