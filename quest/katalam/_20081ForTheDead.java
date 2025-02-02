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
 * @author Cheatkiller
 */
public class _20081ForTheDead extends QuestHandler {

	private final static int questId = 20081;
	private final static int[] mobs = { 232479, 232480, 232481, 233194 };

	public _20081ForTheDead() {
		super(questId);
	}

	@Override
	public void register() {
		int[] npcIds = { 800529, 800531, 801239, 701535 };
		qe.registerOnLevelUp(questId);
		for (int npcId : npcIds) {
			qe.registerQuestNpc(npcId).addOnTalkEvent(questId);
		}
		for (int mob : mobs) {
			qe.registerQuestNpc(mob).addOnKillEvent(questId);
		}
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 20080);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		DialogAction dialog = env.getDialog();

		if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (targetId == 800529) {
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 0) {
							return sendQuestDialog(env, 1011);
						} else if (qs.getQuestVarById(0) == 1) {
							return sendQuestDialog(env, 1352);
						}
					}
					case SETPRO1: {
						return defaultCloseDialog(env, 0, 1);
					}
					case CHECK_USER_HAS_QUEST_ITEM: {
						return checkQuestItems(env, 1, 2, false, 10000, 10001);
					}
					case SETPRO2: {
						return closeDialogWindow(env);
					}
					default:
						break;
				}
			} else if (targetId == 701535) {
				return true;
			} else if (targetId == 800531) {
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 2) {
							return sendQuestDialog(env, 1693);
						}
					}
					case SETPRO3: {
						return defaultCloseDialog(env, 2, 3);
					}
					default:
						break;
				}
			} else if (targetId == 801239) {
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 3) {
							return sendQuestDialog(env, 2716);
						}
					}
					case SETPRO6: {
						return defaultCloseDialog(env, 3, 4);
					}
					default:
						break;
				}
			}
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 801239) {
				if (dialog == DialogAction.USE_OBJECT) {
					return sendQuestDialog(env, 10002);
				} else {
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}

	@Override
	public boolean onKillEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			if (var >= 4) {
				changeQuestStep(env, var, var + 1, false);
				if (var == 6) {
					qs.setQuestVar(6);
					qs.setStatus(QuestStatus.REWARD);
					updateQuestStatus(env);
				}
				return true;
			}
		}
		return false;
	}
}
