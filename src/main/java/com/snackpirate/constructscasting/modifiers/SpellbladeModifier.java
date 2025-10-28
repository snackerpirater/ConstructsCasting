package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.ConstructsCasting;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.modules.build.ModifierRequirementsModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class SpellbladeModifier extends NoLevelsModifier implements MeleeHitModifierHook {
	@Override
	protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
		super.registerHooks(hookBuilder);
		hookBuilder.addHook(this, ModifierHooks.MELEE_HIT);
		hookBuilder.addModule(ModifierRequirementsModule.builder().requireModifier(CCModifiers.IMBUED.getId(), 1).translationKey("constructs_casting.modifier.spellblade.requirement").build());

	}

    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        float ret = MeleeHitModifierHook.super.beforeMeleeHit(tool, modifier, context, damage, baseKnockback, knockback);
        Player player = context.getPlayerAttacker();
        InteractionHand interactionHand = context.getHand();
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (!ISpellContainer.isSpellContainer(itemStack)) return ret;
        ISpellContainer container = ISpellContainer.get(itemStack);
        if (container.isEmpty()) return ret;
        SpellData spellData = container.getSpellAtIndex(0);

        if (player.level().isClientSide()) {
            if (ClientMagicData.isCasting()) {
                return ret;
            } else if (ClientMagicData.getPlayerMana() < spellData.getSpell().getManaCost(spellData.getLevel())
                    || ClientMagicData.getCooldowns().isOnCooldown(spellData.getSpell())
                    || !ClientMagicData.getSyncedSpellData(player).isSpellLearned(spellData.getSpell())) {
                return ret;
            } else {
                return ret;
            }
        }

        String castingSlot = interactionHand.ordinal() == 0 ? SpellSelectionManager.MAINHAND : SpellSelectionManager.OFFHAND;
        if (spellData.getSpell().attemptInitiateCast(itemStack, spellData.getLevel(), player.level(), player, CastSource.SWORD, true, castingSlot)) {
			MagicData.getPlayerMagicData(player).initiateCast(spellData.getSpell(), spellData.getLevel(), 0, CastSource.SWORD, castingSlot);
		}
        return ret;
    }

	@Override
	public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
		//to allow a melee hit, then a spell hit right afterwards
		if (context.getLivingTarget() != null) context.getLivingTarget().invulnerableTime = 0;
	}

	@Override
	public int getPriority() {
		return 150;//anything above blocking (100) works, but just to be sure
	}

}
