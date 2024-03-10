package tritium.api.utils.event.events;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import tritium.api.utils.event.api.events.callables.EventCancellable;


public class GuiScreenMouseInputEvent extends EventCancellable 
{
    public final GuiScreen gui;
    
    public GuiScreenMouseInputEvent(GuiScreen gui)
    {
        this.gui = gui;
    }

}
