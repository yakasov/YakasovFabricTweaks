package com.yakasov.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.block.piston.PistonBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SpawnerBlock.class)
public class SpawnerBlockMixin {
    @ModifyArg(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockWithEntity;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V"),
            index = 0
    )
    private static AbstractBlock.Settings addBlastResistanceToSpawner(AbstractBlock.Settings settings) {
        settings.strength(50.0F, 1200.0F);
        return settings;
    }
}
