package com.romanvonklein.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;

@Mixin(EnchantedBookItem.class)
public class EnchantedBookItemMixin {
    @Inject(method = "addEnchantment", at = @At("HEAD"), cancellable = true)
    private static void durabilityrebalanced$disableDisallowedEnchantments(ItemStack stack, EnchantmentLevelEntry entry,
            CallbackInfo ci) {
        if (entry.enchantment instanceof MendingEnchantment || entry.enchantment instanceof UnbreakingEnchantment) {
            ci.cancel();
        }
    }
}