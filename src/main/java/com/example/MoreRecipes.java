package com.example;

import com.example.entity.ThrownBigExperienceBottle;
import com.example.item.BigExperienceBottleItem;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreRecipes implements net.fabricmc.api.ModInitializer {
	public static final String MOD_ID = "more-recipes";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// === Big Experience Bottle ===

	private static final ResourceKey<Item> BIG_EXPERIENCE_BOTTLE_ITEM_KEY = ResourceKey.create(
			Registries.ITEM,
			Identifier.fromNamespaceAndPath(MOD_ID, "big_experience_bottle"));

	public static final BigExperienceBottleItem BIG_EXPERIENCE_BOTTLE = Registry.register(
			BuiltInRegistries.ITEM,
			BIG_EXPERIENCE_BOTTLE_ITEM_KEY,
			new BigExperienceBottleItem(new Item.Properties()
					.setId(BIG_EXPERIENCE_BOTTLE_ITEM_KEY)
					.component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
					.rarity(Rarity.UNCOMMON)
					.stacksTo(64)));

	private static final ResourceKey<EntityType<?>> BIG_EXPERIENCE_BOTTLE_ENTITY_KEY = ResourceKey.create(
			Registries.ENTITY_TYPE,
			Identifier.fromNamespaceAndPath(MOD_ID, "big_experience_bottle"));

	public static final EntityType<ThrownBigExperienceBottle> BIG_EXPERIENCE_BOTTLE_ENTITY = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			BIG_EXPERIENCE_BOTTLE_ENTITY_KEY,
			EntityType.Builder.<ThrownBigExperienceBottle>of(ThrownBigExperienceBottle::new, MobCategory.MISC)
					.sized(0.25F, 0.25F)
					.clientTrackingRange(4)
					.updateInterval(10)
					.build(BIG_EXPERIENCE_BOTTLE_ENTITY_KEY));

	// === Stone Coal ===

	private static final ResourceKey<Item> STONE_COAL_ITEM_KEY = ResourceKey.create(
			Registries.ITEM,
			Identifier.fromNamespaceAndPath(MOD_ID, "stone_coal"));

	public static final Item STONE_COAL = Registry.register(
			BuiltInRegistries.ITEM,
			STONE_COAL_ITEM_KEY,
			new Item(new Item.Properties()
					.setId(STONE_COAL_ITEM_KEY)
					.stacksTo(64)));

	// === Fast Attack Enchantment ===

	public static final ResourceKey<Enchantment> FAST_ATTACK = ResourceKey.create(
			Registries.ENCHANTMENT,
			Identifier.fromNamespaceAndPath(MOD_ID, "fast_attack"));

	// === Creative Tab ===

	private static final ResourceKey<CreativeModeTab> MORE_RECIPES_TAB_KEY = ResourceKey.create(
			Registries.CREATIVE_MODE_TAB,
			Identifier.fromNamespaceAndPath(MOD_ID, "more_recipes"));

	@SuppressWarnings("unused")
	public static final CreativeModeTab MORE_RECIPES_TAB = Registry.register(
			BuiltInRegistries.CREATIVE_MODE_TAB,
			MORE_RECIPES_TAB_KEY,
			FabricItemGroup.builder()
					.title(Component.translatable("itemGroup.more-recipes"))
					.icon(() -> new ItemStack(BIG_EXPERIENCE_BOTTLE))
					.displayItems((params, output) -> {
						output.accept(STONE_COAL);
						output.accept(BIG_EXPERIENCE_BOTTLE);
					})
					.build());

	@Override
	public void onInitialize() {
		// Load enchantment key to ensure class initialization
		@SuppressWarnings("unused")
		ResourceKey<Enchantment> _key = FAST_ATTACK;

		// Register fuel: 6400 ticks (= 4x coal, burns 320 items)
		FuelRegistryEvents.BUILD.register((builder, context) -> {
			builder.add(STONE_COAL, 6400);
		});

		LOGGER.info("More Recipes loaded!");
	}
}
