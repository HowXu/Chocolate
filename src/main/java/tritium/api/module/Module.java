package tritium.api.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import tritium.api.module.gui.GuiEntity;
import tritium.api.module.value.Value;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.utils.event.api.EventManager;
import tritium.api.utils.render.clickable.ClickEntity;

public class Module {

	private String label;
	private String introduction;
	private boolean enabled;
	private Category category;
	private boolean bindable;
	private int keybind;
	private final List<Value> values = new ArrayList<>();
	private final List<GuiEntity> entities = new ArrayList<>();

	public Minecraft mc = Minecraft.getMinecraft();

	public Module(final String label, final String introduction) {
		this(label, introduction, false, false);
	}

	public Module(final String label, final String introduction, final boolean enabled) {
		this(label, introduction, enabled, false);
	}

	public Module(final String label, final String introduction, final boolean enabled, final boolean bindable) {
		this.label = label;
		this.introduction = introduction;
		this.bindable = bindable;
		this.startUp(enabled);
	}

	public void addValues(final Value... value) {
		for (Value val : value) {
			this.values.add(val);
			val.init(this);
		}
	}

	public void addGuiEntities(final GuiEntity... value) {
		for (GuiEntity entity : value) {
			this.values.add(entity.getPosition());
			this.entities.add(entity);
		}
	}

	public List<Value> getValues(boolean withPositionValue) {
		if (!withPositionValue) {
			List<Value> list = new ArrayList();
			for (Value value : this.values) {
				if (!(value instanceof PositionValue)) {
					list.add(value);
				}
			}
			return list;
		}
		return this.values;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void load(final JsonObject directory) {
		directory.entrySet().forEach(data -> {
			switch (data.getKey()) {
			case "key":
				this.setKeybind(data.getValue().getAsInt());
				break;
			case "enabled":
				if (!(this.isEnabled() && data.getValue().getAsBoolean())
						&& !(!this.isEnabled() && !data.getValue().getAsBoolean())) {
					this.startUp(data.getValue().getAsBoolean());
				}
				break;
			}
			final Value val = this.find(data.getKey());
			if (val != null) {
				val.setValue(data.getValue().getAsString());
			}
		});
	}

	public Value find(final String term) {
		for (final Value value : this.values) {
			if (value.getLabel().equalsIgnoreCase(term)) {
				return value;
			}
		}
		return null;
	}

	public void onDisable() {
	}

	public void onEnable() {
	}

	public void save(final JsonObject directory) {
		directory.addProperty("key", this.getKeybind());
		directory.addProperty("enabled", this.isEnabled());
		this.values.forEach(val -> {
			directory.addProperty(val.getLabel(), val.getValue().toString());
		});
	}

	public void startUp(final boolean enabled) {
		this.enabled = enabled;
		if (enabled) {
			EventManager.register(this);
		} else {
			EventManager.unregister(this);
		}
	}

	public void toggle() {
		this.setEnabled(!this.isEnabled());
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public int getKeybind() {
		return keybind;
	}

	public void setKeybind(int keybind) {
		this.keybind = keybind;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if (enabled) {
			EventManager.register(this);
			this.onEnable();
		} else {
			EventManager.unregister(this);
			this.onDisable();
		}
	}

	public enum Category {
		Render, Player, Settings
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isBindable() {
		return bindable;
	}

	public void setBindable(boolean bindable) {
		this.bindable = bindable;
	}

	public List<GuiEntity> getGuiEntities() {
		return this.entities;
	}

}
