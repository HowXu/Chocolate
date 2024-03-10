package me.imflowow.tritium.utils.netease.mcac;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.digest.DigestUtils;


import me.imflowow.tritium.utils.netease.mcac.HttpUtil;
public class AESUtil {
    private final String KEY;

    public AESUtil(int key) {
        this.KEY = this.genKey(key);
    }

    private String genKey(int a) {
        String base = null;
        try {
            base = HttpUtil.performGetRequest(new URL("http://42.186.61.212:11561")).replace(" ", "");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        long hour = Long.parseLong(new String(Base64.getDecoder().decode(base)));
        Random random = new Random(hour * 2L);
        for (int i = a; i != 0; --i) {
            random.nextInt(63667);
        }
        String key = DigestUtils.md5Hex((String)String.valueOf(random.nextInt(18833)));
        return DigestUtils.md5Hex((String)Base64.getEncoder().encodeToString(key.getBytes()).substring(a / 4, a / 2));
    }

    public String AESEncode(String content) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(this.KEY.getBytes());
            keygen.init(128, random);
            SecretKey original_key = keygen.generateKey();
            byte[] raw = original_key.getEncoded();
            SecretKeySpec key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, key);
            byte[] byte_encode = content.getBytes("utf-8");
            byte[] byte_AES = cipher.doFinal(byte_encode);
            String AES_encode = new String(Base64.getEncoder().encode(byte_AES));
            return AES_encode;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String AESDecode(String content) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(this.KEY.getBytes());
            keygen.init(128, random);
            SecretKey original_key = keygen.generateKey();
            byte[] raw = original_key.getEncoded();
            SecretKeySpec key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, key);
            byte[] byte_content = Base64.getDecoder().decode(content);
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode, "utf-8");
            return AES_decode;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

