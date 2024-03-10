package tritium.api.utils.event.events;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundManager;
import tritium.api.utils.event.api.events.Event;

public class PlaySoundEvent implements Event
{
    public final String name;
    public final ISound sound;
    public final SoundCategory category;
    public ISound result;
    public final SoundManager manager;

    
    public PlaySoundEvent(SoundManager manager, ISound sound, SoundCategory category)
    {
    	this.manager = manager;
        this.sound = sound;
        this.category = category;
        this.name = sound.getSoundLocation().getResourcePath();
        this.result = sound;
    }
}
