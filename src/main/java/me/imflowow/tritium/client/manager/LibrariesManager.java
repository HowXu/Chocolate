package me.imflowow.tritium.client.manager;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

import me.imflowow.tritium.client.libraries.Libraries;
import me.imflowow.tritium.client.libraries.library.*;

public class LibrariesManager {
	private File directory;
	private HashMap<String, Libraries> plugins = new HashMap();

	public LibrariesManager(File directory) {
		this.directory = directory;
	}

	public void initialize() {
		if (!this.directory.exists()) {
			this.directory.mkdir();
		}
		this.loadLibraries();
		this.initLibraries(this.directory);
	}

	private void loadLibraries() {
		// plugins
		Class[] plugins = { EPlusAntiCheat.class, StaffModules.class };

		for (Class class_ : plugins) {
			this.addPlugin(class_);
		}
	}

	public boolean isLoaded(final Class<?> clazz) {
		for (final Libraries plugin : this.plugins.values()) {
			if (plugin.getClass().equals(clazz)) {
				return plugin.isLoaded();
			}
		}
		return false;
	}

	public Libraries getLibraries(final Class<?> clazz) {
		for (final Libraries plugin : this.plugins.values()) {
			if (plugin.getClass().equals(clazz)) {
				return plugin;
			}
		}
		return null;
	}

	private void addPlugin(final Class<? extends Libraries> pluginClass) {
		try {
			final Libraries createdPlugin = pluginClass.newInstance();
			this.plugins.put(createdPlugin.getName().toLowerCase(), createdPlugin);
		} catch (final Exception e) {
		}
	}

	private void initLibraries(File file) {
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
								this.initPlugin(file2, fileName.substring(0, lastindex).toLowerCase());
							}
						}
					}
				}
			}
		}
	}

	private void initPlugin(File file, String name) {
		Libraries plugin = this.plugins.get(name);
		if (plugin == null) {
			return;
		}
		if (plugin.classload) {
			this.loadClassLoader(file, plugin);
		} else {
			this.loadJar(file, plugin);
		}

		plugin.preInit();
	}

	public void loadJar(File jarFile, Libraries plugin) {
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

	public void loadClassLoader(File jarFile, Libraries plugin) {
		try {
			URL url = jarFile.toURI().toURL();
			plugin.classloader = new URLClassLoader(new URL[] { url });
		} catch (MalformedURLException e2) {
		}
	}
}
