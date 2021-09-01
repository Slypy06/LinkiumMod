
package fr.slypy.linkium.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.extensions.IForgeStructure;
import net.minecraftforge.common.world.ForgeWorldType.IChunkGeneratorFactory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.BossInfo;
import net.minecraft.world.Explosion;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.item.Item;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.command.arguments.ParticleArgument;
import fr.slypy.linkium.itemgroup.LinkiumModItemGroup;
import fr.slypy.linkium.entity.renderer.OverpoweredLinkiumIncarnationRenderer;
import fr.slypy.linkium.item.LinkiumDynamiteItem;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.slypy.linkium.LinkiumModElements;

@LinkiumModElements.ModElement.Tag
public class OverpoweredLinkiumIncarnationEntity extends LinkiumModElements.ModElement {
	public static EntityType entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).immuneToFire()
			.size(2.5f, 3.7f)).build("overpowered_linkium_incarnation").setRegistryName("overpowered_linkium_incarnation");
	public OverpoweredLinkiumIncarnationEntity(LinkiumModElements instance) {
		super(instance, 70);
		FMLJavaModLoadingContext.get().getModEventBus().register(new OverpoweredLinkiumIncarnationRenderer.ModelRegisterHandler());
		FMLJavaModLoadingContext.get().getModEventBus().register(new EntityAttributesRegisterHandler());
	}

	@Override
	public void initElements() {
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -16764160, -16724941, new Item.Properties().group(LinkiumModItemGroup.tab))
				.setRegistryName("overpowered_linkium_incarnation_spawn_egg"));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
	}
	private static class EntityAttributesRegisterHandler {
		@SubscribeEvent
		public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
			AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
			ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.4);
			ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 1024);
			ammma = ammma.createMutableAttribute(Attributes.ARMOR, 20);
			ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 30);
			ammma = ammma.createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.4);
			ammma = ammma.createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 0.3);
			event.put(entity, ammma.create());
		}
	}

	public static class CustomEntity extends MonsterEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 1000;
			setNoAI(false);
			enablePersistence();
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
			this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, PlayerEntity.class, false, true));
			this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.5, true));
			this.goalSelector.addGoal(3, new FollowMobGoal(this, (float) 0.5, 10, 5));
			this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 0.4));
			this.targetSelector.addGoal(5, new HurtByTargetGoal(this).setCallsForHelp(this.getClass()));
			this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
			this.goalSelector.addGoal(7, new SwimGoal(this));
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
		}

		@Override
		public boolean canDespawn(double distanceToClosestPlayer) {
			return false;
		}

		@Override
		public net.minecraft.util.SoundEvent getAmbientSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("ambient.basalt_deltas.additions"));
		}

		@Override
		public void playStepSound(BlockPos pos, BlockState blockIn) {
			this.playSound((net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.shroomlight.place")),
					0.15f, 1);
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.netherite_block.break"));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("ambient.basalt_deltas.mood"));
		}

		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			if (source == DamageSource.FALL)
				return false;
			if (source == DamageSource.DROWN)
				return false;
			if (source.isExplosion())
				return false;
			if (source == DamageSource.WITHER)
				return false;
			if (source.getDamageType().equals("witherSkull"))
				return false;
			if(source.getDamageType().equals("arrow") && getHealth() < getMaxHealth() / 2.0f)
				return false;
			return super.attackEntityFrom(source, amount / 2);
		}

		@Override
		public boolean isNonBoss() {
			return false;
		}
		private final ServerBossInfo bossInfo = new ServerBossInfo(this.getDisplayName(), BossInfo.Color.GREEN, BossInfo.Overlay.PROGRESS);
		@Override
		public void addTrackingPlayer(ServerPlayerEntity player) {
			super.addTrackingPlayer(player);
			this.bossInfo.addPlayer(player);
		}

		@Override
		public void removeTrackingPlayer(ServerPlayerEntity player) {
			super.removeTrackingPlayer(player);
			this.bossInfo.removePlayer(player);
		}

		@Override
		public void updateAITasks() {
			super.updateAITasks();
		}
		
		@Override
		public void tick() {
			
			super.tick();
			
			double x = getPosX();
			double y = getPosY();
			double z = getPosZ();
			
			this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
		
			Stream<PlayerEntity> str = world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(x - (15 / 2d), y - (15 / 2d), z - (15 / 2d), x + (15 / 2d), y + (15 / 2d), z + (15 / 2d)), null).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
				}
			}.compareDistOf(x, y, z));
			
			boolean player = false;

			List<PlayerEntity> list = str.collect(Collectors.toList());
			
			for(PlayerEntity e : list) {
				
				if(!e.isCreative() && !e.isSpectator()) {
					
					player = true;
					
				}
				
			}
			
			if (player && (Math.random() <= 0.002)) {
				if (world instanceof World && !((World) world).isRemote) {
					
					((World) world).createExplosion(null, (int) x, (int) y + 3, (int) z, (float)3, Explosion.Mode.NONE);
					
				}
			} else if (player && (Math.random() <= ((getHealth() >= getMaxHealth() / 2.0f) ? 0.003 : 0.004))) {
				if (world instanceof World && !((World) world).isRemote) {
					
					for(int i = 0; i < (new Random()).nextInt(((getHealth() >= getMaxHealth() / 2.0f) ? 25 : 50)); i++) {
						
						LinkiumDynamiteItem.shoot(world, x, y, z, new Random().nextInt(360) - 180, new Random().nextInt(360) - 180, new Random().nextInt(360) - 180, new Random(), this);
						
					}
					
				}
				
			} else if(player && (getHealth() < getMaxHealth() / 2.0f) && (Math.random() <= 0.005)) {
				
				if (world instanceof ServerWorld && !((World) world).isRemote) {
					
					((ServerWorld) world).spawnParticle(ParticleTypes.WARPED_SPORE, x, (y + 2), z, (int) 50000, 1, 2, 1, 1);
					
					list.forEach(p -> {
						
						if(!p.isCreative() && !p.isSpectator()) {
							
							p.addPotionEffect(new EffectInstance(Effects.POISON, 200, 1, false, false));
							
						}
						
					});
					
				}
				
			}
			
		}
		
	}
}
