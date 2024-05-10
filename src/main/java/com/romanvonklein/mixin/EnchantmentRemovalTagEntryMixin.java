package com.romanvonklein.mixin;

import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(TagEntry.class)
public class EnchantmentRemovalTagEntryMixin {
    @Shadow
    @Final
    private Identifier id;

    @Inject(method = "resolve", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    private <T> void durabilityrebalanced$preventTagsFromAddingRemovedEnchantments(TagEntry.ValueGetter<T> valueGetter,
            Consumer<T> consumer, CallbackInfoReturnable<Boolean> cir) {
        if (!id.toString().equals("minecraft:unbreaking") && !id.toString().equals("minecraft:mending")
                && Registries.ENCHANTMENT.get(id) != null) {
            cir.setReturnValue(true);
        }
    }
}