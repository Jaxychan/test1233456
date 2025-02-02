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

package quest.fort_tiamat;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.SystemMessageId;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;

/**
 * @author Cheatkiller
 *
 */
public class _10070KaisinelsCommand extends QuestHandler {

	private final static int questId = 10070;

	public _10070KaisinelsCommand() {
		super(questId);
	}

	@Override
	public void register() {
		int[] npcs = { 205579, 798600, 205842, 730628, 730625, 800386, 800431, 800352, 730691 };
		for (int npc : npcs) {
			qe.registerQuestNpc(npc).addOnTalkEvent(questId);
		}
		qe.registerOnLevelUp(questId);
		qe.registerOnEnterWorld(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();
		int targetId = env.getTargetId();

		if (qs == null) {
			return false;
		}

		int var = qs.getQuestVarById(0);

		if (qs.getStatus() == QuestStatus.START) {
			if (targetId == 205579) {
				switch (dialog) {
					case QUEST_SELECT: {
						if (var == 0) {
							return sendQuestDialog(env, 1011);
						} else if (var == 2) {
							return sendQuestDialog(env, 1693);
						}
					}
					case SETPRO1: {
						return defaultCloseDialog(env, 0, 1); // 1
					}
					case SETPRO3: {
						removeQuestItem(env, 182213241, 1);
						return defaultCloseDialog(env, 2, 3);
					}
					default:
						break;
				}
			} else if (targetId == 798600) {
				switch (dialog) {
					case QUEST_SELECT: {
						if (var == 1) {
							return sendQuestDialog(env, 1352);
						}
					}
					case SETPRO2: {
						giveQuestItem(env, 182213241, 1);
						return defaultCloseDialog(env, 1, 2);
					}
					default:
						break;
				}
			} else if (targetId == 205842) {
				switch (dialog) {
					case QUEST_SELECT: {
						if (var == 3) {
							return sendQuestDialog(env, 2034);
						}
					}
					case SETPRO4: {
						return defaultCloseDialog(env, 3, 4);
					}
					default:
						break;
				}
			} else if (targetId == 730628) {
				switch (dialog) {
					case USE_OBJECT: {
						if (var == 4) {
							return sendQuestDialog(env, 2375);
						}
					}
					case SETPRO5: {
						WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(300490000);
						InstanceService.registerPlayerWithInstance(newInstance, player);
						TeleportService2.teleportTo(player, 300490000, newInstance.getInstanceId(), 497, 507, 241);
						changeQuestStep(env, 4, 5, false);
						return closeDialogWindow(env);
					}
					default:
						break;
				}
			} else if (targetId == 730691) {
				switch (dialog) {
					case USE_OBJECT: {
						if (var == 5) {
							return sendQuestDialog(env, 2716);
						}
					}
					case SETPRO6: {
						TeleportService2.teleportTo(player, 300490000, 549f, 525f, 417f, (byte) 0, TeleportAnimation.BEAM_ANIMATION);
						QuestService.addNewSpawn(300490000, player.getInstanceId(), 800386, 458f, 514f, 417f, (byte) 119);
						changeQuestStep(env, 5, 6, false);
						return closeDialogWindow(env);
					}
					default:
						break;
				}
			} else if (targetId == 800386) {
				switch (dialog) {
					case QUEST_SELECT: {
						if (var == 6) {
							return sendQuestDialog(env, 3057);
						}
					}
					case SETPRO7: {
						playQuestMovie(env, 493);
						env.getVisibleObject().getController().delete();
						QuestService.addNewSpawn(300490000, player.getInstanceId(), 800385, 458f, 514f, 417f, (byte) 119);
						QuestService.addNewSpawn(300490000, player.getInstanceId(), 800431, 502f, 526f, 417f, (byte) 70);
						QuestService.addNewSpawn(300490000, player.getInstanceId(), 800352, 499f, 500f, 417f, (byte) 53);
						return defaultCloseDialog(env, 6, 7);
					}
					default:
						break;
				}
			} else if (targetId == 800431) {
				switch (dialog) {
					case QUEST_SELECT: {
						if (var == 7) {
							return sendQuestDialog(env, 3398);
						}
					}
					case SETPRO8: {
						env.getVisibleObject().getController().delete();
						return defaultCloseDialog(env, 7, 8);
					}
					default:
						break;
				}
			} else if (targetId == 800352) {
				switch (dialog) {
					case QUEST_SELECT: {
						if (var == 8) {
							return sendQuestDialog(env, 3739);
						}
					}
					case SET_SUCCEED: {
						env.getVisibleObject().getController().delete();
						return defaultCloseDialog(env, 8, 9, true, false);
					}
					default:
						break;
				}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 205842) {
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
	public boolean onEnterWorldEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);

		if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (player.getWorldId() != 300490000) {
				int var = qs.getQuestVarById(0);
				if (var >= 5) {
					qs.setQuestVar(3);
					updateQuestStatus(env);
					PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(SystemMessageId.QUEST_FAILED_$1, DataManager.QUEST_DATA.getQuestById(questId)
							.getName()));
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 10064);
	}
}
