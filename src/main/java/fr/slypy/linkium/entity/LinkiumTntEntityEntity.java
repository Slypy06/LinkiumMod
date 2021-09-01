
package fr.slypy.linkium.entity;

import javax.annotation.Nullable;

import fr.slypy.linkium.LinkiumModElements;
import fr.slypy.linkium.entity.renderer.LinkiumTntEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EntityExplosionContext;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.World;
import net.minecraft.world.Explosion.Mode;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

@LinkiumModElements.ModElement.Tag
public class LinkiumTntEntityEntity extends LinkiumModElements.ModElement {
	public static EntityType entity = EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MISC).immuneToFire().size(0.98F, 0.98F).trackingRange(10).func_233608_b_(10).build("linkium_tnt_entity").setRegistryName("linkium_tnt_entity");
	public LinkiumTntEntityEntity(LinkiumModElements instance) {
		super(instance, 66);
		FMLJavaModLoadingContext.get().getModEventBus().register(new LinkiumTntEntityRenderer.ModelRegisterHandler());
	}

	@Override
	public void initElements() {
		elements.entities.add(() -> entity);
	}

	public static class CustomEntity extends TNTEntity {

		LivingEntity tntPlacedBy;
		
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
		}
		
		   public CustomEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
			      this(entity, worldIn);
			      this.setPosition(x, y, z);
			      double d0 = worldIn.rand.nextDouble() * (double)((float)Math.PI * 2F);
			      this.setMotion(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
			      this.setFuse(80);
			      this.prevPosX = x;
			      this.prevPosY = y;
			      this.prevPosZ = z;
			      this.tntPlacedBy = igniter;
			   }

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}
		
		@Override
		public void tick() {
			
			System.out.println(this.getTntPlacedBy());
			
		      if (!this.hasNoGravity()) {
			         this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
			      }

			      this.move(MoverType.SELF, this.getMotion());
			      this.setMotion(this.getMotion().scale(0.98D));
			      if (this.onGround) {
			         this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
			      }
			      
			      this.setFuse(this.getFuse() - 1);
			      
			      if(this.getFuse() <= 0) {
			    	  
			    	  remove();
			    	  
			    	  if(!world.isRemote()) {
			    		  
			    		  explode();
			    		  
			    	  } else {
			    		  
			    		  
			    		  
			    	  }
			    	  
			      } else {
			      
			         this.func_233566_aG_();
			         if (this.world.isRemote) {
			            this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), 0.0D, 0.0D, 0.0D);
			         }
			         
			      }
			
		}
		
		@Override
		protected void explode() {
			
			this.world.createExplosion(this, this.getPosX(), this.getPosYHeight(0.0625D), this.getPosZ(), 10.0F, Explosion.Mode.BREAK);
			
		}

		/*@Override
		protected void registerData() {
			
			this.dataManager.register(FUSE, 80);
			
		}

		@Override
		protected void readAdditional(CompoundNBT compound) {
			 this.setFuse(compound.getShort("Fuse"));
			
		}

		@Override
		protected void writeAdditional(CompoundNBT compound) {
			
			compound.putShort("Fuse", (short)this.getFuse());
			
		}
		
		@Override
		protected boolean canTriggerWalking() {
			return false;
		}
		
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

				   public int getFuseDataManager() {
				      return this.dataManager.get(FUSE);
				   }

			public void setFuse(int fuse) {
				this.fuse = fuse;
				this.dataManager.set(FUSE, fuse);
			}*/
			
			@Override
			   public LivingEntity getTntPlacedBy() {
				      return this.tntPlacedBy;
				   }
				   
				   
		
	}
}
