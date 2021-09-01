
package fr.slypy.linkium.block;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import fr.slypy.linkium.LinkiumModElements;
import fr.slypy.linkium.entity.LinkiumTntEntityEntity;
import fr.slypy.linkium.itemgroup.LinkiumModItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.TNTBlock;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ObjectHolder;

@LinkiumModElements.ModElement.Tag
public class LinkiumTntBlock extends LinkiumModElements.ModElement {
	@ObjectHolder("linkium:linkium_tnt")
	public static final Block block = null;
	
	public LinkiumTntBlock(LinkiumModElements instance) {
		super(instance, 64);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items.add(() -> new BlockItem(block, new Item.Properties().group(LinkiumModItemGroup.tab)).setRegistryName(block.getRegistryName()));
	}
	
	
	
	@Override
	public void init(FMLCommonSetupEvent event) {
		
		DispenserBlock.registerDispenseBehavior(block, new IDispenseItemBehavior() {
			
			@Override
			public ItemStack dispense(IBlockSource dispenser, ItemStack stack) {

				IPosition pos = DispenserBlock.getDispensePosition(dispenser);
				
		        LinkiumTntEntityEntity.CustomEntity tntentity = new LinkiumTntEntityEntity.CustomEntity(dispenser.getWorld(), (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, null);
		        dispenser.getWorld().addEntity(tntentity);
		        dispenser.getWorld().playSound((PlayerEntity)null, tntentity.getPosX(), tntentity.getPosY(), tntentity.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
				
				return stack.split(1);
				
			}
			
		});
		
		super.init(event);
	}



	public static class CustomBlock extends TNTBlock {
		
		public CustomBlock() {
			super(Block.Properties.create(Material.TNT).sound(SoundType.PLANT).hardnessAndResistance(0, 0).setLightLevel(s -> 0));
			setRegistryName("linkium_tnt");
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
			return Collections.singletonList(new ItemStack(this, 1));
		}
		
		@Override
		public void catchFire(BlockState state, World world, BlockPos pos, @Nullable net.minecraft.util.Direction face, @Nullable LivingEntity igniter) {
			
			if(!world.isRemote) {
				
		         LinkiumTntEntityEntity.CustomEntity tntentity = new LinkiumTntEntityEntity.CustomEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, igniter);
		         world.addEntity(tntentity);
		         world.playSound((PlayerEntity)null, tntentity.getPosX(), tntentity.getPosY(), tntentity.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
				
			}
			
		}
		
		@Override
		public void onExplosionDestroy(World world, BlockPos pos, Explosion explosion) {
			
			if(!world.isRemote) {

				LinkiumTntEntityEntity.CustomEntity tntentity = new LinkiumTntEntityEntity.CustomEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, explosion.getExplosivePlacedBy());
		        tntentity.setFuse((short)(world.rand.nextInt(tntentity.getFuse() / 4) + tntentity.getFuse() / 8));
		        world.addEntity(tntentity);
		         
			}
			
		}
		
	}
	
}
