import java.util.Arrays;

import tritium.launch.Main;

public class Start {


	public static void main(String[] args) {
		//调用的是别的Main tritium.launch.Main
		//包括在版本json中声明参数也要将主类声明为tritium.launch.Main
		//但是使用tritium.launch.Main会导致参数不足问题，使用Start在打包之后又会导致参数过多，什么脑炎设计
		Main.main(concat(new String[] { "--version", "mcp", "--accessToken", "0", "--assetsDir", "assets",
				"--assetIndex", "1.8", "--userProperties", "{}" }, args));
	}

	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
}
