package tritium.api.utils.event.events;

import net.minecraft.client.multiplayer.WorldClient;
import tritium.api.utils.event.api.events.Event;

public class LoadWorldEvent implements Event {
    private WorldClient worldClient;

    public LoadWorldEvent(WorldClient worldClient) {
        this.worldClient = worldClient;
    }

    public WorldClient getWorldClient() {
        return worldClient;
    }
}