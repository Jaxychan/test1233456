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

package quest.daevation;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Rhys2002
 * @modified apozema
 * TODO: gunner should not be able to interact with 801223 NPC
 */
public class _2989CeremonyOfTheWise extends QuestHandler {

	private final static int questId = 2989;

	public _2989CeremonyOfTheWise() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(204146).addOnQuestStart(questId);
		qe.registerQuestNpc(204146).addOnTalkEvent(questId);
		qe.registerQuestNpc(204056).addOnTalkEvent(questId);
		qe.registerQuestNpc(204057).addOnTalkEvent(questId);
		qe.registerQuestNpc(204058).addOnTalkEvent(questId);
		qe.registerQuestNpc(204059).addOnTalkEvent(questId);
		qe.registerQuestNpc(801222).addOnTalkEvent(questId);
		qe.registerQuestNpc(801223).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();

		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc) {
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		}
		QuestState qs = player.getQuestStateList().getQuestState(questId);

		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 204146) {
				if (env.getDialog() == DialogAction.QUEST_SELECT) {
					return sendQuestDialog(env, 1011);
				} else {
					return sendQuestStartDialog(env);
				}
			}
		}

		if (qs == null) {
			return false;
		}

		int var = qs.getQuestVarById(0);

		if (qs.getStatus() == QuestStatus.START) {
			PlayerClass playerClass = player.getCommonData().getPlayerClass();
			switch (targetId) {
				case 204056:// Traufnir
					switch (env.getDialog()) {
						case QUEST_SELECT:
							if (playerClass == PlayerClass.GLADIATOR || playerClass == PlayerClass.TEMPLAR) {
								return sendQuestDialog(env, 1352);
							} else {
								return sendQuestDialog(env, 1438);
							}
						case SETPRO1:
							qs.setQuestVarById(0, var + 1);
							updateQuestStatus(env);
							PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
							return true;
						default:
							break;
					}
				case 204057:// Sigyn
					switch (env.getDialog()) {
						case QUEST_SELECT:
							if (playerClass == PlayerClass.ASSASSIN || playerClass == PlayerClass.RANGER) {
								return sendQuestDialog(env, 1693);
							} else {
								return sendQuestDialog(env, 1779);
							}
						case SETPRO1:
							qs.setQuestVarById(0, var + 1);
							updateQuestStatus(env);
							PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
							return true;
						default:
							break;
					}
				case 204058:// Sif
					switch (env.getDialog()) {
						case QUEST_SELECT:
							if (playerClass == PlayerClass.SORCERER || playerClass == PlayerClass.SPIRIT_MASTER) {
								return sendQuestDialog(env, 2034);
							} else {
								return sendQuestDialog(env, 2120);
							}
						case SETPRO1:
							qs.setQuestVarById(0, var + 1);
							updateQuestStatus(env);
							PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
							return true;
						default:
							break;
					}
				case 204059:// Freyr
					switch (env.getDialog()) {
						case QUEST_SELECT:
							if (playerClass == PlayerClass.CLERIC || playerClass == PlayerClass.CHANTER) {
								return sendQuestDialog(env, 2375);
							} else {
								return sendQuestDialog(env, 2461);
							}
						case SETPRO1:
							qs.setQuestVarById(0, var + 1);
							updateQuestStatus(env);
							PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
							return true;
						default:
							break;
					}
				case 801222:
					switch (env.getDialog()) {
						case QUEST_SELECT:
							if (playerClass == PlayerClass.ENGINEER) {
								return sendQuestDialog(env, 2548);
							} else {
								return sendQuestDialog(env, 2568);
							}
						case SETPRO1:
							qs.setQuestVarById(0, var + 1);
							updateQuestStatus(env);
							PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
							return true;
						default:
							break;
					}
				case 801223:
					switch (env.getDialog()) {
						case QUEST_SELECT:
							if (playerClass == PlayerClass.ARTIST) {
								return sendQuestDialog(env, 2633);
							} else {
								return sendQuestDialog(env, 2653);
							}
						case SETPRO1:
							qs.setQuestVarById(0, var + 1);
							updateQuestStatus(env);
							PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
							return true;
						default:
							break;
					}
				case 204146:
					switch (env.getDialog()) {
						case QUEST_SELECT:
							if (var == 1) {
								return sendQuestDialog(env, 2716);
							} else if (var == 2) {
								return sendQuestDialog(env, 3057);
							} else if (var == 3) {
								if (player.getCommonData().getDp() < 4000) {
									return sendQuestDialog(env, 3484);
								} else {
									return sendQuestDialog(env, 3398);
								}
							} else if (var == 4) {
								if (player.getCommonData().getDp() < 4000) {
									return sendQuestDialog(env, 3825);
								} else {
									return sendQuestDialog(env, 3739);
								}
							}
						case SELECT_QUEST_REWARD:
							if (var == 3) {
								playQuestMovie(env, 137);
								player.getCommonData().setDp(0);
								qs.setStatus(QuestStatus.REWARD);
								updateQuestStatus(env);
								return sendQuestDialog(env, 5);
							} else if (var == 4) {
								playQuestMovie(env, 137);
								player.getCommonData().setDp(0);
								qs.setStatus(QuestStatus.REWARD);
								updateQuestStatus(env);
								return sendQuestDialog(env, 5);
							} else {
								return this.sendQuestEndDialog(env);
							}
						case SETPRO2:
							qs.setQuestVarById(0, var + 1);
							updateQuestStatus(env);
							return sendQuestDialog(env, 3057);
						case SETPRO4:
							qs.setQuestVarById(0, 3);
							updateQuestStatus(env);
							PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
							return true;
						case SETPRO5:
							qs.setQuestVarById(0, 4);
							updateQuestStatus(env);
							PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
							return true;
						default:
							break;
					}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 204146) {
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}
