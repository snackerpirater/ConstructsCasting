package com.snackpirate.constructscasting.items;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ModifiableGeoArmorRenderer extends GeoArmorRenderer<ModifiableGeoArmorItem> {
    public <I extends ModifiableGeoArmorItem> ModifiableGeoArmorRenderer(I armorItem) {
        super(armorItem);
    }

    @Override
    public ResourceLocation getTextureLocation(ModifiableGeoArmorItem animatable) {
        return super.getTextureLocation(animatable);
    }
}
