package com.yakasov.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.item.Items.STICK;

@Mixin(SaplingBlock.class)
public class SaplingsOnSandDecay {
    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random, CallbackInfo ci) {
        if (world.getBlockState(pos.down()).isIn(BlockTags.SAND)) {
            world.breakBlock(pos, false);

            final int amount = random.nextInt(4);
            final ItemStack sticks = new ItemStack(STICK, amount);
            world.spawnEntity(
                    new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), sticks)
            );
            ci.cancel();
        }
    }
}
