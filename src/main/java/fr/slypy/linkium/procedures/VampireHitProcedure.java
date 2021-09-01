package fr.slypy.linkium.procedures;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.enchantment.EnchantmentHelper;

import java.util.Map;
import java.util.HashMap;

import fr.slypy.linkium.enchantment.VampireEnchantment;
import fr.slypy.linkium.LinkiumModElements;
import fr.slypy.linkium.LinkiumMod;

@LinkiumModElements.ModElement.Tag
public class VampireHitProcedure extends LinkiumModElements.ModElement {
	public VampireHitProcedure(LinkiumModElements instance) {
		super(instance, 93);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				LinkiumMod.LOGGER.warn("Failed to load dependency sourceentity for procedure VampireHit!");
			return;
		}
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		double rand = 0;
		if (((EnchantmentHelper.getEnchantmentLevel(VampireEnchantment.enchantment,
				((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHeldItemMainhand() : ItemStack.EMPTY))) != 0)) {
			if ((Math.random() <= (0.1 * (EnchantmentHelper.getEnchantmentLevel(VampireEnchantment.enchantment,
					((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHeldItemMainhand() : ItemStack.EMPTY)))))) {
				if (((((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHealth() : -1)
						+ 2) > ((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getMaxHealth() : -1))) {
					if (sourceentity instanceof LivingEntity)
						((LivingEntity) sourceentity)
								.setHealth((float) ((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getMaxHealth() : -1));
				} else {
					if (sourceentity instanceof LivingEntity)
						((LivingEntity) sourceentity)
								.setHealth((float) (((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHealth() : -1) + 2));
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null && !event.getEntityLiving().getEntityWorld().isRemote()) {
			Entity entity = event.getEntity();
			Entity sourceentity = event.getSource().getTrueSource();
			Entity imediatesourceentity = event.getSource().getImmediateSource();
			double i = entity.getPosX();
			double j = entity.getPosY();
			double k = entity.getPosZ();
			double amount = event.getAmount();
			World world = entity.world;
			Map<String, Object> dependencies = new HashMap<>();
			dependencies.put("x", i);
			dependencies.put("y", j);
			dependencies.put("z", k);
			dependencies.put("amount", amount);
			dependencies.put("world", world);
			dependencies.put("entity", entity);
			dependencies.put("sourceentity", sourceentity);
			dependencies.put("imediatesourceentity", imediatesourceentity);
			dependencies.put("event", event);
			this.executeProcedure(dependencies);
		}
	}
}
