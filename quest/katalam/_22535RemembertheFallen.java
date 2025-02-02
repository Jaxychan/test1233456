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

package quest.katalam;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Romanz
 */
public class _22535RemembertheFallen extends QuestHandler {

	private final static int questId = 22535;

	public _22535RemembertheFallen() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(800557).addOnQuestStart(questId);
		qe.registerQuestNpc(800557).addOnTalkEvent(questId);
		qe.registerQuestNpc(701746).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		DialogAction dialog = env.getDialog();

		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 800557) {
				switch (dialog) {
					case QUEST_SELECT: {
                        giveQuestItem(env, 182213350, 3);
						return sendQuestDialog(env, 4762);
					}
					case QUEST_ACCEPT_SIMPLE: {
						return sendQuestStartDialog(env);
					}
					default:
						return false;
				}
			}
		}
			else if (targetId == 701746) {
				if (env.getDialog() == DialogAction.USE_OBJECT) {
				int var = qs.getQuestVarById(0);
							if (var == 0) {
                                removeQuestItem(env, 182213350, 1);
								return useQuestObject(env, 0, 1, false, true);
							}
							if (var == 1) {removeQuestItem(env, 182213350, 1);
								return useQuestObject(env, 1, 2, false, true);
							}
							if (var == 2) {
                                removeQuestItem(env, 182213350, 1);
								return useQuestObject(env, 2, 3, true, true);
							}
				}
			}
		else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 800557) {
				if (dialog == DialogAction.USE_OBJECT) {
					return sendQuestDialog(env, 10002);
				}
				else {
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}


}
