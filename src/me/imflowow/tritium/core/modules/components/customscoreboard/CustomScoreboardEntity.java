package me.imflowow.tritium.core.modules.components.customscoreboard;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.modules.CustomScoreboard;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import tritium.api.module.gui.GuiEntity;
import tritium.api.module.value.impl.PositionValue;

public class CustomScoreboardEntity extends GuiEntity {

	int width;
	int height;

	public CustomScoreboardEntity(PositionValue position) {
		super(position);
	}

	@Override
	public void init() {
		width = 10;
		height = 10;
	}

	@Override
	public void draw(double x, double y) {

		Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
		ScoreObjective scoreobjective = null;
		ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.thePlayer.getName());

		if (scoreplayerteam != null) {
			int i1 = scoreplayerteam.getChatFormat().getColorIndex();

			if (i1 >= 0) {
				scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + i1);
			}
		}

		ScoreObjective scoreobjective1 = scoreobjective != null ? scoreobjective
				: scoreboard.getObjectiveInDisplaySlot(1);

		if (scoreobjective1 != null) {
			this.renderScoreboard(scoreobjective1, this.getScaledResolution(),(float)x,(float)y);
		}
	}

	private void renderScoreboard(ScoreObjective objective, ScaledResolution scaledRes,float x,float y) {
		
		
		Scoreboard scoreboard = objective.getScoreboard();
		Collection<Score> collection = scoreboard.getSortedScores(objective);
		List<Score> list = Lists.newArrayList(Iterables.filter(collection, new Predicate<Score>() {
			public boolean apply(Score p_apply_1_) {
				return p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#");
			}
		}));

		if (list.size() > 15) {
			collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
		} else {
			collection = list;
		}

		int i = mc.fontRendererObj.getStringWidth(objective.getDisplayName());

		for (Score score : collection) {
			ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
			String s = ScorePlayerTeam.formatPlayerName(scoreplayerteam, score.getPlayerName()) + ": "
					+ EnumChatFormatting.RED + score.getScorePoints();
			if(this.getModule().hideRedNumber.getValue())
				s = ScorePlayerTeam.formatPlayerName(scoreplayerteam, score.getPlayerName());
			i = Math.max(i, mc.fontRendererObj.getStringWidth(s));
		}

		int i1 = collection.size() * mc.fontRendererObj.FONT_HEIGHT;
		int j1 = scaledRes.getScaledHeight() / 2 + i1 / 3;
		int k1 = 3;
		int l1 = scaledRes.getScaledWidth() - i - k1;
		int j = 0;
		int l = scaledRes.getScaledWidth() - k1 + 2;
		this.height = (collection.size() + 1) * mc.fontRendererObj.FONT_HEIGHT;
		this.width = l - (l1 - 2);
		if(this.getModule().hidden.getValue())
			return;
		for (Score score1 : collection) {
			++j;
			ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
			String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score1.getPlayerName());
			String s2 = EnumChatFormatting.RED + "" + score1.getScorePoints();
			int k = j1 - j * mc.fontRendererObj.FONT_HEIGHT;
			if(!this.getModule().clearBackground.getValue())
			{
				Gui.drawRect(x, y + (collection.size() - j + 1) * mc.fontRendererObj.FONT_HEIGHT, x + width,
						y + ( collection.size() -j + 2) * mc.fontRendererObj.FONT_HEIGHT, 1342177280);
			}
			GlStateManager.disableBlend();
			mc.fontRendererObj.drawString(s1, x + 2, y + (collection.size() - j + 1) * mc.fontRendererObj.FONT_HEIGHT, 553648127);
			if(!this.getModule().hideRedNumber.getValue())
			mc.fontRendererObj.drawString(s2, x + width - mc.fontRendererObj.getStringWidth(s2),
					y + (collection.size() - j + 1) * mc.fontRendererObj.FONT_HEIGHT, 553648127);

			if (j == collection.size()) {
				String s3 = objective.getDisplayName();
				if(!this.getModule().clearBackground.getValue())
				{
					Gui.drawRect(x, y, x + width, y + mc.fontRendererObj.FONT_HEIGHT - 1, 1610612736);
					Gui.drawRect(x, y + mc.fontRendererObj.FONT_HEIGHT - 1, x + width, y + mc.fontRendererObj.FONT_HEIGHT,
							1342177280);
				}
				mc.fontRendererObj.drawString(s3, x + 2 + i / 2 - mc.fontRendererObj.getStringWidth(s3) / 2,
						y, 553648127);
			}
		}

	}

	@Override
	public int getHeight() {
		return height;

		// k
	}

	@Override
	public int getWidth() {
		return width;
		// k
	}

	public CustomScoreboard getModule() {
		return (CustomScoreboard) Tritium.instance.getModuleManager().getModule(CustomScoreboard.class);
	}
}
