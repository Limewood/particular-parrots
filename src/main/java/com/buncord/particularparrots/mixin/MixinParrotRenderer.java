package com.buncord.particularparrots.mixin;

import com.buncord.particularparrots.ParrotTextures;
import com.buncord.particularparrots.ParticularParrot;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Parrot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Locale;

@Mixin(ParrotRenderer.class)
public class MixinParrotRenderer {
	@Inject(at = @At("HEAD"), method = "getTextureLocation", cancellable = true)
	public void particularParrots_getTextureLocation(Parrot parrot, CallbackInfoReturnable<ResourceLocation> cir) {
		Component name = parrot.getCustomName();
		if (name != null) {
			String lowerName = name.getString().toLowerCase(Locale.ROOT);
			for (ParticularParrot pp : ParticularParrot.values()) {
				if (pp.names.contains(lowerName)) {
					cir.setReturnValue(ParrotTextures.getParrotTextureFor(pp));
					break;
				}
			}
		}
	}
}
