package fr.slypy.linkium.procedures;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.Entity;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;

import java.util.Map;

import fr.slypy.linkium.entity.OverpoweredLinkiumIncarnationEntity;
import fr.slypy.linkium.block.OverpoweredLinkiumRecptacleBlock;
import fr.slypy.linkium.block.LinkiumBlockBlock;
import fr.slypy.linkium.LinkiumModElements;
import fr.slypy.linkium.LinkiumMod;

@LinkiumModElements.ModElement.Tag
public class OverpoweredLinkiumRecptacleBlockIsPlacedByProcedure extends LinkiumModElements.ModElement {
	public OverpoweredLinkiumRecptacleBlockIsPlacedByProcedure(LinkiumModElements instance) {
		super(instance, 74);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				LinkiumMod.LOGGER.warn("Failed to load dependency x for procedure OverpoweredLinkiumRecptacleBlockIsPlacedBy!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				LinkiumMod.LOGGER.warn("Failed to load dependency y for procedure OverpoweredLinkiumRecptacleBlockIsPlacedBy!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				LinkiumMod.LOGGER.warn("Failed to load dependency z for procedure OverpoweredLinkiumRecptacleBlockIsPlacedBy!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				LinkiumMod.LOGGER.warn("Failed to load dependency world for procedure OverpoweredLinkiumRecptacleBlockIsPlacedBy!");
			return;
		}
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		if ((0 == ((new Object() {
			public Direction getDirection(BlockPos pos) {
				try {
					BlockState _bs = world.getBlockState(pos);
					DirectionProperty property = (DirectionProperty) _bs.getBlock().getStateContainer().getProperty("facing");
					if (property != null)
						return _bs.get(property);
					return Direction.getFacingFromAxisDirection(
							_bs.get((EnumProperty<Direction.Axis>) _bs.getBlock().getStateContainer().getProperty("axis")),
							Direction.AxisDirection.POSITIVE);
				} catch (Exception e) {
					return Direction.NORTH;
				}
			}
		}.getDirection(new BlockPos((int) x, (int) y, (int) z))).getXOffset()))) {
			if ((((LinkiumBlockBlock.block.getDefaultState().getBlock() == (world.getBlockState(new BlockPos((int) (x + 1), (int) y, (int) z)))
					.getBlock())
					&& (LinkiumBlockBlock.block.getDefaultState().getBlock() == (world.getBlockState(new BlockPos((int) (x - 1), (int) y, (int) z)))
							.getBlock()))
					&& (LinkiumBlockBlock.block.getDefaultState().getBlock() == (world.getBlockState(new BlockPos((int) x, (int) (y - 1), (int) z)))
							.getBlock()))) {
				world.playEvent(2001, new BlockPos((int) (x + 1), (int) y, (int) z), Block.getStateId(LinkiumBlockBlock.block.getDefaultState()));
				world.playEvent(2001, new BlockPos((int) (x - 1), (int) y, (int) z), Block.getStateId(LinkiumBlockBlock.block.getDefaultState()));
				world.playEvent(2001, new BlockPos((int) x, (int) (y - 1), (int) z), Block.getStateId(LinkiumBlockBlock.block.getDefaultState()));
				world.playEvent(2001, new BlockPos((int) x, (int) y, (int) z),
						Block.getStateId(OverpoweredLinkiumRecptacleBlock.block.getDefaultState()));
				world.setBlockState(new BlockPos((int) (x + 1), (int) y, (int) z), Blocks.AIR.getDefaultState(), 3);
				world.setBlockState(new BlockPos((int) (x - 1), (int) y, (int) z), Blocks.AIR.getDefaultState(), 3);
				world.setBlockState(new BlockPos((int) x, (int) (y - 1), (int) z), Blocks.AIR.getDefaultState(), 3);
				world.setBlockState(new BlockPos((int) x, (int) y, (int) z), Blocks.AIR.getDefaultState(), 3);
				if (world instanceof ServerWorld) {
					Entity entityToSpawn = new OverpoweredLinkiumIncarnationEntity.CustomEntity(OverpoweredLinkiumIncarnationEntity.entity,
							(World) world);
					entityToSpawn.setLocationAndAngles(x, y, z, world.getRandom().nextFloat() * 360F, 0);
					if (entityToSpawn instanceof MobEntity)
						((MobEntity) entityToSpawn).onInitialSpawn((ServerWorld) world, world.getDifficultyForLocation(entityToSpawn.getPosition()),
								SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
					world.addEntity(entityToSpawn);
				}
			}
		} else {
			if ((((LinkiumBlockBlock.block.getDefaultState().getBlock() == (world.getBlockState(new BlockPos((int) x, (int) y, (int) (z + 1))))
					.getBlock())
					&& (LinkiumBlockBlock.block.getDefaultState().getBlock() == (world.getBlockState(new BlockPos((int) x, (int) y, (int) (z - 1))))
							.getBlock()))
					&& (LinkiumBlockBlock.block.getDefaultState().getBlock() == (world.getBlockState(new BlockPos((int) x, (int) (y - 1), (int) z)))
							.getBlock()))) {
				world.playEvent(2001, new BlockPos((int) x, (int) y, (int) (z + 1)), Block.getStateId(LinkiumBlockBlock.block.getDefaultState()));
				world.playEvent(2001, new BlockPos((int) x, (int) y, (int) (z - 1)), Block.getStateId(LinkiumBlockBlock.block.getDefaultState()));
				world.playEvent(2001, new BlockPos((int) x, (int) (y - 1), (int) z), Block.getStateId(LinkiumBlockBlock.block.getDefaultState()));
				world.playEvent(2001, new BlockPos((int) x, (int) y, (int) z),
						Block.getStateId(OverpoweredLinkiumRecptacleBlock.block.getDefaultState()));
				world.setBlockState(new BlockPos((int) x, (int) y, (int) (z + 1)), Blocks.AIR.getDefaultState(), 3);
				world.setBlockState(new BlockPos((int) x, (int) y, (int) (z - 1)), Blocks.AIR.getDefaultState(), 3);
				world.setBlockState(new BlockPos((int) x, (int) (y - 1), (int) z), Blocks.AIR.getDefaultState(), 3);
				world.setBlockState(new BlockPos((int) x, (int) y, (int) z), Blocks.AIR.getDefaultState(), 3);
				if (world instanceof ServerWorld) {
					Entity entityToSpawn = new OverpoweredLinkiumIncarnationEntity.CustomEntity(OverpoweredLinkiumIncarnationEntity.entity,
							(World) world);
					entityToSpawn.setLocationAndAngles(x, y, z, world.getRandom().nextFloat() * 360F, 0);
					if (entityToSpawn instanceof MobEntity)
						((MobEntity) entityToSpawn).onInitialSpawn((ServerWorld) world, world.getDifficultyForLocation(entityToSpawn.getPosition()),
								SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
					world.addEntity(entityToSpawn);
				}
			}
		}
	}
}
