package com.snackpirate.constructscasting.compat.metalborn;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.fluids.CCFluids;
import com.snackpirate.constructscasting.materials.CCMaterials;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import knightminer.metalborn.metal.AbstractMetalPowerProvider;
import knightminer.metalborn.metal.MetalFormat;
import knightminer.metalborn.metal.MetalId;
import knightminer.metalborn.metal.effects.general.AttributeMetalEffect;
import knightminer.metalborn.metal.effects.nesting.CappedMetalEffect;
import knightminer.metalborn.metal.effects.nesting.StoringMetalEffect;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.concurrent.CompletableFuture;

public class MetalbornCompat {
	public static final MetalId arcanium = id("arcanium");
	public static final MetalId exilite = id("exilite");
	public static final MetalId mithril = id("mithril");
	public static final MetalId pyrium = id("pyrium");
	private MetalbornCompat() {}

	public static void init() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(MetalbornCompat::datagen);
	}

	private static void datagen(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();
		boolean server = event.includeServer();
		PackOutput output = gen.getPackOutput();
		ExistingFileHelper fileHelper = event.getExistingFileHelper();
		CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();

		gen.addProvider(server, new MetalPowerProvider(output));
	}

	private static class MetalPowerProvider extends AbstractMetalPowerProvider
	{

		public MetalPowerProvider(PackOutput output) {
			super(output);
		}

		/**
		 *
		 */
		@Override
		public void addMetals() {
			metal(arcanium)
					.ingot(ItemTags.create(ResourceLocation.parse("forge:ingots/arcane")))
					.nugget(ItemTags.create(ResourceLocation.parse("forge:nuggets/arcane")))
					.fluid(CCFluids.Tags.MOLTEN_ARCANIUM)
					.index(21)
					.hemalurgyCharge(5)
					.temperature(800)
					.feruchemy(new CappedMetalEffect(4, AttributeMetalEffect.builder(AttributeRegistry.MAX_MANA, AttributeModifier.Operation.MULTIPLY_TOTAL).eachLevel(0.15f)))
					.feruchemy(new CappedMetalEffect(4, AttributeMetalEffect.builder(AttributeRegistry.MANA_REGEN, AttributeModifier.Operation.MULTIPLY_BASE).eachLevel(0.25f)))
					.build();
			metal(exilite)
					.ingot(ItemTags.create(ResourceLocation.parse("forge:ingots/exilite")))
					.nugget(ItemTags.create(ResourceLocation.parse("forge:nuggets/exilite")))
					.fluid(CCFluids.Tags.MOLTEN_EXILITE)
					.index(22)
					.hemalurgyCharge(1)
					.temperature(800)
					.feruchemy(AttributeMetalEffect.builder(AttributeRegistry.SPELL_POWER, AttributeModifier.Operation.MULTIPLY_BASE).eachLevel(0.05f))
					.feruchemy(AttributeMetalEffect.builder(AttributeRegistry.SPELL_RESIST, AttributeModifier.Operation.MULTIPLY_BASE).eachLevel(-0.1f))
					.build();
		}

		@Override
		public String getName() {
			return "Construct's Casting Metalborn Power Provider";
		}
	}
	private static MetalId id(String name) {
		return new MetalId(ConstructsCasting.id(name));
	}
}
