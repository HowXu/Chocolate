package me.imflowow.tritium.core;

import java.io.File;

import me.imflowow.tritium.client.manager.ConfigManager;
import me.imflowow.tritium.client.manager.ModuleManager;
import me.imflowow.tritium.client.manager.PluginsManager;
import me.imflowow.tritium.client.manager.LibrariesManager;
import me.imflowow.tritium.client.ui.init.GuiInit;
import me.imflowow.tritium.utils.account.utils.AccountManager;
import me.imflowow.tritium.utils.information.Author;
import me.imflowow.tritium.utils.information.Version;
import me.imflowow.tritium.utils.information.Version.VersionType;
import me.imflowow.tritium.utils.information.characters.ImFl0wow;
import me.imflowow.tritium.utils.information.characters.IsNotThread;
import me.imflowow.tritium.utils.information.characters.MapleLeaf;
import me.imflowow.tritium.utils.language.LanguageManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import tritium.api.Wrapper;
import tritium.api.manager.FontManager;
import tritium.api.manager.MessageManager;
import tritium.netease.auth.AuthManager;

public class Tritium {
	public static Version version = new Version(Version.VersionType.Release, 1, 1, 1);
	//public static Version version = new Version(Version.VersionType.Development, 0, 1, 0);
//	public static Version version = new Version(Version.VersionType.Beta, 0, 7, 6);
	public static Author[] authors = { new ImFl0wow(), new IsNotThread(), new MapleLeaf() };
	public static Tritium instance = new Tritium();

	public static boolean initialization;

	private Wrapper wrapper;

	private ClientListener clientlistener;
	private GuiListener guilistener;
	private FontManager fontmanager;
	private ModuleManager modulemanager;
	private MessageManager messagemanager;
	private LibrariesManager librariesmanager;
	private PluginsManager pluginsManager;
	private AuthManager authmanager;
	private LanguageManager languagemanager;
	private ConfigManager configmanager;
	private AccountManager accountManager;

	public void onStart() {
		this.clientlistener = new ClientListener();
		this.guilistener = new GuiListener();
		this.wrapper = new Wrapper();
		this.wrapper.init(this.wrapper);

		File directory = new File(Minecraft.getMinecraft().mcDataDir, "Tritium-X");
		if (!directory.exists()) {
			directory.mkdir();
		}

		File lang = new File(directory, "language.json");

		if (!lang.exists()) {
			initialization = true;
		}

		Tritium.instance.setFontManager(new FontManager());
		Tritium.instance.setLanguagemanager(new LanguageManager(directory));

		Tritium.instance.setLibrariesmanager(new LibrariesManager(new File(directory, "libraries")));

		Tritium.instance.setPluginsManager(new PluginsManager(new File(directory, "plugins")));

		Tritium.instance.setModuleManager(new ModuleManager(new File(directory, "modules")));

		if(Tritium.version.getType() == VersionType.Development)
		{
			Tritium.instance.setAuthmanager(new AuthManager(directory));
		}


		Tritium.instance.setMessagemanager(new MessageManager());

		Tritium.instance.setConfigmanager(new ConfigManager(new File(directory, "configs")));



		Tritium.instance.setAccountManager(new AccountManager(directory));


		Tritium.instance.getAccountManager().getAltSaving().loadFile();

//在启动之后选择语言之前会先调用这个,因此设置语言在此之后在下面的if之前

		{
			Tritium.instance.getLanguagemanager().setLang("chinese");
			Tritium.instance.getLanguagemanager().getTextRender().setLang("chinese");
			if (!Tritium.instance.getLanguagemanager().getTextRender().isEnglish()) {
				if (Tritium.instance.getLanguagemanager().getTextRender().init()) {
					Tritium.instance.getFontManager().loadUnicodeRender();
				}
			}
			Tritium.instance.getModuleManager().initialize();
			Tritium.instance.getConfigmanager().load();
			if(Tritium.version.getType() == Version.VersionType.Development)
			{
				Tritium.instance.getAuthmanager().getAuth().loadAuth(Tritium.instance.getAuthmanager().getAuthtype());
			}
			Tritium.instance.getClientListener().init();
			Tritium.initialization = false;
			//mc.displayGuiScreen(new GuiMainMenu());最后这一步用不了，依然保存下来用在最终Main菜单呈现上
		}


		if (!initialization) {
			Tritium.instance.getLanguagemanager().loadConfig();
			Tritium.instance.getLanguagemanager().getTextRender()
					.setLang(Tritium.instance.getLanguagemanager().getLang());
			if (!Tritium.instance.getLanguagemanager().getTextRender().isEnglish()) {
				if (Tritium.instance.getLanguagemanager().getTextRender().init()) {
					Tritium.instance.getFontManager().loadUnicodeRender();
				}
			}
			Tritium.instance.getModuleManager().initialize();
			Tritium.instance.getConfigmanager().load();
			if(Tritium.version.getType() == VersionType.Development)
			{
				Tritium.instance.getAuthmanager().loadConfig();
				Tritium.instance.getAuthmanager().getAuth().loadAuth(Tritium.instance.getAuthmanager().getAuthtype());
			}
			clientlistener.init();
		}
	}

	public void onStop() {
		File directory = new File(Minecraft.getMinecraft().mcDataDir, "Tritium-X");
		if (!directory.exists())
			directory.mkdir();
		Tritium.instance.getModuleManager().saveModules();
		if(Tritium.version.getType() == VersionType.Development)
		{
			Tritium.instance.getAuthmanager().saveConfig();
		}

		Tritium.instance.getLanguagemanager().saveConfig();
		accountManager.getAltSaving().saveFile();
	}

	public FontManager getFontManager() {
		return this.fontmanager;
	}

	public ModuleManager getModuleManager() {
		return this.modulemanager;
	}

	public ClientListener getClientListener() {
		return this.clientlistener;
	}

	public void setFontManager(FontManager fontmanager) {
		this.fontmanager = fontmanager;
	}

	public void setModuleManager(ModuleManager modulemanager) {
		this.modulemanager = modulemanager;
	}

	public void setClientListener(ClientListener clientlistener) {
		this.clientlistener = clientlistener;
	}

	public MessageManager getMessagemanager() {
		return messagemanager;
	}

	public void setMessagemanager(MessageManager messagemanager) {
		this.messagemanager = messagemanager;
	}

	public GuiListener getGuiListener() {
		return guilistener;
	}

	public void setGuiListener(GuiListener guilistener) {
		this.guilistener = guilistener;
	}

	public LibrariesManager getLibrariesmanager() {
		return librariesmanager;
	}

	public void setLibrariesmanager(LibrariesManager pluginmanager) {
		this.librariesmanager = pluginmanager;
	}

	public AuthManager getAuthmanager() {
		return authmanager;
	}

	public void setAuthmanager(AuthManager authmanager) {
		this.authmanager = authmanager;
	}

	public LanguageManager getLanguagemanager() {
		return languagemanager;
	}

	public void setLanguagemanager(LanguageManager languagemanager) {
		this.languagemanager = languagemanager;
	}

	public ConfigManager getConfigmanager() {
		return configmanager;
	}

	public void setConfigmanager(ConfigManager configmanager) {
		this.configmanager = configmanager;
	}

	public PluginsManager getPluginsManager() {
		return pluginsManager;
	}

	public void setPluginsManager(PluginsManager pluginsManager) {
		this.pluginsManager = pluginsManager;
	}

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
}
