package fr.slypy.linkium.procedures;

import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import java.util.Random;
import java.util.Map;

import fr.slypy.linkium.item.StrengthLinkiumStickItem;
import fr.slypy.linkium.item.SpeedLinkiumStickItem;
import fr.slypy.linkium.item.ResistanceLinkiumStickItem;
import fr.slypy.linkium.item.JumpLinkiumStickItem;
import fr.slypy.linkium.item.HealLinkiumStickItem;
import fr.slypy.linkium.item.FeedLinkiumStickItem;
import fr.slypy.linkium.LinkiumModElements;
import fr.slypy.linkium.LinkiumMod;

@LinkiumModElements.ModElement.Tag
public class LinkiumStickRightClickedInAirProcedure extends LinkiumModElements.ModElement {
	public LinkiumStickRightClickedInAirProcedure(LinkiumModElements instance) {
		super(instance, 79);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				LinkiumMod.LOGGER.warn("Failed to load dependency entity for procedure LinkiumStickRightClickedInAir!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				LinkiumMod.LOGGER.warn("Failed to load dependency itemstack for procedure LinkiumStickRightClickedInAir!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		if (entity instanceof PlayerEntity)
			((PlayerEntity) entity).getCooldownTracker().setCooldown(((itemstack)).getItem(), (int) 600);
		{
			ItemStack _ist = (itemstack);
			if (_ist.attemptDamageItem((int) 1, new Random(), null)) {
				_ist.shrink(1);
				_ist.setDamage(0);
			}
		}
		if (((itemstack).getItem() == new ItemStack(SpeedLinkiumStickItem.block, (int) (1)).getItem())) {
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SPEED, (int) 300, (int) 2, (false), (false)));
		}
		if (((itemstack).getItem() == new ItemStack(StrengthLinkiumStickItem.block, (int) (1)).getItem())) {
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.STRENGTH, (int) 300, (int) 2, (false), (false)));
		}
		if (((itemstack).getItem() == new ItemStack(JumpLinkiumStickItem.block, (int) (1)).getItem())) {
			entity.setMotion(0, 4, 0);
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, (int) 200, (int) 0, (false), (false)));
		}
		if (((itemstack).getItem() == new ItemStack(FeedLinkiumStickItem.block, (int) (1)).getItem())) {
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SATURATION, (int) 300, (int) 0, (false), (false)));
		}
		if (((itemstack).getItem() == new ItemStack(ResistanceLinkiumStickItem.block, (int) (1)).getItem())) {
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.RESISTANCE, (int) 300, (int) 2, (false), (false)));
		}
		if (((itemstack).getItem() == new ItemStack(HealLinkiumStickItem.block, (int) (1)).getItem())) {
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.REGENERATION, (int) 300, (int) 2, (false), (false)));
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.INSTANT_HEALTH, (int) 1, (int) 0, (false), (false)));
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.HEALTH_BOOST, (int) 600, (int) 1, (false), (false)));
		}
	}
}
