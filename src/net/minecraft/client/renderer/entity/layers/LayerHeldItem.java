package net.minecraft.client.renderer.entity.layers;

import static net.minecraft.init.Items.fishing_rod;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.modules.OldAnimations;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class LayerHeldItem implements LayerRenderer<EntityLivingBase> {
	private final RendererLivingEntity<?> livingEntityRenderer;

	public LayerHeldItem(RendererLivingEntity<?> livingEntityRendererIn) {
		this.livingEntityRenderer = livingEntityRendererIn;
	}

	public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float f1, float f2, float partialTicks, float f3,
			float f4, float f5, float scale) {
		OldAnimations anim = (OldAnimations) Tritium.instance.getModuleManager().getModule(OldAnimations.class);

		ItemStack itemstack = entitylivingbaseIn.getHeldItem();

		if (itemstack != null) {
			GlStateManager.pushMatrix();
			if (livingEntityRenderer.getMainModel().isChild) {
				GlStateManager.translate(0.0f, 0.625f, 0.0f);
				GlStateManager.rotate(-20.0f, -1.0f, 0.0f, 0.0f);
				GlStateManager.scale(0.5f, 0.5f, 0.5f);
			}

			itemsLabel: if (entitylivingbaseIn instanceof EntityPlayer) {
				if (anim.blockPosition.getValue()) {
					if (((EntityPlayer) entitylivingbaseIn).isBlocking()) {
						float itemScale = 0.0325f;

						float glScale = 1.05f;
						float glAngle = 24405.0f;
						float glAngleX = 137290.0f;
						float glAngleY = 2009900.0f;
						float glAngleZ = 2654900.0f;

						if (entitylivingbaseIn.isSneaking()) {
							((ModelBiped) livingEntityRenderer.getMainModel()).postRenderArm(itemScale);
							GlStateManager.scale(glScale, glScale, glScale);
							GlStateManager.translate(-0.58f, 0.32f, -0.07f);
							GlStateManager.rotate(-glAngle, glAngleX, -glAngleY, -glAngleZ);
						} else {
							((ModelBiped) livingEntityRenderer.getMainModel()).postRenderArm(itemScale);
							GlStateManager.scale(glScale, glScale, glScale);
							GlStateManager.translate(-0.45f, 0.25f, -0.07f);
							GlStateManager.rotate(-glAngle, glAngleX, -glAngleY, -glAngleZ);
						}
					} else {
						((ModelBiped) livingEntityRenderer.getMainModel()).postRenderArm(0.0625f);
					}
				} else {
					((ModelBiped) livingEntityRenderer.getMainModel()).postRenderArm(0.0625f);
				}

				if (!anim.itemPosition.getValue()) {
					GlStateManager.translate(-0.0625f, 0.4375f, 0.0625f);
				} else {
					if (!((EntityPlayer) entitylivingbaseIn).isBlocking()) {
						if (anim.itemPosition.getValue()) {
							GlStateManager.translate(-0.0855f, 0.4775f, 0.1585f);
							GlStateManager.rotate(-19.0f, 20.0f, 0.0f, -6.0f);
							break itemsLabel;
						}
					}

					if (((EntityPlayer) entitylivingbaseIn).isBlocking()) {
						GlStateManager.translate(-0.0625f, 0.4375f, 0.0625f);
					}
				}
			} else {
				((ModelBiped) livingEntityRenderer.getMainModel()).postRenderArm(0.0625f);
				GlStateManager.translate(-0.0625f, 0.4375f, 0.0625f);
			}

			if (entitylivingbaseIn instanceof EntityPlayer && ((EntityPlayer) entitylivingbaseIn).fishEntity != null) {
				itemstack = new ItemStack(fishing_rod, 0);
			}

			Item item = itemstack.getItem();
			Minecraft mc = Minecraft.getMinecraft();

			if (item instanceof ItemBlock && Block.getBlockFromItem(item).getRenderType() == 2) {
				GlStateManager.translate(0.0f, 0.1875f, -0.3125f);
				GlStateManager.rotate(20.0f, 1.0f, 0.0f, 0.0f);
				GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
				GlStateManager.scale(-0.375f, -0.375f, 0.375f);
			}

			if (entitylivingbaseIn.isSneaking()) {
				GlStateManager.translate(0.0f, 0.203125f, 0.0f);
			}

			mc.getItemRenderer().renderItem(entitylivingbaseIn, itemstack,
					ItemCameraTransforms.TransformType.THIRD_PERSON);
			GlStateManager.popMatrix();
		}
	}

	public boolean shouldCombineTextures() {
		return false;
	}
}
