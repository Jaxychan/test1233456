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

package quest.oriel;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 *
 * @author Bobobear & Ritsu
 */
public class _18832ImaginingAQuietLife extends QuestHandler {

	private static final int questId = 18832;

	public _18832ImaginingAQuietLife() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(830365).addOnQuestStart(questId);
		qe.registerQuestNpc(830365).addOnTalkEvent(questId);
		qe.registerQuestNpc(830001).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();
		int targetId = env.getTargetId();

		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 830365) {
				if (dialog == DialogAction.QUEST_SELECT) {
					return sendQuestDialog(env, 1011);
				} else {
					return sendQuestStartDialog(env);
				}
			}
		} else if (qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			switch (targetId) {
				case 830001: {
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 0) {
								return sendQuestDialog(env, 2375);
							}
						}
						case SELECT_QUEST_REWARD: {
							playQuestMovie(env, 801);
							changeQuestStep(env, 0, 0, true);
							return sendQuestDialog(env, 5);
						}
						default:
							break;
					}
				}

			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 830001) {
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}
