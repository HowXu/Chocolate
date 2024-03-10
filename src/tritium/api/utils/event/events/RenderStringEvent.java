package tritium.api.utils.event.events;

import tritium.api.utils.event.api.events.Event;

public class RenderStringEvent implements Event {
    private String text;

    public RenderStringEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
