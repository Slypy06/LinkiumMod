package fr.slypy.linkium.procedures;

import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;

import java.util.Map;
import java.util.Random;
import java.util.HashMap;

import fr.slypy.linkium.enchantment.ImmortalityEnchantment;
import fr.slypy.linkium.LinkiumModElements;
import fr.slypy.linkium.LinkiumMod;

@LinkiumModElements.ModElement.Tag
public class ImmortalityDeathProcedure extends LinkiumModElements.ModElement {
	
	public ImmortalityDeathProcedure(LinkiumModElements instance) {
		super(instance, 98);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			LivingEntity entity = event.getEntityLiving();
			Entity sourceentity = event.getSource().getTrueSource();
			double x = entity.getPosX();
			double y = entity.getPosY();
			double z = entity.getPosZ();
			World world = entity.world;
			double chance = 0;
			chance = (double) (EnchantmentHelper.getEnchantmentLevel(ImmortalityEnchantment.enchantment, entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3))));
			chance = (double) ((chance) + (EnchantmentHelper.getEnchantmentLevel(ImmortalityEnchantment.enchantment, entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3)))));
			chance = (double) ((chance) + (EnchantmentHelper.getEnchantmentLevel(ImmortalityEnchantment.enchantment, entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2)))));
			chance = (double) ((chance) + (EnchantmentHelper.getEnchantmentLevel(ImmortalityEnchantment.enchantment, entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3)))));
			if ((Math.random() <= ((chance) * 0.04))) {
				
				event.setCanceled(true);
				
				entity.setHealth(6.0f);
				
				if (world instanceof World && !world.isRemote()) {
					
					((World) world).playSound(null, new BlockPos(x, y, z), SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE, SoundCategory.PLAYERS, 1, 0.75f);
					((World) world).playSound(null, new BlockPos(x, y, z), SoundEvents.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, SoundCategory.PLAYERS, 1, 1.25f);
					((World) world).playSound(null, new BlockPos(x, y, z), SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR, SoundCategory.PLAYERS, 1, 1);
					((World) world).playSound(null, new BlockPos(x, y, z), SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST_FAR, SoundCategory.PLAYERS, 1, 0.75f);
					
				} else {
					
					((World) world).playSound(x, y, z, SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE, SoundCategory.PLAYERS, 1, 0.75f, false);
					((World) world).playSound(x, y, z, SoundEvents.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, SoundCategory.PLAYERS, 1, 1.25f, false);
					((World) world).playSound(x, y, z, SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR, SoundCategory.PLAYERS, 1, 1, false);
					((World) world).playSound(x, y, z, SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST_FAR, SoundCategory.PLAYERS, 1, 0.75f, false);
					
				}
				
				if (world instanceof ServerWorld) {
					
					((ServerWorld) world).spawnParticle(ParticleTypes.NAUTILUS, x, y + 1.4, z, 750, 0.1, 0.1, 0.1, 0.75);
				
				}
				
				entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 30, 255));
				entity.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 30, 250));
				entity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 100, 2));
				entity.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 300, 1));
				
				int rand = new Random().nextInt((int) chance);
				int armor0 = EnchantmentHelper.getEnchantmentLevel(ImmortalityEnchantment.enchantment, entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 0)));
				int armor1 = EnchantmentHelper.getEnchantmentLevel(ImmortalityEnchantment.enchantment, entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 1)));
				int armor2 = EnchantmentHelper.getEnchantmentLevel(ImmortalityEnchantment.enchantment, entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2)));
				int armor3 = EnchantmentHelper.getEnchantmentLevel(ImmortalityEnchantment.enchantment, entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3)));
				
				if(rand < armor0) {
					
					if (EnchantmentHelper.getEnchantments(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 0))).containsKey(Enchantments.UNBREAKING)) {
						
						int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 0)));
						int uchance = 100 / (level + 1);
						
						for(int i = 0; i < 300; i++) {
						
							if(new Random().nextInt(100) + 1 <= uchance) {
								
								entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 0)).setDamage(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 0)).getDamage() + 1);
								
							}
						
						}
						
					} else {
						
						entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 0)).setDamage(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 0)).getDamage() + 300);
						
					}
					
					if(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 0)).getDamage() >= entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 0)).getMaxDamage()) {
						
						entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 0)).shrink(1);
						
						if(entity instanceof PlayerEntity) {
							
							((PlayerEntity) entity).addStat(Stats.ITEM_BROKEN.get(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 0)).getItem()));
							
						}
						
					}
					
				} else if(rand < armor0 + armor1) {
					
					if (EnchantmentHelper.getEnchantments(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 1))).containsKey(Enchantments.UNBREAKING)) {
						
						int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 1)));
						int uchance = 100 / (level + 1);
						
						for(int i = 0; i < 300; i++) {
						
							if(new Random().nextInt(100) + 1 <= uchance) {
								
								entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 1)).setDamage(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 1)).getDamage() + 1);
								
							}
						
						}
						
					} else {
						
						entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 1)).setDamage(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 1)).getDamage() + 300);
						
					}
					
					if(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 1)).getDamage() >= entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 1)).getMaxDamage()) {
						
						entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 1)).shrink(1);
						
						if(entity instanceof PlayerEntity) {
							
							((PlayerEntity) entity).addStat(Stats.ITEM_BROKEN.get(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 1)).getItem()));
							
						}
						
					}
					
				} else if(rand < armor0 + armor1 + armor2) {
					
					if (EnchantmentHelper.getEnchantments(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2))).containsKey(Enchantments.UNBREAKING)) {
						
						int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2)));
						int uchance = 100 / (level + 1);
						
						for(int i = 0; i < 300; i++) {
						
							if(new Random().nextInt(100) + 1 <= uchance) {
								
								entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2)).setDamage(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2)).getDamage() + 1);
								
							}
						
						}
						
					} else {
						
						entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2)).setDamage(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2)).getDamage() + 300);
						
					}
					
					if(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2)).getDamage() >= entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2)).getMaxDamage()) {
						
						entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2)).shrink(1);
						
						if(entity instanceof PlayerEntity) {
							
							((PlayerEntity) entity).addStat(Stats.ITEM_BROKEN.get(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 2)).getItem()));
							
						}
						
					}
					
				} else {
					
					if (EnchantmentHelper.getEnchantments(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3))).containsKey(Enchantments.UNBREAKING)) {
						
						int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3)));
						int uchance = 100 / (level + 1);
						
						for(int i = 0; i < 300; i++) {
						
							if(new Random().nextInt(100) + 1 <= uchance) {
								
								entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3)).setDamage(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3)).getDamage() + 1);
								
							}
						
						}
						
					} else {
						
						entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3)).setDamage(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3)).getDamage() + 300);
						
					}
					
					if(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3)).getDamage() >= entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3)).getMaxDamage()) {
						
						entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3)).shrink(1);
						
						if(entity instanceof PlayerEntity) {
							
							((PlayerEntity) entity).addStat(Stats.ITEM_BROKEN.get(entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3)).getItem()));
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
}
