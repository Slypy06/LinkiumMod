package fr.slypy.linkium.procedures;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.IWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.EquipmentSlotType.Group;
import net.minecraft.item.ItemStack;

import java.util.Map;
import java.util.HashMap;

import fr.slypy.linkium.LinkiumModElements;
import fr.slypy.linkium.item.OverpoweredLinkiumArmorItem;

@LinkiumModElements.ModElement.Tag
public class OverpoweredLinkiumArmorDamageProcedure extends LinkiumModElements.ModElement {
	
	public OverpoweredLinkiumArmorDamageProcedure(LinkiumModElements instance) {
		super(instance, 63);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onPlayerDamaged(LivingHurtEvent event) {

		if(event.getEntity() instanceof PlayerEntity) {
			
			PlayerEntity p = (PlayerEntity) event.getEntity();
			
			int reduction = 1;
			
			if(p.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(Group.ARMOR, 0)).getItem() == new ItemStack(OverpoweredLinkiumArmorItem.boots).getItem()) {
				
				reduction += 3;
				
			}
			
			if(p.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(Group.ARMOR, 1)).getItem() == new ItemStack(OverpoweredLinkiumArmorItem.legs).getItem()) {
				
				reduction += 8;
				
			}
			
			if(p.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(Group.ARMOR, 2)).getItem() == new ItemStack(OverpoweredLinkiumArmorItem.body).getItem()) {
				
				reduction += 6;
				
			}
			
			if(p.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(Group.ARMOR, 3)).getItem() == new ItemStack(OverpoweredLinkiumArmorItem.helmet).getItem()) {
				
				reduction += 3;
				
			}
			
			if(reduction > 1) {
				
				event.setAmount(event.getAmount() / (float) reduction);
				
			}
			
		}
		
	}
	
}
