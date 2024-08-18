package tritium.api.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;

public class StringUtils {
	public static String removeFormattingCodes(String text) {
		return Pattern.compile("(?i)" + String.valueOf('\u00a7') + "[0-9A-FK-OR]").matcher(text).replaceAll("");
	}

	public static void drawString(String text, double x, double y, int color, SizeType type) {
		Tritium.instance.getLanguagemanager().getTextRender().drawString(text, x, y, color, type);
	}

	public static void drawStringWithShadow(String text, double x, double y, int color, SizeType type) {
		Tritium.instance.getLanguagemanager().getTextRender().drawStringWithShadow(text, x, y, color, type);
	}

	public static void drawOutlineCenteredString(String text, double x, double y, int color, int onlineColor,
			SizeType type) {
		Tritium.instance.getLanguagemanager().getTextRender().drawOutlineCenteredString(text, x, y, color, onlineColor,
				type);
	}

	public static int getWidth(String text, SizeType type) {
		return Tritium.instance.getLanguagemanager().getTextRender().getWidth(text, type);
	}

	public static void drawCenteredStringWithShadow(String text, double x, double y, int color, SizeType type) {
		Tritium.instance.getLanguagemanager().getTextRender().drawCenteredStringWithShadow(text, x, y, color, type);
	}

	public static String getSubString(String text, String left, String right) {
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

	public static String translate(String string) {
		String text = getURLEncoderString(string);
		Map<String, String> header = new HashMap();
		header.put("Connection", " keep-alive");
		header.put("Referer", "http://fanyi.youdao.com/translate");
		header.put("Accept-Language", " zh-CN,zh;q=0.8");
		return (org.apache.commons.lang3.StringUtils.substringAfterLast(HttpUtils.get(
				"http://fanyi.youdao.com//translate?i=" + text + "&type=AUTO&doctype=text&xmlVersion=1.1&keyfrom=360se",
				null, header), "result="));
	}

	public static String getURLEncoderString(String str) {
		String result = "";
		try {
			result = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String URLDecoderString(String str) {
		String result = "";
		try {
			result = URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
