
package net.mcreator.lukamodv.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.client.util.ITooltipFlag;

import net.mcreator.lukamodv.itemgroup.LukaModv2ItemGroup;
import net.mcreator.lukamodv.LukamodV2ModElements;

import java.util.List;

@LukamodV2ModElements.ModElement.Tag
public class RubyPickaxeItem extends LukamodV2ModElements.ModElement {
	@ObjectHolder("lukamod_v2:ruby_pickaxe")
	public static final Item block = null;
	public RubyPickaxeItem(LukamodV2ModElements instance) {
		super(instance, 3);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new PickaxeItem(new IItemTier() {
			public int getMaxUses() {
				return 4350;
			}

			public float getEfficiency() {
				return 150f;
			}

			public float getAttackDamage() {
				return -1f;
			}

			public int getHarvestLevel() {
				return 4;
			}

			public int getEnchantability() {
				return 6;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(RubyItem.block));
			}
		}, 1, -3f, new Item.Properties().group(LukaModv2ItemGroup.tab).isImmuneToFire()) {
			@Override
			public boolean hasContainerItem() {
				return true;
			}

			@Override
			public ItemStack getContainerItem(ItemStack itemstack) {
				return new ItemStack(this);
			}

			@Override
			public boolean isRepairable(ItemStack itemstack) {
				return false;
			}

			@Override
			public void addInformation(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
				super.addInformation(itemstack, world, list, flag);
				list.add(new StringTextComponent("A godly like pickaxe"));
			}
		}.setRegistryName("ruby_pickaxe"));
	}
}
