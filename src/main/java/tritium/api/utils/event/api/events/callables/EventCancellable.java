package tritium.api.utils.event.api.events.callables;

import tritium.api.utils.event.api.events.Cancellable;
import tritium.api.utils.event.api.events.Event;

/**
 * Abstract example implementation of the Cancellable interface.
 *
 * @author DarkMagician6
 * @since August 27, 2013
 */
public abstract class EventCancellable implements Event, Cancellable {

    private boolean cancelled;

    protected EventCancellable() {
    }

    /**
     * @see me.imflowow.peaceclient.eventInput.events.Cancellable.isCancelled
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * @see me.imflowow.peaceclient.eventInput.events.Cancellable.setCancelled
     */
    @Override
    public void setCancelled(boolean state) {
        cancelled = state;
    }

}
