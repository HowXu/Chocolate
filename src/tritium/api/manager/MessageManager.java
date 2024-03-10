package tritium.api.manager;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import me.imflowow.tritium.client.ui.clickgui.ClickGui;
import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.globals.ClientConfig;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import net.minecraft.client.gui.ScaledResolution;
import tritium.api.utils.StringUtils;
import tritium.api.utils.animate.Translate;
import tritium.api.utils.event.api.EventManager;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.GLRenderEvent;
import tritium.api.utils.render.Rect;
import tritium.api.utils.timer.MsTimer;

public class MessageManager {

	List<Message> list;

	public MessageManager() {
		EventManager.register(this);
		this.list = new ArrayList();
	}

	@EventTarget
	public void onGLRender(GLRenderEvent event) {
		ScaledResolution sr = event.getScaledResolution();
		int y = 0;
		int commandbox = Tritium.instance.getClientListener().getClickGui().IsOnCommandBox() ? 10 : 0;
		for (Message message : this.list) {
			double diff = message.draw(sr.getScaledWidth(), sr.getScaledHeight(), y + 16 + 2 + commandbox , y);
			y += diff - commandbox;
		}
		this.list.removeIf(message -> message.shouldRemove());
	}

	public void addMessage(String message, MessageType type, int outtime, int color) {
		this.list.add(new Message(message, type, outtime, color));
	}

	public void addMessage(String message, MessageType type, int outtime) {
		this.list.add(new Message(message, type, outtime));
	}

	public enum MessageType {
		Info, Help, Warnning, Error, Wrong, Right;
	}

	public static int getColor(int type) {
		ClientConfig config = (ClientConfig) Tritium.instance.getModuleManager().getModule(ClientConfig.class);
		switch (config.theme.getValue()) {
		case Dark:
			switch (type) {
			case 0:
				return new Color(23, 32, 42).getRGB();// Rect
			case 1:
				return new Color(255, 255, 255).getRGB();// Text
			}
			break;
		case Light:
			switch (type) {
			case 0:
				return new Color(242, 243, 244).getRGB();// Rect
			case 1:
				return new Color(23, 32, 42).getRGB();// Text
			}
			break;
		}
		return 0;
	}

	public class Message {
		String message;
		MessageType type;
		Translate anim;
		MsTimer timer;
		int outtime;
		int color;

		public Message(String message, MessageType type, int outtime, int color) {
			this.message = message;
			this.type = type;
			this.outtime = outtime;
			this.anim = new Translate(0, 0);
			this.timer = new MsTimer();
			this.color = color;
		}

		public Message(String message, MessageType type, int outtime) {
			this.message = message;
			this.type = type;
			this.outtime = outtime;
			this.anim = new Translate(0, 0);
			this.timer = new MsTimer();
			this.color = -1;
		}

		public double draw(double x, double y, double targetY, double diff) {
			int width = 16 + 2 + StringUtils.getWidth(this.message, SizeType.Size18) + 2;
			new Rect(x - this.anim.getX(), y - this.anim.getY(), width, 16, getColor(0), Rect.RenderType.Expand).draw();
			String str;
			Tritium.instance.getFontManager().logo24.drawString(this.getTypeString(), x - this.anim.getX() + 2,
					y + 5 - this.anim.getY(), getColor(1));
			StringUtils.drawString(this.message, x - this.anim.getX() + 2 + 16,
					y + 5 - this.anim.getY(), color == -1 ? getColor(1) : color, SizeType.Size18);

			if (timer.reach(outtime)) {
				this.anim.interpolate(0, 0, 0.3f);
			} else {
				this.anim.interpolate(width, (float) targetY, 0.3f);
			}

			return this.anim.getY() - diff;
		}

		public String getTypeString() {
			if (this.type == null)
				return "";
			switch (this.type) {
			case Error:
				return "o";
			case Help:
				return "m";
			case Info:
				return "n";
			case Right:
				return "r";
			case Warnning:
				return "l";
			case Wrong:
				return "q";
			default:
				return "";
			}
		}

		public boolean shouldRemove() {
			if (timer.reach(outtime) && this.anim.getX() == 0 && this.anim.getY() == 0) {
				return true;
			}
			return false;
		}
	}
}
