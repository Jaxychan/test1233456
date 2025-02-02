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

package quest.satra_treasure_hoard;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Ritsu
 *
 */
public class _28904ApsilonsAbilities extends QuestHandler {

	private static final int questId = 28904;

	public _28904ApsilonsAbilities() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(800331).addOnQuestStart(questId);
		qe.registerQuestNpc(205866).addOnTalkEvent(questId);
		qe.registerQuestNpc(219302).addOnKillEvent(questId);
		qe.registerQuestNpc(219303).addOnKillEvent(questId);
		qe.registerQuestNpc(219304).addOnKillEvent(questId);
		qe.registerQuestNpc(219305).addOnKillEvent(questId);
		qe.registerQuestNpc(219306).addOnKillEvent(questId);
		qe.registerQuestNpc(219307).addOnKillEvent(questId);
		qe.registerQuestNpc(219308).addOnKillEvent(questId);
		qe.registerQuestNpc(219309).addOnKillEvent(questId);
	}

	@Override
	public boolean onKillEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null || qs.getStatus() != QuestStatus.START) {
			return false;
		}

		int var = qs.getQuestVarById(0);
		int targetId = env.getTargetId();

		switch (targetId) {
			case 219302:
			case 219303:
			case 219304:
			case 219305:
			case 219306:
			case 219307:
			case 219308:
			case 219309:
				if (var >= 0 && var < 10) {
					qs.setQuestVarById(0, var + 1);
					updateQuestStatus(env);
					return true;
				}
		}
		return false;
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		int targetId = env.getTargetId();
		QuestState qs = player.getQuestStateList().getQuestState(questId);

		if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
			if (targetId == 800331) {
				if (env.getDialog() == DialogAction.QUEST_SELECT) {
					return sendQuestDialog(env, 1011);
				} else {
					return sendQuestStartDialog(env);
				}
			}
		} else if (qs.getStatus() == QuestStatus.START) {
			if (targetId == 205866) {
				if (env.getDialog() == DialogAction.QUEST_SELECT && qs.getQuestVarById(0) == 10) {
					qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
					qs.setStatus(QuestStatus.REWARD);
					updateQuestStatus(env);
					return sendQuestDialog(env, 1352);
				} else {
					return sendQuestStartDialog(env);
				}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 205866) {
				if (env.getDialog() == DialogAction.QUEST_SELECT) {
					return sendQuestDialog(env, 5);
				} else if (env.getDialogId() == DialogAction.SELECT_QUEST_REWARD.id()) {
					return sendQuestDialog(env, 5);
				} else {
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}
}
