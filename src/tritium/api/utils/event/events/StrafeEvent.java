package tritium.api.utils.event.events;

import tritium.api.utils.event.api.events.callables.EventCancellable;

public final class StrafeEvent extends EventCancellable
{
    private final float strafe;
    private final float forward;
    private final float friction;
    
    public final float getStrafe() {
        return this.strafe;
    }
    
    public final float getForward() {
        return this.forward;
    }
    
    public final float getFriction() {
        return this.friction;
    }
    
    public StrafeEvent(final float strafe, final float forward, final float friction) {
        this.strafe = strafe;
        this.forward = forward;
        this.friction = friction;
    }
}
