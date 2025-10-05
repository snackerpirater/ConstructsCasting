package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.items.ModifiableSpellbookItem;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(modid = ConstructsCasting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ConservingModifier extends Modifier {
	private static final TagKey<Item> SPELLBOOK = TagKey.create(Registries.ITEM, ResourceLocation.tryBuild("curios", "spellbook"));
	@SubscribeEvent
	static void discountMana(SpellOnCastEvent event) {
//		ConstructsCasting.LOGGER.info("change mana event");
		//only applies when losing mana
			Player player = event.getEntity();
			CuriosApi.getCuriosInventory(player).ifPresent((handler) -> handler.findFirstCurio(stack -> stack.is(SPELLBOOK)).ifPresent(slotResult -> {
				if (slotResult.stack().getItem() instanceof ModifiableSpellbookItem) {
//					ConstructsCasting.LOGGER.info("change mana event 2");
					event.setManaCost(Math.max(5, event.getManaCost() - (10 * ModifierUtil.getModifierLevel(slotResult.stack(), CCModifiers.CONSERVING.getId()))));
				}
			}));
	}
}
