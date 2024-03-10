package me.imflowow.tritium.utils.language;

import java.util.HashMap;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.language.lang.ChineseLanguage;
import me.imflowow.tritium.utils.language.lang.JapaneseLanguage;
import me.imflowow.tritium.utils.language.lang.Language;
import tritium.api.utils.font.MCFontRenderer;

public class LangUtils {
	public String lang;
	public HashMap<String, String> langMap;

	public LangUtils() {
		this.langMap = new HashMap();
	}

	public boolean init() {
		this.langMap.clear();
		boolean flag = false;
		Language lang;
		if (this.lang.equalsIgnoreCase("chinese")) {
			lang = new ChineseLanguage();
			this.langMap = lang.load();
			flag = true;
		}
		if (this.lang.equalsIgnoreCase("japanese")) {
			lang = new JapaneseLanguage();
			this.langMap = lang.load();
			flag = true;
		}
		return flag;
	}

	public boolean isEnglish() {
		return this.lang.equalsIgnoreCase("english");
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getText(String text) {
		if (!this.isEnglish()) {
			String str = this.langMap.get(text);
			if (str == null) {
				return text;
			}
			return str;
		}
		return text;
	}

	public void drawString(String text, double x, double y, int color, SizeType type) {
		this.getFontRender(type).drawString(this.getText(text), x, y, color);
	}

	public void drawStringWithShadow(String text, double x, double y, int color, SizeType type) {
		this.getFontRender(type).drawStringWithShadow(this.getText(text), x, y, color);
	}

	public void drawCenteredString(String text, double x, double y, int color, SizeType type) {
		this.getFontRender(type).drawCenteredString(this.getText(text), x, y, color);
	}

	public void drawOutlineCenteredString(String text, double x, double y, int color, int onlineColor, SizeType type) {
		this.getFontRender(type).drawOutlineCenteredString(this.getText(text), x, y, color, onlineColor);
	}

	public void drawCenteredStringWithShadow(String text, double x, double y, int color, SizeType type) {
		this.getFontRender(type).drawCenteredStringWithShadow(this.getText(text), x, y, color);
	}

	public int getWidth(String text, SizeType type) {
		return this.getFontRender(type).getStringWidth(this.getText(text));
	}

	public MCFontRenderer getFontRender(SizeType type) {
		switch (type) {
		case Size14:
			return Tritium.instance.getFontManager().load14;
		case Size16:
			return Tritium.instance.getFontManager().load16;
		case Size18:
			return Tritium.instance.getFontManager().load18;
		default:
			return Tritium.instance.getFontManager().arial18;
		}
	}

	public enum SizeType {
		Size14, Size16, Size18;
	}

}
