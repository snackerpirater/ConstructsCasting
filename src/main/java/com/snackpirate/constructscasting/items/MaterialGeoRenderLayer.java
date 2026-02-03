package com.snackpirate.constructscasting.items;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class MaterialGeoRenderLayer extends GeoRenderLayer<ModifiableGeoArmorItem> {
    public MaterialGeoRenderLayer(GeoRenderer<ModifiableGeoArmorItem> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    protected ResourceLocation getTextureResource(ModifiableGeoArmorItem animatable) {
        ((ModifiableGeoArmorRenderer) this.getRenderer()).getCurrentStack();
        return super.getTextureResource(animatable);
    }
}
