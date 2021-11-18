
package net.mcreator.lukamodv.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.item.AxeItem;

import net.mcreator.lukamodv.itemgroup.LukaModv2ItemGroup;
import net.mcreator.lukamodv.LukamodV2ModElements;

@LukamodV2ModElements.ModElement.Tag
public class EmeraldToolsAxeItem extends LukamodV2ModElements.ModElement {
	@ObjectHolder("lukamod_v2:emerald_tools_axe")
	public static final Item block = null;
	public EmeraldToolsAxeItem(LukamodV2ModElements instance) {
		super(instance, 33);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new AxeItem(new IItemTier() {
			public int getMaxUses() {
				return 2520;
			}

			public float getEfficiency() {
				return 7f;
			}

			public float getAttackDamage() {
				return 7.2f;
			}

			public int getHarvestLevel() {
				return 4;
			}

			public int getEnchantability() {
				return 16;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(Items.EMERALD));
			}
		}, 1, -2.7999999999999998f, new Item.Properties().group(LukaModv2ItemGroup.tab)) {
		}.setRegistryName("emerald_tools_axe"));
	}
}
