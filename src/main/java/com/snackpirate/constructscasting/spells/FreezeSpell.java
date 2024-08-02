package com.snackpirate.constructscasting.spells;

import com.snackpirate.constructscasting.ConstructsCasting;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.smeltery.block.entity.CastingBlockEntity;

import java.util.Optional;

@AutoSpellConfig
public class FreezeSpell extends AbstractSpell {
	private final ResourceLocation id = ConstructsCasting.id("freeze");

	private final DefaultConfig config = new DefaultConfig()
			.setMaxLevel(1)
			.setMinRarity(SpellRarity.RARE)
			.setSchoolResource(SchoolRegistry.ICE_RESOURCE)
			.setCooldownSeconds(7)
			.build();

	public FreezeSpell() {
		this.baseManaCost = 50;
		this.castTime = 15;
	}
	/**
	 * @return
	 */
	@Override
	public ResourceLocation getSpellResource() {
		return id;
	}

	/**
	 * @return
	 */
	@Override
	public DefaultConfig getDefaultConfig() {
		return config;
	}

	/**
	 * @return
	 */
	@Override
	public CastType getCastType() {
		return CastType.LONG;
	}

	@Override
	public Optional<SoundEvent> getCastStartSound() {
		return Optional.of(SoundRegistry.FROSTWAVE_PREPARE.get());
	}

	@Override
	public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
		var hitResult = Utils.raycastForBlock(level, entity.getEyePosition(), entity.getEyePosition().add(entity.getLookAngle().scale(4.5f)), ClipContext.Fluid.NONE);
		BlockPos pos = hitResult.getBlockPos();
		playerMagicData.setAdditionalCastData(new TargetBlockCastData(pos, hitResult.getDirection()));
		if (level.getBlockEntity(pos) instanceof CastingBlockEntity cbe) {
			var fluidStack = cbe.getTank().getFluid();
			if (fluidStack.getAmount() > 0 && fluidStack.getFluid().getFluidType().getTemperature() > 700) {
				return true;
			}
		}
		sendInvalidMessage(entity);
		return false;
	}

	private static void sendInvalidMessage(Entity e) {
		if (e instanceof Player player) player.displayClientMessage(Component.translatable("spell.constructs_casting.freeze.invalid_target").withStyle(ChatFormatting.RED), true);
	}

	@Override
	public SchoolType getSchoolType() {
		return CCSpells.Schools.SLIME.get();
	}

	@Override
	public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
		ConstructsCasting.LOGGER.info("freeze cast");
		if (playerMagicData.getAdditionalCastData() instanceof TargetBlockCastData data && level.getBlockEntity(data.getTargetPos()) instanceof CastingBlockEntity cbe) {
			//check if fluid is above molten temperature
			BlockPos pos = data.getTargetPos();
			//oh boy i hope this doesn't explode
			CompoundTag tag = level.getBlockEntity(pos).getUpdateTag();
			tag.putInt("timer", Integer.MAX_VALUE - 5);
			level.getBlockEntity(pos).load(tag);

			MagicManager.spawnParticles(level, ParticleHelper.SNOWFLAKE, pos.getX() + 0.5, pos.getY() + 1d, pos.getZ() + 0.5, 15, .1, .1, .1, .1, true);
		}
		super.onCast(level, spellLevel, entity, castSource, playerMagicData);
	}

	@Override
	public AnimationHolder getCastStartAnimation() {
		return SpellAnimations.CHARGE_RAISED_HAND;
	}

	@Override
	public AnimationHolder getCastFinishAnimation() {
		return SpellAnimations.ANIMATION_INSTANT_CAST;
	}
}
