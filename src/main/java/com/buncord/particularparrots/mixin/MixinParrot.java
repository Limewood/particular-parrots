package com.buncord.particularparrots.mixin;

import com.buncord.particularparrots.ParticularParrot;
import com.buncord.particularparrots.init.SoundsInit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Locale;

@Mixin(Parrot.class)
public abstract class MixinParrot extends ShoulderRidingEntity implements FlyingAnimal {
	protected MixinParrot(EntityType<? extends ShoulderRidingEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(at = @At("HEAD"), method = "getAmbientSound", cancellable = true)
	public void particularParrots_getAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
		if (getCustomName() != null) {
			String parrotName = getCustomName().getString().toLowerCase(Locale.ROOT);
			for (ParticularParrot parrot : ParticularParrot.values()) {
				if (parrot.names.contains(parrotName) && level.random.nextInt(parrot.probability) == 0) {
					cir.setReturnValue(SoundsInit.getRandomSoundFrom(parrot, level));
					break;
				}
			}
		}
	}
}
