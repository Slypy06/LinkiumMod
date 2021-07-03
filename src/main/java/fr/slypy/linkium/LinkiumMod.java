/*
 *    MCreator note:
 *
 *    If you lock base mod element files, you can edit this file and the proxy files
 *    and they won't get overwritten. If you change your mod package or modid, you
 *    need to apply these changes to this file MANUALLY.
 *
 *    Settings in @Mod annotation WON'T be changed in case of the base mod element
 *    files lock too, so you need to set them manually here in such case.
 *
 *    Keep the LinkiumModElements object in this class and all calls to this object
 *    INTACT in order to preserve functionality of mod elements generated by MCreator.
 *
 *    If you do not lock base mod element files in Workspace settings, this file
 *    will be REGENERATED on each build.
 *
 */
package fr.slypy.linkium;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod("linkium")
public class LinkiumMod {
	public static final Logger LOGGER = LogManager.getLogger(LinkiumMod.class);
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation("linkium", "linkium"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	public static LinkiumMod instance;
	public LinkiumModElements elements;
	public LinkiumMod() {
		instance = this;
		elements = new LinkiumModElements();
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientLoad);
		MinecraftForge.EVENT_BUS.register(new LinkiumModFMLBusEvents(this));
	}

	private void init(FMLCommonSetupEvent event) {
		elements.getElements().forEach(element -> element.init(event));
	}

	public void clientLoad(FMLClientSetupEvent event) {
		elements.getElements().forEach(element -> element.clientLoad(event));
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(elements.getBlocks().stream().map(Supplier::get).toArray(Block[]::new));
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(elements.getItems().stream().map(Supplier::get).toArray(Item[]::new));
	}

	@SubscribeEvent
	public void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().registerAll(elements.getEntities().stream().map(Supplier::get).toArray(EntityType[]::new));
	}

	@SubscribeEvent
	public void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
		event.getRegistry().registerAll(elements.getEnchantments().stream().map(Supplier::get).toArray(Enchantment[]::new));
	}

	@SubscribeEvent
	public void registerSounds(RegistryEvent.Register<net.minecraft.util.SoundEvent> event) {
		elements.registerSounds(event);
	}
	private static class LinkiumModFMLBusEvents {
		private final LinkiumMod parent;
		LinkiumModFMLBusEvents(LinkiumMod parent) {
			this.parent = parent;
		}

		@SubscribeEvent
		public void serverLoad(FMLServerStartingEvent event) {
			this.parent.elements.getElements().forEach(element -> element.serverLoad(event));
		}
		
		@SubscribeEvent
		public void blockBreak(BlockEvent.BreakEvent e) {
			
			PlayerEntity p = e.getPlayer();
			IWorld iw = e.getWorld();
			ItemStack tool = p.getHeldItemMainhand();
			BlockPos pos = e.getPos();
			
			if(iw instanceof World && !iw.isRemote()) {
				
				ServerWorld w = (ServerWorld) iw;
				
				if(LinkiumMod.instance.hasEnchantment("smelt", tool) && !p.isCreative()) {
					
					List<ItemStack> drops = Block.getDrops(e.getState(), w, pos, null, p, tool);
					
					System.out.println(drops);

					for(ItemStack item : drops) {
					
						if(w.getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(item), w).isPresent()) {

							FurnaceRecipe recipe = w.getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(item), w).get();
							
							ExperienceOrbEntity xp = new ExperienceOrbEntity(w, (float) pos.getX() + 0.5f, (float) pos.getY() + 0.5f, (float) pos.getZ() + 0.5f, (int) (recipe.getExperience() + 1));
							
							w.addEntity(xp);
	
							ItemStack result = recipe.getRecipeOutput().copy();
							result.setCount(item.getCount());

							ItemEntity drop = new ItemEntity(w, (float) pos.getX() + 0.5f, (float)  pos.getY() + 0.5f, (float) pos.getZ() + 0.5f, result);
							drop.setPickupDelay(10);
							
							w.addEntity(drop);
							
							int fortunebonus = new Random().nextInt(EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, tool) + 1);
							
							ItemStack resultBonus = result.copy();
							resultBonus.setCount(fortunebonus);
							
							ItemEntity dropBonus = new ItemEntity(w, pos.getX(), pos.getY(), pos.getZ(), resultBonus);
							dropBonus.setPickupDelay(10);
							
							w.addEntity(dropBonus);
							
							if (EnchantmentHelper.getEnchantments(tool).containsKey(Enchantments.UNBREAKING)) {
								
								int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, tool);
								int chance = 100 / (level + 1);
								
								if(new Random().nextInt(100) + 1 <= chance) {
									
									tool.setDamage(tool.getDamage() + 1);
									
								}
								
							} else {
								
								tool.setDamage(tool.getDamage() + 1);
								
							}
							
						} else {
							
							ItemEntity drop = new ItemEntity(w, (float) pos.getX() + 0.5f, (float) pos.getY() + 0.5f, (float) pos.getZ() + 0.5f, item);
							drop.setPickupDelay(10);
							
							w.addEntity(drop);
							
							if(e.getExpToDrop() > 0) {
							
								ExperienceOrbEntity xp = new ExperienceOrbEntity(w, (float) pos.getX() + 0.5f, (float) pos.getY() + 0.5f, (float) pos.getZ() + 0.5f, e.getExpToDrop());
								
								w.addEntity(xp);
							
							}
							
						}
					
					}
					
					if (EnchantmentHelper.getEnchantments(tool).containsKey(Enchantments.UNBREAKING)) {
						
						int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, tool);
						int chance = 100 / (level + 1);
						
						if(new Random().nextInt(100) + 1 <= chance) {
							
							tool.setDamage(tool.getDamage() + 1);
							
						}
						
					} else {
						
						tool.setDamage(tool.getDamage() + 1);
						
					}
					
					e.setCanceled(true);
					
					w.removeBlock(pos, false);

				}
				
			}

		}
		
	}
	
	public boolean hasEnchantment(String ench, ItemStack item) {
		
		Map<Enchantment, Integer> enchs = EnchantmentHelper.getEnchantments(item);
		
		for(Enchantment e : enchs.keySet()) {
			
			System.out.println(e.getRegistryName().getPath());
			
			if(e.getRegistryName().getPath().equalsIgnoreCase(ench)) {
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	public Enchantment getLinkiumEnchantment(String name) {
		
		Enchantment r = null;
		
		for(Supplier<Enchantment> e : elements.getEnchantments()) {
			
			Enchantment ench = e.get();
			
			System.out.println(ench.getRegistryName().getPath());
			
			if(ench.getRegistryName().getPath().equalsIgnoreCase(name)) {
				
				r = ench;
				
			}
			
		}
		
		return r;
		
	}
	
}
