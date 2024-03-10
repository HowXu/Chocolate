package me.imflowow.tritium.core.globals;

import tritium.api.module.GlobalModule;
import tritium.api.module.value.impl.BooleanValue;
import tritium.api.module.value.impl.EnumValue;

public class ClientConfig extends GlobalModule {

	public EnumValue<ThemeType> theme = new EnumValue("Theme", ThemeType.Dark);
	public BooleanValue versionwarning = new BooleanValue("VersionWarning", true);

	public ClientConfig() {
		super("ClientConfig", true);
		super.addValues( versionwarning);
	}

	public enum ThemeType {
		Dark, Light;
	}

}
