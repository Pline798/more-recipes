package com.para.morerecipes.component;

import com.para.morerecipes.MoreRecipes;
import com.mojang.serialization.Codec;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;

public class ModComponents {
	public static final DataComponentType<Boolean> MAGNET_ACTIVE = Registry.register(
			BuiltInRegistries.DATA_COMPONENT_TYPE,
			Identifier.fromNamespaceAndPath(MoreRecipes.MOD_ID, "magnet_active"),
			DataComponentType.<Boolean>builder()
					.persistent(Codec.BOOL)
					.networkSynchronized(ByteBufCodecs.BOOL)
					.build()
	);

	public static void initialize() {
		// Force static initializer
	}
}
