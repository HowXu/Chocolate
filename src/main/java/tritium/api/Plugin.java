package tritium.api;

import tritium.api.module.Module;

public interface Plugin {
	public String getName();

	public String getAuthor();

	public Module[] getModules();

	public void preInit();
	
	public void postInit();

}
