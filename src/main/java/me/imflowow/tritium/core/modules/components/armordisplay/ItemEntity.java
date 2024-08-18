package me.imflowow.tritium.core.modules.components.armordisplay;

import org.lwjgl.opengl.GL11;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.modules.ArmorDisplay;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import tritium.api.module.gui.GuiEntity;
import tritium.api.module.value.impl.PositionValue;

public abstract class ItemEntity extends GuiEntity {

	public ItemEntity(PositionValue position) {
		super(position);
	}

	@Override
	public void init() {

	}

	@Override
	public void draw(double x, double y) {

		if (this.getItem() == null)
			return;
		GL11.glPushMatrix();

		RenderItem ir = mc.getRenderItem();

		RenderHelper.enableGUIStandardItemLighting();
		ir.renderItemIntoGUI(this.getItem(), x, y);
		ir.renderItemOverlays(mc.fontRendererObj, this.getItem(), x, y);
		RenderHelper.disableStandardItemLighting();
		int damage = this.getItem().getMaxDamage() - this.getItem().getItemDamage();
		GlStateManager.enableAlpha();
		GlStateManager.disableCull();
		GlStateManager.disableBlend();
		GlStateManager.disableLighting();
		GlStateManager.clear(256);
		if (this.shouldRenderDamage() && damage > 0) {
			Tritium.instance.getFontManager().arial16
					.drawString(String.valueOf(damage) + "/" + this.getItem().getMaxDamage(), x + 15 + 2, y + 6, -1);
		}

		GL11.glPopMatrix();
	}

	@Override
	public int getHeight() {
		return 15;
	}

	@Override
	public int getWidth() {
		int x = 0;

		if (this.shouldRenderDamage() && this.getItem() != null) {
			int damage = this.getItem().getMaxDamage() - this.getItem().getItemDamage();
			if (damage > 0) {
				x += 2 + Tritium.instance.getFontManager().arial16
						.getStringWidth(String.valueOf(damage) + "/" + this.getItem().getMaxDamage());
			}
		}

		return 15 + x;
	}

	public abstract ItemStack getItem();

	public boolean shouldRenderDamage() {
		ArmorDisplay module = (ArmorDisplay) Tritium.instance.getModuleManager().getModule(ArmorDisplay.class);
		if (module == null)
			return false;
		return module.damageValue.getValue();
	}

}
