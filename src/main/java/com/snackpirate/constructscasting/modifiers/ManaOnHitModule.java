package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.ConstructsCasting;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.network.SyncManaPacket;
import io.redspace.ironsspellbooks.setup.PacketDistributor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.json.LevelingValue;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.entity.ProjectileWithPower;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockHarvestModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.special.PlantHarvestModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import java.util.List;

public record ManaOnHitModule(LevelingValue manaPerDamage, LevelingValue chance) implements ModifierModule, MeleeHitModifierHook, ProjectileHitModifierHook, BlockHarvestModifierHook {
	public static final List<ModuleHook<?>> HOOKS = HookProvider.<ManaOnHitModule>defaultHooks(ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_HIT, ModifierHooks.BLOCK_HARVEST);
	public static final RecordLoadable<ManaOnHitModule> LOADER = RecordLoadable.create(
			LevelingValue.LOADABLE.requiredField("mana_per_damage", ManaOnHitModule::manaPerDamage),
			LevelingValue.LOADABLE.requiredField("chance", ManaOnHitModule::chance),
			ManaOnHitModule::new
	);

	@Override
	public RecordLoadable<? extends ModifierModule> getLoader() {
		return LOADER;
	}

	@Override
	public List<ModuleHook<?>> getDefaultHooks() {
		return HOOKS;
	}

	@Override
	public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
		LivingEntity attacker = context.getAttacker();
		float ran = attacker.getRandom().nextFloat();
		float chance = this.chance.compute(modifier.getLevel());
		if (context.getLivingTarget() != null) {
			float manaToAdd = manaPerDamage.compute(modifier.getLevel())
					* damageDealt
					* (context.isExtraAttack() ? 0.25f : 1f) //if it's an extra attack from a scythe/etc, nerf mana gain since it runs per-entity
					* ((float) context.getAttacker().getAttributeValue(AttributeRegistry.MANA_REGEN.get()));
			if (ran < chance) {
				addManaToAttacker(attacker, manaToAdd);
			}
			if (chance > 1 && ran < chance-1) {
				addManaToAttacker(attacker, manaToAdd);
			}
		}
	}

	@Override
	public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target, boolean notBlocked) {
		if (attacker == null) return false;
		float ran = attacker.getRandom().nextFloat();
		float chance = this.chance.compute(modifier.getLevel());
		if (target != null) {
			float manaToAdd = manaPerDamage.compute(modifier.getLevel()) * ProjectileWithPower.getDamage(projectile) * ((float) attacker.getAttributeValue(AttributeRegistry.MANA_REGEN.get()));
			if (ran < chance) {
				addManaToAttacker(attacker, manaToAdd);
			}
			if (chance > 1 && ran < chance-1) {
				addManaToAttacker(attacker, manaToAdd);
			}
		}
		return false;
	}
	private static void addManaToAttacker(LivingEntity attacker, float mana) {
		MagicData.getPlayerMagicData(attacker).addMana(mana);
		if (attacker instanceof ServerPlayer sp) {
			PacketDistributor.sendToPlayer(sp, new SyncManaPacket(MagicData.getPlayerMagicData(attacker)));
		}
	}

	/**
	 * @param tool      Tool used
	 * @param modifier  Modifier level
	 * @param context   Harvest context
	 * @param harvested Number of blocks harvested
	 */
	@Override
	public void finishHarvest(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context, int harvested) {
		LivingEntity miner = context.getPlayer();
		if (miner == null) return;
		float ran = miner.getRandom().nextFloat();
		float chance = this.chance.compute(modifier.getLevel());

		ConstructsCasting.LOGGER.info("destroy speed {}", context.getState().getDestroySpeed(miner.level(), context.getPos()));
		//stone is 1.5 break speed
		//current modifier formula is 3 mana/damage
		//stone should give maybe 20-30 per level since it's a chance
		//means obsidian gives 900 mana (50 break speed)

		float manaToAdd = manaPerDamage.compute(modifier.getLevel()) //3
				* (context.getState().getDestroySpeed(miner.level(), context.getPos())*6) // * 1.5 * 6
				* ((float) miner.getAttributeValue(AttributeRegistry.MANA_REGEN.get())); // * 1~ish
		manaToAdd = Math.min(manaToAdd, 100); //hardcoded cap to not abuse, mithril is fairly fast mining
		if (ran < chance) {
			addManaToAttacker(miner, manaToAdd);
		}
		if (chance > 1 && ran < chance-1) {
			addManaToAttacker(miner, manaToAdd);
		}

	}
}
