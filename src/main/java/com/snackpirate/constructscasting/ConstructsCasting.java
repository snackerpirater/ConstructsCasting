package com.snackpirate.constructscasting;

import com.mojang.logging.LogUtils;
import com.snackpirate.constructscasting.fluids.CCFluids;
import com.snackpirate.constructscasting.items.CCItems;
import com.snackpirate.constructscasting.items.CCTools;
import com.snackpirate.constructscasting.materials.CCMaterialTextures;
import com.snackpirate.constructscasting.materials.CCMaterials;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
import com.snackpirate.constructscasting.recipe.CCRecipes;
import com.snackpirate.constructscasting.spells.CCEntities;
import com.snackpirate.constructscasting.spells.CCSpells;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import slimeknights.mantle.registration.deferred.SynchronizedDeferredRegister;
import slimeknights.tconstruct.common.data.tags.BlockTagProvider;
import slimeknights.tconstruct.library.client.data.material.MaterialPartTextureGenerator;
import slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider;

import java.util.concurrent.CompletableFuture;

@Mod(ConstructsCasting.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConstructsCasting {
    public static final String MOD_ID = "constructs_casting";
    protected static final SynchronizedDeferredRegister<CreativeModeTab> CREATIVE_TABS = SynchronizedDeferredRegister.create(Registries.CREATIVE_MODE_TAB, ConstructsCasting.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB = CREATIVE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.constructs_casting.constructs_casting"))
            .icon(() -> new ItemStack(CCItems.slimySpellbook.get()))
            .displayItems(CCItems.DISPLAY_ITEMS)
            .build());

    public static final Logger LOGGER = LogUtils.getLogger();

    public ConstructsCasting() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        CCModifiers.MODIFIERS.register(modEventBus);
        CCFluids.FLUIDS.register(modEventBus);
        CCItems.ITEMS.register(modEventBus);
        CCRecipes.RECIPE_SERIALIZERS.register(modEventBus);
        CCSounds.register(modEventBus);
        CCEntities.register(modEventBus);
        CCSpells.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);
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
        PackOutput output = gen.getPackOutput();
        CCMaterials mats = new CCMaterials(output);
        gen.addProvider(server, mats);
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        gen.addProvider(server, new CCTools.CCToolDefinitions(output, MOD_ID));
        gen.addProvider(server, new MaterialPartTextureGenerator(output, fileHelper, new TinkerPartSpriteProvider(), new CCMaterialTextures()));
        gen.addProvider(server, new CCMaterials.CCMaterialStats(output, mats));
        gen.addProvider(server, new CCMaterials.CCMaterialRenderInfo(output, new CCMaterialTextures(), fileHelper));
        gen.addProvider(server, new CCModifiers(output));
        gen.addProvider(server, new CCMaterials.CCMaterialTraits(output, mats));
        gen.addProvider(server, new CCItems.Tags(output, provider, CompletableFuture.completedFuture(TagsProvider.TagLookup.empty()), MOD_ID, fileHelper));
        gen.addProvider(server, new CCFluids.CCFluidTextures(output, MOD_ID));
        gen.addProvider(server, new CCFluids.CCBucketModels(output, MOD_ID));
        gen.addProvider(server, new CCFluids.Tags(output, provider));
        gen.addProvider(server, new CCFluids.Tags.CCFluidTooltipProvider(output, MOD_ID));
        gen.addProvider(server, new CCRecipes(output));
        gen.addProvider(server, new CCLang(output, ConstructsCasting.MOD_ID, "en_us"));
        gen.addProvider(server, new CCDamageTypes.Tags(output, provider, MOD_ID, fileHelper));
    }
}
