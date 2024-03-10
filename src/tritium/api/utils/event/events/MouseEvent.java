package tritium.api.utils.event.events;

import tritium.api.utils.event.api.events.callables.EventCancellable;

public class MouseEvent extends EventCancellable {
	private int button;
	private boolean down;

	public MouseEvent(final int button, final boolean down) {
		this.button = button;
		this.down = down;
	}

	public int getButton() {
		return this.button;
	}

	public void setButton(final int button) {
		this.button = button;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
}
