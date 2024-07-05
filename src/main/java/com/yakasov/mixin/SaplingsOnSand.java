package com.yakasov.mixin;

import net.minecraft.block.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlantBlock.class)
public abstract class SaplingsOnSand extends Block {
    public SaplingsOnSand(Settings settings) {
        super(settings);
    }

    @Inject(method = "canPlantOnTop", at = @At("HEAD"), cancellable = true)
    protected void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        PlantBlock thisObj = (PlantBlock) (Object) this;
        if (thisObj.getClass() == SaplingBlock.class) {
            cir.setReturnValue(floor.isIn(BlockTags.SAND) || floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND));
        }
    }
}
