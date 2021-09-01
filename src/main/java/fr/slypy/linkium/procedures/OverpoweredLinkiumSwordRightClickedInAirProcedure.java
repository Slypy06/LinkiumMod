package fr.slypy.linkium.procedures;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import java.util.Map;

import fr.slypy.linkium.LinkiumModElements;
import fr.slypy.linkium.LinkiumMod;

@LinkiumModElements.ModElement.Tag
public class OverpoweredLinkiumSwordRightClickedInAirProcedure extends LinkiumModElements.ModElement {
	public OverpoweredLinkiumSwordRightClickedInAirProcedure(LinkiumModElements instance) {
		super(instance, 95);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				LinkiumMod.LOGGER.warn("Failed to load dependency entity for procedure OverpoweredLinkiumSwordRightClickedInAir!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				LinkiumMod.LOGGER.warn("Failed to load dependency itemstack for procedure OverpoweredLinkiumSwordRightClickedInAir!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		if ((entity.isSneaking())) {
			if (entity instanceof PlayerEntity)
				((PlayerEntity) entity).getCooldownTracker().setCooldown(((itemstack)).getItem(), (int) 20);
			if ((((itemstack).getOrCreateTag().getDouble("mode")) == 0)) {
				(itemstack).getOrCreateTag().putDouble("mode", 1);
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Sword mode set to : Extermination Mode"), (false));
				}
			} else {
				(itemstack).getOrCreateTag().putDouble("mode", 0);
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Sword mode set to : Classic Mode"), (false));
				}
			}
		}
	}
}
