package tritium.netease;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.account.GuiAccountManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.util.Session;

public class TritiumNetease {

	public String type = "";

	public Socket socket;

	public TritiumNetease() {
		socket = new Socket();

		try {
			InetSocketAddress address = new InetSocketAddress("localhost", 48823);
			socket.connect(address, 500);
		} catch (IOException e1) {
		}
		new Thread(null, null, "TritiumNeteaseSocket") {
			@Override
			public void run() {
				try {
					String msg = "";
					while (!(msg = recv()).equals("")) {
						System.out.println(msg);
						String[] args = msg.split("\\|");
						switch (args[1]) {
						case "LoadSkinReply":
							if (!SkinHandler.lockObjectMap.containsKey(args[2])) {
								break;
							}
							Object object1 = SkinHandler.lockObjectMap.get(args[2]);
							synchronized (object1) {
								try {
									SkinHandler.nameSkinMap.put(args[2], args[3]);
									SkinHandler.nameCapeMap.put(args[2], args[4]);
									SkinHandler.lockObjectMap.get(args[2]).notify();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							break;
						case "LoadSkinReplyV2":
							if (!SkinHandler.lockObjectMap.containsKey(args[2])) {
								break;
							}
							Object object2 = SkinHandler.lockObjectMap.get(args[2]);
							synchronized (object2) {
								try {
									SkinHandler.nameSkinMap.put(args[2], args[3]);
									SkinHandler.nameCapeMap.put(args[2], args[4]);
									SkinHandler.nameSkinMode.put(args[2], args[5].equals("1"));
									SkinHandler.lockObjectMap.get(args[2]).notify();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							break;
						case "NeteaseServer":
							Minecraft.getMinecraft().setSession(new Session(args[2], "crazy", args[3], "legacy"));
							GuiAccountManager.serverip = args[4];
							GuiAccountManager.port = args[5];
				    		GuiAccountManager.info =  "§a" +args[4] + ":"+args[5];
				    		GuiAccountManager.infotimer.reset();
				    		Tritium.instance.getClientListener().skinhandler = new TritiumNetease();
				    		Tritium.instance.getClientListener().skinhandler.send("TritiumNetease|Skin");
							break;
						case "successed":
							if (Tritium.instance.getAuthmanager().getAuth().socket == null
									|| Tritium.instance.getAuthmanager().getAuth().socket.socket.isClosed()
									|| !Tritium.instance.getAuthmanager().getAuth().socket.socket.isConnected()) {
								Tritium.instance.getAuthmanager().getAuth().socket = new TritiumNetease();
							}
							type = "successed";
							break;
						case "failed":
							type = "failed";
							break;
						}
					}
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void send(String str) {
		try {
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF(str);
		} catch (Exception e) {
		}
	}

	public String recv() {
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			String string;
			string = input.readUTF();
			return string;
		} catch (Exception e) {
			return "";
		}
	}

	public void disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
