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
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;

/**
 * @author tyrto
 */
public class _10083AReianRecord extends QuestHandler {

	private final static int questId = 10083;

	public _10083AReianRecord() {
		super(questId);
	}

	@Override
	public void register() {
		int[] npcIds = { 800535, 800538, 800540, 730709, 730710 };
		qe.registerOnLevelUp(questId);
		for (int npcId : npcIds) {
			qe.registerQuestNpc(npcId).addOnTalkEvent(questId);
		}
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 10082);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		DialogAction dialog = env.getDialog();

		if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (targetId == 800535) {
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 0) {
							return sendQuestDialog(env, 1011);
						}
					}
					case SETPRO1: {
						return defaultCloseDialog(env, 0, 1);
					}
					default:
						break;
				}
			} else if (targetId == 800538) {
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 1) {
							return sendQuestDialog(env, 1352);
						}
					}
					case SETPRO2: {
						return defaultCloseDialog(env, 1, 2);
					}
					default:
						break;
				}
			} else if (targetId == 800540) {
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
			} else if (targetId == 730709) {
				switch (dialog) {
					case USE_OBJECT: {
						if (qs.getQuestVarById(0) == 3) {
							return sendQuestDialog(env, 2375);
						}
					}
					case SETPRO5: {
						Npc npc = (Npc) env.getVisibleObject();
						QuestService.addNewSpawn(npc.getWorldId(), npc.getInstanceId(), 230389, npc.getX() + 2, npc.getY() - 2, npc.getZ(), (byte) 0);
						QuestService.addNewSpawn(npc.getWorldId(), npc.getInstanceId(), 230390, npc.getX() - 2, npc.getY() + 2, npc.getZ(), (byte) 0);
						return defaultCloseDialog(env, 3, 4);
					}
					default:
						break;
				}
			} else if (targetId == 730710) {
				switch (dialog) {
					case USE_OBJECT: {
						if (qs.getQuestVarById(0) == 4) {
							return sendQuestDialog(env, 2034);
						}
					}
					case SETPRO4: {
						giveQuestItem(env, 182215226, 1);
						return defaultCloseDialog(env, 4, 4, true, false);
					}
					default:
						break;
				}
			}
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 800540) {
				if (dialog == DialogAction.USE_OBJECT) {
					return sendQuestDialog(env, 10002);
				} else {
					removeQuestItem(env, 182215226, 1);
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}
}
