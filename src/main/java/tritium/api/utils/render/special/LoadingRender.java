package tritium.api.utils.render.special;

import java.awt.Color;

import me.imflowow.tritium.core.Tritium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import tritium.api.manager.MessageManager.MessageType;
import tritium.api.utils.animate.Opacity;
import tritium.api.utils.render.Image;
import tritium.api.utils.render.Rect;

public class LoadingRender {
	boolean welcome;

	public LoadingRender(boolean welcome) {
		this.welcome = welcome;
	}

	Minecraft mc = Minecraft.getMinecraft();
	Opacity alpha;

	public void draw() {
		//这个draw是负责welcome提示的，会在mainmenu被调用
		if (this.alpha == null) {
			if (welcome){
				Tritium.instance.getMessagemanager().addMessage("Welcome to Tritium!", MessageType.Info, 5000);
			}

			this.alpha = new Opacity(255);
		}
		ScaledResolution sr = new ScaledResolution(mc);

		new Rect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0, 0, 0, (int) alpha.getOpacity()).getRGB(),
				Rect.RenderType.Position).draw();
		GlStateManager.color(1f, 1f, 1f, (float) alpha.getOpacity() / 255f);
		new Image(new ResourceLocation("tritium/icons/logo_white.png"), sr.getScaledWidth() / 2F - 32,
				sr.getScaledHeight() / 2F - 64, 64, 64, Image.RenderType.None).draw();
		GlStateManager.color(1f, 1f, 1f, 1f);
		alpha.interpolate(0, 10);
	}

}
