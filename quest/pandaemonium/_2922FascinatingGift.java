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

package quest.pandaemonium;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Cheatkiller
 *
 */
public class _2922FascinatingGift extends QuestHandler {

	private final static int questId = 2922;

	public _2922FascinatingGift() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(204261).addOnQuestStart(questId);
		qe.registerQuestNpc(204261).addOnTalkEvent(questId);
		qe.registerQuestNpc(798058).addOnTalkEvent(questId);
		qe.registerQuestNpc(204108).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();
		int targetId = env.getTargetId();

		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 204261) {
				if (dialog == DialogAction.QUEST_SELECT) {
					return sendQuestDialog(env, 4762);
				} else {
					return sendQuestStartDialog(env);
				}
			}
		} else if (qs.getStatus() == QuestStatus.START) {
			if (targetId == 204261) {
				if (dialog == DialogAction.QUEST_SELECT) {
					if (qs.getQuestVarById(0) == 0) {
						return sendQuestDialog(env, 1003);
					}
				} else if (dialog == DialogAction.SELECT_ACTION_1012) {
					return sendQuestDialog(env, 1012);
				} else if (dialog == DialogAction.SELECT_ACTION_1097) {
					return sendQuestDialog(env, 1097);
				} else if (dialog == DialogAction.SETPRO10) {
					qs.setQuestVar(10);
					return defaultCloseDialog(env, 10, 10, true, false);
				} else if (dialog == DialogAction.SETPRO20) {
					qs.setQuestVar(20);
					return defaultCloseDialog(env, 20, 20, true, false);
				}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 798058 && qs.getQuestVarById(0) == 10) {
				if (dialog == DialogAction.USE_OBJECT) {
					return sendQuestDialog(env, 1352);
				}
				return sendQuestEndDialog(env);
			} else if (targetId == 204108 && qs.getQuestVarById(0) == 20) {
				if (dialog == DialogAction.USE_OBJECT) {
					return sendQuestDialog(env, 1693);
				}
				return sendQuestEndDialog(env, 1);
			}
		}
		return false;
	}
}
