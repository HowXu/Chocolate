package tritium.api.utils.render.clickable.entity;

import net.minecraft.util.ResourceLocation;
import tritium.api.utils.render.Image;
import tritium.api.utils.render.Image.RenderType;
import tritium.api.utils.render.clickable.ClickEntity;
import tritium.api.utils.render.utils.MouseBounds.CallType;

public class ClickableImage extends ClickEntity {

	private Image image;

	public ClickableImage(ResourceLocation image, double x, double y, double x1, double y1, RenderType type,
			Runnable click, Runnable hold, Runnable focus, Runnable release, Runnable onBlur) {
		super(x, y, x1, y1, CallType.Expand, click, hold, focus, release, onBlur);
		this.image = new Image(image, x, y, x1, y1, type);
	}

	public void draw() {
		image.draw();
		super.tick();
	}

	public ResourceLocation getImage() {
		return image.getImage();
	}

	public void setImage(ResourceLocation image) {
		this.image.setImage(image);
	}

	public double getX() {
		return image.getX();
	}

	public void setX(double x) {
		super.setX(x);
		image.setX(x);
	}

	public double getY() {
		return image.getY();
	}

	public void setY(double y) {
		super.setY(y);
		image.setY(y);
	}

	public double getWidth() {
		return image.getWidth();
	}

	public void setWidth(double width) {
		super.setX1(width);
		image.setWidth(width);
	}

	public double getHeight() {
		return image.getHeight();
	}

	public void setHeight(double height) {
		super.setY1(height);
		image.setHeight(height);
	}

	public RenderType getType() {
		return image.getType();
	}

	public void setType(RenderType type) {
		image.setType(type);
	}

}
