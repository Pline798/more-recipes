package com.para.morerecipes.entity;

import com.para.morerecipes.MoreRecipes;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownExperienceBottle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ThrownBigExperienceBottle extends ThrownExperienceBottle {

	public ThrownBigExperienceBottle(EntityType<? extends ThrownExperienceBottle> entityType, Level level) {
		super(entityType, level);
	}

	public ThrownBigExperienceBottle(EntityType<? extends ThrownExperienceBottle> entityType,
			LivingEntity owner, Level level, ItemStack stack) {
		super(entityType, level);
		this.setOwner(owner);
		this.setItem(stack);
		this.setPos(owner.getX(), owner.getEyeY() - (double)0.1F, owner.getZ());
	}

	public static ThrownBigExperienceBottle create(ServerLevel level, LivingEntity owner, ItemStack stack) {
		return new ThrownBigExperienceBottle(
				MoreRecipes.BIG_EXPERIENCE_BOTTLE_ENTITY, owner, level, stack);
	}

	@Override
	protected void onHit(HitResult result) {
		if (!this.level().isClientSide()) {
			this.level().levelEvent(2002, this.blockPosition(), -1);

			if (this.level() instanceof ServerLevel serverLevel) {
				int xp = (3 + this.random.nextInt(5) + this.random.nextInt(5)) * 10;
				ExperienceOrb.award(serverLevel, this.position(), xp);
			}

			this.discard();
		}
	}
}
