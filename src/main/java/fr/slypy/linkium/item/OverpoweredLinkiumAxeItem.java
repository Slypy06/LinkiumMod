
package fr.slypy.linkium.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.item.AxeItem;

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
		}.setRegistryName("overpowered_linkium_axe"));
	}
}
