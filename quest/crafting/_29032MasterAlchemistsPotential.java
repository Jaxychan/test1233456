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

package quest.crafting;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Thuatan
 */
public class _29032MasterAlchemistsPotential extends QuestHandler {

	private final static int questId = 29032;

	public _29032MasterAlchemistsPotential() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(204102).addOnQuestStart(questId);
		qe.registerQuestNpc(204102).addOnTalkEvent(questId);
		qe.registerQuestNpc(204103).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);

		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc) {
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		}

		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 204102) {
				if (env.getDialog() == DialogAction.QUEST_SELECT) {
					return sendQuestDialog(env, 4762);
				} else {
					return sendQuestStartDialog(env);
				}
			}
		}

		if (qs == null) {
			return false;
		}

		if (qs.getStatus() == QuestStatus.START) {
			switch (targetId) {
				case 204103:
					switch (env.getDialog()) {
						case QUEST_SELECT:
							return sendQuestDialog(env, 1011);
						case SETPRO10:
							if (!giveQuestItem(env, 152207149, 1)) {
								return true;
							}
							if (!giveQuestItem(env, 152029249, 1)) {
								return true;
							}
							qs.setQuestVarById(0, 1);
							updateQuestStatus(env);
							PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
							return true;
						case SETPRO20:
							if (!giveQuestItem(env, 152207150, 1)) {
								return true;
							}
							if (!giveQuestItem(env, 152029249, 1)) {
								return true;
							}
							qs.setQuestVarById(0, 1);
							updateQuestStatus(env);
							PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
							return true;
						default:
							break;
					}
				case 204102:
					switch (env.getDialog()) {
						case QUEST_SELECT:
							long itemCount1 = player.getInventory().getItemCountByItemId(182207902);
							if (itemCount1 > 0) {
								removeQuestItem(env, 182207902, 1);
								qs.setStatus(QuestStatus.REWARD);
								updateQuestStatus(env);
								return sendQuestDialog(env, 1352);
							} else {
								return sendQuestDialog(env, 2716);
							}
						default:
							break;
					}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 204102) {
				if (env.getDialogId() == DialogAction.CHECK_USER_HAS_QUEST_ITEM.id()) {
					return sendQuestDialog(env, 5);
				} else {
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}
}
