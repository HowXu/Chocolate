package me.imflowow.tritium.client.ui.clickgui.components;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import me.imflowow.tritium.client.ui.clickgui.utils.UIComponent;
import me.imflowow.tritium.core.Tritium;
import net.minecraft.util.ChatAllowedCharacters;
import tritium.api.utils.render.Rect;

public class CommandBox extends UIComponent {

	StringBuilder commands;

	@Override
	public void init(double positionX, double positionY) {
		this.commands = new StringBuilder();
	}

	@Override
	public void draw(int mouseX, int mouseY, double positionX, double positionY) {
		String text;
		int width;
		text = this.getText();
		width = Tritium.instance.getFontManager().arial16.getStringWidth(text);

		int diff = 2;
		if (width > diff)
			diff = width;

		new Rect(this.getScaledWidth() - 3 - diff, this.getScaledHeight() - 10, diff, 8, this.getColor(28),
				Rect.RenderType.Expand).draw();
		Tritium.instance.getFontManager().arial16.drawString(text, this.getScaledWidth() - 3 - width,
				this.getScaledHeight() - 8, this.getColor(29));
	}

	public void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 14) {
			this.deleteText();
			return;
		}
		if (keyCode == Keyboard.KEY_GRAVE) {
			this.commands = new StringBuilder();
			return;
		}
		if (keyCode == Keyboard.KEY_RETURN) {
			this.getClickGui().getCommandmanager().dispatch(this.getText());
			this.commands = new StringBuilder();
			return;
		}
		if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
			this.addText(Character.toString(typedChar));
		}
	}

	public void addText(String text) {
		this.commands.append(text);
	}

	public void deleteText() {
		if (this.commands.length() > 0)
			this.commands.deleteCharAt(this.commands.length() - 1);
	}

	public String getText() {
		return this.commands.toString();
	}
}
