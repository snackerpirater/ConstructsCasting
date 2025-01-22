package com.snackpirate.constructscasting.spells.slime.slimeball;

import com.snackpirate.constructscasting.CCDamageTypes;
import com.snackpirate.constructscasting.spells.CCEntities;
import com.snackpirate.constructscasting.spells.CCSpells;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SlimeballProjectile extends AbstractMagicProjectile implements IEntityAdditionalSpawnData {
	private static final EntityDataAccessor<Integer> BOUNCES = SynchedEntityData.defineId(SlimeballProjectile.class, EntityDataSerializers.INT);

	public SlimeballProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	public SlimeballProjectile(Level levelIn, Entity shooter, int spellLevel) {
		this(CCEntities.SLIMEBALL_PROJECTILE.get(), levelIn);
		setBounces(spellLevel);
		setOwner(shooter);
	}

	@Override
	public void trailParticles() {
	}

	@Override
	public void impactParticles(double v, double v1, double v2) {
		MagicManager.spawnParticles(level(), ParticleTypes.ITEM_SLIME, v, v1, v2, 5, .1, .1, .1, .25, true);

	}

	@Override
	public float getSpeed() {
		return 1.75f;
	}

	@Override
	public Optional<SoundEvent> getImpactSound() {
		return getBounces() == 0 ? Optional.of(SoundEvents.SLIME_BLOCK_BREAK) : Optional.of(SoundEvents.SLIME_SQUISH);
	}

	@Override
	protected void doImpactSound(SoundEvent sound) {
		level().playSound(null, getX(), getY(), getZ(), sound, SoundSource.NEUTRAL, 2, 1.2f + Utils.random.nextFloat() * .2f);
	}

	@Override
	protected void onHitEntity(EntityHitResult pResult) {
		super.onHitEntity(pResult);
		var target = pResult.getEntity();
		DamageSources.applyDamage(target, getDamage(), DamageSources.get(this.level(), CCDamageTypes.SLIME_MAGIC));
		if (getBounces() > 0) {
			final double x = this.getDeltaMovement().scale(0.8).x;
			final double y = this.getDeltaMovement().scale(0.8).y;
			final double z = this.getDeltaMovement().scale(0.8).z;
			this.setDeltaMovement(-x, y, -z);
			decBounces();
		} else {
			discard();
		}
	}

	@Override
	protected void onHitBlock(@NotNull BlockHitResult pResult) {
		super.onHitBlock(pResult);
		if (getBounces() > 0) {
			final double x = this.getDeltaMovement().x;
			final double y = this.getDeltaMovement().y;
			final double z = this.getDeltaMovement().z;
			switch (pResult.getDirection()) {
				case UP, DOWN -> this.setDeltaMovement(x, 0 - y, z);
				case EAST, WEST -> this.setDeltaMovement(0 - x, y, z);
				case NORTH, SOUTH -> this.setDeltaMovement(x, y, 0 - z);
			}
			this.setDeltaMovement(this.getDeltaMovement().scale(0.8));
			decBounces();
		} else {
			discard();
		}
	}

	/**
	 * @param buffer The packet data stream
	 */
	@Override
	public void writeSpawnData(FriendlyByteBuf buffer) {
		buffer.writeInt(getBounces());
	}

	/**
	 * @param additionalData The packet data stream
	 */
	@Override
	public void readSpawnData(FriendlyByteBuf additionalData) {
		setBounces(additionalData.readInt());
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("bounces", getBounces());
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		setBounces(tag.getInt("bounces"));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(BOUNCES, 1);
	}

	private int getBounces() {
		return this.entityData.get(BOUNCES);
	}

	private void decBounces() {
		setBounces(getBounces() - 1);
	}
	private void setBounces(int bounces) {
		this.entityData.set(BOUNCES, bounces);
	}
}

