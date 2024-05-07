package com.snackpirate.constructscasting.entity;

import com.snackpirate.constructscasting.spells.CCSpells;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.shared.block.SlimeType;

import java.util.Optional;

public class SlimeballProjectile extends AbstractMagicProjectile {
	private final SlimeType slimeType;
	public SlimeballProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
		SlimeType[] slimeTypes = {SlimeType.EARTH, SlimeType.SKY, SlimeType.ICHOR, SlimeType.ENDER};
		this.slimeType = slimeTypes[Mth.randomBetweenInclusive(this.random, 0, 3)];

	}
	public SlimeballProjectile(Level levelIn, Entity shooter) {
		this(CCEntities.SLIMEBALL_PROJECTILE.get(), levelIn);
		setOwner(shooter);
	}

	@Override
	public void trailParticles() {}

	@Override
	public void impactParticles(double v, double v1, double v2) {
		MagicManager.spawnParticles(level, ParticleTypes.ITEM_SLIME, v, v1, v2, 5, .1, .1, .1, .25, true);
	}

	@Override
	public float getSpeed() {
		return 2.25f;
	}

	@Override
	public Optional<SoundEvent> getImpactSound() {
		return Optional.of(SoundEvents.SLIME_BLOCK_BREAK);
	}

	@Override
	protected void doImpactSound(SoundEvent sound) {
		level.playSound(null, getX(), getY(), getZ(), sound, SoundSource.NEUTRAL, 2, 1.2f + Utils.random.nextFloat() * .2f);
	}

	@Override
	protected void onHitEntity(EntityHitResult pResult) {
		super.onHitEntity(pResult);
		var target = pResult.getEntity();
		DamageSources.applyDamage(target, getDamage(), CCSpells.SLIMEBALL_SPELL.get().getDamageSource(this, getOwner()));
		discard();
	}

	@Override
	protected void onHitBlock(BlockHitResult pResult) {
		super.onHitBlock(pResult);
		discard();
	}
	public SlimeType getSlimeType() {
		return slimeType;
	}
}
