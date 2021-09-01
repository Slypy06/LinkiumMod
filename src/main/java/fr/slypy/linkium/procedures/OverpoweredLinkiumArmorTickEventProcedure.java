package fr.slypy.linkium.procedures;

import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.potion.EffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import java.util.Map;

import org.spongepowered.asm.mixin.MixinEnvironment.Side;

import fr.slypy.linkium.item.OverpoweredLinkiumArmorItem;
import fr.slypy.linkium.LinkiumModElements;
import fr.slypy.linkium.LinkiumMod;

@LinkiumModElements.ModElement.Tag
public class OverpoweredLinkiumArmorTickEventProcedure extends LinkiumModElements.ModElement {
	public OverpoweredLinkiumArmorTickEventProcedure(LinkiumModElements instance) {
		super(instance, 59);
	}

	@OnlyIn(Dist.CLIENT)
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				LinkiumMod.LOGGER.warn("Failed to load dependency entity for procedure OverpoweredLinkiumArmorTickEvent!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if ((((entity instanceof LivingEntity)
				? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 1))
				: ItemStack.EMPTY).getItem() == new ItemStack(OverpoweredLinkiumArmorItem.legs, (int) (1)).getItem())) {
			if ((entity.isSprinting())) {
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SPEED, (int) 5, (int) 2, (false), (false)));
			} else {
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SPEED, (int) 5, (int) 1, (false), (false)));
			}
		}
		if ((((entity instanceof LivingEntity)
				? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2))
				: ItemStack.EMPTY).getItem() == new ItemStack(OverpoweredLinkiumArmorItem.body, (int) (1)).getItem())) {
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.STRENGTH, (int) 5, (int) 3, (false), (false)));
		}
		if ((((entity instanceof LivingEntity)
				? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3))
				: ItemStack.EMPTY).getItem() == new ItemStack(OverpoweredLinkiumArmorItem.helmet, (int) (1)).getItem())) {
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, (int) 205, (int) 0, (false), (false)));
		}
		if (entity instanceof PlayerEntity) {
			
			if(((((entity instanceof LivingEntity)
					? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 0))
					: ItemStack.EMPTY).getItem() == new ItemStack(OverpoweredLinkiumArmorItem.boots, (int) (1)).getItem())
					&& (((entity instanceof LivingEntity)
							? ((LivingEntity) entity)
									.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 1))
							: ItemStack.EMPTY).getItem() == new ItemStack(OverpoweredLinkiumArmorItem.legs, (int) (1)).getItem()))
					&& ((((entity instanceof LivingEntity)
							? ((LivingEntity) entity)
									.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2))
							: ItemStack.EMPTY).getItem() == new ItemStack(OverpoweredLinkiumArmorItem.body, (int) (1)).getItem())
							&& (((entity instanceof LivingEntity)
									? ((LivingEntity) entity)
											.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3))
									: ItemStack.EMPTY).getItem() == new ItemStack(OverpoweredLinkiumArmorItem.helmet, (int) (1)).getItem()))) {
				
				if(entity.world.isRemote) {
				
					((PlayerEntity) entity).abilities.allowFlying = true;
					((PlayerEntity) entity).abilities.setFlySpeed(0.12f);
				
				} else {
					
					((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.INVISIBILITY, (int) 5, (int) 0, (false), (false)));
					
				}
				
			} else {
				
				if(entity.world.isRemote) {
				
					((PlayerEntity) entity).abilities.allowFlying = ((PlayerEntity) entity).isCreative();
					((PlayerEntity) entity).abilities.setFlySpeed(0.05f);
				
				}
				
			}
			
			if(entity.world.isRemote) {
			
				((PlayerEntity) entity).sendPlayerAbilities();
			
			}
			
		}
	}
}
