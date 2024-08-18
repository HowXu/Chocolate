package me.imflowow.tritium.core.modules.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class DamageParticle extends EntityFX {

	protected static final float GRAVITY = 0.1F;
	protected static final float SIZE = 3.0F;
	protected static final int LIFESPAN = 12;
	protected static final double BOUNCE_STRENGTH = 1.5F;

	protected String text;
	protected boolean shouldOnTop = true;
	protected boolean grow = true;
	protected float scale = 1.0F;
	private int damage;

	public DamageParticle(int damage, World world, double parX, double parY, double parZ, double parMotionX, double parMotionY, double parMotionZ) {
		super(world, parX, parY, parZ, parMotionX, parMotionY, parMotionZ);
		particleTextureJitterX = 0.0F;
		particleTextureJitterY = 0.0F;
		particleGravity = GRAVITY;
		particleScale = SIZE;
		particleMaxAge = LIFESPAN;
		this.damage = damage;
		this.text = Integer.toString(Math.abs(damage));
	}

	protected DamageParticle(World worldIn, double posXIn, double posYIn, double posZIn) {
		this(0, worldIn, posXIn, posYIn, posZIn, 0, 0, 0);
	}

	@Override
	public void renderParticle(WorldRenderer worldRendererIn, final Entity entity, final float x, final float y, final float z, final float dX, final float dY, final float dZ) {
		float rotationYaw = (-Minecraft.getMinecraft().thePlayer.rotationYaw);
		float rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;

		final float locX = ((float) (this.prevPosX + (this.posX - this.prevPosX) * x - interpPosX));
		final float locY = ((float) (this.prevPosY + (this.posY - this.prevPosY) * y - interpPosY));
		final float locZ = ((float) (this.prevPosZ + (this.posZ - this.prevPosZ) * z - interpPosZ));

		GL11.glPushMatrix();
		if (this.shouldOnTop) {
			GL11.glDepthFunc(519);
		} else {
			GL11.glDepthFunc(515);
		}
		GL11.glTranslatef(locX, locY, locZ);
		GL11.glRotatef(rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(rotationPitch, 1.0F, 0.0F, 0.0F);

		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		GL11.glScaled(this.particleScale * 0.008D, this.particleScale * 0.008D, this.particleScale * 0.008D);
		GL11.glScaled(this.scale, this.scale, this.scale);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 0.003662109F);
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDepthMask(true);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDisable(2896);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glEnable(3008);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		int color = 0xff0000;
		if (damage < 0) {
			color = 0x00ff00;
		}

		final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
		fontRenderer.drawStringWithShadow(this.text, -MathHelper.floor_float(fontRenderer.getStringWidth(this.text) / 2.0F) + 1, -MathHelper.floor_float(fontRenderer.FONT_HEIGHT / 2.0F) + 1, color);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDepthFunc(515);

		GL11.glPopMatrix();
		if (this.grow) {
			this.particleScale *= 1.08F;
			if (this.particleScale > SIZE * 3.0D) {
				this.grow = false;
			}
		} else {
			this.particleScale *= 0.96F;
		}
	}

	public int getFXLayer() {
		return 3;
	}

}
