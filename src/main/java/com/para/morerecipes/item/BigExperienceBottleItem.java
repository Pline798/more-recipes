package com.para.morerecipes.item;

import com.para.morerecipes.entity.ThrownBigExperienceBottle;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BigExperienceBottleItem extends Item {

	public BigExperienceBottleItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (level instanceof ServerLevel serverLevel) {
			level.playSound(null, player.getX(), player.getY(), player.getZ(),
					SoundEvents.EXPERIENCE_BOTTLE_THROW, SoundSource.NEUTRAL,
					0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

			Projectile.spawnProjectileFromRotation(
					ThrownBigExperienceBottle::create,
					serverLevel, stack, player, -20.0F, 0.7F, 1.0F);
		}

		player.awardStat(Stats.ITEM_USED.get(this));
		stack.consume(1, player);
		return InteractionResult.SUCCESS;
	}
}
