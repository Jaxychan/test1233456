/**
 *  Project Q is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Project Q is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License along with Project Q. If not, see <http://www.gnu.org/licenses/>.
 */

package quest.AbyssalSplinter;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;

public class _30365ARayOfHope extends QuestHandler {

	private final static int questId = 30365;

	public _30365ARayOfHope() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(204241).addOnTalkEvent(questId);
		qe.registerQuestNpc(203574).addOnTalkEvent(questId);
		qe.registerQuestNpc(278040).addOnTalkEvent(questId);
		qe.registerQuestItem(182209824, questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();
		int targetId = env.getTargetId();

		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			return false;
		} else if (qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			switch (targetId) {
				case 204241: { // Annemari
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 0) {
								return sendQuestDialog(env, 1352);
							}
						}
						case SETPRO1: {
							return defaultCloseDialog(env, 0, 1);
						}
						default:
							break;
					}
					break;
				}
				case 203574: { // Arekedil
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 1) {
								return sendQuestDialog(env, 1693);
							} else if (var == 3) {
								return sendQuestDialog(env, 2375);
							}
						}
						case SETPRO2: {
							return defaultCloseDialog(env, 1, 2);
						}
						case SELECT_QUEST_REWARD: {
							changeQuestStep(env, 3, 3, true);
							return sendQuestDialog(env, 5);
						}
						default:
							break;
					}
					break;
				}
				case 278040: { // Haug
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 2) {
								return sendQuestDialog(env, 2034);
							}
						}
						case SETPRO3: {
							return defaultCloseDialog(env, 2, 3);
						}
						default:
							break;
					}
					break;
				}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 203574) { // Arekedil
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}

	@Override
	public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (QuestService.startQuest(env)) {
				return HandlerResult.fromBoolean(sendQuestDialog(env, 4));
			}
		}
		return HandlerResult.FAILED;
	}
}