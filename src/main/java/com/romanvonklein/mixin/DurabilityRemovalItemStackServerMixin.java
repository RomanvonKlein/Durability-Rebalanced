package com.romanvonklein.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.item.ItemStack;

@Mixin(ItemStack.class)
public class DurabilityRemovalItemStackServerMixin {
	@Inject(method = "isDamageable", at = @At("RETURN"), cancellable = true)
	public void durabilityrebalanced$onIsDamageable(CallbackInfoReturnable<Boolean> info) {
		info.setReturnValue(false);
	}

	@Unique
	private static Enchantment replacement = null;

	@Inject(method = "addEnchantment", at = @At("HEAD"), cancellable = true)
	private void durabilityrebalanced$disableDisallowedEnchantments(Enchantment enchantment, int level,
			CallbackInfo ci) {
		if (enchantment instanceof MendingEnchantment || enchantment instanceof UnbreakingEnchantment) {
			ci.cancel();
		}
	}
	/*
	 * TODO: dont think i need this...
	 * 
	 * @ModifyVariable(method = "addEnchantment", at = @At("HEAD"), argsOnly = true)
	 * private Enchantment
	 * durabilityrebalanced$disableDisallowedEnchantments(Enchantment value) {
	 * if (replacement != null) {
	 * return replacement;
	 * }
	 * return value;
	 * }
	 */
	/*
	 * @ModifyVariable(method = "addEnchantment", at = @At("HEAD"), argsOnly = true)
	 * private int durabilityrebalanced$disableDisallowedEnchantments(int value) {
	 * if (replacement != null) {
	 * Enchantment temp = replacement;
	 * replacement = null;
	 * return Math.min(temp.getMaxLevel(), value);
	 * }
	 * return value;
	 * }
	 */
}
