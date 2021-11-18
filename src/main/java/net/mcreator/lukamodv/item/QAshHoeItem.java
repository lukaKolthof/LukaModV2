
package net.mcreator.lukamodv.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.item.HoeItem;

import net.mcreator.lukamodv.itemgroup.LukaModv2ItemGroup;
import net.mcreator.lukamodv.LukamodV2ModElements;

@LukamodV2ModElements.ModElement.Tag
public class QAshHoeItem extends LukamodV2ModElements.ModElement {
	@ObjectHolder("lukamod_v2:q_ash_hoe")
	public static final Item block = null;
	public QAshHoeItem(LukamodV2ModElements instance) {
		super(instance, 211);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new HoeItem(new IItemTier() {
			public int getMaxUses() {
				return 95;
			}

			public float getEfficiency() {
				return 4f;
			}

			public float getAttackDamage() {
				return -1f;
			}

			public int getHarvestLevel() {
				return 1;
			}

			public int getEnchantability() {
				return 7;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(AshItem.block));
			}
		}, 0, -3f, new Item.Properties().group(LukaModv2ItemGroup.tab)) {
		}.setRegistryName("q_ash_hoe"));
	}
}
