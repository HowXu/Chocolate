package cn.howxu;

import me.imflowow.tritium.core.Tritium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tritium.api.module.Module;

public class RenderWings extends ModelBase {
    private final Minecraft mc;
    private final ResourceLocation location;
    private final ModelRenderer wing;
    private final ModelRenderer wingTip;
    private final boolean playerUsesFullHeight;
    private final Module wingsModule;

    public RenderWings(Module wings) {
        //ELog.log_info("RenderWings init","begin");
        wingsModule = wings;
        this.mc = Minecraft.getMinecraft();
        this.location = new ResourceLocation("tritium/wings.png");
        //ELog.log_info("RenderWings init","res found in " + location.toString());
        //Logger.getLogger("RenderWings Init").log(Level.INFO,"获取res: " + this.location.toString());
        this.playerUsesFullHeight = false; //Loader.isModLoaded("animations");

        //this.wingsModule = Tritium.instance.getModuleManager().getModule("Wings");  //这行有用吗?

        //Logger.getLogger("RenderWings Init").log(Level.INFO,"获取module: " + this.wingsModule.toString());

        // Set texture offsets.
        setTextureOffset("wing.bone", 0, 0);
        setTextureOffset("wing.skin", -10, 8);
        setTextureOffset("wingtip.bone", 0, 5);
        setTextureOffset("wingtip.skin", -10, 18);

        // Create wing model renderer.
        wing = new ModelRenderer(this, "wing");
        wing.setTextureSize(30, 30); // 300px / 10px
        wing.setRotationPoint(-2F, 0, 0);
        wing.addBox("bone", -10.0F, -1.0F, -1.0F, 10, 2, 2);
        wing.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);

        // Create wing tip model renderer.
        wingTip = new ModelRenderer(this, "wingtip");
        wingTip.setTextureSize(30, 30); // 300px / 10px
        wingTip.setRotationPoint(-10.0F, 0.0F, 0.0F);
        wingTip.addBox("bone", -10.0F, -0.5F, -0.5F, 10, 1, 1);
        wingTip.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);
        wing.addChild(wingTip); // Make the wingtip rotate around the wing.
        //ELog.log_info("RenderWings init","finish A" + wing.boxName + "AA  "+ wing.toString());

    }

    public void renderWings(EntityPlayer player, float partialTicks) {

        //正式渲染部分
        //Logger.getLogger("renderWings").log(Level.INFO,"正式渲染");
        //ELog.log_info("renderWings","begin");
        //ELog.log_info("",wingsModule.toString() + "  "+ wingsModule.toString());
        //ELog.log_info("","WTF value " + ((Wings) wingsModule).scale.getValue().doubleValue());

        double scale = ((Wings) wingsModule).scale.getValue().doubleValue() / 100D;
        double rotate = interpolate(player.prevRenderYawOffset, player.renderYawOffset, partialTicks);

        //ELog.log_info("renderWings","scale "+ scale);
        //ELog.log_info("renderWings","rotate " + rotate);
        GL11.glPushMatrix();
        GL11.glScaled(-scale, -scale, scale);
        GL11.glRotated(180 + rotate, 0, 1, 0); // Rotate the wings to be with the player.
        GL11.glTranslated(0, -(playerUsesFullHeight ? 1.45 : 1.25) / scale, 0); // Move wings correct amount up.
        GL11.glTranslated(0, 0, 0.2 / scale);

        if (player.isSneaking()) {
            GL11.glTranslated(0D, 0.125D / scale, 0D);
        }

        float[] colors = ((Wings) wingsModule).getColors();
        GL11.glColor3f(colors[0], colors[1], colors[2]);
        mc.getTextureManager().bindTexture(location);

        for (int j = 0; j < 2; ++j) {
            GL11.glEnable(GL11.GL_CULL_FACE);
            float f11 = (System.currentTimeMillis() % 1000) / 1000F * (float) Math.PI * 2.0F;
            this.wing.rotateAngleX = (float) Math.toRadians(-80F) - (float) Math.cos(f11) * 0.2F;
            this.wing.rotateAngleY = (float) Math.toRadians(20F) + (float) Math.sin(f11) * 0.4F;
            this.wing.rotateAngleZ = (float) Math.toRadians(20F);
            this.wingTip.rotateAngleZ = -((float) (Math.sin(f11 + 2.0F) + 0.5D)) * 0.75F;
            this.wing.render(0.0625F);
            //ELog.log_info("renderWings","medium");
            GL11.glScalef(-1.0F, 1.0F, 1.0F);

            if (j == 0) {
                GL11.glCullFace(1028);
            }
        }
        GL11.glCullFace(1029);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glColor3f(255F, 255F, 255F);
        GL11.glPopMatrix();
        //Logger.getLogger("renderWings").log(Level.INFO,"渲染完成");
        //ELog.log_info("renderWings","finish");
    }

    private float interpolate(float yaw1, float yaw2, float percent) {
        float f = (yaw1 + (yaw2 - yaw1) * percent) % 360;

        if (f < 0) {
            f += 360;
        }

        return f;
    }
}
