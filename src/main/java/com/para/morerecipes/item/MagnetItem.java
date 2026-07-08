package com.para.morerecipes.item;

import com.para.morerecipes.component.ModComponents;

import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MagnetItem extends Item {
	private final double range;

	public MagnetItem(Properties properties, double range) {
		super(properties);
		this.range = range;
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (level.isClientSide()) {
			return InteractionResult.CONSUME;
		}

		ServerLevel serverLevel = (ServerLevel) level;
		boolean active = stack.getOrDefault(ModComponents.MAGNET_ACTIVE, false);
		boolean newActive = !active;

		stack.set(ModComponents.MAGNET_ACTIVE, newActive);
		stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, newActive);

		serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
				newActive ? SoundEvents.EXPERIENCE_ORB_PICKUP : SoundEvents.EXPERIENCE_ORB_PICKUP,
				SoundSource.PLAYERS, 0.3F, newActive ? 1.2F : 0.8F);

		return InteractionResult.SUCCESS;
	}

	@Override
	public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, EquipmentSlot slot) {
		if (!(entity instanceof Player player)) return;
		if (!stack.getOrDefault(ModComponents.MAGNET_ACTIVE, false)) return;

		double rangeSq = range * range;
		Vec3 playerPos = player.position();

		// Attract item entities
		List<ItemEntity> items = level.getEntitiesOfClass(
				ItemEntity.class,
				player.getBoundingBox().inflate(range),
				item -> item.isAlive()
						&& !item.hasPickUpDelay()
						&& item.position().distanceToSqr(playerPos) <= rangeSq);

		for (ItemEntity item : items) {
			item.setPos(playerPos.x, playerPos.y + 0.5, playerPos.z);
			item.setPickUpDelay(0);
		}

		// Attract experience orbs
		List<ExperienceOrb> xpOrbs = level.getEntitiesOfClass(
				ExperienceOrb.class,
				player.getBoundingBox().inflate(range),
				orb -> orb.isAlive()
						&& orb.position().distanceToSqr(playerPos) <= rangeSq);

		for (ExperienceOrb orb : xpOrbs) {
			orb.setPos(playerPos.x, playerPos.y + 0.5, playerPos.z);
		}
	}

	public double getRange() {
		return range;
	}
}
