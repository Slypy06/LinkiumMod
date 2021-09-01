package fr.slypy.linkium.entity.renderer;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.IRenderTypeBuffer;

import fr.slypy.linkium.entity.OverpoweredLinkiumIncarnationEntity;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class OverpoweredLinkiumIncarnationRenderer {
	public static class ModelRegisterHandler {
		@SuppressWarnings("unchecked")
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(OverpoweredLinkiumIncarnationEntity.entity, renderManager -> {
				return new MobRenderer(renderManager, new Modelcustom_model(), 2.1999999999999997f) {
					{
						this.addLayer(new GlowingLayer<>(this));
					}
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("linkium:textures/overpowered_linkium_incarnation.png");
					}
				};
			});
		}
	}

	@OnlyIn(Dist.CLIENT)
	private static class GlowingLayer<T extends Entity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
		public GlowingLayer(IEntityRenderer<T, M> er) {
			super(er);
		}

		public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing,
				float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
			if(((OverpoweredLinkiumIncarnationEntity.CustomEntity) entitylivingbaseIn).getHealth() < ((OverpoweredLinkiumIncarnationEntity.CustomEntity) entitylivingbaseIn).getMaxHealth() / 2) {
				IVertexBuilder ivertexbuilder = bufferIn
						.getBuffer(RenderType.getEyes(new ResourceLocation("linkium:textures/overpowered_linkium_incarnation_glow.png")));
				this.getEntityModel().render(matrixStackIn, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			}
		}
	}

	// Made with Blockbench 3.9.1
	// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
	// Paste this class into your mod and generate all required imports
	public static class Modelcustom_model extends EntityModel<Entity> {
		private final ModelRenderer head;
		private final ModelRenderer body_r1;
		private final ModelRenderer body;
		private final ModelRenderer arm1;
		private final ModelRenderer arm2;
		private final ModelRenderer leg3;
		private final ModelRenderer leg4;
		private final ModelRenderer neck;
		private final ModelRenderer jaw;
		public Modelcustom_model() {
			textureWidth = 256;
			textureHeight = 256;
			head = new ModelRenderer(this);
			head.setRotationPoint(0.0F, -36.5F, -3.0F);
			body_r1 = new ModelRenderer(this);
			body_r1.setRotationPoint(0.0F, 0.0F, 13.0F);
			head.addChild(body_r1);
			setRotationAngle(body_r1, 1.5708F, 0.0F, 0.0F);
			body_r1.setTextureOffset(0, 0).addBox(-8.0F, -18.0F, 0.0F, 16.0F, 12.0F, 16.0F, 0.0F, false);
			body = new ModelRenderer(this);
			body.setRotationPoint(0.0F, 0.5F, 3.0F);
			setRotationAngle(body, 1.5708F, 0.0F, 0.0F);
			body.setTextureOffset(0, 28).addBox(-20.0F, -13.0F, 5.0F, 40.0F, 20.0F, 32.0F, 0.0F, false);
			body.setTextureOffset(72, 6).addBox(-11.0F, -9.0F, -3.0F, 22.0F, 14.0F, 8.0F, 0.0F, false);
			body.setTextureOffset(132, 16).addBox(-8.0F, -5.0F, 9.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
			arm1 = new ModelRenderer(this);
			arm1.setRotationPoint(-20.0F, -26.0F, 0.0F);
			arm1.setTextureOffset(129, 82).addBox(-8.0F, 37.0F, -4.5F, 3.0F, 8.0F, 5.0F, 0.0F, false);
			arm1.setTextureOffset(83, 82).addBox(-16.0F, 15.0F, -5.5F, 11.0F, 22.0F, 12.0F, 0.0F, false);
			arm1.setTextureOffset(145, 80).addBox(-14.0F, 37.0F, -4.5F, 3.0F, 10.0F, 5.0F, 0.0F, false);
			arm1.setTextureOffset(145, 80).addBox(-14.0F, 37.0F, 1.5F, 3.0F, 10.0F, 5.0F, 0.0F, false);
			arm1.setTextureOffset(31, 80).addBox(-14.0F, -9.0F, -5.5F, 14.0F, 24.0F, 12.0F, 0.0F, false);
			arm2 = new ModelRenderer(this);
			arm2.setRotationPoint(20.0F, -26.0F, 0.0F);
			arm2.setTextureOffset(31, 116).addBox(0.0F, -9.0F, -5.5F, 14.0F, 24.0F, 12.0F, 0.0F, false);
			arm2.setTextureOffset(83, 118).addBox(5.0F, 15.0F, -5.5F, 11.0F, 22.0F, 12.0F, 0.0F, false);
			arm2.setTextureOffset(145, 80).addBox(11.0F, 37.0F, -4.5F, 3.0F, 10.0F, 5.0F, 0.0F, false);
			arm2.setTextureOffset(145, 80).addBox(11.0F, 37.0F, 1.5F, 3.0F, 10.0F, 5.0F, 0.0F, false);
			arm2.setTextureOffset(129, 82).addBox(5.0F, 37.0F, -4.5F, 3.0F, 8.0F, 5.0F, 0.0F, false);
			leg3 = new ModelRenderer(this);
			leg3.setRotationPoint(-8.0F, 4.0F, 0.0F);
			leg3.setTextureOffset(144, 48).addBox(-12.0F, 0.0F, -4.5F, 12.0F, 20.0F, 12.0F, 0.0F, false);
			leg4 = new ModelRenderer(this);
			leg4.setRotationPoint(8.0F, 4.0F, 0.0F);
			leg4.setTextureOffset(192, 48).addBox(1.0F, 0.0F, -4.5F, 12.0F, 20.0F, 12.0F, 0.0F, false);
			neck = new ModelRenderer(this);
			neck.setRotationPoint(0.0F, -6.0F, 8.0F);
			jaw = new ModelRenderer(this);
			jaw.setRotationPoint(0.0F, 24.0F, 0.0F);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			head.render(matrixStack, buffer, packedLight, packedOverlay);
			body.render(matrixStack, buffer, packedLight, packedOverlay);
			arm1.render(matrixStack, buffer, packedLight, packedOverlay);
			arm2.render(matrixStack, buffer, packedLight, packedOverlay);
			leg3.render(matrixStack, buffer, packedLight, packedOverlay);
			leg4.render(matrixStack, buffer, packedLight, packedOverlay);
			neck.render(matrixStack, buffer, packedLight, packedOverlay);
			jaw.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.leg4.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.leg3.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.arm1.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
			this.arm2.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
		}
	}
}
