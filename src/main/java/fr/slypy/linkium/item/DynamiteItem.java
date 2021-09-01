
package fr.slypy.linkium.item;

import java.util.Random;

import javax.annotation.Nullable;

import fr.slypy.linkium.LinkiumModElements;
import fr.slypy.linkium.entity.LinkiumTntEntityEntity.CustomEntity;
import fr.slypy.linkium.entity.renderer.DynamiteRenderer;
import fr.slypy.linkium.itemgroup.LinkiumModItemGroup;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@LinkiumModElements.ModElement.Tag
public class DynamiteItem extends LinkiumModElements.ModElement {
	@ObjectHolder("linkium:dynamite")
	public static final Item block = null;
	public static final EntityType arrow = (EntityType.Builder.<ArrowCustomEntity>create(ArrowCustomEntity::new, EntityClassification.MISC)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).setCustomClientFactory(ArrowCustomEntity::new)
			.size(0.75f, 0.75f)).build("dynamite").setRegistryName("dynamite");
	public DynamiteItem(LinkiumModElements instance) {
		super(instance, 65);
		FMLJavaModLoadingContext.get().getModEventBus().register(new DynamiteRenderer.ModelRegisterHandler());
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemRanged());
		elements.entities.add(() -> arrow);
	}

	public static class ItemRanged extends Item {
		public ItemRanged() {
			super(new Item.Properties().group(LinkiumModItemGroup.tab).maxStackSize(64));
			setRegistryName("dynamite");
		}

		@Override
		public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity entity, Hand hand) {

			ArrowCustomEntity entityarrow = shoot(world, entity, random, 0.25f, 0, 0);
			entity.getHeldItem(hand).setCount(entity.getHeldItem(hand).getCount() - 1);
			
			return new ActionResult(ActionResultType.SUCCESS, entity.getHeldItem(hand));
			
		}

		@Override
		public UseAction getUseAction(ItemStack itemstack) {
			return UseAction.NONE;
		}

		@Override
		public int getUseDuration(ItemStack itemstack) {
			return 72000;
		}
		
	}

	@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
	public static class ArrowCustomEntity extends ThrowableEntity implements IRendersAsItem {
		
		int fuse;
		private static final DataParameter<Integer> FUSE = EntityDataManager.createKey(CustomEntity.class, DataSerializers.VARINT);
		LivingEntity thrownBy;
		
		public ArrowCustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			super(arrow, world);
		}

		public ArrowCustomEntity(EntityType<? extends ArrowCustomEntity> type, World world) {
			super(type, world);
		}

		public ArrowCustomEntity(EntityType<? extends ArrowCustomEntity> type, double x, double y, double z, World world) {
			super(type, x, y, z, world);
		}

		public ArrowCustomEntity(EntityType<? extends ArrowCustomEntity> type, LivingEntity entity, World world) {
			super(type, entity, world);
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		public void tick() {
			
		      if (!this.hasNoGravity()) {
			         this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
			      }

			      this.move(MoverType.SELF, this.getMotion());
			      this.setMotion(this.getMotion().scale(0.98D));
			      if (this.onGround) {
			         this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
			      }
			      
			      fuse--;
			      
			      if(fuse <= 0) {
			    	  
			    	  remove();
			    	  
			    	  if(!world.isRemote()) {
			    		  
			    		  explode();
			    		  
			    	  }
			    	  
			      } else {
			      
			         this.func_233566_aG_();
			         if (this.world.isRemote) {
			            this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), 0.0D, 0.0D, 0.0D);
			         }
			         
			      }

		}
		
		protected void explode() {
			
			this.world.createExplosion(this, this.getPosX(), this.getPosYHeight(0.0625D), this.getPosZ(), 2.0F, Explosion.Mode.BREAK);

		}

		@Override
		protected void registerData() {
			
			this.dataManager.register(FUSE, 80);
			
		}

		@Override
		public void readAdditional(CompoundNBT compound) {
			
			 this.setFuse(compound.getShort("Fuse"));
			
		}

		@Override
		public void writeAdditional(CompoundNBT compound) {
			
			compound.putShort("Fuse", (short)this.getFuse());
			
		}
		
		@Override
		protected boolean canTriggerWalking() {
			return false;
		}
			   /**
			    * Returns true if other Entities should be prevented from moving through this Entity.
			    */
		@Override
		public boolean canBeCollidedWith() {
			return !this.removed;
		}

			public int getFuse() {
				return fuse;
			}
			
			   public void notifyDataManagerChange(DataParameter<?> key) {
				      if (FUSE.equals(key)) {
				         this.fuse = this.getFuseDataManager();
				      }

				   }

				   /**
				    * Gets the fuse from the data manager
				    */
				   public int getFuseDataManager() {
				      return this.dataManager.get(FUSE);
				   }

			public void setFuse(int fuse) {
				this.fuse = fuse;
				this.dataManager.set(FUSE, fuse);
			}
			
			   public LivingEntity getThrownBy() {
				      return this.thrownBy;
				   }

		@Override
		public ItemStack getItem() {

			return new ItemStack(DynamiteItem.block);
			
		}
	}
	
	public static ArrowCustomEntity shoot(World world, LivingEntity entity, Random random, float power, double damage, int knockback) {
		ArrowCustomEntity entityarrow = new ArrowCustomEntity(arrow, entity, world);
		entityarrow.shoot(entity.getLookVec().x, entity.getLookVec().y, entity.getLookVec().z, 0.35f * 2, 0);
		entityarrow.setFuse(80);
		entityarrow.setSilent(true);
		world.addEntity(entityarrow);
		double x = entity.getPosX();
		double y = entity.getPosY();
		double z = entity.getPosZ();
		world.playSound((PlayerEntity) null, (double) x, (double) y, (double) z, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
		return entityarrow;
	}

	public static ArrowCustomEntity shoot(LivingEntity entity, LivingEntity target) {
		ArrowCustomEntity entityarrow = new ArrowCustomEntity(arrow, entity, entity.world);
		double d0 = target.getPosY() + (double) target.getEyeHeight() - 1.1;
		double d1 = target.getPosX() - entity.getPosX();
		double d3 = target.getPosZ() - entity.getPosZ();
		entityarrow.shoot(d1, d0 - entityarrow.getPosY() + (double) MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F, d3, 0.35f * 2, 12.0F);
		entityarrow.setFuse(80);
		entityarrow.setSilent(true);
		entity.world.addEntity(entityarrow);
		double x = entity.getPosX();
		double y = entity.getPosY();
		double z = entity.getPosZ();
		entity.world.playSound((PlayerEntity) null, (double) x, (double) y, (double) z,
				(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.egg.throw")),
				SoundCategory.PLAYERS, 1, 1f / (new Random().nextFloat() * 0.5f + 1));
		return entityarrow;
	}
	
	public static ArrowCustomEntity shoot(World world, double x, double y, double z, double vecX, double vecY, double vecZ, Random random, @Nullable LivingEntity thrower) {
		ArrowCustomEntity entityarrow = new ArrowCustomEntity(arrow, thrower, world);
		entityarrow.shoot(vecX, vecY, vecZ, 0.35f * 2, 0);
		entityarrow.setFuse(80);
		entityarrow.setSilent(true);
		world.addEntity(entityarrow);
		world.playSound((PlayerEntity) null, (double) x, (double) y, (double) z, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (0.35f / 2));
		return entityarrow;
	}
	
}
