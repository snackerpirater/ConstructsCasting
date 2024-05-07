package com.snackpirate.constructscasting.spells;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.entity.SlimeballProjectile;
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

public class SlimeballSpell extends AbstractSpell {

	private final DefaultConfig defaultConfig = new DefaultConfig()
			.setMinRarity(SpellRarity.COMMON)
			.setSchoolResource(CCSchools.SLIME_RESOURCE)
			.setMaxLevel(10)
			.setCooldownSeconds(1.0)
			.build();

	@Override
	public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
		return List.of(Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 2)));
	}
	public SlimeballSpell() {
		this.manaCostPerLevel = 2;
		this.baseSpellPower = 12;
		this.spellPowerPerLevel = 1;
		this.castTime = 0;
		this.baseManaCost = 10;
	}

	@Override
	public SchoolType getSchoolType() {
			return CCSchools.SLIME.get();
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
		SlimeballProjectile slimeball = new SlimeballProjectile(level, entity);
		slimeball.setPos(entity.position().add(0, entity.getEyeHeight() - slimeball.getBoundingBox().getYsize() * .5f, 0));
		slimeball.shoot(entity.getLookAngle());
		slimeball.setDamage(getDamage(spellLevel, entity));
		level.addFreshEntity(slimeball);
		super.onCast(level, spellLevel, entity, castSource, playerMagicData);
	}

	@Override
	public int getSpellCooldown() {
		return 20;
	}

	private float getDamage(int spellLevel, LivingEntity entity) {
		return getSpellPower(spellLevel, entity) * .5f;
	}
}
