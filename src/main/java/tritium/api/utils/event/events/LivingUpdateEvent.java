package tritium.api.utils.event.events;

import net.minecraft.entity.EntityLivingBase;
import tritium.api.utils.event.api.events.Event;

public class LivingUpdateEvent implements Event{
    public EntityLivingBase entity;

    public LivingUpdateEvent(EntityLivingBase targetEntity) {
        this.entity = targetEntity;
    }

    public EntityLivingBase getEntity() {
        return entity;
    }
}
