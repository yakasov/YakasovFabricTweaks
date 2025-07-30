package com.yakasov.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.block.Block.pushEntitiesUpBeforeBlockChange;

@Mixin(SaplingBlock.class)
public class SaplingBlockMixin {
    @Inject(
            method = "generate",
            at = @At("HEAD"),
            cancellable = true
    )
    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random, CallbackInfo ci) {
        if (world.getBlockState(pos.down()).isIn(BlockTags.SAND)) {
            BlockState blockState = pushEntitiesUpBeforeBlockChange(state, Blocks.DEAD_BUSH.getDefaultState(), world, pos);
            world.setBlockState(pos, blockState);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));

            ci.cancel();
        }
    }
}
