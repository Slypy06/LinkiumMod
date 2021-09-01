
package fr.slypy.linkium.item;

import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ArmorItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import java.util.Map;
import java.util.HashMap;

import fr.slypy.linkium.procedures.OverpoweredLinkiumArmorTickEventProcedure;
import fr.slypy.linkium.itemgroup.LinkiumModItemGroup;
import fr.slypy.linkium.LinkiumModElements;

@LinkiumModElements.ModElement.Tag
public class OverpoweredLinkiumArmorItem extends LinkiumModElements.ModElement {
	@ObjectHolder("linkium:overpowered_linkium_armor_helmet")
	public static final Item helmet = null;
	@ObjectHolder("linkium:overpowered_linkium_armor_chestplate")
	public static final Item body = null;
	@ObjectHolder("linkium:overpowered_linkium_armor_leggings")
	public static final Item legs = null;
	@ObjectHolder("linkium:overpowered_linkium_armor_boots")
	public static final Item boots = null;
	public OverpoweredLinkiumArmorItem(LinkiumModElements instance) {
		super(instance, 54);
	}

	@Override
	public void initElements() {
		IArmorMaterial armormaterial = new IArmorMaterial() {
			@Override
			public int getDurability(EquipmentSlotType slot) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 350;
			}

			@Override
			public int getDamageReductionAmount(EquipmentSlotType slot) {
				return new int[]{3, 6, 8, 3}[slot.getIndex()];
			}

			@Override
			public int getEnchantability() {
				return 28;
			}

			@Override
			public net.minecraft.util.SoundEvent getSoundEvent() {
				return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_turtle"));
			}

			@Override
			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(OverpoweredLinkiumIngotItem.block, (int) (1)));
			}

			@OnlyIn(Dist.CLIENT)
			@Override
			public String getName() {
				return "overpowered_linkium_armor";
			}

			@Override
			public float getToughness() {
				return 10f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.5f;
			}
		};
		elements.items.add(() -> new ArmorItem(armormaterial, EquipmentSlotType.HEAD, new Item.Properties().group(LinkiumModItemGroup.tab).isImmuneToFire()) {
			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return "linkium:textures/models/armor/overpowered_linkium___layer_" + (slot == EquipmentSlotType.LEGS ? "2" : "1") + ".png";
			}

			@Override
			public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
				super.onArmorTick(itemstack, world, entity);
				
				double x = entity.getPosX();
				double y = entity.getPosY();
				double z = entity.getPosZ();
				{
					Map<String, Object> $_dependencies = new HashMap<>();
					$_dependencies.put("entity", entity);
					OverpoweredLinkiumArmorTickEventProcedure.executeProcedure($_dependencies);
				}
			}
		}.setRegistryName("overpowered_linkium_armor_helmet"));
		elements.items.add(() -> new ArmorItem(armormaterial, EquipmentSlotType.CHEST, new Item.Properties().group(LinkiumModItemGroup.tab).isImmuneToFire()) {
			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return "linkium:textures/models/armor/overpowered_linkium___layer_" + (slot == EquipmentSlotType.LEGS ? "2" : "1") + ".png";
			}

			@Override
			public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
				super.onArmorTick(itemstack, world, entity);
				
				double x = entity.getPosX();
				double y = entity.getPosY();
				double z = entity.getPosZ();
				{
					Map<String, Object> $_dependencies = new HashMap<>();
					$_dependencies.put("entity", entity);
					OverpoweredLinkiumArmorTickEventProcedure.executeProcedure($_dependencies);
				}
			}
		}.setRegistryName("overpowered_linkium_armor_chestplate"));
		elements.items.add(() -> new ArmorItem(armormaterial, EquipmentSlotType.LEGS, new Item.Properties().group(LinkiumModItemGroup.tab).isImmuneToFire()) {
			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return "linkium:textures/models/armor/overpowered_linkium___layer_" + (slot == EquipmentSlotType.LEGS ? "2" : "1") + ".png";
			}

			@Override
			public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
				super.onArmorTick(itemstack, world, entity);
				
				double x = entity.getPosX();
				double y = entity.getPosY();
				double z = entity.getPosZ();
				{
					Map<String, Object> $_dependencies = new HashMap<>();
					$_dependencies.put("entity", entity);
					OverpoweredLinkiumArmorTickEventProcedure.executeProcedure($_dependencies);
				}
			}
		}.setRegistryName("overpowered_linkium_armor_leggings"));
		elements.items.add(() -> new ArmorItem(armormaterial, EquipmentSlotType.FEET, new Item.Properties().group(LinkiumModItemGroup.tab).isImmuneToFire()) {
			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
				return "linkium:textures/models/armor/overpowered_linkium___layer_" + (slot == EquipmentSlotType.LEGS ? "2" : "1") + ".png";
			}

			@Override
			public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
				super.onArmorTick(itemstack, world, entity);
				
				double x = entity.getPosX();
				double y = entity.getPosY();
				double z = entity.getPosZ();
				{
					Map<String, Object> $_dependencies = new HashMap<>();
					$_dependencies.put("entity", entity);
					OverpoweredLinkiumArmorTickEventProcedure.executeProcedure($_dependencies);
				}
			}
		}.setRegistryName("overpowered_linkium_armor_boots"));
	}
}
