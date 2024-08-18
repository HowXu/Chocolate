package tritium.api.utils.render.clickable.entity;

import net.minecraft.client.Minecraft;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.Rect.RenderType;
import tritium.api.utils.render.clickable.ClickEntity;
import tritium.api.utils.render.utils.MouseBounds.CallType;

public class ClickableRect extends ClickEntity {
    private Rect rect;

    public ClickableRect(double x, double y, double x1, double y1, int color,
                         RenderType type, Runnable click, Runnable hold, Runnable focus, Runnable release,Runnable onBlur) {
        super(x, y, x1, y1, getCallType(type), click, hold, focus, release,onBlur);
        this.rect = new Rect(x, y, x1, y1, color, type);
    }

    public void draw() {
        rect.draw();
        super.tick();
    }

    public double getX() {
        return rect.getX();
    }

    public void setX(double x) {
        super.setX(x);
        rect.setX(x);
    }

    public double getY() {
        return rect.getY();
    }

    public void setY(double y) {
        super.setY(y);
        rect.setY(y);
    }

    public double getWidth() {
        return rect.getWidth();
    }

    public void setWidth(double width) {
        super.setX1(width);
        rect.setWidth(width);
    }

    public double getHeight() {
        return rect.getHeight();
    }

    public void setHeight(double height) {
        super.setY1(height);
        rect.setHeight(height);
    }

    public int getColor() {
        return rect.getColor();
    }

    public void setColor(int color) {
        rect.setColor(color);
    }

    public RenderType getType() {
        return rect.getType();
    }

    public void setType(RenderType type) {
        rect.setType(type);
    }

    private static CallType getCallType(RenderType type) {
        switch (type) {
            case Expand:
                return CallType.Expand;
            case Position:
                return CallType.Position;
            default:
                return CallType.Expand;
        }
    }

}
