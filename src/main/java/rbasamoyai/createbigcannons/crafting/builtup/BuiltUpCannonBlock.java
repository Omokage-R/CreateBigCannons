package rbasamoyai.createbigcannons.crafting.builtup;

import java.util.List;

import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import rbasamoyai.createbigcannons.cannons.big_cannons.BigCannonBlock;
import rbasamoyai.createbigcannons.cannons.big_cannons.cannon_end.BigCannonEnd;
import rbasamoyai.createbigcannons.cannons.big_cannons.material.BigCannonMaterial;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import rbasamoyai.createbigcannons.index.CBCBigCannonMaterials;
import rbasamoyai.createbigcannons.index.CBCBlockEntities;

public class BuiltUpCannonBlock extends DirectionalBlock implements IBE<LayeredBigCannonBlockEntity>, BigCannonBlock {

	public BuiltUpCannonBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public BigCannonMaterial getCannonMaterial() {
		return CBCBigCannonMaterials.INCOMPLETE_LAYERED;
	}

	@Override
	public CannonCastShape getCannonShape() {
		return CannonCastShape.MEDIUM;
	}

	@Override
	public Direction getFacing(BlockState state) {
		return state.getValue(FACING);
	}

	@Override
	public BigCannonMaterial getCannonMaterialInLevel(LevelAccessor level, BlockState state, BlockPos pos) {
		return level.getBlockEntity(pos) instanceof LayeredBigCannonBlockEntity layered ? layered.getBaseMaterial() : this.getCannonMaterial();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!level.isClientSide) this.onRemoveCannon(state, level, pos, newState, isMoving);
		super.onRemove(state, level, pos, newState, isMoving);
	}

	@Override
	public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if (!level.isClientSide) this.playerWillDestroyBigCannon(level, pos, state, player);
		super.playerWillDestroy(level, pos, state, player);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
		List<ItemStack> drops = super.getDrops(state, params);
		if (params.getParameter(LootContextParams.BLOCK_ENTITY) instanceof LayeredBigCannonBlockEntity layered) {
			for (Block block : layered.getLayers().values()) {
				drops.addAll(block.defaultBlockState().getDrops(params));
			}
		}
		return drops;
	}

	@Override public BigCannonEnd getDefaultOpeningType() { return BigCannonEnd.OPEN; }

	@Override
	public boolean isComplete(BlockState state) {
		return false;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.INVISIBLE;
	}

	@Override
	public Class<LayeredBigCannonBlockEntity> getBlockEntityClass() {
		return LayeredBigCannonBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends LayeredBigCannonBlockEntity> getBlockEntityType() {
		return CBCBlockEntities.LAYERED_CANNON.get();
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}

}
