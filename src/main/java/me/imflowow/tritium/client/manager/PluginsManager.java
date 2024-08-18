package me.imflowow.tritium.client.manager;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import me.imflowow.tritium.core.Tritium;
import tritium.api.Plugin;
import tritium.api.Wrapper;
import tritium.api.module.Module;

public class PluginsManager {
	private File directory;
	public static boolean development;
	public static String mainClass;
	public ArrayList<Plugin> plugins = new ArrayList();

	public PluginsManager(File directory) {
		this.directory = directory;
	}

	public void initialize() {
		if (!this.directory.exists()) {
			this.directory.mkdir();
		}
		this.initLibraries(this.directory);
	}

	private void initLibraries(File file) {
		int failedcount = 0;
		int successedcount = 0;

		if (file.exists()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (File file2 : files) {
					if (!file2.isDirectory()) {
						String fileName = file2.getName();
						int lastindex = fileName.lastIndexOf(".");
						if (lastindex != -1) {
							String fileTyle = fileName.substring(lastindex, fileName.length());
							if (fileTyle.equalsIgnoreCase(".jar")) {
								if (this.initPlugin(file2, fileName.substring(0, lastindex))) {
									successedcount++;
								} else {
									failedcount++;
								}
							}
						}
					}
				}
			}
		}

		if (PluginsManager.development) {
			if (this.initPlugin(PluginsManager.mainClass)) {
				successedcount++;
			} else {
				failedcount++;
			}
		}

		Wrapper.logger.info(successedcount + " Plugin(s) has been loaded. Failed: " + failedcount);
	}

	private boolean initPlugin(File file, String name) {
		try {
			String mainClassPath = "";

			final File jsonfile = new File(this.directory, name + ".json");
			if (!jsonfile.exists()) {
				return false;
			}

			Reader reader = new FileReader(jsonfile);
			final JsonElement node = new JsonParser().parse(reader);
			if (!node.isJsonObject()) {
				return false;
			}
			for (Entry<String, JsonElement> data : node.getAsJsonObject().entrySet()) {
				switch (data.getKey()) {
				case "mainClass":
					mainClassPath = data.getValue().getAsString();
					break;
				}
			}

			this.loadJar(file);

			Class mainClass = Class.forName(mainClassPath);
			Plugin instance = (Plugin) mainClass.newInstance();

			this.plugins.add(instance);

			instance.preInit();
			if (instance.getModules() != null) {
				for (Module module : instance.getModules()) {
					Tritium.instance.getModuleManager().map.put(module.getLabel().toLowerCase(), module);
				}
			}

			Wrapper.logger.info("Plugin: " + instance.getName() + " Author:" + instance.getAuthor());

		} catch (Exception e) {
			Wrapper.logger.info("Failed to load " + name + ".jar");
			return false;
		}
		return true;
	}

	private boolean initPlugin(String mainClassPath) {
		try {
			Class mainClass = Class.forName(mainClassPath);
			Plugin instance = (Plugin) mainClass.newInstance();

			this.plugins.add(instance);

			instance.preInit();
			if (instance.getModules() != null) {
				for (Module module : instance.getModules()) {
					Tritium.instance.getModuleManager().map.put(module.getLabel().toLowerCase(), module);
				}
			}

			Wrapper.logger.info("Plugin: " + instance.getName() + " Author:" + instance.getAuthor());

		} catch (Exception e) {
			Wrapper.logger.info("Failed to load " + mainClassPath);
			return false;
		}
		return true;
	}

	public void loadJar(File jarFile) {
		Method method = null;
		try {
			method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}
		boolean accessible = method.isAccessible();
		try {
			if (accessible == false) {
				method.setAccessible(true);
			}
			URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			URL url = jarFile.toURI().toURL();
			method.invoke(classLoader, url);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.setAccessible(accessible);
		}
	}
}
