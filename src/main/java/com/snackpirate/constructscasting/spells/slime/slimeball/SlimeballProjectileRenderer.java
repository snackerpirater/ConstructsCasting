package com.snackpirate.constructscasting.spells.slime.slimeball;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;

public class SlimeballProjectileRenderer extends EntityRenderer<SlimeballProjectile> {
	private final ItemRenderer itemRenderer;
	public SlimeballProjectileRenderer(EntityRendererProvider.Context pContext) {
		super(pContext);
		itemRenderer = pContext.getItemRenderer();
	}


	//copy what vanilla snowballs
	@Override
	public void render(SlimeballProjectile entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		if (entity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entity) < 12.25D)) {
			matrixStackIn.pushPose();
			matrixStackIn.mulPose(this.entityRenderDispatcher.cameraOrientation());
			matrixStackIn.mulPose(Axis.YP.rotationDegrees(180.0F));
			this.itemRenderer.renderStatic(Items.SLIME_BALL.getDefaultInstance(), ItemDisplayContext.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn, entity.level(), entity.getId());
			matrixStackIn.popPose();
			super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(SlimeballProjectile slimeballProjectile) {
		return ConstructsCasting.id("texture/entity/slimeball_projectile.png");
	}
}
