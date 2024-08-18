package me.imflowow.tritium.client.libraries;

import java.net.URLClassLoader;

public abstract class Libraries {

	private String name;
	private String mainclass;
	private boolean isLoaded;
	public URLClassLoader classloader;
	public boolean classload;

	public Libraries(String name, String mainclass,boolean classload) {
		this.name = name;
		this.mainclass = mainclass;
		this.classload = classload;
		this.isLoaded = false;
	}

	public final void preInit() {
		this.isLoaded = this.load();
	}

	public abstract boolean load();

	public boolean isLoaded() {
		return isLoaded;
	}
	
	public void setLoaded(boolean isLoaded)
	{
		this.isLoaded = isLoaded;
	}

	public String getName() {
		return name;
	}

}
