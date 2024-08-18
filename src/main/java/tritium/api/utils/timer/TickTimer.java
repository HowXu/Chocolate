package tritium.api.utils.timer;

public final class TickTimer {

    private int tick;

    public void update() {
        tick++;
    }

    public void reset() {
        tick = 0;
    }

    public boolean reach(final int ticks) {
        return tick >= ticks;
    }
    
    public boolean sleep(final int ticks) {
		if (tick >= ticks) {
			reset();
			return true;
		}
		return false;
    }
}
