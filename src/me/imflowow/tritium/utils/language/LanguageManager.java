package me.imflowow.tritium.utils.language;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LanguageManager {
	private String lang = "English";
	private LangUtils textRender = new LangUtils();

	private File directory;

	public LanguageManager(File directory) {
		this.directory = directory;
	}

	public void loadConfig() {
		final File file = new File(this.directory, "language.json");
		if (!file.exists()) {
			return;
		}
		try (Reader reader = new FileReader(file)) {
			final JsonElement node = new JsonParser().parse(reader);
			if (!node.isJsonObject()) {
				return;
			}
			node.getAsJsonObject().entrySet().forEach(data -> {
				switch (data.getKey()) {
				case "lang":
					this.lang = data.getValue().getAsString();
					break;
				}
			});
		} catch (final IOException e) {
		}
	}

	public void saveConfig() {
		File[] files = this.directory.listFiles();
		if (!this.directory.exists()) {
			this.directory.mkdir();
		}
		final File file = new File(this.directory, "Language.json");

		final JsonObject node = new JsonObject();
		node.addProperty("lang", lang);

		if (node.entrySet().isEmpty()) {
			return;
		}
		try {
			file.createNewFile();
		} catch (final IOException e) {
			return;
		}
		try (Writer writer = new FileWriter(file)) {
			writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(node));
		} catch (final IOException e) {
			file.delete();
		}
		files = this.directory.listFiles();
		if ((files == null) || (files.length == 0)) {
			this.directory.delete();
		}
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public LangUtils getTextRender() {
		return textRender;
	}

	public void setTextRender(LangUtils textRender) {
		this.textRender = textRender;
	}

}
