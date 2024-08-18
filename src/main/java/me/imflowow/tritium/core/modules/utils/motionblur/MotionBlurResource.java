
package me.imflowow.tritium.core.modules.utils.motionblur;

import java.io.InputStream;
import java.util.Locale;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.globals.MotionBlur;

public class MotionBlurResource implements IResource {

	public InputStream getInputStream() {
		MotionBlur module = (MotionBlur)Tritium.instance.getModuleManager().getModule(MotionBlur.class);
		double amount = module.multiplier.getValue();
		return IOUtils.toInputStream(String.format(Locale.ENGLISH,
				"{\"targets\":[\"swap\",\"previous\"],\"passes\":[{\"name\":\"phosphor\",\"intarget\":\"minecraft:main\",\"outtarget\":\"swap\",\"auxtargets\":[{\"name\":\"PrevSampler\",\"id\":\"previous\"}],\"uniforms\":[{\"name\":\"Phosphor\",\"values\":[%.2f, %.2f, %.2f]}]},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"previous\"},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"previous\"},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"previous\"},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"minecraft:main\"}]}",
				amount, amount, amount));
	}

	public boolean hasMetadata() {
		return false;
	}

	public IMetadataSection getMetadata(String metadata) {
		return null;
	}

	public ResourceLocation getResourceLocation() {
		return null;
	}

	public String getResourcePackName() {
		return null;
	}
}
