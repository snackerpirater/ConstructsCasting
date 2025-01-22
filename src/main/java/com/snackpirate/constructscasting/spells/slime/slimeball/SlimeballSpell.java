package com.snackpirate.constructscasting.spells.slime.slimeball;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.spells.CCSpells;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

@AutoSpellConfig
public class SlimeballSpell extends AbstractSpell {

	private final DefaultConfig defaultConfig = new DefaultConfig()
			.setMinRarity(SpellRarity.COMMON)
			.setSchoolResource(CCSpells.Schools.SLIME_LOC)
			.setMaxLevel(10)
			.setCooldownSeconds(1.0)
			.build();

	@Override
	public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
		return List.of(
				Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 2)),
				Component.translatable("ui.constructs_casting.slimeball.max_bounces", getMaxBounces(spellLevel)));
	}

	public SlimeballSpell() {
		this.manaCostPerLevel = 2;
		this.baseSpellPower = 6;
		this.spellPowerPerLevel = 1;
		this.castTime = 0;
		this.baseManaCost = 10;
	}

	@Override
	public SchoolType getSchoolType() {
		return CCSpells.Schools.SLIME.get();
	}


	@Override
	public ResourceLocation getSpellResource() {return ConstructsCasting.id("slimeball");}


	@Override
	public DefaultConfig getDefaultConfig() {
		return defaultConfig;
	}

	@Override
	public CastType getCastType() {
		return CastType.INSTANT;
	}

	@Override
	public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
		SlimeballProjectile slimeball = new SlimeballProjectile(level, entity, spellLevel);
		slimeball.setPos(entity.position().add(0, entity.getEyeHeight() - slimeball.getBoundingBox().getYsize() * .5f, 0));
		slimeball.setDeltaMovement(entity.getLookAngle().scale(slimeball.getSpeed()).add(entity.getDeltaMovement()));
		slimeball.setDamage(getDamage(spellLevel, entity));
		level.addFreshEntity(slimeball);
		super.onCast(level, spellLevel, entity, castSource, playerMagicData);
	}


	public float getDamage(int spellLevel, LivingEntity entity) {
		return getSpellPower(spellLevel, entity) * .5f;
	}
	public static int getMaxBounces(int spellLevel) {return spellLevel;}

}
