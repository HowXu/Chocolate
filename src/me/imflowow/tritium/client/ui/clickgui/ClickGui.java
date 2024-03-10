package me.imflowow.tritium.client.ui.clickgui;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import me.imflowow.tritium.client.ui.clickgui.commands.CommandManager;
import me.imflowow.tritium.client.ui.clickgui.components.CommandBox;
import me.imflowow.tritium.client.ui.clickgui.components.MenuButton;
import me.imflowow.tritium.client.ui.clickgui.components.ModuleList;
import me.imflowow.tritium.client.ui.clickgui.components.ValueList;
import me.imflowow.tritium.client.ui.clickgui.components.Window;
import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.globals.ClientConfig;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import tritium.api.manager.MessageManager.MessageType;
import tritium.api.module.Module;
import tritium.api.utils.StringUtils;
import tritium.api.utils.animate.Opacity;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.Scissor;

public class ClickGui extends GuiScreen {
	private double positionX, positionY;

	private int alpha;
	private Opacity alpha_animation = new Opacity(0);

	private boolean hasInited;
	private boolean hasClosed;

	private Scissor wholeScreenScissor;

	private Window mainWindow;
	private ModuleList moduleList;
	private ValueList valueList;

	private CommandManager commandmanager;
	private CommandBox commandBox;
	private boolean onCommandBox;

	private Module onBindingModule;

	private int wheel;

	public ClickGui() {
		this.alpha = 0;
		this.positionX = 600;
		this.positionY = 100;
		this.hasClosed = true;
		this.hasInited = false;
		this.wholeScreenScissor = new Scissor(0, 0, this.width, this.height);
		this.mainWindow = new Window();
		this.moduleList = new ModuleList();
		this.valueList = new ValueList();
		this.commandmanager = new CommandManager();
		this.commandBox = new CommandBox();
		this.onCommandBox = false;
	}

