package com.para.morerecipes;

import com.para.morerecipes.entity.ThrownBigExperienceBottle;
import com.para.morerecipes.item.BigExperienceBottleItem;
import com.para.morerecipes.mixin.accessor.ItemAccessor;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
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

	// === Conversion Cores ===

	private static final ResourceKey<Item> CONVERSION_CORE_KEY = ResourceKey.create(
			Registries.ITEM,
			Identifier.fromNamespaceAndPath(MOD_ID, "conversion_core"));

	public static final Item CONVERSION_CORE = Registry.register(
			BuiltInRegistries.ITEM,
			CONVERSION_CORE_KEY,
			new Item(new Item.Properties()
					.setId(CONVERSION_CORE_KEY)
					.stacksTo(1)));

	private static final ResourceKey<Item> REVERSE_CORE_KEY = ResourceKey.create(
			Registries.ITEM,
			Identifier.fromNamespaceAndPath(MOD_ID, "reverse_core"));

	public static final Item REVERSE_CORE = Registry.register(
			BuiltInRegistries.ITEM,
			REVERSE_CORE_KEY,
			new Item(new Item.Properties()
					.setId(REVERSE_CORE_KEY)
					.stacksTo(1)));

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
						output.accept(CONVERSION_CORE);
						output.accept(REVERSE_CORE);
						output.accept(STONE_COAL);
						output.accept(BIG_EXPERIENCE_BOTTLE);
					})
					.build());

	@Override
	public void onInitialize() {
		// Force class-load the enchantment ResourceKey
		@SuppressWarnings("unused")
		ResourceKey<Enchantment> _key = FAST_ATTACK;

		// Set craft remainders via mixin accessor
		((ItemAccessor) (Object) CONVERSION_CORE).more_recipes$setCraftingRemainingItem(CONVERSION_CORE);
		((ItemAccessor) (Object) REVERSE_CORE).more_recipes$setCraftingRemainingItem(REVERSE_CORE);

		FuelRegistryEvents.BUILD.register((builder, context) -> {
			builder.add(STONE_COAL, 30000);
		});

		LOGGER.info("More Recipes loaded!");
	}
}
