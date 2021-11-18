
package net.mcreator.lukamodv.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;

import net.mcreator.lukamodv.itemgroup.LukaModv2ItemGroup;
import net.mcreator.lukamodv.LukamodV2ModElements;

@LukamodV2ModElements.ModElement.Tag
public class QuartzPickaxeItem extends LukamodV2ModElements.ModElement {
	@ObjectHolder("lukamod_v2:quartz_pickaxe")
	public static final Item block = null;
	public QuartzPickaxeItem(LukamodV2ModElements instance) {
		super(instance, 53);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new PickaxeItem(new IItemTier() {
			public int getMaxUses() {
				return 400;
			}

			public float getEfficiency() {
				return 7f;
			}

			public float getAttackDamage() {
				return 1f;
			}

			public int getHarvestLevel() {
				return 3;
			}

			public int getEnchantability() {
				return 20;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(Items.QUARTZ));
			}
		}, 1, -3f, new Item.Properties().group(LukaModv2ItemGroup.tab)) {
		}.setRegistryName("quartz_pickaxe"));
	}
}
