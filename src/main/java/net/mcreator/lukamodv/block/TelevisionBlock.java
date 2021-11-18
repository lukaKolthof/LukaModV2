
package net.mcreator.lukamodv.block;

import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Rotation;
import net.minecraft.util.Mirror;
import net.minecraft.util.Direction;
import net.minecraft.state.StateContainer;
import net.minecraft.state.DirectionProperty;
import net.minecraft.loot.LootContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.BlockItem;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;

import net.mcreator.lukamodv.itemgroup.LukaModv2ItemGroup;
import net.mcreator.lukamodv.LukamodV2ModElements;

import java.util.List;
import java.util.Collections;

@LukamodV2ModElements.ModElement.Tag
public class TelevisionBlock extends LukamodV2ModElements.ModElement {
	@ObjectHolder("lukamod_v2:television")
	public static final Block block = null;
	public TelevisionBlock(LukamodV2ModElements instance) {
		super(instance, 130);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items.add(() -> new BlockItem(block, new Item.Properties().group(LukaModv2ItemGroup.tab)).setRegistryName(block.getRegistryName()));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void clientLoad(FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
	}
	public static class CustomBlock extends Block {
		public static final DirectionProperty FACING = DirectionalBlock.FACING;
		public CustomBlock() {
			super(Block.Properties.create(Material.ROCK).sound(SoundType.METAL).hardnessAndResistance(1f, 10f).setLightLevel(s -> 0).notSolid()
					.setOpaque((bs, br, bp) -> false));
			this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
			setRegistryName("television");
		}

		@Override
		public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
			return true;
		}

		@Override
		public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
			Vector3d offset = state.getOffset(world, pos);
			switch ((Direction) state.get(FACING)) {
				case SOUTH :
				default :
					return VoxelShapes.or(makeCuboidShape(3.25, 10, 8.5, 12, 10.75, 9.5), makeCuboidShape(3.25, 6, 8.5, 4, 10, 9.5),
							makeCuboidShape(12, 6, 9, 12.75, 10.75, 9.5), makeCuboidShape(5, 0, 8, 11, 1, 11), makeCuboidShape(3.25, 5.25, 8.5, 12.75, 6, 9.5),
							makeCuboidShape(3, 5, 8, 13, 11, 9), makeCuboidShape(7, 0, 7, 9, 7, 8)).withOffset(offset.x, offset.y, offset.z);
				case NORTH :
					return VoxelShapes.or(makeCuboidShape(4, 10, 6.5, 12.75, 10.75, 7.5), makeCuboidShape(12, 6, 6.5, 12.75, 10, 7.5),
							makeCuboidShape(3.25, 6, 6.5, 4, 10.75, 7), makeCuboidShape(5, 0, 5, 11, 1, 8), makeCuboidShape(3.25, 5.25, 6.5, 12.75, 6, 7.5),
							makeCuboidShape(3, 5, 7, 13, 11, 8), makeCuboidShape(7, 0, 8, 9, 7, 9)).withOffset(offset.x, offset.y, offset.z);
				case WEST :
					return VoxelShapes.or(makeCuboidShape(6.5, 10, 3.25, 7.5, 10.75, 12), makeCuboidShape(6.5, 6, 3.25, 7.5, 10, 4),
							makeCuboidShape(6.5, 6, 12, 7, 10.75, 12.75), makeCuboidShape(5, 0, 5, 8, 1, 11), makeCuboidShape(6.5, 5.25, 3.25, 7.5, 6, 12.75),
							makeCuboidShape(7, 5, 3, 8, 11, 13), makeCuboidShape(8, 0, 7, 9, 7, 9)).withOffset(offset.x, offset.y, offset.z);
				case EAST :
					return VoxelShapes.or(makeCuboidShape(8.5, 10, 4, 9.5, 10.75, 12.75), makeCuboidShape(8.5, 6, 12, 9.5, 10, 12.75),
							makeCuboidShape(9, 6, 3.25, 9.5, 10.75, 4), makeCuboidShape(8, 0, 5, 11, 1, 11), makeCuboidShape(8.5, 5.25, 3.25, 9.5, 6, 12.75),
							makeCuboidShape(8, 5, 3, 9, 11, 13), makeCuboidShape(7, 0, 7, 8, 7, 9)).withOffset(offset.x, offset.y, offset.z);
				case UP :
					return VoxelShapes.or(makeCuboidShape(0, 16, 0, 16, 0, 15), makeCuboidShape(0, 16, 0, 16, 0, 16),
							makeCuboidShape(0, 16, 0, 16, 0, 16), makeCuboidShape(0, 16, 0, 16, 0, 16), makeCuboidShape(0, 16, 0, 16, 0, 16),
							makeCuboidShape(0, 16, 0, 16, 0, 16), makeCuboidShape(0, 16, 0, 16, 0, 16)).withOffset(offset.x, offset.y, offset.z);
				case DOWN :
					return VoxelShapes.or(makeCuboidShape(0, 0, 16, 16, 16, 1), makeCuboidShape(0, 0, 16, 16, 16, 0),
							makeCuboidShape(0, 0, 16, 16, 16, 0), makeCuboidShape(0, 0, 16, 16, 16, 0), makeCuboidShape(0, 0, 16, 16, 16, 0),
							makeCuboidShape(0, 0, 16, 16, 16, 0), makeCuboidShape(0, 0, 16, 16, 16, 0)).withOffset(offset.x, offset.y, offset.z);
			}
		}

		@Override
		protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
			builder.add(FACING);
		}

		public BlockState rotate(BlockState state, Rotation rot) {
			return state.with(FACING, rot.rotate(state.get(FACING)));
		}

		public BlockState mirror(BlockState state, Mirror mirrorIn) {
			return state.rotate(mirrorIn.toRotation(state.get(FACING)));
		}

		@Override
		public BlockState getStateForPlacement(BlockItemUseContext context) {
			;
			return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite());
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty())
				return dropsOriginal;
			return Collections.singletonList(new ItemStack(this, 1));
		}
	}
}
