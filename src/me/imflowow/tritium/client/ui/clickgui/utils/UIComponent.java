package me.imflowow.tritium.client.ui.clickgui.utils;

import me.imflowow.tritium.client.ui.clickgui.ClickGui;
import me.imflowow.tritium.core.Tritium;
import tritium.api.utils.render.base.RenderEntity;

public class UIComponent extends RenderEntity{
	public void init(double positionX, double positionY) {

	}

	public void draw(int mouseX, int mouseY, double positionX, double positionY) {

	}

	protected void setClickGuiPosition(double x, double y) {
		Tritium.instance.getClientListener().getClickGui().setPositionX(x);
		Tritium.instance.getClientListener().getClickGui().setPositionY(y);
	}

	protected int getMenuAlpha() {
		return Tritium.instance.getClientListener().getClickGui().getAlpha();
	}

	protected int getColor(int type) {
		return Tritium.instance.getClientListener().getClickGui().getColor(type);
	}

	protected String getResource(int type) {
		return Tritium.instance.getClientListener().getClickGui().getResource(type);
	}

	protected void clickModulesButton(int id) {
		Tritium.instance.getClientListener().getClickGui().clickModulesButton(id);
	}
	
	protected int getWheel()
	{
		return Tritium.instance.getClientListener().getClickGui().getWheel();
	}
	
	protected ClickGui getClickGui()
	{
		return Tritium.instance.getClientListener().getClickGui();
	}
}
