package fr.slypy.linkium.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import fr.slypy.linkium.block.LinkiumTntBlock;
import fr.slypy.linkium.entity.LinkiumTntEntityEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.TNTMinecartRenderer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class LinkiumTntEntityRenderer {
	public static class ModelRegisterHandler {
		@SuppressWarnings("unchecked")
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(LinkiumTntEntityEntity.entity,
					renderManager -> new EntityRenderer<LinkiumTntEntityEntity.CustomEntity>(renderManager) {
						
						@Override
						public void render(LinkiumTntEntityEntity.CustomEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
							System.out.println("render");
						      matrixStackIn.push();
						      matrixStackIn.translate(0.0D, 0.5D, 0.0D);
						      if ((float)entityIn.getFuse() - partialTicks + 1.0F < 10.0F) {
							         float f = 1.0F - ((float)entityIn.getFuse() - partialTicks + 1.0F) / 10.0F;
							         f = MathHelper.clamp(f, 0.0F, 1.0F);
							         f = f * f;
							         f = f * f;
							         float f1 = 1.0F + f * 0.3F;
							         matrixStackIn.scale(f1, f1, f1);
							      }
						      matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-90.0F));
						      matrixStackIn.translate(-0.5D, -0.5D, 0.5D);
						      matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));
						      TNTMinecartRenderer.renderTntFlash(LinkiumTntBlock.block.getDefaultState(), matrixStackIn, bufferIn, packedLightIn, entityIn.getFuse() / 5 % 2 == 0);
						      matrixStackIn.pop();
						      super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
						   }

						@Override
						public ResourceLocation getEntityTexture(LinkiumTntEntityEntity.CustomEntity entity) {
							// TODO Auto-generated method stub
							return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
						}
						
					});
		}
	}
}
