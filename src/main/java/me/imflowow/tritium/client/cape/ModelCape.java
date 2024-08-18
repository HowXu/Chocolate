package me.imflowow.tritium.client.cape;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ModelCape
extends ModelBase {
    private ModelRenderer cape = new ModelRenderer(this, 0, 0);

    public ModelCape() {
        this.cape.setTextureSize(64, 32);
        this.cape.addBox(-5.0f, 0.0f, -1.0f, 10, 16, 1);
    }

    @Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.cape.render(scale);
    }

    @Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        EntityPlayer livingEntity = (EntityPlayer)entityIn;
        if (livingEntity.getCurrentArmor(2) != null) {
            if (livingEntity.isSneaking()) {
                this.cape.rotationPointZ = 0.8f;
                this.cape.rotationPointY = 1.85f;
            } else {
                this.cape.rotationPointZ = -1.1f;
                this.cape.rotationPointY = 0.0f;
            }
        } else if (livingEntity.isSneaking()) {
            this.cape.rotationPointZ = 1.0f;
            this.cape.rotationPointY = 1.2f;
        } else {
            this.cape.rotationPointZ = 0.0f;
            this.cape.rotationPointY = 0.0f;
        }
    }
}

