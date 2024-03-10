package me.imflowow.tritium.client.manager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.howxu.Wings;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.globals.*;
import me.imflowow.tritium.core.modules.*;
import tritium.api.module.GlobalModule;
import tritium.api.module.Module;

public class ModuleManager {
	public Map<String, Module> map = new HashMap<>();
	private File directory;

	public ModuleManager(File directory) {
		this.directory = directory;
	}

	public void initialize() {

		// global
		Class[] globals = { ClientConfig.class, ContainerAnimations.class, ScreenBackground.class, Chat.class,
				ChunkAnimator.class, AnimatedView.class,Translator.class };
		// modules 添加了Wings
		Class[] modules = { MotionBlur.class, Sprint.class, OldAnimations.class, Fullbright.class, Wings.class,
				BlockAnimations.class, Perspective.class, WorldTime.class, ChatCopy.class, MoreParticles.class,
				ItemPhysic.class, LowFire.class, KeyStrokes.class, BlockOverlay.class, ArmorDisplay.class,
				Crosshair.class, ReachDisplay.class, CPSDisplay.class, PotionDisplay.class, CustomParticles.class,
				EnchantEffect.class, DuelInfo.class, FPSDisplay.class, ClockDisplay.class, PingDisplay.class,
				CoordDisplay.class, SelfHealth.class, AutoGG.class, DamageDealt.class, CustomScoreboard.class };

		for (Class class_ : globals) {
			this.register(class_);
		}

		for (Class class_ : modules) {
			this.register(class_);
		}

		Tritium.instance.getLibrariesmanager().initialize();
		Tritium.instance.getPluginsManager().initialize();

		final Map<String, Module> result = new LinkedHashMap<>();
		this.map.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
		this.map = result;
		this.loadModules();
	}

	public ArrayList<Module> getModules(boolean includingGlobal) {
		final ArrayList<Module> modsInCategory = new ArrayList<>();
		for (final Module mod : this.getModuleMap().values()) {
			if (!(mod instanceof GlobalModule) || includingGlobal)
				modsInCategory.add(mod);
		}
		return modsInCategory;
	}

	public ArrayList<Module> getModules(final String search, boolean includingGlobal) {
		if ((search == null) || (search == "")) {
			return this.getModules(includingGlobal);
		} else {
			final ArrayList<Module> modsInCategory = this.getModules(includingGlobal);
			modsInCategory.removeIf(m -> {
				return !m.getLabel().toLowerCase().contains(search.toLowerCase());
			});
			return modsInCategory;
		}
	}

	public Module getModule(final String name) {
		return this.getModuleMap().get(name.toLowerCase());
	}

	public Module getModule(final Class<?> clazz) {
		for (final Module mod : this.getModuleMap().values()) {
			if (mod.getClass().equals(clazz)) {
				return mod;
			}
		}
		return null;
	}

	public Map<String, Module> getModuleMap() {
		return this.map;
	}

	public boolean isModule(final String modulename) {
		for (final Module mod : this.getModuleMap().values()) {
			if (mod.getLabel().equalsIgnoreCase(modulename)) {
				return true;
			}
		}
		return false;
	}

	public void loadModules() {
		this.getModuleMap().values().forEach(module -> {
			final File file = new File(this.directory, module.getLabel() + ".json");
			if (!file.exists()) {
				return;
			}
			try (Reader reader = new FileReader(file)) {
				final JsonElement node = new JsonParser().parse(reader);
				if (!node.isJsonObject()) {
					return;
				}
				module.load(node.getAsJsonObject());
			} catch (final IOException e) {
			}
		});
	}

	public void register(final Class<? extends Module> moduleClass) {
		try {
			final Module createdModule = moduleClass.newInstance();
			this.map.put(createdModule.getLabel().toLowerCase(), createdModule);
		} catch (final Exception e) {
		}
	}

	public void saveModules() {
		if (this.getModuleMap().values().isEmpty()) {
			this.directory.delete();
		}
		File[] files = this.directory.listFiles();
		if (!this.directory.exists()) {
			this.directory.mkdir();
		} else if (files != null) {
			for (final File file : files) {
				file.delete();
			}
		}
		this.getModuleMap().values().forEach(module -> {
			final File file = new File(this.directory, module.getLabel() + ".json");
			final JsonObject node = new JsonObject();
			module.save(node);
			if (node.entrySet().isEmpty()) {
				return;
			}
			try {
				file.createNewFile();
			} catch (final IOException e) {
				return;
			}
			try (Writer writer = new FileWriter(file)) {
				writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(node));
			} catch (final IOException e) {
				file.delete();
			}
		});
		files = this.directory.listFiles();
		if ((files == null) || (files.length == 0)) {
			this.directory.delete();
		}
	}
}
