package com.yakasov.mixin;

import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ComposterBlock.class)
public class ComposterBlockMixin {
    @Inject(method = "registerDefaultCompostableItems", at = @At("TAIL"))
    private static void addCustomCompostableItems(CallbackInfo ci) {
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(Items.ROTTEN_FLESH, 0.65F);
    }
}
