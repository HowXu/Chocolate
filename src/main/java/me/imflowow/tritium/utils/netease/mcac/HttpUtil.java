package me.imflowow.tritium.utils.netease.mcac;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;

public class HttpUtil {
    private static HttpURLConnection createUrlConnection(URL url) throws IOException {
        Validate.notNull((Object)url);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);
        connection.setUseCaches(false);
        return connection;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String performGetRequest(URL url) throws IOException {
        String var11;
        Validate.notNull((Object)url);
        HttpURLConnection connection = HttpUtil.createUrlConnection(url);
        InputStream inputStream = null;
        connection.setRequestProperty("User-agent", "Mozilla/5.0 AppIeWebKit");
        try {
            inputStream = connection.getInputStream();
            String string = IOUtils.toString((InputStream)inputStream, (Charset)Charsets.UTF_8);
            return string;
        }
        catch (IOException var10) {
            IOUtils.closeQuietly((InputStream)inputStream);
            inputStream = connection.getErrorStream();
            if (inputStream == null) {
                throw var10;
            }
            var11 = IOUtils.toString((InputStream)inputStream, (Charset)Charsets.UTF_8);
        }
        finally {
            IOUtils.closeQuietly((InputStream)inputStream);
        }
        System.out.println(var11);
        return var11;
    }

    public static String performPostRequest(String Surl, String data) throws IOException {
        String s;
        String urlString = Surl;
        URL url = new URL(urlString);
        URLConnection uc = url.openConnection();
        uc.setDoOutput(true);
        OutputStream os = uc.getOutputStream();
        PrintStream ps = new PrintStream(os);
        ps.print(data);
        ps.close();
        InputStream is = uc.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String result = "";
        while ((s = reader.readLine()) != null) {
            result = result + s;
        }
        reader.close();
        return result;
    }
}

