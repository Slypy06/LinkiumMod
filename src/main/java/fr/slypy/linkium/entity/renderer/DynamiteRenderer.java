package fr.slypy.linkium.entity.renderer;

import fr.slypy.linkium.item.DynamiteItem;
import fr.slypy.linkium.item.LinkiumDynamiteItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class DynamiteRenderer {
	public static class ModelRegisterHandler {
		@SuppressWarnings("unchecked")
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(DynamiteItem.arrow,
					renderManager -> new SpriteRenderer<DynamiteItem.ArrowCustomEntity>(renderManager, Minecraft.getInstance().getItemRenderer()));
			
		}
	}
}
