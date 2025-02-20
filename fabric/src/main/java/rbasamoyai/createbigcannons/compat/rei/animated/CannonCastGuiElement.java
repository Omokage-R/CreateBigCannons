package rbasamoyai.createbigcannons.compat.rei.animated;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.gui.element.GuiGameElement;

import me.shedaniel.rei.api.client.gui.widgets.Widget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import rbasamoyai.createbigcannons.crafting.casting.CannonCastShape;
import rbasamoyai.createbigcannons.index.CBCBlockPartials;

public class CannonCastGuiElement extends Widget {

	private CannonCastShape currentShape = CannonCastShape.VERY_SMALL;

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		PoseStack poseStack = graphics.pose();
		poseStack.pushPose();
		poseStack.translate(0, 0, 200);
		poseStack.mulPose(Axis.XP.rotationDegrees(-22.5f));
		poseStack.mulPose(Axis.YP.rotationDegrees(22.5f));
		int scale = 23;

		GuiGameElement.of(CBCBlockPartials.cannonCastFor(this.currentShape))
			.atLocal(0, 0.25, 0)
			.scale(scale)
			.render(graphics);

		poseStack.popPose();
	}

	public CannonCastGuiElement withShape(CannonCastShape shape) { this.currentShape = shape; return this; }

	@Override public List<? extends GuiEventListener> children() { return List.of(); }

}
