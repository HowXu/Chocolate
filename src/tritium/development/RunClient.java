package tritium.development;

import java.util.Arrays;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import me.imflowow.tritium.client.manager.PluginsManager;
import net.minecraft.client.Minecraft;
import tritium.launch.Main;

public class RunClient {
	public static void main(String[] args) {
		OptionParser optionparser = new OptionParser();
		optionparser.allowsUnrecognizedOptions();
		OptionSpec<String> optionspec = optionparser.accepts("pluginClass").withRequiredArg().defaultsTo("",
				new String[0]);
		OptionSet optionset = optionparser.parse(args);
		String mainClassPath = (String) optionset.valueOf(optionspec);
		if (!mainClassPath.equals("")) {
			PluginsManager.development = true;
			PluginsManager.mainClass = mainClassPath;
		}

		Main.main(concat(new String[] { "--version", "TritiumPlugin", "--accessToken", "0", "--assetsDir", "assets",
				"--assetIndex", "1.8", "--userProperties", "{}" }, args));
	}

	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

}
