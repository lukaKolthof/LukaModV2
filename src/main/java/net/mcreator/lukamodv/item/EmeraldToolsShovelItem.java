
package net.mcreator.lukamodv.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;

import net.mcreator.lukamodv.itemgroup.LukaModv2ItemGroup;
import net.mcreator.lukamodv.LukamodV2ModElements;

@LukamodV2ModElements.ModElement.Tag
public class EmeraldToolsShovelItem extends LukamodV2ModElements.ModElement {
	@ObjectHolder("lukamod_v2:emerald_tools_shovel")
	public static final Item block = null;
	public EmeraldToolsShovelItem(LukamodV2ModElements instance) {
		super(instance, 35);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ShovelItem(new IItemTier() {
			public int getMaxUses() {
				return 2505;
			}

			public float getEfficiency() {
				return 7f;
			}

			public float getAttackDamage() {
				return 0.2f;
			}

			public int getHarvestLevel() {
				return 4;
			}

			public int getEnchantability() {
				return 15;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(Items.EMERALD));
			}
		}, 1, -2.9f, new Item.Properties().group(LukaModv2ItemGroup.tab)) {
		}.setRegistryName("emerald_tools_shovel"));
	}
}
