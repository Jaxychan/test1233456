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

package quest.heiron;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Balthazar, fixed Shaman
 * @reworked vlog
 */
public class _1636AFluteForTheFixing extends QuestHandler {

	private final static int questId = 1636;

	public _1636AFluteForTheFixing() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(204535).addOnQuestStart(questId);
		qe.registerQuestNpc(204535).addOnTalkEvent(questId);
		qe.registerQuestNpc(203792).addOnTalkEvent(questId);
		qe.registerQuestItem(182201785, questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();
		int targetId = env.getTargetId();

		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 204535) { // Maximus
				if (dialog == DialogAction.QUEST_SELECT) {
					return sendQuestDialog(env, 4762);
				} else {
					return sendQuestStartDialog(env);
				}
			}
		} else if (qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			switch (targetId) {
				case 203792: { // Utsida
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 0) {
								return sendQuestDialog(env, 1011);
							} else if (var == 1) {
								return sendQuestDialog(env, 1352);
							} else if (var == 2) {
								return sendQuestDialog(env, 2034);
							}
						}
						case SETPRO1: {
							return defaultCloseDialog(env, 0, 1); // 1
						}
						case CHECK_USER_HAS_QUEST_ITEM: {
							return checkQuestItems(env, 1, 2, false, 10000, 10001); // 2
						}
						case SETPRO4: {
							return defaultCloseDialog(env, 2, 3, 182201785, 1, 0, 0); // 3
						}
						case FINISH_DIALOG: {
							return sendQuestSelectionDialog(env);
						}
						default:
							break;
					}
				}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 204535) { // Maximus
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
	public HandlerResult onItemUseEvent(final QuestEnv env, Item item) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (player.isInsideZone(ZoneName.get("LF3_ITEMUSEAREA_Q1636"))) {
				return HandlerResult.fromBoolean(useQuestItem(env, item, 3, 3, true));
			}
		}
		return HandlerResult.FAILED;
	}
}
