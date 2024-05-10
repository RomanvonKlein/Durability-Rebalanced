package com.romanvonklein.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.serialization.Lifecycle;

import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryOwner;

@Mixin(SimpleRegistry.class)
public abstract class RemoveEnchantmentsSimpleRegistryMixin<T> {
    @Shadow
    public abstract RegistryEntryOwner<T> getEntryOwner();

    @Inject(method = "set(ILnet/minecraft/registry/RegistryKey;Ljava/lang/Object;Lcom/mojang/serialization/Lifecycle;)Lnet/minecraft/registry/entry/RegistryEntry$Reference;", at = @At("HEAD"), cancellable = true)
    private void durabilityrebalanced$disableMendingUnbreaking(int i, RegistryKey<T> registryKey, T object,
            Lifecycle lifecycle, CallbackInfoReturnable<RegistryEntry.Reference<T>> cir) {
        if ((Object) this == Registries.ENCHANTMENT && (!(registryKey.getValue().toString().equals("minecraft:mending")
                && (!(registryKey.getValue().toString().equals("minecraft:unbreaking")))))) {
            cir.setReturnValue(RegistryEntry.Reference.standAlone(getEntryOwner(), registryKey));
        }
    }
}
