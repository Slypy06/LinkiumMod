
package fr.slypy.linkium.gui;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import fr.slypy.linkium.LinkiumMod;
import fr.slypy.linkium.block.LinkiumPowerLoaderBlock;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class LinkiumPowerLoaderGUIGuiWindow extends ContainerScreen<LinkiumPowerLoaderGUIGui.GuiContainerMod> {
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	public LinkiumPowerLoaderGUIGuiWindow(LinkiumPowerLoaderGUIGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.xSize = 200;
		this.ySize = 220;
	}
	private static final ResourceLocation texture = new ResourceLocation("linkium:textures/linkium_power_loader_gui.png");
	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.blit(ms, k, l, 0, 0, this.xSize, this.ySize, this.xSize + 17, this.ySize);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeScreen();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {
		
		this.font.drawString(ms, "Linkium Power Loader", 48, 6, -12829636);
		
		if(container.te != null && container.te instanceof LinkiumPowerLoaderBlock.CustomTileEntity) {
			
			int i = ((LinkiumPowerLoaderBlock.CustomTileEntity) container.te).getCraftTime();
			float diff = 200 - i;
			float percent = diff / 200.0f;
			
			RenderSystem.color4f(1, 1, 1, 1);
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("linkium:textures/linkium_power_loader_gui.png"));
			
			this.blit(ms, 90, 44, 200, 0, 17, (int) (24.0f * percent), this.xSize + 17, this.ySize);
			
			RenderSystem.disableBlend();
			
		}

	}

	@Override
	public void onClose() {
		super.onClose();
		Minecraft.getInstance().keyboardListener.enableRepeatEvents(false);
	}

	@Override
	public void init(Minecraft minecraft, int width, int height) {
		super.init(minecraft, width, height);
		minecraft.keyboardListener.enableRepeatEvents(true);
		this.addButton(new Button(this.guiLeft + 76, this.guiTop + 106, 45, 20, new StringTextComponent("LOAD"), e -> {
			if (true) {
				LinkiumMod.PACKET_HANDLER.sendToServer(new LinkiumPowerLoaderGUIGui.ButtonPressedMessage(0, x, y, z));
				LinkiumPowerLoaderGUIGui.handleButtonAction(entity, 0, x, y, z);
			}
		}));
	}
}
