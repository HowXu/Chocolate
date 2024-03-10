package tritium.api.utils.event.events;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import tritium.api.utils.event.api.events.Event;

public class RenderPlayerEvent implements Event {
	public EntityPlayer player;
	public RenderPlayer renderer;
	public float partialRenderTick;
	public double x;
	public double y;
	public double z;

	public RenderPlayerEvent(EntityPlayer player, RenderPlayer renderer, float partialRenderTick, double x, double y,
			double z) {
		this.player = player;
		this.renderer = renderer;
		this.partialRenderTick = partialRenderTick;
		this.x = x;
		this.y = y;
		this.z = z;
	}

}