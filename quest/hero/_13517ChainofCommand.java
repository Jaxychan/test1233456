/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Aion-Lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. *
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 */

package quest.hero;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.stats.AbyssRankEnum;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Evil_dnk
 */
public class _13517ChainofCommand extends QuestHandler {

	private final static int questId = 13517;

	public _13517ChainofCommand() {
		super(questId);
	}

	@Override
	public void register() {
	qe.registerQuestNpc(800527).addOnQuestStart(questId);
	qe.registerQuestNpc(800527).addOnTalkEvent(questId);
        qe.registerOnKillRanked(AbyssRankEnum.STAR1_OFFICER, questId);
        qe.registerOnKillRanked(AbyssRankEnum.STAR2_OFFICER, questId);
        qe.registerOnKillRanked(AbyssRankEnum.STAR3_OFFICER, questId);
        qe.registerOnKillRanked(AbyssRankEnum.STAR4_OFFICER, questId);
        qe.registerOnKillRanked(AbyssRankEnum.STAR5_OFFICER, questId);
	}

	@Override
	public boolean onKillRankedEvent(QuestEnv env) {
        Player player = env.getPlayer();
		if (env.getVisibleObject() instanceof Player && player != null && player.isInsideZone(ZoneName.get("KATALAM_600050000"))) {
			return defaultOnKillRankedEvent(env, 0, 44, true);
		}
		return false;
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (env.getTargetId() == 800527) {
			if (qs == null || qs.getStatus() == QuestStatus.NONE) {
				if (env.getDialog() == DialogAction.QUEST_SELECT)
					return sendQuestDialog(env, 1011);
				else
					return sendQuestStartDialog(env);
			}
			else if (qs.getStatus() == QuestStatus.REWARD) {
				if (env.getDialog() == DialogAction.QUEST_SELECT)
					return sendQuestDialog(env, 1352);
				else
					return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}
