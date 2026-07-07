package com.para.morerecipes.client;

import com.para.morerecipes.MoreRecipes;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class MoreRecipesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRenderers.register(MoreRecipes.BIG_EXPERIENCE_BOTTLE_ENTITY, ThrownItemRenderer::new);
	}
}
