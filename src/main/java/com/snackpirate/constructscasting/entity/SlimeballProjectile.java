package com.snackpirate.constructscasting.entity;

import com.snackpirate.constructscasting.spells.CCSpells;
import com.snackpirate.constructscasting.spells.SlimeballSpell;
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
	private int bounces;
	private int maxBounces;
	public SlimeballProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
		//TODO: Wizardslime slime type, might have to create own class with slime types and their specialties? Would also allow for other types of "slime"
		SlimeType[] slimeTypes = {SlimeType.EARTH, SlimeType.SKY, SlimeType.ICHOR, SlimeType.ENDER};
		this.slimeType = slimeTypes[Mth.randomBetweenInclusive(this.random, 0, 3)];
		this.bounces = 0;
		this.maxBounces = 0;
	}
	public SlimeballProjectile(Level levelIn, Entity shooter, int spellLevel) {
		this(CCEntities.SLIMEBALL_PROJECTILE.get(), levelIn);
		this.maxBounces = SlimeballSpell.getMaxBounces(spellLevel);
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
		return 1.75f;
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
		if (bounces < maxBounces) {
			switch (pResult.getDirection()) {
				case UP, DOWN -> this.setDeltaMovement(this.getDeltaMovement().x, 0-this.getDeltaMovement().y, this.getDeltaMovement().z);
				case EAST, WEST -> this.setDeltaMovement(0-this.getDeltaMovement().x, this.getDeltaMovement().y, this.getDeltaMovement().z);
				case NORTH, SOUTH -> this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y, 0-this.getDeltaMovement().z);
			}
			this.setDeltaMovement(this.getDeltaMovement().scale(0.8));
			bounces++;
		}
		else {
			discard();
		}
	}
	public SlimeType getSlimeType() {
		return slimeType;
	}
}
