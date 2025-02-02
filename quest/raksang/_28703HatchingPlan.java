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

package quest.raksang;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;

/**
 * @author Cheatkiller
 *
 */
public class _28703HatchingPlan extends QuestHandler {

	private final static int questId = 28703;

	public _28703HatchingPlan() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestItem(182212209, questId);
		qe.registerQuestItem(182212210, questId);
		qe.registerQuestNpc(799431).addOnTalkEvent(questId);
		qe.registerQuestNpc(701115).addOnTalkEvent(questId);
		qe.registerQuestNpc(798442).addOnTalkEvent(questId);
		qe.addHandlerSideQuestDrop(questId, 701115, 182212211, 1, 100);
		qe.registerGetingItem(182212211, questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();
		int targetId = env.getTargetId();

		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 0) {
				if (dialog == DialogAction.QUEST_ACCEPT_1) {
					removeQuestItem(env, 182212209, 1);
					giveQuestItem(env, 182212210, 1);
					QuestService.startQuest(env);
					return closeDialogWindow(env);
				}
			}
		} else if (qs.getStatus() == QuestStatus.START) {
			if (targetId == 799431) {
				if (dialog == DialogAction.QUEST_SELECT) {
					if (qs.getQuestVarById(0) == 0) {
						return sendQuestDialog(env, 1011);
					} else if (qs.getQuestVarById(0) == 2) {
						return sendQuestDialog(env, 1693);
					}
				} else if (dialog == DialogAction.SETPRO1) {
					return defaultCloseDialog(env, 0, 1);
				} else if (dialog == DialogAction.SETPRO3) {
					return defaultCloseDialog(env, 2, 3);
				}
			} else if (targetId == 701115) {
				if (qs.getQuestVarById(0) == 3) {
					return true;
				}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 798442) {
				if (dialog == DialogAction.USE_OBJECT) {
					return sendQuestDialog(env, 10002);
				}
				removeQuestItem(env, 182212211, 1);
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}

	@Override
	public boolean onGetItemEvent(QuestEnv env) {
		return defaultOnGetItemEvent(env, 3, 3, true);
	}

	@Override
	public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (item.getItemId() == 182212209) {
			if (qs == null || qs.getStatus() == QuestStatus.NONE) {
				return HandlerResult.fromBoolean(sendQuestDialog(env, 4));
			}
		} else if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (qs.getQuestVarById(0) == 1) {
				return HandlerResult.fromBoolean(useQuestItem(env, item, 1, 2, false));
			}
		}
		return HandlerResult.FAILED;
	}
}
