package com.snackpirate.constructscasting.fluids;

import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.fluid.UnplaceableFluid;
import slimeknights.mantle.fluid.texture.AbstractFluidTextureProvider;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.data.FluidBucketModelProvider;

public class CCFluids {
	public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(ConstructsCasting.MOD_ID);

	public static final FluidObject<UnplaceableFluid> arcaneEssence = FLUIDS.register("arcane_essence").bucket().type(FluidType.Properties.create()
					.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
					.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY))
					.unplacable();

	public static final FluidObject<UnplaceableFluid> liquidLightning = FLUIDS.register("liquid_lightning").bucket().type(FluidType.Properties.create()
					.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
					.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY))
					.unplacable();

	public static class CCFluidTextures extends AbstractFluidTextureProvider {

		public CCFluidTextures(DataGenerator generator, @Nullable String modId) {
			super(generator, modId);
		}

		@Override
		public void addTextures() {
			texture(arcaneEssence).textures(new ResourceLocation("tconstruct:fluid/potion/"), false, false).color(0xFF79C0F3);
			texture(liquidLightning).textures(new ResourceLocation("tconstruct:fluid/potion/"), false, false).color(0xffd7eef5);
		}

		@Override
		public String getName() {
			return "Construct's Casting's Fluid Textures";
		}
	}
	public static class CCBucketModels extends FluidBucketModelProvider {
		public CCBucketModels(DataGenerator generator, String modId) {
			super(generator, modId);
		}

	}
}
