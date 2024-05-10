package com.romanvonklein.mixin;

import java.util.LinkedHashMap;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.item.ItemStack;

@Mixin(EnchantmentHelper.class)
public class EnchantmentsRemovalEnchantmentHelperServerMixin {
	@ModifyVariable(method = "set", at = @At(value = "HEAD"), argsOnly = true)
	private static Map<Enchantment, Integer> durabilityrebalanced$removeMendingAndUnbreaking(
			Map<Enchantment, Integer> value,
			Map<Enchantment, Integer> map, ItemStack stack) {
		Map<Enchantment, Integer> newMap = new LinkedHashMap<>();
		for (Enchantment enchantment : value.keySet()) {
			if (!(enchantment instanceof MendingEnchantment) && !(enchantment instanceof UnbreakingEnchantment)) {
				newMap.put(enchantment, value.get(enchantment));
			}
		}
		return newMap;
	}
}
