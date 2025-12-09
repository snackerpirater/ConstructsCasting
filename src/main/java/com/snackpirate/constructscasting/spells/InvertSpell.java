package com.snackpirate.constructscasting.spells;

import com.snackpirate.constructscasting.ConstructsCasting;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.shared.TinkerEffects;

import java.util.List;

@AutoSpellConfig
public class InvertSpell extends AbstractSpell {

	private final DefaultConfig defaultConfig = new DefaultConfig()
			.setMinRarity(SpellRarity.RARE)
			.setSchoolResource(SchoolRegistry.ENDER_RESOURCE)
			.setMaxLevel(5)
			.setCooldownSeconds(45)
			.build();

	@Override
	public ResourceLocation getSpellResource() {
		return ConstructsCasting.id("invert");
	}

	@Override
	public DefaultConfig getDefaultConfig() {
		return defaultConfig;
	}

	@Override
	public CastType getCastType() {
		return CastType.LONG;
	}

	@Override
	public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
		return 2;
	}

	public InvertSpell() {
		this.baseSpellPower = 5;
		this.spellPowerPerLevel = 5;
		this.baseManaCost = 70;
		this.manaCostPerLevel = 10;
		this.castTime = 30;
	}

	@Override
	public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
		return List.of(Component.translatable("ui.irons_spellbooks.effect_length", Utils.timeFromTicks(getEffectDuration(spellLevel, caster), 1)));
	}

	@Override
	public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
		if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(getSpellId())) {
			playerMagicData.getPlayerRecasts().addRecast(new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, entity), getEffectDuration(spellLevel, entity), castSource, null), playerMagicData);
		}
		if (!entity.hasEffect(TinkerEffects.antigravity.get())) {
			entity.addEffect(new MobEffectInstance(TinkerEffects.antigravity.get(), getEffectDuration(spellLevel, entity), 0));
		} else {
			entity.removeEffect(TinkerEffects.antigravity.get());
		}
		super.onCast(level, spellLevel, entity, castSource, playerMagicData);
	}

	private int getEffectDuration(int spellLevel, LivingEntity entity) {
		return (int) (200 + (40 * getSpellPower(spellLevel, entity)));
	}

	@Override
	public AnimationHolder getCastStartAnimation() {
		return SpellAnimations.CHARGE_WAVY_ANIMATION;
	}

	@Override
	public AnimationHolder getCastFinishAnimation() {
		return SpellAnimations.SELF_CAST_TWO_HANDS;
	}
}
