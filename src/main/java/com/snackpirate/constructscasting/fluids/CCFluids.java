package com.snackpirate.constructscasting.fluids;

import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.fluid.UnplaceableFluid;
import slimeknights.mantle.fluid.texture.AbstractFluidTextureProvider;
import slimeknights.mantle.fluid.texture.FluidTexture;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.fluids.data.FluidBucketModelProvider;

public class CCFluids {
	public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(ConstructsCasting.MOD_ID);

	public static final FluidObject<UnplaceableFluid> arcaneEssence    = essence("arcane_essence"   );
	public static final FluidObject<UnplaceableFluid> fireEssence      = essence("fire_essence"     );
	public static final FluidObject<UnplaceableFluid> iceEssence       = essence("ice_essence"      );
	public static final FluidObject<UnplaceableFluid> lightningEssence = essence("lightning_essence");
	public static final FluidObject<UnplaceableFluid> enderEssence     = essence("ender_essence"    );
	public static final FluidObject<UnplaceableFluid> holyEssence      = essence("holy_essence"     );
	public static final FluidObject<UnplaceableFluid> bloodEssence     = essence("blood_essence"    );
	public static final FluidObject<UnplaceableFluid> evocationEssence = essence("evocation_essence");
	public static final FluidObject<UnplaceableFluid> natureEssence    = essence("nature_essence"   );

	public static final FluidObject<UnplaceableFluid> liquidLightning = FLUIDS.register("liquid_lightning").type(FluidType.Properties.create().sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)).unplacable();

	public static final FluidObject<UnplaceableFluid> liquidDivinity = FLUIDS.register("liquid_divinity").bucket().type(FluidType.Properties.create().sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)).unplacable();

	public static FlowingFluidObject<ForgeFlowingFluid> potatoStew = FLUIDS.register("potato_stew").type(cool().temperature(400)).bucket().block(Material.WATER).flowing();
	public static FlowingFluidObject<ForgeFlowingFluid> poisonousPotatoStew = FLUIDS.register("poisonous_potato_stew").type(cool().temperature(400)).bucket().block(Material.WATER).flowing();

	public static FluidObject<UnplaceableFluid> essence(String name) {
		return FLUIDS.register(name)
				.bucket()
				.type(FluidType.Properties.create()
						.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
						.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY))
				.unplacable();
	}
	private static FluidType.Properties cool() {
		return FluidType.Properties.create()
				.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
				.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY);
	}

	public static class CCFluidTextures extends AbstractFluidTextureProvider {

		public CCFluidTextures(DataGenerator generator, @Nullable String modId) {
			super(generator, modId);
		}

		@Override
		public void addTextures() {
			ResourceLocation potion = new ResourceLocation("tconstruct:fluid/potion/");
			texture(arcaneEssence   ).textures(potion, false, false).color(0xff79c0f3);
			texture(fireEssence     ).textures(potion, false, false).color(0xfffc9269);
			texture(iceEssence      ).textures(potion, false, false).color(0xff75f1ec);
			texture(lightningEssence).textures(potion, false, false).color(0xff8273e0);
			texture(enderEssence    ).textures(potion, false, false).color(0xffe073fc);
			texture(holyEssence     ).textures(potion, false, false).color(0xfffce969);
			texture(bloodEssence    ).textures(potion, false, false).color(0xffd67369);
			texture(evocationEssence).textures(potion, false, false).color(0xff75fc79);
			texture(natureEssence   ).textures(potion, false, false).color(0xffb0f869);

			texture(liquidLightning ).textures(potion, false, false).color(0xffd7eef5);
			texture(liquidDivinity  ).textures(new ResourceLocation("tconstruct:fluid/slime/venom/" ), false, false).color(0xfffce969);
			texture(potatoStew      ).textures(new ResourceLocation("tconstruct:fluid/food/stew/"), false, false).color(0xffe9ba61);
			texture(poisonousPotatoStew).textures(new ResourceLocation("tconstruct:fluid/food/stew/"), false, false).color(0xffedea61);
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
