package com.yakasov.mixin;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {
    @Unique
    private static Map<Block, Block> getReversedStrippedBlocks() {
            Map<Block, Block> strippedBlocks = AxeItemAccessor.getStrippedBlocks();
            return strippedBlocks.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    @Inject(
            method = "useOnBlock",
            at = @At(
                    value = "RETURN",
                    ordinal = 2
            ),
            cancellable = true)
    public void unstripWood(
            ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir
    ) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        PlayerEntity playerEntity = context.getPlayer();

        Optional<BlockState> optional = Optional
                .ofNullable(getReversedStrippedBlocks().get(blockState.getBlock()))
                .map(block -> block
                        .getDefaultState()
                        .with(PillarBlock.AXIS, blockState.get(PillarBlock.AXIS)));

        if (optional.isPresent()) {
            world.playSound(
                    playerEntity, blockPos,
                    SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS,
                    1.0F, 1.0F
            );

            ItemStack itemStack = context.getStack();
            if (playerEntity instanceof ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) playerEntity, blockPos, itemStack);
            }

            world.setBlockState(blockPos, optional.get(), Block.NOTIFY_ALL_AND_REDRAW);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, optional.get()));
            itemStack.decrement(1);

            cir.setReturnValue(ActionResult.success(world.isClient));
        }
    }
}
