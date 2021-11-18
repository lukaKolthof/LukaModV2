
package net.mcreator.lukamodv.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.item.AxeItem;
import net.minecraft.client.util.ITooltipFlag;

import net.mcreator.lukamodv.itemgroup.LukaModv2ItemGroup;
import net.mcreator.lukamodv.LukamodV2ModElements;

import java.util.List;

@LukamodV2ModElements.ModElement.Tag
public class RubyAxeItem extends LukamodV2ModElements.ModElement {
	@ObjectHolder("lukamod_v2:ruby_axe")
	public static final Item block = null;
	public RubyAxeItem(LukamodV2ModElements instance) {
		super(instance, 5);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new AxeItem(new IItemTier() {
			public int getMaxUses() {
				return 4250;
			}

			public float getEfficiency() {
				return 140f;
			}

			public float getAttackDamage() {
				return 38f;
			}

			public int getHarvestLevel() {
				return 1;
			}

			public int getEnchantability() {
				return 5;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.EMPTY;
			}
		}, 1, 6f, new Item.Properties().group(LukaModv2ItemGroup.tab).isImmuneToFire()) {
			@Override
			public void addInformation(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
				super.addInformation(itemstack, world, list, flag);
				list.add(new StringTextComponent("A godly like axe"));
			}
		}.setRegistryName("ruby_axe"));
	}
}
