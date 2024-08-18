package me.imflowow.tritium.core.globals;

import me.imflowow.tritium.core.globals.utils.AnimationHandler;
import tritium.api.module.GlobalModule;
import tritium.api.module.value.impl.BooleanValue;
import tritium.api.module.value.impl.EnumValue;

public class ChunkAnimator extends GlobalModule {
	
	public AnimationHandler animation = new AnimationHandler();

	public ChunkAnimator() {
		super("ChunkAnimator","Mum!I have ChunkAnimator!");
	}


}
