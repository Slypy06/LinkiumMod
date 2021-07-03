
package fr.slypy.linkium.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.item.HoeItem;

import fr.slypy.linkium.itemgroup.LinkiumModItemGroup;
import fr.slypy.linkium.LinkiumModElements;

@LinkiumModElements.ModElement.Tag
public class OverpoweredLinkiumHoeItem extends LinkiumModElements.ModElement {
	@ObjectHolder("linkium:overpowered_linkium_hoe")
	public static final Item block = null;
	public OverpoweredLinkiumHoeItem(LinkiumModElements instance) {
		super(instance, 41);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new HoeItem(new IItemTier() {
			public int getMaxUses() {
				return 15000;
			}

			public float getEfficiency() {
				return 500f;
			}

			public float getAttackDamage() {
				return 13f;
			}

			public int getHarvestLevel() {
				return 999;
			}

			public int getEnchantability() {
				return 28;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(OverpoweredLinkiumIngotItem.block, (int) (1)));
			}
		}, 0, 0.5f, new Item.Properties().group(LinkiumModItemGroup.tab).isImmuneToFire()) {
		}.setRegistryName("overpowered_linkium_hoe"));
	}
}
