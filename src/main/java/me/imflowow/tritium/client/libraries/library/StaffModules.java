package me.imflowow.tritium.client.libraries.library;

import java.lang.reflect.Method;

import me.imflowow.tritium.client.libraries.Libraries;

public class StaffModules extends Libraries {

	public StaffModules() {
		super("StaffModules", "tritium/staffmodules/StaffModules.java",true);
	}

	@Override
	public boolean load() {
		try {
			Class clz = this.classloader.loadClass("tritium.staffmodules.StaffModules");
			Method method = clz.getMethod("register");
			method.invoke(clz);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public void unload() {
		try {
			Class clz = this.classloader.loadClass("tritium.staffmodules.StaffModules");
			Method method = clz.getMethod("unregister");
			method.invoke(clz);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean check()
	{
		boolean result = false;
		try {
			Class clz = this.classloader.loadClass("tritium.staffmodules.StaffModules");
			Method method = clz.getMethod("check");
			result = (boolean)method.invoke(clz);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return result;
	}

}
