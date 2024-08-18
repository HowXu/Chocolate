package me.imflowow.tritium.utils.netease;

import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.imflowow.tritium.utils.ServerUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import tritium.api.utils.event.api.EventManager;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.api.types.Priority;
import tritium.api.utils.event.events.PacketEvent;
import tritium.api.utils.event.events.TickEvent;
public class DoMCerUtils {
	public HashMap md5Map = new HashMap();
	public byte[] keyBytes = null;

	public DoMCerUtils() {
		EventManager.register(this);
		this.initMD5Map();
	}

	public void sendData(String str) {
		if (str != null) {
			try {
				this.sendData(str.getBytes("utf-8"));
			} catch (UnsupportedEncodingException var3) {
				this.sendData(str.getBytes());
			}

		}
	}

	public void sendData(byte[] bytes) {
		if (bytes != null) {
			ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
			C17PacketCustomPayload packet = new C17PacketCustomPayload("FuckCheater", new PacketBuffer(byteBuf));
			Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
		}
	}

	@EventTarget
	public void onPacket(PacketEvent event) {
		if (!ServerUtils.isDoMCer()) {
			return;
		}
		if (event.getPacket() instanceof S3FPacketCustomPayload) {
			S3FPacketCustomPayload packet = (S3FPacketCustomPayload) event.getPacket();
			this.verify(packet);
		}

	}

	public void verify(S3FPacketCustomPayload packet) {
		String str1 = "";
		if (packet.getBufferData().array().length == 8) {
			keyBytes = new byte[8];
			HashMap hashMap = md5Map;
			int a4 = 0;
			byte[] var8;
			int var7 = (var8 = packet.getBufferData().array()).length;

			for (int var6 = 0; var6 < var7; ++var6) {
				int a5 = var8[var6];
				if (a5 != 0) {
					int n = a4++;
					keyBytes[n] = (byte) a5;
				}
			}

			String str = new String(keyBytes);
			Iterator var15 = hashMap.keySet().iterator();

			while (var15.hasNext()) {
				String str3 = (String) var15.next();
				String str4 = (String) hashMap.get(str3);
				str4 = (new StringBuilder()).insert(0, str).append(":").append(str4).toString();

				try {
					MessageDigest messageDigest = MessageDigest.getInstance("MD5");
					messageDigest.update(str4.getBytes());
					String str5 = (new BigInteger(1, messageDigest.digest())).toString(16);

					for (int k = 32 - str5.length(); k > 0; --k) {
						str5 = (new StringBuilder()).insert(0, "0").append(str5).toString();
					}

					str1 = (new StringBuilder()).insert(0, str1).append(str5).append(",").toString();
				} catch (NoSuchAlgorithmException var12) {
					var12.printStackTrace();
				}
			}

			this.sendData(this.AES_E(str1));
		}
	}

	public void initMD5Map() {
		md5Map.put("1.8.9.jar", "1a350638e57490a52884a59dd9345077");
		md5Map.put("4618419806295460477@3@0.jar", "26a425b1988cc9f2600bb95ac5e23bf3");
		md5Map.put("4618424574399199550@3@0.jar", "e2debac985313968e48a6ccc2f8a5a03");
		md5Map.put("4618827437296985101@3@0.jar", "f1d383f387f4ca94f45132f845f8b0a8");
		md5Map.put("4620273813159696403@3@0.jar", "8fcd22a5291635330d047da768b1101b");
		md5Map.put("4620273813196076442@3@0.jar", "bdfc9f52d05da04c72faf297de70a5a7");
		md5Map.put("4620273813222949778@3@0.jar", "190b6f49fb735f86d866cec0090ccf76");
		md5Map.put("4620608833856825631@3@0.jar", "7d14d10e04bddc2357169a5716939f99");
		md5Map.put("4620702952524438419@3@0.jar", "c33402f43defafd72b99fd6dfb3d30d8");
		md5Map.put("4624103992226684617@3@0.jar", "423ed3260ca0cdb61dd47db2a7b86381");
		md5Map.put("4626894634154779079@3@0.jar", "66b43dfecbbabff04db0628f52273ce6");
		md5Map.put("4640208077513769652@3@0.jar", "156747f22ea28023b97fe8d2866fa5e9");
		md5Map.put("4645205035030434654@3@0.jar", "3d911d1414025a67cf273e1ffc08891c");
		md5Map.put("4647071150181039341@2@8.jar", "603f2ade23d5da9aafa439a20c864561");
		md5Map.put("4647071150187486919@2@8.jar", "1e6b9eee4a27fa30c86b5f7e71b16e68");
		md5Map.put("4647071150194172444@2@8.jar", "66b899589a81b92b95d2d8a8ce0faf93");
		md5Map.put("4647071150200993316@2@8.jar", "0b037fcb84384bd324feb80bc8be2cf5");
		md5Map.put("4647071150207880932@2@8.jar", "adfc722c7fc5f4e31d24b3ff412bfdc9");
		md5Map.put("4647071150215399507@2@8.jar", "d9522fbe154ea97395cb0fd7ed3b0042");
		md5Map.put("4647071150222447106@2@8.jar", "5b9464376082bb8f179374490232a5f3");
		md5Map.put("4647071150228885567@2@8.jar", "2332fcdb59ca0e4ee79cddf8fe114d25");
		md5Map.put("4647071150236938828@2@8.jar", "d2af3615bdbb930219eec8acc3979b8e");
		md5Map.put("4647071150244820343@2@8.jar", "eae417f50728840b31674f0c323077bf");
		md5Map.put("4647071150251700892@2@8.jar", "f5ddbbb18a8e27fa2806816f7029893b");
		md5Map.put("4647071150259558934@2@8.jar", "83dc65e8cf540c9dd7d3317f8bf7819a");
		md5Map.put("4647071150266663035@2@8.jar", "cc03bb6e904342dce232c3cee264c626");
		md5Map.put("4647071150273210002@2@8.jar", "2add0c39e13f179ba471c2a2ffe61eca");
		md5Map.put("4647071150280141132@2@8.jar", "99492e82e18f055dc2d5fe9d31944d27");
	}

	@EventTarget(Priority.HIGHEST)
	public void HeartbeatPacket(TickEvent event) {
		if (ServerUtils.isDoMCer()) {
			if (keyBytes != null && keyBytes.length == 8) {
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("type", new Integer(2));
				jsonObject.addProperty("time", new Long(System.currentTimeMillis()));
				jsonObject.addProperty("click", new Integer(1));
				this.sendData(this.AES_E(jsonObject.toString()));
			}
		}
	}

	public byte[] AES_E(String str) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128, new SecureRandom(keyBytes));
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] arrayOfByte1 = secretKey.getEncoded();
			SecretKeySpec secretKeySpec = new SecretKeySpec(arrayOfByte1, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(1, secretKeySpec);
			byte[] arrayOfByte2 = cipher.doFinal(str.getBytes("utf-8"));
			StringBuffer stringBuffer = new StringBuffer();

			for (int k = 0; k < arrayOfByte2.length; ++k) {
				String str1 = Integer.toHexString(arrayOfByte2[k] & 255);
				if (str1.length() == 1) {
					str1 = '0' + str1;
				}

				stringBuffer.append(str1.toUpperCase());
			}

			return stringBuffer.toString().getBytes();
		} catch (Exception var11) {
			return null;
		}
	}

}
