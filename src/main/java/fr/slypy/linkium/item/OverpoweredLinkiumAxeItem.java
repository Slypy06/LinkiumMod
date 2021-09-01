
package fr.slypy.linkium.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.world.World;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.item.AxeItem;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Map;
import java.util.HashMap;

import fr.slypy.linkium.procedures.OverpoweredLinkiumAxeRightClickedInAirProcedure;
import fr.slypy.linkium.itemgroup.LinkiumModItemGroup;
import fr.slypy.linkium.LinkiumModElements;

@LinkiumModElements.ModElement.Tag
public class OverpoweredLinkiumAxeItem extends LinkiumModElements.ModElement {
	@ObjectHolder("linkium:overpowered_linkium_axe")
	public static final Item block = null;
	public OverpoweredLinkiumAxeItem(LinkiumModElements instance) {
		super(instance, 42);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new AxeItem(new IItemTier() {
			public int getMaxUses() {
				return 15000;
			}

			public float getEfficiency() {
				return 500f;
			}

			public float getAttackDamage() {
				return 123f;
			}

			public int getHarvestLevel() {
				return 999;
			}

			public int getEnchantability() {
				return 282;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(OverpoweredLinkiumIngotItem.block, (int) (1)));
			}
		}, 1, -3f, new Item.Properties().group(LinkiumModItemGroup.tab).isImmuneToFire()) {
			@Override
			public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity entity, Hand hand) {
				ActionResult<ItemStack> retval = super.onItemRightClick(world, entity, hand);
				ItemStack itemstack = retval.getResult();
				double x = entity.getPosX();
				double y = entity.getPosY();
				double z = entity.getPosZ();
				{
					Map<String, Object> $_dependencies = new HashMap<>();
					$_dependencies.put("entity", entity);
					$_dependencies.put("itemstack", itemstack);
					OverpoweredLinkiumAxeRightClickedInAirProcedure.executeProcedure($_dependencies);
				}
				return retval;
			}
		}.setRegistryName("overpowered_linkium_axe"));
	}
}
