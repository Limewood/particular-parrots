package com.buncord.particularparrots.mixin;

import com.buncord.particularparrots.ParrotTextures;
import com.buncord.particularparrots.ParticularParrot;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ParrotOnShoulderLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Locale;

@Mixin(ParrotOnShoulderLayer.class)
public abstract class MixinParrotOnShoulderLayer<T extends Player> extends RenderLayer<T, PlayerModel<T>> {
	private static final Logger LOGGER = LogManager.getLogger();

	private static ResourceLocation getDefaultParrotResourceLocation(CompoundTag compoundTag) {
		return ParrotRenderer.PARROT_LOCATIONS[compoundTag.getInt("Variant")];
	}

	public MixinParrotOnShoulderLayer(RenderLayerParent<T, PlayerModel<T>> parent) {
		super(parent);
	}

	@Accessor(value = "model")
	abstract ParrotModel getModel();

	/**
	 * @author Limewood
	 * @reason Need to render correct parrot graphic when on player's shoulder.
	 */
	@Overwrite
	private void render(PoseStack poseStack, MultiBufferSource p_117319_, int p_117320_, T p_117321_, float p_117322_, float p_117323_, float p_117324_, float p_117325_, boolean left) {
		CompoundTag compoundtag = left ? p_117321_.getShoulderEntityLeft() : p_117321_.getShoulderEntityRight();
		EntityType.byString(compoundtag.getString("id")).filter((type) -> type == EntityType.PARROT).ifPresent((entityType) -> {
			poseStack.pushPose();
			poseStack.translate(left ? (double)0.4F : (double)-0.4F, p_117321_.isCrouching() ? (double)-1.3F : -1.5D, 0.0D);
			String name = compoundtag.getString("CustomName");
			Component comp = null;
			try {
				comp = Component.Serializer.fromJson(name);
			} catch (Exception exception) {
				LOGGER.warn("Failed to parse entity custom name {}", name, exception);
			}
			ResourceLocation loc = null;
			if (comp == null) {
				loc = getDefaultParrotResourceLocation(compoundtag);
			} else {
				String parrotName = comp.getString().toLowerCase(Locale.ROOT);
				for (ParticularParrot parrot : ParticularParrot.values()) {
					if (parrot.names.contains(parrotName)) {
						loc = ParrotTextures.getParrotTextureFor(parrot);
						break;
					}
				}
				if (loc == null) {
					loc = getDefaultParrotResourceLocation(compoundtag);
				}
			}
			VertexConsumer vertexconsumer = p_117319_.getBuffer(getModel().renderType(loc));
			getModel().renderOnShoulder(poseStack, vertexconsumer, p_117320_, OverlayTexture.NO_OVERLAY, p_117322_, p_117323_, p_117324_, p_117325_, p_117321_.tickCount);
			poseStack.popPose();
		});
	}
}
