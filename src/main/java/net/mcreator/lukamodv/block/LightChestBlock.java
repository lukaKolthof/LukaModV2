
package net.mcreator.lukamodv.block;

import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.World;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Hand;
import net.minecraft.util.Direction;
import net.minecraft.util.ActionResultType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.NetworkManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.loot.LootContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;

import net.mcreator.lukamodv.itemgroup.LukaModv2ItemGroup;
import net.mcreator.lukamodv.gui.LightChestGuiGui;
import net.mcreator.lukamodv.LukamodV2ModElements;

import javax.annotation.Nullable;

import java.util.stream.IntStream;
import java.util.List;
import java.util.Collections;

import io.netty.buffer.Unpooled;

@LukamodV2ModElements.ModElement.Tag
public class LightChestBlock extends LukamodV2ModElements.ModElement {
	@ObjectHolder("lukamod_v2:light_chest")
	public static final Block block = null;
	@ObjectHolder("lukamod_v2:light_chest")
	public static final TileEntityType<CustomTileEntity> tileEntityType = null;
	public LightChestBlock(LukamodV2ModElements instance) {
		super(instance, 342);
		FMLJavaModLoadingContext.get().getModEventBus().register(new TileEntityRegisterHandler());
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items.add(() -> new BlockItem(block, new Item.Properties().group(LukaModv2ItemGroup.tab)).setRegistryName(block.getRegistryName()));
	}
	private static class TileEntityRegisterHandler {
		@SubscribeEvent
		public void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event) {
			event.getRegistry().register(TileEntityType.Builder.create(CustomTileEntity::new, block).build(null).setRegistryName("light_chest"));
		}
	}

	public static class CustomBlock extends Block {
		public CustomBlock() {
			super(Block.Properties.create(Material.ROCK).sound(SoundType.GROUND).hardnessAndResistance(1f, 10f).setLightLevel(s -> 0));
			setRegistryName("light_chest");
		}

		@Override
		public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
			return 15;
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty())
				return dropsOriginal;
			return Collections.singletonList(new ItemStack(this, 1));
		}

		@Override
		public ActionResultType onBlockActivated(BlockState blockstate, World world, BlockPos pos, PlayerEntity entity, Hand hand,
				BlockRayTraceResult hit) {
			super.onBlockActivated(blockstate, world, pos, entity, hand, hit);
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			if (entity instanceof ServerPlayerEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) entity, new INamedContainerProvider() {
					@Override
					public ITextComponent getDisplayName() {
						return new StringTextComponent("Light Chest");
					}

					@Override
					public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
						return new LightChestGuiGui.GuiContainerMod(id, inventory,
								new PacketBuffer(Unpooled.buffer()).writeBlockPos(new BlockPos(x, y, z)));
					}
				}, new BlockPos(x, y, z));
			}
			return ActionResultType.SUCCESS;
		}

		@Override
		public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			return tileEntity instanceof INamedContainerProvider ? (INamedContainerProvider) tileEntity : null;
		}

		@Override
		public boolean hasTileEntity(BlockState state) {
			return true;
		}

		@Override
		public TileEntity createTileEntity(BlockState state, IBlockReader world) {
			return new CustomTileEntity();
		}

		@Override
		public boolean eventReceived(BlockState state, World world, BlockPos pos, int eventID, int eventParam) {
			super.eventReceived(state, world, pos, eventID, eventParam);
			TileEntity tileentity = world.getTileEntity(pos);
			return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
		}

		@Override
		public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
			if (state.getBlock() != newState.getBlock()) {
				TileEntity tileentity = world.getTileEntity(pos);
				if (tileentity instanceof CustomTileEntity) {
					InventoryHelper.dropInventoryItems(world, pos, (CustomTileEntity) tileentity);
					world.updateComparatorOutputLevel(pos, this);
				}
				super.onReplaced(state, world, pos, newState, isMoving);
			}
		}

		@Override
		public boolean hasComparatorInputOverride(BlockState state) {
			return true;
		}

		@Override
		public int getComparatorInputOverride(BlockState blockState, World world, BlockPos pos) {
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity instanceof CustomTileEntity)
				return Container.calcRedstoneFromInventory((CustomTileEntity) tileentity);
			else
				return 0;
		}
	}

	public static class CustomTileEntity extends LockableLootTileEntity implements ISidedInventory {
		private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(152, ItemStack.EMPTY);
		protected CustomTileEntity() {
			super(tileEntityType);
		}

		@Override
		public void read(BlockState blockState, CompoundNBT compound) {
			super.read(blockState, compound);
			if (!this.checkLootAndRead(compound)) {
				this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
			}
			ItemStackHelper.loadAllItems(compound, this.stacks);
		}

		@Override
		public CompoundNBT write(CompoundNBT compound) {
			super.write(compound);
			if (!this.checkLootAndWrite(compound)) {
				ItemStackHelper.saveAllItems(compound, this.stacks);
			}
			return compound;
		}

		@Override
		public SUpdateTileEntityPacket getUpdatePacket() {
			return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
		}

		@Override
		public CompoundNBT getUpdateTag() {
			return this.write(new CompoundNBT());
		}

		@Override
		public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
			this.read(this.getBlockState(), pkt.getNbtCompound());
		}

		@Override
		public int getSizeInventory() {
			return stacks.size();
		}

		@Override
		public boolean isEmpty() {
			for (ItemStack itemstack : this.stacks)
				if (!itemstack.isEmpty())
					return false;
			return true;
		}

		@Override
		public ITextComponent getDefaultName() {
			return new StringTextComponent("light_chest");
		}

		@Override
		public int getInventoryStackLimit() {
			return 64;
		}

		@Override
		public Container createMenu(int id, PlayerInventory player) {
			return new LightChestGuiGui.GuiContainerMod(id, player, new PacketBuffer(Unpooled.buffer()).writeBlockPos(this.getPos()));
		}

		@Override
		public ITextComponent getDisplayName() {
			return new StringTextComponent("Light Chest");
		}

		@Override
		protected NonNullList<ItemStack> getItems() {
			return this.stacks;
		}

		@Override
		protected void setItems(NonNullList<ItemStack> stacks) {
			this.stacks = stacks;
		}

		@Override
		public boolean isItemValidForSlot(int index, ItemStack stack) {
			return true;
		}

		@Override
		public int[] getSlotsForFace(Direction side) {
			return IntStream.range(0, this.getSizeInventory()).toArray();
		}

		@Override
		public boolean canInsertItem(int index, ItemStack stack, @Nullable Direction direction) {
			return this.isItemValidForSlot(index, stack);
		}

		@Override
		public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
			if (index == 0)
				return false;
			if (index == 1)
				return false;
			if (index == 2)
				return false;
			if (index == 3)
				return false;
			if (index == 4)
				return false;
			if (index == 5)
				return false;
			if (index == 6)
				return false;
			if (index == 7)
				return false;
			if (index == 8)
				return false;
			if (index == 9)
				return false;
			if (index == 10)
				return false;
			if (index == 11)
				return false;
			if (index == 12)
				return false;
			if (index == 13)
				return false;
			if (index == 14)
				return false;
			if (index == 15)
				return false;
			if (index == 16)
				return false;
			if (index == 17)
				return false;
			if (index == 18)
				return false;
			if (index == 19)
				return false;
			if (index == 20)
				return false;
			if (index == 21)
				return false;
			if (index == 22)
				return false;
			if (index == 23)
				return false;
			if (index == 24)
				return false;
			if (index == 25)
				return false;
			if (index == 26)
				return false;
			if (index == 27)
				return false;
			if (index == 28)
				return false;
			if (index == 29)
				return false;
			if (index == 30)
				return false;
			if (index == 31)
				return false;
			if (index == 32)
				return false;
			if (index == 33)
				return false;
			if (index == 34)
				return false;
			if (index == 35)
				return false;
			if (index == 36)
				return false;
			if (index == 37)
				return false;
			if (index == 38)
				return false;
			if (index == 39)
				return false;
			if (index == 40)
				return false;
			if (index == 41)
				return false;
			if (index == 42)
				return false;
			if (index == 43)
				return false;
			if (index == 44)
				return false;
			if (index == 45)
				return false;
			if (index == 46)
				return false;
			if (index == 47)
				return false;
			if (index == 48)
				return false;
			if (index == 49)
				return false;
			if (index == 50)
				return false;
			if (index == 51)
				return false;
			if (index == 52)
				return false;
			if (index == 53)
				return false;
			if (index == 54)
				return false;
			if (index == 55)
				return false;
			if (index == 56)
				return false;
			if (index == 57)
				return false;
			if (index == 58)
				return false;
			if (index == 59)
				return false;
			if (index == 60)
				return false;
			if (index == 61)
				return false;
			if (index == 62)
				return false;
			if (index == 63)
				return false;
			if (index == 64)
				return false;
			if (index == 65)
				return false;
			if (index == 66)
				return false;
			if (index == 67)
				return false;
			if (index == 68)
				return false;
			if (index == 69)
				return false;
			if (index == 70)
				return false;
			if (index == 71)
				return false;
			if (index == 72)
				return false;
			if (index == 73)
				return false;
			if (index == 74)
				return false;
			if (index == 75)
				return false;
			if (index == 76)
				return false;
			if (index == 77)
				return false;
			if (index == 78)
				return false;
			if (index == 79)
				return false;
			if (index == 80)
				return false;
			if (index == 81)
				return false;
			if (index == 82)
				return false;
			if (index == 83)
				return false;
			if (index == 84)
				return false;
			if (index == 85)
				return false;
			if (index == 86)
				return false;
			if (index == 87)
				return false;
			if (index == 88)
				return false;
			if (index == 89)
				return false;
			if (index == 90)
				return false;
			if (index == 91)
				return false;
			if (index == 92)
				return false;
			if (index == 93)
				return false;
			if (index == 94)
				return false;
			if (index == 95)
				return false;
			if (index == 96)
				return false;
			if (index == 97)
				return false;
			if (index == 98)
				return false;
			if (index == 99)
				return false;
			if (index == 100)
				return false;
			if (index == 101)
				return false;
			if (index == 102)
				return false;
			if (index == 103)
				return false;
			if (index == 104)
				return false;
			if (index == 105)
				return false;
			if (index == 106)
				return false;
			if (index == 107)
				return false;
			if (index == 108)
				return false;
			if (index == 109)
				return false;
			if (index == 110)
				return false;
			if (index == 111)
				return false;
			if (index == 112)
				return false;
			if (index == 113)
				return false;
			if (index == 114)
				return false;
			if (index == 115)
				return false;
			if (index == 116)
				return false;
			if (index == 117)
				return false;
			if (index == 118)
				return false;
			if (index == 119)
				return false;
			if (index == 120)
				return false;
			if (index == 121)
				return false;
			if (index == 122)
				return false;
			if (index == 123)
				return false;
			if (index == 124)
				return false;
			if (index == 125)
				return false;
			if (index == 126)
				return false;
			if (index == 127)
				return false;
			if (index == 128)
				return false;
			if (index == 129)
				return false;
			if (index == 130)
				return false;
			if (index == 131)
				return false;
			if (index == 132)
				return false;
			if (index == 133)
				return false;
			if (index == 134)
				return false;
			if (index == 135)
				return false;
			if (index == 136)
				return false;
			if (index == 137)
				return false;
			if (index == 138)
				return false;
			if (index == 139)
				return false;
			if (index == 140)
				return false;
			if (index == 141)
				return false;
			if (index == 142)
				return false;
			if (index == 143)
				return false;
			if (index == 144)
				return false;
			if (index == 145)
				return false;
			if (index == 146)
				return false;
			if (index == 147)
				return false;
			if (index == 148)
				return false;
			if (index == 149)
				return false;
			if (index == 150)
				return false;
			if (index == 151)
				return false;
			return true;
		}
		private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());
		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
			if (!this.removed && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
				return handlers[facing.ordinal()].cast();
			return super.getCapability(capability, facing);
		}

		@Override
		public void remove() {
			super.remove();
			for (LazyOptional<? extends IItemHandler> handler : handlers)
				handler.invalidate();
		}
	}
}
