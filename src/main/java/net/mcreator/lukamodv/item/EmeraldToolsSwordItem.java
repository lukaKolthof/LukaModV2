
package net.mcreator.lukamodv.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.SwordItem;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;

import net.mcreator.lukamodv.itemgroup.LukaModv2ItemGroup;
import net.mcreator.lukamodv.LukamodV2ModElements;

@LukamodV2ModElements.ModElement.Tag
public class EmeraldToolsSwordItem extends LukamodV2ModElements.ModElement {
	@ObjectHolder("lukamod_v2:emerald_tools_sword")
	public static final Item block = null;
	public EmeraldToolsSwordItem(LukamodV2ModElements instance) {
		super(instance, 34);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(new IItemTier() {
			public int getMaxUses() {
				return 2500;
			}

			public float getEfficiency() {
				return 8.5f;
			}

			public float getAttackDamage() {
				return 4.5f;
			}

			public int getHarvestLevel() {
				return 7;
			}

			public int getEnchantability() {
				return 18;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(Items.EMERALD));
			}
		}, 3, -2.5f, new Item.Properties().group(LukaModv2ItemGroup.tab)) {
		}.setRegistryName("emerald_tools_sword"));
	}
}
