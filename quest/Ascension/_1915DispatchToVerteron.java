/**
 *  Project Q is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Project Q is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License along with Project Q. If not, see <http://www.gnu.org/licenses/>.
 */

package quest.Ascension;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _1915DispatchToVerteron extends QuestHandler {

	private final static int questId = 1915;

	public _1915DispatchToVerteron() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerOnLevelUp(questId);
		qe.registerQuestNpc(203726).addOnTalkEvent(questId);
		qe.registerQuestNpc(203097).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null) {
			return false;
		}

		int var = qs.getQuestVarById(0);
		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc) {
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		}
		if (qs.getStatus() == QuestStatus.START) {
			switch (targetId) {
				case 203726: { // Polyidus
					switch (env.getDialog()) {
						case QUEST_SELECT:
							if (var == 0) {
								return sendQuestDialog(env, 1352);
							}
							break;
						case SETPRO1:
							if (var == 0) {
								qs.setQuestVarById(0, var + 1);
								updateQuestStatus(env);
								TeleportService2.teleportTo(player, 210030000, player.getInstanceId(), 1643f, 1500f, 120f);
								PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
								return true;
							}
						default:
							break;
					}
				}
				case 203097: // Hyacinte
					switch (env.getDialog()) {
						case QUEST_SELECT:
							if (var == 1) {
								qs.setStatus(QuestStatus.REWARD);
								updateQuestStatus(env);
								return sendQuestDialog(env, 2375);
							}
						default:
							break;
					}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 203097) { // Hyacinte
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env);
	}
}