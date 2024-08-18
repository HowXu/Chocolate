package me.imflowow.tritium.utils.update;

import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.UUID;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.information.Version.VersionType;
import me.imflowow.tritium.utils.netease.mcac.AESUtil;
import tritium.api.utils.HttpUtils;
import tritium.api.utils.StringUtils;

public class MainHook {
	public static void run(String[] p_main_0_) {
		//用http写了一个读取远程版本的东西
		//主进程Hook里添加了版本检查？
		//关掉关掉


		/*
		try {

			String configs = HttpUtils.get("https://gitee.com/LEF-ganga/Tritium/raw/master/ClientInfo", null);

			String latestVersion = getValue(configs, "Version");
			String developmentVersion = getValue(configs, "DevelopmentVersion");
			String downloadlink = getValue(configs, "Link");



			if (Tritium.version.getType() == VersionType.Development) {
				if (!developmentVersion.equals(Tritium.version.toString())) {
					int result = JOptionPane.showConfirmDialog(null, "您当前使用的开发版本版本过低，请使用最新版本。", "版本过低...",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					System.exit(-1);
				}

			} else {
				if (!latestVersion.equals(Tritium.version.toString())) {
					int result = JOptionPane.showConfirmDialog(null,
							"是否更新？\r\n" + "当前版本：" + Tritium.version + "\r\n" + "最新版本：" + latestVersion, "检测到新版本",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == 0) {
						openWebLink(new URI(downloadlink));
						System.exit(-1);
					}
				}

			}
		} catch (Throwable e) {
		}
		 */

	}

	private static String getValue(String config, String key) {
		return getSubString(config, key + ":[", "];");
	}

	private static String getSubString(String text, String left, String right) {
		String result = "";
		int zLen;
		if (left == null || left.isEmpty()) {
			zLen = 0;
		} else {
			zLen = text.indexOf(left);
			if (zLen > -1) {
				zLen += left.length();
			} else {
				zLen = 0;
			}
		}
		int yLen = text.indexOf(right, zLen);
		if (yLen < 0 || right == null || right.isEmpty()) {
			yLen = text.length();
		}
		result = text.substring(zLen, yLen);
		return result;
	}

	public static void openWebLink(URI url) {
		try {
			Class<?> oclass = Class.forName("java.awt.Desktop");
			Object object = oclass.getMethod("getDesktop").invoke((Object) null);
			oclass.getMethod("browse", URI.class).invoke(object, url);
		} catch (Throwable throwable) {
		}
	}
}
