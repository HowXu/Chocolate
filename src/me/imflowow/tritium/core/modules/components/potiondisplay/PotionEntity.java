package me.imflowow.tritium.core.modules.components.potiondisplay;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import tritium.api.module.gui.GuiEntity;
import tritium.api.module.value.impl.PositionValue;

public class PotionEntity extends GuiEntity {
	private ResourceLocation inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");

	int width;
	int height;

	public PotionEntity(PositionValue position) {
		super(position);
	}

	@Override
	public void init() {
		this.width = 0;
		this.height = 0;
	}

	@Override
	public void draw(double x, double y) {
		int addY = 0;
		for (PotionEffect potioneffect : mc.thePlayer.getActivePotionEffects()) {
			Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(inventoryBackground);

			if (potion.hasStatusIcon()) {
				int i1 = potion.getStatusIconIndex();
				this.mc.ingameGUI.drawTexturedModalRect(x, y + addY, 0 + i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
				String s1 = I18n.format(potion.getName(), new Object[0]);

				if (potioneffect.getAmplifier() == 1) {
					s1 = s1 + " " + I18n.format("enchantment.level.2", new Object[0]);
				} else if (potioneffect.getAmplifier() == 2) {
					s1 = s1 + " " + I18n.format("enchantment.level.3", new Object[0]);
				} else if (potioneffect.getAmplifier() == 3) {
					s1 = s1 + " " + I18n.format("enchantment.level.4", new Object[0]);
				}

				mc.fontRendererObj.drawStringWithShadow(s1, (float) (x + 4 + 18), (float) (y + addY - 1), 16777215);
				String s = Potion.getDurationString(potioneffect);
				mc.fontRendererObj.drawStringWithShadow(s, (float) (x + 4 + 18), (float) (y + addY + 9), 8355711);
				this.width = Math.max(this.width, 22 + mc.fontRendererObj.getStringWidth(s1));
				addY += 33;
			}
		}
		this.height = addY;

	}

	@Override
	public int getHeight() {
		return Math.max(20, this.height);
	}

	@Override
	public int getWidth() {
		return Math.max(20, this.width);
	}

}
