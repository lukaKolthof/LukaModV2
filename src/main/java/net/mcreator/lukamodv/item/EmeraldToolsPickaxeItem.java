
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
public class EmeraldToolsPickaxeItem extends LukamodV2ModElements.ModElement {
	@ObjectHolder("lukamod_v2:emerald_tools_pickaxe")
	public static final Item block = null;
	public EmeraldToolsPickaxeItem(LukamodV2ModElements instance) {
		super(instance, 32);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new PickaxeItem(new IItemTier() {
			public int getMaxUses() {
				return 6000;
			}

			public float getEfficiency() {
				return 7.5f;
			}

			public float getAttackDamage() {
				return 0.4f;
			}

			public int getHarvestLevel() {
				return 5;
			}

			public int getEnchantability() {
				return 17;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(Items.EMERALD));
			}
		}, 1, -2.7f, new Item.Properties().group(LukaModv2ItemGroup.tab)) {
		}.setRegistryName("emerald_tools_pickaxe"));
	}
}
