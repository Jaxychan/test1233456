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

package quest.pernon;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Bobobear
 */
public class _51009TheGiftOfCharity extends QuestHandler {

	private final static int questId = 51009;

	public _51009TheGiftOfCharity() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(831033).addOnQuestStart(questId);
		qe.registerQuestNpc(831039).addOnQuestStart(questId);
		qe.registerQuestNpc(831033).addOnTalkEvent(questId);
		qe.registerQuestNpc(831039).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();
		int targetId = env.getTargetId();

		if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
			if (targetId == 831033 || targetId == 831039) {
				switch (dialog) {
					case QUEST_SELECT: {
						return sendQuestDialog(env, 1011);
					}
					case QUEST_ACCEPT_1:
					case QUEST_ACCEPT_SIMPLE:
						return sendQuestStartDialog(env);
					default:
						break;
				}
			}
		} else if (qs.getStatus() == QuestStatus.START) {
			if (targetId == 831033 || targetId == 831039) {
				changeQuestStep(env, 0, 0, true); // reward
				return sendQuestDialog(env, 2375);
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 831033 || targetId == 831039) {
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}
