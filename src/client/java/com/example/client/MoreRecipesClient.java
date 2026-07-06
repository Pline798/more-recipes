package com.example.client;

import com.example.MoreRecipes;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class MoreRecipesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Register entity renderer on client side
		EntityRenderers.register(MoreRecipes.BIG_EXPERIENCE_BOTTLE_ENTITY, ThrownItemRenderer::new);
	}
}
