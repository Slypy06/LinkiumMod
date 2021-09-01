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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;

import fr.slypy.linkium.item.OverpoweredLinkiumAxeItem;
import fr.slypy.linkium.item.OverpoweredLinkiumPickaxeItem;
import fr.slypy.linkium.item.OverpoweredLinkiumSwordItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
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
		boolean ignorePickaxe = false;
		private final HashMultimap<UUID, BlockPos> nextMap = HashMultimap.create();
		private final Map<UUID, Integer> mined = new HashMap<UUID, Integer>();
		private boolean realAxeHit = true;
		private final LinkiumMod parent;
		LinkiumModFMLBusEvents(LinkiumMod parent) {
			this.parent = parent;
		}

		@SubscribeEvent
		public void serverLoad(FMLServerStartingEvent event) {
			this.parent.elements.getElements().forEach(element -> element.serverLoad(event));
		}
		
		public int determineOrientation(World w, BlockPos pos, LivingEntity living) {
			
			if(MathHelper.abs((float) (living.getPosX() - pos.getX())) < 2.0F && MathHelper.abs((float) (living.getPosZ() - pos.getZ())) < 2.0F) {
				
				double d0 = living.getPosY() + 1.82D - living.getYOffset();
				
				if(d0 - pos.getY() > 2.0D || pos.getY() - d0 > 0.0D) {
					
					return 0;
					
				}
				
			}
			
			float rotation = MathHelper.abs(living.rotationYaw);
			
			return (rotation > 45F && rotation < 135F) || (rotation > 225F && rotation < 315F) ? 2 : 1;
			
		}
		
		@SubscribeEvent
		public void blockBreakPickaxe(BlockEvent.BreakEvent e) {

			if(e.getWorld() instanceof World && e.getPlayer() instanceof ServerPlayerEntity && !e.getWorld().isRemote() && !ignorePickaxe && e.getPlayer().getHeldItemMainhand().getItem() == OverpoweredLinkiumPickaxeItem.block && e.getPlayer().getHeldItemMainhand().getOrCreateTag().contains("mode") && e.getPlayer().getHeldItemMainhand().getOrCreateTag().getInt("mode") == 1) {
				
				World w = (ServerWorld) e.getWorld();
				
				int orientation = determineOrientation(w, e.getPos(), e.getPlayer());
				
				ServerPlayerEntity p = (ServerPlayerEntity) e.getPlayer();
				
				ignorePickaxe = true;
				
				switch(orientation) {
				
				case 0:
					
					for(int xi = -1; xi < 2; xi++) {
						
						for(int zi = -1; zi < 2; zi++) {
							
							BlockPos pos = new BlockPos(e.getPos().getX() + xi, e.getPos().getY(), e.getPos().getZ() + zi);
							BlockState state = w.getBlockState(pos);
							
							if(e.getState().getBlock() != Blocks.AIR && e.getPlayer().isCreative() || (!state.getRequiresTool() || state.getHarvestTool() == ToolType.PICKAXE && 999 >= state.getHarvestLevel()) && state.getBlockHardness(w, pos) > 0) {
							
								p.interactionManager.tryHarvestBlock(pos);
							
							}
							
						}
						
					}
					
					break;
					
				case 1:
					
					for(int xi = -1; xi < 2; xi++) {
						
						for(int yi = -1; yi < 2; yi++) {
							
							BlockPos pos = new BlockPos(e.getPos().getX() + xi, e.getPos().getY() + yi, e.getPos().getZ());
							BlockState state = w.getBlockState(pos);
							
							if(e.getState().getBlock() != Blocks.AIR && e.getPlayer().isCreative() || (!state.getRequiresTool() || state.getHarvestTool() == ToolType.PICKAXE && 999 >= state.getHarvestLevel()) && state.getBlockHardness(w, pos) > 0) {
							
								p.interactionManager.tryHarvestBlock(pos);
							
							}
							
						}
						
					}
					
					break;
					
				case 2:
					
					for(int zi = -1; zi < 2; zi++) {
						
						for(int yi = -1; yi < 2; yi++) {
							
							BlockPos pos = new BlockPos(e.getPos().getX(), e.getPos().getY() + yi, e.getPos().getZ() + zi);
							BlockState state = w.getBlockState(pos);
							
							if(e.getState().getBlock() != Blocks.AIR && e.getPlayer().isCreative() || (!state.getRequiresTool() || state.getHarvestTool() == ToolType.PICKAXE && 999 >= state.getHarvestLevel()) && state.getBlockHardness(w, pos) > 0) {
							
								p.interactionManager.tryHarvestBlock(pos);
							
							}
							
						}
						
					}
					
					break;
				
				}
				
				ignorePickaxe = false;
				
			}
			
		}
		
		@SubscribeEvent
		public void tickAxe(TickEvent.PlayerTickEvent e) {
			
			if(e.phase != TickEvent.Phase.START) {
				
				return;
				
			}
			
			if(e.side != LogicalSide.SERVER) {
				
				return;
				
			}
			
			UUID uuid = e.player.getUniqueID();

			if (!nextMap.containsKey(uuid) || nextMap.get(uuid).isEmpty()) {
				
				return;
				
			}

			int i = 0;

			for(BlockPos point : ImmutableSet.copyOf(nextMap.get(uuid))) {

				realAxeHit = false;
				((ServerPlayerEntity) e.player).interactionManager.tryHarvestBlock(point);
				realAxeHit = true;
	
				nextMap.remove(uuid, point);

				System.out.println(nextMap);
				
				if(i++ > 16) { // tick limit
				
					System.out.println("break to next tick");
					
					break;
					
				}
			
			}
			
			if(mined.get(uuid) > 128) { // total limit
				
				nextMap.removeAll(uuid);
				
			}
			
		}
		
		@SubscribeEvent
		public void entityKilledSword(LivingDeathEvent e) {
			
			if(!(e.getEntityLiving() instanceof PlayerEntity) && !e.getEntity().getEntityWorld().isRemote()) {
				
				LivingEntity entity = e.getEntityLiving();
				LivingEntity killer = entity.getAttackingEntity();
				
				if(killer != null && killer.getEntityWorld() instanceof World && killer instanceof ServerPlayerEntity && !killer.getEntityWorld().isRemote() && !ignorePickaxe && killer.getHeldItemMainhand().getItem() == OverpoweredLinkiumSwordItem.block && killer.getHeldItemMainhand().getOrCreateTag().contains("mode") && killer.getHeldItemMainhand().getOrCreateTag().getInt("mode") == 1) {
					
					entity.getEntityWorld().getEntitiesWithinAABB(entity.getClass(), new AxisAlignedBB(entity.getPosX() - 15, entity.getPosY() - 15, entity.getPosY() - 15, entity.getPosX() + 15, entity.getPosY() + 15, entity.getPosZ() + 15), null).forEach(livingEntity -> {
						
						if(!livingEntity.getUniqueID().equals(entity.getUniqueID())) {
						
							livingEntity.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) killer), Float.MAX_VALUE);
							
							if (EnchantmentHelper.getEnchantments(killer.getHeldItemMainhand()).containsKey(Enchantments.UNBREAKING)) {
								
								int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, killer.getHeldItemMainhand());
								int chance = 100 / (level + 1);
								
								for(int i = 0; i < 5; i++) {
								
									if(new Random().nextInt(100) + 1 <= chance) {
										
										killer.getHeldItemMainhand().setDamage(killer.getHeldItemMainhand().getDamage() + 1);
										
									}
								
								}
								
							} else {
								
								killer.getHeldItemMainhand().setDamage(killer.getHeldItemMainhand().getDamage() + 5);
								
							}
						
						}
						
						if(killer.getHeldItemMainhand().getDamage() >= killer.getHeldItemMainhand().getMaxDamage()) {
							
							killer.getHeldItemMainhand().shrink(1);
							
							((PlayerEntity) killer).addStat(Stats.ITEM_BROKEN.get(killer.getHeldItemMainhand().getItem()));
							
						}
						
					});
					
				}
				
			}
			
		}
		
		@SubscribeEvent
		public void blockBreakAxe(BlockEvent.BreakEvent e) {
			
			if(e.getWorld() instanceof World && e.getPlayer() instanceof ServerPlayerEntity && !e.getWorld().isRemote() && e.getPlayer().getHeldItemMainhand().getItem() == OverpoweredLinkiumAxeItem.block && e.getPlayer().getHeldItemMainhand().getOrCreateTag().contains("mode") && e.getPlayer().getHeldItemMainhand().getOrCreateTag().getInt("mode") == 1) {
				
				if(e.getState().getBlock().getRegistryName().getPath().contains("log") || e.getState().getBlock().getRegistryName().getPath().contains("wood") || e.getState().getMaterial() == Material.LEAVES) {
					
					if(realAxeHit) {
						
						mined.remove(e.getPlayer().getUniqueID());
						
					}
					
					UUID uuid = e.getPlayer().getUniqueID();
					
					mined.put(uuid, mined.getOrDefault(uuid, 0) + 1);
					
					for(int xi = -1; xi < 2; xi++) {
						
						for(int yi = -1; yi < 2; yi++) {
							
							for(int zi = -1; zi < 2; zi++) {
								
								if(xi != 0 || yi != 0 || zi != 0) {
								
									BlockPos newPos = new BlockPos(e.getPos().getX() + xi, e.getPos().getY() + yi, e.getPos().getZ() + zi);
									BlockState state = e.getWorld().getBlockState(newPos);
									
									if(state.getBlock().getRegistryName().getPath().contains("log") || state.getBlock().getRegistryName().getPath().contains("wood") || state.getMaterial() == Material.LEAVES) {
										
										nextMap.put(uuid, newPos);
										
									}
								
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
		@SubscribeEvent
		public void blockBreakSmelt(BlockEvent.BreakEvent e) {
			
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
