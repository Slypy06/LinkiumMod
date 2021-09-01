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
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
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

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
		this.leg4.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
		this.leg3.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
		this.arm1.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
		this.arm2.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
	}
}