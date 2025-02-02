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

package quest.pandaemonium;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * Put the Inactivated Surkana inside the Balaur Material Converter (730212).
 * Talk with Chopirunerk (798358).
 *
 * @author Bobobear
 */
public class _4920MakingTheActivatedSurkana extends QuestHandler {

	private final static int questId = 4920;

	public _4920MakingTheActivatedSurkana() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(798358).addOnQuestStart(questId); // Chopirunerk
		qe.registerQuestNpc(798358).addOnTalkEvent(questId); // Chopirunerk
		qe.registerQuestNpc(730212).addOnTalkEvent(questId); // Balaur Material
																// Converter
		qe.registerQuestItem(182207100, questId);
		qe.registerQuestItem(182207101, questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();
		int targetId = env.getTargetId();
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 798358) {
				switch (dialog) {
					case QUEST_SELECT: {
						return sendQuestDialog(env, 1011);
					}
					case QUEST_ACCEPT_1: {
						giveQuestItem(env, 182207100, 1);
					}
					default:
						return sendQuestStartDialog(env);
				}
			}
		} else if (qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			switch (targetId) {
				case 730212: { // Balaur Material Converter
					switch (dialog) {
						case USE_OBJECT: {
							if ((var == 0) && player.getInventory().getItemCountByItemId(182207100) > 0) {
								return useQuestObject(env, 0, 1, true, 0, 182207101, 1, 182207100, 1, 0, false);
							}
						}
						default:
							break;
					}
					break;
				}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 798358) { // Chopirunerk
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}
