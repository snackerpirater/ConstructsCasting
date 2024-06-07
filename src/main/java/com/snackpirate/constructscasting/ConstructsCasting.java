package com.snackpirate.constructscasting;

import com.mojang.logging.LogUtils;
import com.snackpirate.constructscasting.fluids.CCFluids;
import com.snackpirate.constructscasting.items.CCItems;
import com.snackpirate.constructscasting.items.CCTools;
import com.snackpirate.constructscasting.materials.CCMaterialTextures;
import com.snackpirate.constructscasting.materials.CCMaterials;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
import com.snackpirate.constructscasting.recipe.CCRecipes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import slimeknights.tconstruct.library.client.data.material.MaterialPartTextureGenerator;
import slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider;

@Mod(ConstructsCasting.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConstructsCasting {

    public static final String MOD_ID = "constructs_casting";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ConstructsCasting() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        CCModifiers.MODIFIERS.register(modEventBus);
        CCFluids.FLUIDS.register(modEventBus);
        CCItems.ITEMS.register(modEventBus);
        CCRecipes.RECIPE_SERIALIZERS.register(modEventBus);
    }
    public static ResourceLocation id(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator gen = event.getGenerator();
        boolean server = event.includeServer();
        CCMaterials mats = new CCMaterials(gen);
        gen.addProvider(server, mats);
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        gen.addProvider(server, new CCTools.CCToolDefinitions(gen, MOD_ID));
        gen.addProvider(server, new MaterialPartTextureGenerator(gen, fileHelper, new TinkerPartSpriteProvider(), new CCMaterialTextures()));
        gen.addProvider(server, new CCMaterials.CCMaterialStats(gen, mats));
        gen.addProvider(server, new CCMaterials.CCMaterialRenderInfo(gen, new CCMaterialTextures(), fileHelper));
        gen.addProvider(server, new CCModifiers(gen));
        gen.addProvider(server, new CCMaterials.CCMaterialTraits(gen, mats));
        gen.addProvider(server, new CCItems.CCItemTagsProvider(gen, new BlockTagsProvider(gen, MOD_ID, fileHelper), MOD_ID, fileHelper));
        gen.addProvider(server, new CCFluids.CCFluidTextures(gen, MOD_ID));
        gen.addProvider(server, new CCFluids.CCBucketModels(gen, MOD_ID));
        gen.addProvider(server, new CCFluids.Tags(gen, MOD_ID, fileHelper));
        gen.addProvider(server, new CCFluids.Tags.CCFluidTooltipProvider(gen, MOD_ID));
        gen.addProvider(server, new CCRecipes(gen));
        gen.addProvider(server, new CCLang(gen, ConstructsCasting.MOD_ID, "en_us"));
    }
}
