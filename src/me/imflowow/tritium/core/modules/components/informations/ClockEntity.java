package me.imflowow.tritium.core.modules.components.informations;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.modules.ClockDisplay;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import tritium.api.module.gui.GuiEntity;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.utils.StringUtils;
import tritium.api.utils.render.Rect;

public class ClockEntity extends GuiEntity {
	private String text;

	public ClockEntity(PositionValue position) {
		super(position);
	}

	@Override
	public void init() {
		this.text = "";
	}

	@Override
	public void draw(double x, double y) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		this.text = formatter.format(date);
		int len = StringUtils.getWidth(text, SizeType.Size16) + 3;

		new Rect(x, y, len, 13, this.getModule().backgroundColor.getValue().getColor().getRGB(), Rect.RenderType.Expand).draw();
		StringUtils.drawStringWithShadow(text, x + 1.5, y + 5, this.getModule().textColor.getValue().getColor().getRGB(), SizeType.Size16);
	}

	@Override
	public int getHeight() {
		return 13;
	}

	@Override
	public int getWidth() {
		return StringUtils.getWidth(text, SizeType.Size16) + 3;
	}

	public ClockDisplay getModule() {
		return (ClockDisplay) Tritium.instance.getModuleManager().getModule(ClockDisplay.class);
	}
}
