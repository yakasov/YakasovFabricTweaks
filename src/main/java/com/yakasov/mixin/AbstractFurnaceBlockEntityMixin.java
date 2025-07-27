package com.yakasov.mixin;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {

    @Inject(method = "createFuelTimeMap", at = @At("RETURN"))
    private static void addMagmaBlockFuel(CallbackInfoReturnable<Map<Item, Integer>> cir) {
        Map<Item, Integer> fuelMap = cir.getReturnValue();
        fuelMap.put(Items.MAGMA_BLOCK, Integer.valueOf(400));
    }
}