package me.imflowow.tritium.client.libraries.library;

import java.lang.reflect.Method;

import me.imflowow.tritium.client.libraries.Libraries;

public class EPlusAntiCheat extends Libraries {

	public EPlusAntiCheat() {
		super("EPlusAntiCheat", "",false);
	}

	@Override
	public boolean load() {
		
		try {
			Class clz = Class.forName("com.soterdev.a");
			Method method = clz.getMethod("AC");
			method.invoke(clz);
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}

}
