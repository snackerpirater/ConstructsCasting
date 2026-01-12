package com.snackpirate.constructscasting.modifiers;

import io.redspace.ironsspellbooks.effect.ImmolateEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.json.LevelingValue;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MonsterMeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import java.util.List;

public record CombustiveModule(LevelingValue chance) implements ModifierModule, MonsterMeleeHitModifierHook, MeleeHitModifierHook, ProjectileHitModifierHook {
	public static final List<ModuleHook<?>> HOOKS = HookProvider.<CombustiveModule>defaultHooks(ModifierHooks.MELEE_HIT, ModifierHooks.MONSTER_MELEE_HIT);
	public static final RecordLoadable<CombustiveModule> LOADER = RecordLoadable.create(
			LevelingValue.LOADABLE.requiredField("chance", CombustiveModule::chance),
			CombustiveModule::new
	);

	@Override
	public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
		float ran = context.getAttacker().getRandom().nextFloat();
		float chance = this.chance.compute(modifier.getLevel());
		LivingEntity target = context.getLivingTarget();
		if (target != null && context.isCritical()) {
			if (ran < chance) {
				ImmolateEffect.addImmolateStack(target, context.getAttacker());
			}
			if (chance > 1 && ran < chance-1) {
				ImmolateEffect.addImmolateStack(target, context.getAttacker());
			}
		}
	}

	@Override
	public void onMonsterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage) {
		float ran = context.getAttacker().getRandom().nextFloat();
		float chance = this.chance.compute(modifier.getLevel());
		if (context.getLivingTarget() != null) { //monsters can't crit
			if (ran < chance) {
				ImmolateEffect.addImmolateStack(context.getLivingTarget(), context.getAttacker());
			}
			if (chance > 1 && ran < chance-1) {
				ImmolateEffect.addImmolateStack(context.getLivingTarget(), context.getAttacker());
			}
		}
	}

	@Override
	public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target, boolean notBlocked) {
		if (notBlocked && attacker != null && target != null) {
			float ran = attacker.getRandom().nextFloat();
			float chance = this.chance.compute(modifier.getLevel());
			if (ran < chance) {
				ImmolateEffect.addImmolateStack(target, attacker);
			}
			if (chance > 1 && ran < chance-1) {
				ImmolateEffect.addImmolateStack(target, attacker);
			}
		}
		return notBlocked;
	}

	@Override
	public RecordLoadable<? extends ModifierModule> getLoader() {
		return LOADER;
	}

	@Override
	public List<ModuleHook<?>> getDefaultHooks() {
		return HOOKS;
	}
}
