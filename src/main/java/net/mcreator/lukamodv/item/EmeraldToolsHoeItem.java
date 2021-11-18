
package net.mcreator.lukamodv.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.item.HoeItem;

import net.mcreator.lukamodv.itemgroup.LukaModv2ItemGroup;
import net.mcreator.lukamodv.LukamodV2ModElements;

@LukamodV2ModElements.ModElement.Tag
public class EmeraldToolsHoeItem extends LukamodV2ModElements.ModElement {
	@ObjectHolder("lukamod_v2:emerald_tools_hoe")
	public static final Item block = null;
	public EmeraldToolsHoeItem(LukamodV2ModElements instance) {
		super(instance, 36);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new HoeItem(new IItemTier() {
			public int getMaxUses() {
				return 2510;
			}

			public float getEfficiency() {
				return 6.5f;
			}

			public float getAttackDamage() {
				return 0.1f;
			}

			public int getHarvestLevel() {
				return 3;
			}

			public int getEnchantability() {
				return 15;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(Items.EMERALD));
			}
		}, 0, -2.9f, new Item.Properties().group(LukaModv2ItemGroup.tab)) {
		}.setRegistryName("emerald_tools_hoe"));
	}
}