	@Override
	public void initGui() {
		hasClosed = false;
		this.alpha_animation = new Opacity(0);
		if (!this.hasInited) {
			this.mainWindow.init(this.positionX, this.positionY);
			this.moduleList.init(this.positionX, this.positionY);
			this.valueList.init(this.positionX, this.positionY);
			this.commandmanager.initialize();
			this.commandBox.init(this.positionX, this.positionY);
			this.hasInited = true;
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		ScaledResolution sr = new ScaledResolution(mc);

//		this.setPositionX((sr.getScaledWidth_double() - 400.0) / 2);
//		this.setPositionY((sr.getScaledHeight_double() - 300.0) / 2);

		this.wheel = Mouse.getDWheel() / 10;

		this.wholeScreenScissor.setX(0);
		this.wholeScreenScissor.setY(0);
		this.wholeScreenScissor.setWidth(this.width);
		this.wholeScreenScissor.setHeight(this.height);
		this.wholeScreenScissor.doScissor();

		if (this.onCommandBox) {
			commandBox.draw(mouseX, mouseY, mouseY, partialTicks);
		}

		GL11.glEnable(GL11.GL_SCISSOR_TEST);

		mainWindow.draw(mouseX, mouseY, this.getPositionX(), this.getPositionY());
		moduleList.draw(mouseX, mouseY, this.getPositionX(), this.getPositionY());
		valueList.draw(mouseX, mouseY, this.getPositionX(), this.getPositionY());
		
		if (moduleList.getOnHover() != null) {
			if (moduleList.getOnHover().isBindable()) {
				String text = "Bind:" + Keyboard.getKeyName(moduleList.getOnHover().getKeybind());;
				if (moduleList.getOnHover().getKeybind() == 0) {
					text = "This module has not been bound.(middle click on it to bind)";
				}
				int width = StringUtils.getWidth(text, SizeType.Size16);
				new Rect(mouseX, mouseY - 12, width + 4, 11, new Color(40, 40, 40, this.getAlpha()).getRGB(),
						Rect.RenderType.Expand).draw();
				StringUtils.drawString(text, mouseX + 2, mouseY - 8, -1, SizeType.Size16);
			}
		}

		this.setPositionX(mainWindow.getPositionX());
		this.setPositionY(mainWindow.getPositionY());

		GL11.glDisable(GL11.GL_SCISSOR_TEST);

		if (hasClosed) {
			alpha_animation.interpolate(0, 20);
		} else {
			alpha_animation.interpolate(255, 20);
		}
		this.alpha = (int) alpha_animation.getOpacity();
		if (this.alpha == 0) {
			this.mc.displayGuiScreen((GuiScreen) null);

			if (this.mc.currentScreen == null) {
				this.mc.setIngameFocus();
			}
		}
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		if (this.onBindingModule != null) {
			if (this.onBindingModule.isBindable()) {
				if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_DELETE) {
					this.onBindingModule.setKeybind(0);
					Tritium.instance.getMessagemanager().addMessage(this.onBindingModule.getLabel() + "is now unbound.", MessageType.Info, 3000);
				} else {
					this.onBindingModule.setKeybind(keyCode);
					Tritium.instance.getMessagemanager().addMessage(this.onBindingModule.getLabel()
							+ " is now bound to \"" + Keyboard.getKeyName(keyCode) + "\".", MessageType.Info, 3000);
				}
			} else {
				Tritium.instance.getMessagemanager().addMessage("That module is unbindable.", MessageType.Warnning,
						3000);
			}
			this.onBindingModule = null;
			return;
		}

		if (keyCode == 1) {
			Tritium.instance.getModuleManager().saveModules();
			this.hasClosed = true;
			this.onCommandBox = false;
		}

		if (this.onCommandBox) {
			if (keyCode == Keyboard.KEY_RETURN) {
				this.onCommandBox = false;
			}
			this.commandBox.keyTyped(typedChar, keyCode);
		}

		// System.out.println(keyCode);
		if (keyCode == Keyboard.KEY_GRAVE || keyCode == Keyboard.CHAR_NONE) {
			this.onCommandBox = !this.onCommandBox;
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	public boolean IsOnCommandBox() {
		return this.onCommandBox;
	}

	public void clickModulesButton(int id) {
		switch (id) {
		case 0:
			this.mainWindow.modules.setChosen(true);
			this.mainWindow.global.setChosen(false);
			break;
		case 1:
			this.mainWindow.modules.setChosen(false);
			this.mainWindow.global.setChosen(true);
			break;
		}
		this.moduleList.setTarY(0);
		this.moduleList.refreshList(id);
	}

	public int getColor(int type) {
		ClientConfig config = (ClientConfig) Tritium.instance.getModuleManager().getModule(ClientConfig.class);
		switch (config.theme.getValue()) {
		case Dark:
			switch (type) {
			case 0:
				return new Color(21, 21, 21, this.alpha).getRGB();// Menu
			case 1:
				return new Color(255, 255, 255, this.alpha).getRGB();// Top Title
			case 2:
				return new Color(21, 21, 21, this.alpha).getRGB();// Top
			case 3:
				return new Color(28, 28, 28, this.alpha).getRGB();// Main
			case 4:
				return new Color(200, 200, 200, this.alpha).getRGB();// Menu Icon off
			case 5:
				return new Color(255, 255, 255, this.alpha).getRGB();// Menu Icon on
			case 6:
				return new Color(28, 28, 28, this.alpha).getRGB();// Menu Frame
			case 7:
				return new Color(60, 81, 249, this.alpha).getRGB();// Menu Chosen Frame
			case 8:
				return new Color(21, 21, 21, this.alpha).getRGB();// ModuleList rect
			case 9:
				return new Color(255, 255, 255, this.alpha).getRGB();// ModuleList text
			case 10:
				return new Color(37, 38, 43, this.alpha).getRGB();// ModuleList rect focus
			case 11:
				return new Color(24, 24, 24, this.alpha).getRGB();// ModuleList setting
			case 12:
				return new Color(37, 38, 43, this.alpha).getRGB();// ModuleList setting focus
			case 13:
				return new Color(21, 21, 21, this.alpha).getRGB();// Module On Hover Rect
			case 14:
				return new Color(255, 255, 255, this.alpha).getRGB();// Module On Hover Text
			case 15:
				return new Color(32, 32, 32, this.alpha).getRGB();// Value List Menu
			case 16:
				return new Color(255, 255, 255, this.alpha).getRGB();// ModuleList settings
			case 17:
				return new Color(255, 255, 255, this.alpha).getRGB();// Value List Back
			case 18:
				return new Color(0, 111, 255, this.alpha).getRGB();// Value List back focus
			case 19:
				return new Color(255, 255, 255, this.alpha).getRGB();// Value List title
			case 20:
				return new Color(255, 255, 255, this.alpha).getRGB();// Value List Label
			case 21:
				return new Color(38, 38, 38, this.alpha).getRGB();// Boolean Value rect
			case 22:
				return new Color(44, 44, 44, this.alpha).getRGB();// Boolean Value focus
			case 23:
				return new Color(38, 38, 38, this.alpha).getRGB();// Enum Value rect
			case 24:
				return new Color(44, 44, 44, this.alpha).getRGB();// Enum Value focus
			case 25:
				return new Color(52, 52, 52, this.alpha).getRGB();// Number Value rect
			case 26:
				return new Color(255, 255, 255, this.alpha).getRGB();// Number Value rounded
			case 27:
				return new Color(0, 111, 255, this.alpha).getRGB();// Number Value rect value
			case 28:
				return new Color(32, 32, 32, this.alpha).getRGB();// Command Box Rect
			case 29:
				return new Color(255, 255, 255, this.alpha).getRGB();// Command Box Text
			}
		case Light:
			switch (type) {
			case 0:
				return new Color(236, 240, 241, this.alpha).getRGB();// Menu
			case 1:
				return new Color(23, 32, 42, this.alpha).getRGB();// Top Title
			case 2:
				return new Color(236, 240, 241, this.alpha).getRGB();// Top
			case 3:
				return new Color(240, 243, 244, this.alpha).getRGB();// Main
			case 4:
				return new Color(28, 40, 51, this.alpha).getRGB();// Menu Icon off
			case 5:
				return new Color(240, 243, 244, this.alpha).getRGB();// Menu Icon on
			case 6:
				return new Color(244, 246, 247, this.alpha).getRGB();// Menu Frame
			case 7:
				return new Color(52, 73, 94, this.alpha).getRGB();// Menu Chosen Frame
			case 8:
				return new Color(234, 236, 238, this.alpha).getRGB();// ModuleList rect
			case 9:
				return new Color(23, 32, 42, this.alpha).getRGB();// ModuleList text
			case 10:
				return new Color(244, 246, 246, this.alpha).getRGB();// ModuleList rect focus
			case 11:
				return new Color(213, 216, 220, this.alpha).getRGB();// ModuleList setting
			case 12:
				return new Color(214, 219, 223, this.alpha).getRGB();// ModuleList setting focus
			case 13:
				return new Color(234, 236, 238, this.alpha).getRGB();// Module On Hover Rect
			case 14:
				return new Color(23, 32, 42, this.alpha).getRGB();// Module On Hover Text
			case 15:
				return new Color(251, 252, 252, this.alpha).getRGB();// Value List Menu
			case 16:
				return new Color(23, 32, 42, this.alpha).getRGB();// ModuleList settings
			case 17:
				return new Color(23, 32, 42, this.alpha).getRGB();// Value List Back
			case 18:
				return new Color(0, 111, 255, this.alpha).getRGB();// Value List back focus
			case 19:
				return new Color(23, 32, 42, this.alpha).getRGB();// Value List title
			case 20:
				return new Color(23, 32, 42, this.alpha).getRGB();// Value List Label
			case 21:
				return new Color(234, 237, 237, this.alpha).getRGB();// Boolean Value rect
			case 22:
				return new Color(242, 243, 244, this.alpha).getRGB();// Boolean Value focus
			case 23:
				return new Color(242, 244, 244, this.alpha).getRGB();// Enum Value rect
			case 24:
				return new Color(244, 246, 246, this.alpha).getRGB();// Enum Value focus
			case 25:
				return new Color(213, 219, 219, this.alpha).getRGB();// Number Value rect
			case 26:
				return new Color(174, 182, 191, this.alpha).getRGB();// Number Value rounded
			case 27:
				return new Color(0, 111, 255, this.alpha).getRGB();// Number Value rect value
			case 28:
				return new Color(251, 252, 252, this.alpha).getRGB();// Command Box Rect
			case 29:
				return new Color(23, 32, 42, this.alpha).getRGB();// Command Box Text
			}
		}
		return 0;
	}

	public String getResource(int type) {
		ClientConfig config = (ClientConfig) Tritium.instance.getModuleManager().getModule(ClientConfig.class);
		switch (config.theme.getValue()) {
		case Dark:
			switch (type) {
			case 0:
				return "d";
			}
		case Light:
		}
		return "";
	}

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public Window getMainWindow() {
		return mainWindow;
	}

	public void setMainWindow(Window mainWindow) {
		this.mainWindow = mainWindow;
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	public int getWheel() {
		return wheel;
	}

	public void setWheel(int wheel) {
		this.wheel = wheel;
	}

	public Scissor getWholeScreenScissor() {
		return wholeScreenScissor;
	}

	public void setWholeScreenScissor(Scissor wholeScreenScissor) {
		this.wholeScreenScissor = wholeScreenScissor;
	}

	public ModuleList getModuleList() {
		return moduleList;
	}

	public void setModuleList(ModuleList moduleList) {
		this.moduleList = moduleList;
	}

	public ValueList getValueList() {
		return valueList;
	}

	public void setValueList(ValueList valueList) {
		this.valueList = valueList;
	}

	public CommandManager getCommandmanager() {
		return commandmanager;
	}

	public void setCommandmanager(CommandManager commandmanager) {
		this.commandmanager = commandmanager;
	}

	public Module getOnBindingModule() {
		return onBindingModule;
	}

	public void setOnBindingModule(Module onBindingModule) {
		this.onBindingModule = onBindingModule;
	}

}
