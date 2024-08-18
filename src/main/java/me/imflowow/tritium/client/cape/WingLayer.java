package me.imflowow.tritium.client.cape;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;

public class WingLayer implements LayerRenderer<AbstractClientPlayer> {
	private final RenderWings wings = new RenderWings();
	private final RenderPlayer playerRenderer;

	public WingLayer(RenderPlayer playerRendererIn) {
		this.playerRenderer = playerRendererIn;
	}

	@Override
	public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float p_177141_2_, float p_177141_3_,
			float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
		//这里是龙翼的渲染位置
		EntityPlayer player = entitylivingbaseIn;
		PlayerHandler playerHandler = PlayerHandler.getFromPlayer(player);

		if (playerHandler.hasWing && playerHandler.settings != null && playerHandler.wing != null
				&& !player.isInvisible()) {
			playerHandler.wing.renderWings(player, playerHandler.settings, partialTicks);
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

}
