
package fr.slypy.linkium.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.world.World;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.BlockState;

import java.util.Map;
import java.util.HashMap;

import fr.slypy.linkium.procedures.LinkiumStickRightClickedInAirProcedure;
import fr.slypy.linkium.itemgroup.LinkiumModItemGroup;
import fr.slypy.linkium.LinkiumModElements;

@LinkiumModElements.ModElement.Tag
public class ResistanceLinkiumStickItem extends LinkiumModElements.ModElement {
	@ObjectHolder("linkium:resistance_linkium_stick")
	public static final Item block = null;
	public ResistanceLinkiumStickItem(LinkiumModElements instance) {
		super(instance, 87);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(LinkiumModItemGroup.tab).maxDamage(25).rarity(Rarity.COMMON));
			setRegistryName("resistance_linkium_stick");
		}

		@Override
		public int getItemEnchantability() {
			return 30;
		}

		@Override
		public int getUseDuration(ItemStack itemstack) {
			return 0;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}

		@Override
		public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity entity, Hand hand) {
			ActionResult<ItemStack> ar = super.onItemRightClick(world, entity, hand);
			ItemStack itemstack = ar.getResult();
			double x = entity.getPosX();
			double y = entity.getPosY();
			double z = entity.getPosZ();
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("entity", entity);
				$_dependencies.put("itemstack", itemstack);
				LinkiumStickRightClickedInAirProcedure.executeProcedure($_dependencies);
			}
			return ar;
		}
	}
}
