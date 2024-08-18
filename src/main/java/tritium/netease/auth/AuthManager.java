package tritium.netease.auth;

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

import tritium.api.module.Module;
import tritium.api.module.value.Value;

public class AuthManager {
	private AuthUtils auth = new AuthUtils();
	private int authtype = 0;

	private File directory;

	public AuthManager(File directory) {
		this.directory = directory;
	}

	public void loadConfig() {
		final File file = new File(this.directory, "auth.json");
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
				case "authtype":
					this.authtype = data.getValue().getAsInt();
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
		final File file = new File(this.directory, "Auth.json");

		final JsonObject node = new JsonObject();
		node.addProperty("authtype", authtype);

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

	public AuthUtils getAuth() {
		return auth;
	}

	public void setAuth(AuthUtils auth) {
		this.auth = auth;
	}

	public int getAuthtype() {
		return authtype;
	}

	public void setAuthtype(int authtype) {
		this.authtype = authtype;
	}

}
