
package net.mcreator.lukamodv.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.world.World;
import net.minecraft.item.UseAction;
import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.Food;
import net.minecraft.entity.LivingEntity;

import net.mcreator.lukamodv.procedures.RubyAppelFoodEatenProcedure;
import net.mcreator.lukamodv.itemgroup.LukaModv2ItemGroup;
import net.mcreator.lukamodv.LukamodV2ModElements;

import java.util.Map;
import java.util.HashMap;

@LukamodV2ModElements.ModElement.Tag
public class RubyAppelItem extends LukamodV2ModElements.ModElement {
	@ObjectHolder("lukamod_v2:ruby_appel")
	public static final Item block = null;
	public RubyAppelItem(LukamodV2ModElements instance) {
		super(instance, 26);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new FoodItemCustom());
	}
	public static class FoodItemCustom extends Item {
		public FoodItemCustom() {
			super(new Item.Properties().group(LukaModv2ItemGroup.tab).maxStackSize(64).rarity(Rarity.RARE)
					.food((new Food.Builder()).hunger(50).saturation(20f).setAlwaysEdible().build()));
			setRegistryName("ruby_appel");
		}

		@Override
		public UseAction getUseAction(ItemStack itemstack) {
			return UseAction.EAT;
		}

		@Override
		public ItemStack onItemUseFinish(ItemStack itemstack, World world, LivingEntity entity) {
			ItemStack retval = super.onItemUseFinish(itemstack, world, entity);
			double x = entity.getPosX();
			double y = entity.getPosY();
			double z = entity.getPosZ();
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("entity", entity);
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				RubyAppelFoodEatenProcedure.executeProcedure($_dependencies);
			}
			return retval;
		}
	}
}
