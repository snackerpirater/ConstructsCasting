package com.snackpirate.constructscasting.recipe;

import com.snackpirate.constructscasting.items.CCItems;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import slimeknights.mantle.loot.AbstractLootTableInjectionProvider;
import slimeknights.mantle.loot.LootTableInjection;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.json.loot.AddToolDataFunction;
import slimeknights.tconstruct.library.json.predicate.material.MaterialPredicate;
import slimeknights.tconstruct.library.materials.RandomMaterial;
import slimeknights.tconstruct.tools.TinkerTools;

public class CCLootInjections extends AbstractLootTableInjectionProvider {
	public CCLootInjections(PackOutput output, String domain) {
		super(output, domain);
	}

	@Override
	protected void addTables() {
		//TODO: when the 1.21 backport rolls around, add flamberges to citadel vault loot
		RandomMaterial random = RandomMaterial.ancient();
		RandomMaterial randomHighTier = RandomMaterial.random().tier(3, 4).material(MaterialPredicate.tag(TinkerTags.Materials.EXCLUDE_FROM_LOOT).inverted()).build();
		AddToolDataFunction.Builder ancientToolData3 = AddToolDataFunction.builder().addMaterial(random).addMaterial(random).addMaterial(random);
		LootTableInjection.Builder burialLoot = inject("burial_loot", IronsSpellbooks.id("chests/battleground/burial_loot"))
				.addToPool("main", LootItem.lootTableItem(CCItems.flamberge.get())
						.setWeight(12)
						.apply(ancientToolData3)
						.build());
		inject("piglin_camp", IronsSpellbooks.id("chests/battleground/piglin_camp"))
				.addToPool("pool1", LootItem.lootTableItem(CCItems.flamberge.get())
						.setWeight(2)
						.apply(ancientToolData3)
						.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1f, 0.9f)))
						.build());
		inject("citadel_vault", IronsSpellbooks.id("chests/citadel/citadel_vault"))
				.addToPool("pool1", LootItem.lootTableItem(CCItems.flamberge.get())
						.setWeight(3)
						.apply(AddToolDataFunction.builder().addMaterial(randomHighTier).addMaterial(randomHighTier).addMaterial(randomHighTier))
//						.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1f, 0.9f)))
						.build());
		inject("catacombs_vault", IronsSpellbooks.id("chests/catacombs/dead_king_vault"))
				.addToPool("pool1", LootItem.lootTableItem(CCItems.battlestaff.get())
						.setWeight(4)
						.apply(AddToolDataFunction.builder().addMaterial(random).addMaterial(random).addMaterial(random).addMaterial(random))
//						.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1f, 0.9f)))
						.build());
	}

	@Override
	public String getName() {
		return "Construct's Casting Loot Table Injection Provider";
	}
}
