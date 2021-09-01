package fr.slypy.linkium.entity.renderer;

import fr.slypy.linkium.item.LinkiumDynamiteItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class LinkiumDynamiteRenderer {
	public static class ModelRegisterHandler {
		@SuppressWarnings("unchecked")
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(LinkiumDynamiteItem.arrow,
					renderManager -> new SpriteRenderer<LinkiumDynamiteItem.ArrowCustomEntity>(renderManager, Minecraft.getInstance().getItemRenderer()));
			
		}
	}
}
