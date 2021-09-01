
package fr.slypy.linkium.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.world.World;
import net.minecraft.item.UseAction;
import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.Food;
import net.minecraft.entity.LivingEntity;

import java.util.Map;
import java.util.HashMap;

import fr.slypy.linkium.procedures.LinkiumAppleFoodEatenProcedure;
import fr.slypy.linkium.LinkiumModElements;

@LinkiumModElements.ModElement.Tag
public class LinkiumAppleItem extends LinkiumModElements.ModElement {
	@ObjectHolder("linkium:linkium_apple")
	public static final Item block = null;
	public LinkiumAppleItem(LinkiumModElements instance) {
		super(instance, 96);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new FoodItemCustom());
	}
	public static class FoodItemCustom extends Item {
		public FoodItemCustom() {
			super(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).rarity(Rarity.COMMON)
					.food((new Food.Builder()).hunger(7).saturation(2f).setAlwaysEdible().build()));
			setRegistryName("linkium_apple");
		}

		@Override
		public UseAction getUseAction(ItemStack itemstack) {
			return UseAction.EAT;
		}

		@Override
		public ItemStack onItemUseFinish(ItemStack itemstack, World world, LivingEntity entity) {
			ItemStack retval = super.onItemUseFinish(itemstack, world, entity);
			double x = entity.getPosX();
			double y = entity.getPosY();
			double z = entity.getPosZ();
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("entity", entity);
				LinkiumAppleFoodEatenProcedure.executeProcedure($_dependencies);
			}
			return retval;
		}
	}
}
