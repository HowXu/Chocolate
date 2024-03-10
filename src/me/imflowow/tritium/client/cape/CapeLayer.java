package me.imflowow.tritium.client.cape;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.modules.EnchantEffect;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class CapeLayer implements LayerRenderer<AbstractClientPlayer> {
	protected static final ResourceLocation ENCHANTED_ITEM_GLINT_RES = new ResourceLocation(
			"textures/misc/enchanted_item_glint.png");
	private final ModelCape modelCape = new ModelCape();
	private final RenderPlayer playerRenderer;

	public CapeLayer(RenderPlayer playerRendererIn) {
		this.playerRenderer = playerRendererIn;
	}

	@Override
	public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		PlayerHandler playerHandler = PlayerHandler.getFromPlayer(entitylivingbaseIn);
		if (entitylivingbaseIn.hasPlayerInfo() && !entitylivingbaseIn.isInvisible()
				&& entitylivingbaseIn.isWearing(EnumPlayerModelParts.CAPE) && playerHandler.getCapeLocation() != null
				&& entitylivingbaseIn.getLocationCape() == null) {
			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(1, 0);
			this.playerRenderer.bindTexture(playerHandler.getCapeLocation());
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0f, 0.0f, 0.125f);
			double d0 = entitylivingbaseIn.prevChasingPosX
					+ (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * partialTicks
					- (entitylivingbaseIn.prevPosX
							+ (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * partialTicks);
			double d1 = entitylivingbaseIn.prevChasingPosY
					+ (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * partialTicks
					- (entitylivingbaseIn.prevPosY
							+ (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * partialTicks);
			double d2 = entitylivingbaseIn.prevChasingPosZ
					+ (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * partialTicks
					- (entitylivingbaseIn.prevPosZ
							+ (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * partialTicks);
			float f = entitylivingbaseIn.prevRenderYawOffset
					+ (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks;
			double d3 = MathHelper.sin(f * ((float) Math.PI / 180));
			double d4 = -MathHelper.cos(f * ((float) Math.PI / 180));
			float f1 = (float) d1 * 10.0f;
			f1 = MathHelper.clamp_float(f1, -6.0f, 32.0f);
			float f2 = (float) (d0 * d3 + d2 * d4) * 100.0f;
			float f3 = (float) (d0 * d4 - d2 * d3) * 100.0f;
			if (f2 < 0.0f) {
				f2 = 0.0f;
			}
			float f4 = entitylivingbaseIn.prevCameraYaw
					+ (entitylivingbaseIn.cameraYaw - entitylivingbaseIn.prevCameraYaw) * partialTicks;
			f1 += MathHelper.sin((entitylivingbaseIn.prevDistanceWalkedModified
					+ (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified)
							* partialTicks)
					* 6.0f) * 32.0f * f4;
			if (entitylivingbaseIn.isSneaking()) {
				f1 += 25.0f;
			}
			GlStateManager.rotate(6.0f + f2 / 2.0f + f1, 1.0f, 0.0f, 0.0f);
			GlStateManager.rotate(f3 / 2.0f, 0.0f, 0.0f, 1.0f);
			GlStateManager.rotate(-f3 / 2.0f, 0.0f, 1.0f, 0.0f);
			GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
			this.modelCape.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale,
					entitylivingbaseIn);
			this.modelCape.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw,
					headPitch, scale);
			if (playerHandler.hasCapeGlint) {
				this.renderEchantmentGlint(entitylivingbaseIn, this.modelCape, limbSwing,
						limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
			}
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}

	private void renderEchantmentGlint(EntityLivingBase entitylivingbaseIn, ModelBase modelbaseIn, float p_177183_3_,
			float p_177183_4_, float p_177183_5_, float p_177183_6_, float p_177183_7_, float p_177183_8_,
			float p_177183_9_) {
		float f = entitylivingbaseIn.ticksExisted + p_177183_5_;
		this.playerRenderer.bindTexture(ENCHANTED_ITEM_GLINT_RES);
		GlStateManager.enableBlend();
		GlStateManager.depthFunc(514);
		GlStateManager.depthMask(false);
		float f1 = 0.5f;
		GlStateManager.color(f1, f1, f1, 1.0f);
		for (int i = 0; i < 2; ++i) {
			GlStateManager.disableLighting();
			GlStateManager.blendFunc(768, 1);
			float f2 = 0.76f;
			GlStateManager.color(0.5f * f2, 0.25f * f2, 0.8f * f2, 1.0f);
			GlStateManager.matrixMode(5890);
			GlStateManager.loadIdentity();
			float f3 = 0.33333334f;
			GlStateManager.scale(f3, f3, f3);
			GlStateManager.rotate(30.0f - i * 60.0f, 0.0f, 0.0f, 1.0f);
			GlStateManager.translate(0.0f, f * (0.001f + i * 0.003f) * 20.0f, 0.0f);
			GlStateManager.matrixMode(5888);
			EnchantEffect module = (EnchantEffect) Tritium.instance.getModuleManager().getModule(EnchantEffect.class);
			if (module.isEnabled())
				GlStateManager.color(module.color.getValue().getColor().getRed() / 255.0f,
						module.color.getValue().getColor().getGreen() / 255.0f,
						module.color.getValue().getColor().getBlue() / 255.0f,
						module.color.getValue().getColor().getAlpha() / 255.0f);
			modelbaseIn.render(entitylivingbaseIn, p_177183_3_, p_177183_4_, p_177183_6_, p_177183_7_,
					p_177183_8_, p_177183_9_);
		}
		GlStateManager.matrixMode(5890);
		GlStateManager.loadIdentity();
		GlStateManager.matrixMode(5888);
		GlStateManager.enableLighting();
		GlStateManager.depthMask(true);
		GlStateManager.depthFunc(515);
		GlStateManager.disableBlend();
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}
