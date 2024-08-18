package tritium.netease;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.yggdrasil.response.MinecraftTexturesPayload;
import com.mojang.util.UUIDTypeAdapter;
import me.imflowow.tritium.core.Tritium;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import tritium.api.Wrapper;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

public class SkinHandler {
	public static final SkinHandler skinHandler = new SkinHandler();
	public static HashMap<String, Object> lockObjectMap = new HashMap();
	public static HashMap<String, String> nameSkinMap = new HashMap();
	public static HashMap<String, String> nameCapeMap = new HashMap();
	public static HashMap<String, Boolean> nameSkinMode = new HashMap();
	private static final File assetSkinsDir = new File("./assets/skins");
	private static final int TIMEOUT = 60000;
	private static Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, (Object) new UUIDTypeAdapter())
			.create();
	private static final Cache<String, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>> cache = CacheBuilder
			.newBuilder().expireAfterWrite(30L, TimeUnit.MINUTES).build();
	private static final LoadingCache<GameProfile, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>> skinCacheLoader = CacheBuilder
			.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS)
			.build((CacheLoader) new CacheLoader<GameProfile, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>>() {

				public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> load(GameProfile p_load_1_)
						throws Exception {
					return Minecraft.getMinecraft().getSessionService().getTextures(p_load_1_, false);
				}
			});
	private static ScheduledExecutorService schduler = Executors.newScheduledThreadPool(10);
	private static final String[] WHITELISTED_DOMAINS = new String[] { ".minecraft.net", ".mojang.com", ".163.com",
			".netease.com" };
	private static final Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> defaultReturn = Collections
			.emptyMap();
	private static final Set<GameProfile> onLoading = Collections.newSetFromMap(new ConcurrentHashMap());

	public static String CopySkinToAsset(File f) {
		try {
			if (f == null) {
				return null;
			}
			String sha = DigestUtils.sha256Hex((InputStream) new FileInputStream(f)).toLowerCase();
			File subDir = new File(assetSkinsDir, sha.substring(0, 2));
			subDir.mkdirs();
			File skin = new File(subDir, sha);
			FileUtils.copyFile((File) f, (File) skin);
			return "http://127.0.0.1/" + sha;
		} catch (Exception ex) {
			return null;
		}
	}

	/*
	 * WARNING - Removed try catching itself - possible behaviour change.
	 */
	public static Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTexturesWrapper(GameProfile profile,
			boolean requireSecure) {
		String url;
		if (profile == null) {
			return SkinHandler.getTextures(profile, requireSecure);
		}
		Thread current = Thread.currentThread();
		if (current.getName().contains("Client")) {
			Exception ex = new Exception();
			StringWriter result = new StringWriter();
			PrintWriter printWriter = new PrintWriter(result);
			ex.printStackTrace(printWriter);
			return SkinHandler.getTextures(profile, requireSecure);
		}
		if (profile.getName() == null) {
			return SkinHandler.getTextures(profile, requireSecure);
		}
		String name = profile.getName();
		HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture> resultCache = (HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>) cache
				.getIfPresent((Object) name);
		if (resultCache != null && resultCache.size() != 0) {
			return resultCache;
		}
		UUID uuid = profile.getId();
		if (uuid == null) {
			return SkinHandler.getTextures(profile, requireSecure);
		}
		if (uuid.version() != 4) {
			return SkinHandler.getTextures(profile, requireSecure);
		}
		resultCache = new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
		String LocalSkinUrl = null;
		String LocalCapeUrl = null;
		String username = profile.getName();
		Minecraft mc = Minecraft.getMinecraft();
		Object object = new Object();
		lockObjectMap.put(username, object);
		System.out.println("111");
		if (Tritium.instance.getClientListener().skinhandler != null
				&& Tritium.instance.getClientListener().skinhandler.socket.isConnected()
				&& !Tritium.instance.getClientListener().skinhandler.socket.isClosed()) {
			Tritium.instance.getClientListener().skinhandler
					.send("TritiumNetease|SkinHandler|" + username + "|" + profile.getId().toString());
			System.out.println("222");
		}
		Object object2 = lockObjectMap.get(username);
		synchronized (object2) {
			try {
				lockObjectMap.get(username).wait(60000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lockObjectMap.remove(username);
		if (nameSkinMap.containsKey(profile.getName())) {
			LocalSkinUrl = nameSkinMap.get(username);
		}
		if (nameCapeMap.containsKey(profile.getName())) {
			LocalCapeUrl = nameCapeMap.get(username);
		}
		if (LocalSkinUrl != null && LocalSkinUrl != "") {
			url = SkinHandler.CopySkinToAsset(new File(LocalSkinUrl));
			if (url != null) {
				boolean isSlim = nameSkinMode.containsKey(name) ? nameSkinMode.get(name) : false;
				HashMap<String, String> modelmap = null;
				if (isSlim) {
					modelmap = new HashMap<String, String>() {
						{
							this.put("model", "slim");
						}
					};
				}
				resultCache.put(MinecraftProfileTexture.Type.SKIN, new MinecraftProfileTexture(url, (Map) modelmap));
			}
		}
		if (LocalCapeUrl != null && LocalCapeUrl != "") {
			url = SkinHandler.CopySkinToAsset(new File(LocalCapeUrl));
			if (url != null) {
				resultCache.put(MinecraftProfileTexture.Type.CAPE, new MinecraftProfileTexture(url, null));
			}
		}
		cache.put((String) name, resultCache);
		return resultCache;
	}

	private static boolean isWhitelistedDomain(String url) {
		URI uri = null;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Invalid URL '" + url + "'");
		}
		String domain = uri.getHost();
		for (int i = 0; i < WHITELISTED_DOMAINS.length; ++i) {
			if (!domain.endsWith(WHITELISTED_DOMAINS[i]))
				continue;
			return true;
		}
		return false;
	}

	public static Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures(GameProfile profile,
			boolean requireSecure) {
		MinecraftTexturesPayload result;
		Property textureProperty = (Property) Iterables
				.getFirst((Iterable) profile.getProperties().get((String) "textures"), null);
		Minecraft mc = Minecraft.getMinecraft();
		if (textureProperty == null) {
			return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
		}
		try {
			String json = new String(Base64.decodeBase64((String) textureProperty.getValue()), Charsets.UTF_8);
			result = (MinecraftTexturesPayload) gson.fromJson(json, MinecraftTexturesPayload.class);
		} catch (JsonParseException e) {
			return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
		}
		if (result.getTextures() == null) {
			return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
		}
		for (Map.Entry entry : result.getTextures().entrySet()) {
			if (SkinHandler.isWhitelistedDomain(((MinecraftProfileTexture) entry.getValue()).getUrl()))
				continue;
			return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
		}
		return result.getTextures();
	}

	public static Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> loadSkinFromCacheWrapper(
			final GameProfile gp) {

		if (onLoading.contains(gp)) {
			return defaultReturn;
		}
		Map ret = (Map) skinCacheLoader.getIfPresent((Object) gp);
		if (ret == null) {
			onLoading.add(gp);
			String name = gp.getName() == null ? gp.toString() : gp.getName();
			new Thread(new Runnable() {

				@Override
				public void run() {
					skinCacheLoader.getUnchecked((GameProfile) gp);
					onLoading.remove(gp);
				}
			}, "Skin-Fetch-" + name).start();
			return defaultReturn;
		}
		return ret;
	}

	public static void loadSkullTexture(final GameProfile profile, final NBTTagCompound target) {
		target.removeTag("SkullOwner");
		schduler.schedule(new Runnable() {

			@Override
			public void run() {
				final GameProfile prof = TileEntitySkull.updateGameprofile((GameProfile) profile);
				Minecraft.getMinecraft().addScheduledTask(new Runnable() {

					@Override
					public void run() {
						target.setTag("SkullOwner", (NBTBase) NBTUtil
								.writeGameProfile((NBTTagCompound) new NBTTagCompound(), (GameProfile) prof));
					}
				});
			}
		}, 1L, TimeUnit.MILLISECONDS);
	}
}
