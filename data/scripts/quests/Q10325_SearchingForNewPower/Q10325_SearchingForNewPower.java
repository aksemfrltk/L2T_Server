/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q10325_SearchingForNewPower;

import l2tserver.gameserver.model.actor.L2Npc;
import l2tserver.gameserver.model.actor.instance.L2PcInstance;
import l2tserver.gameserver.model.quest.GlobalQuest;
import l2tserver.gameserver.model.quest.Quest;
import l2tserver.gameserver.model.quest.QuestState;
import l2tserver.gameserver.model.quest.State;

/**
 * @author Pere
 */
public class Q10325_SearchingForNewPower extends Quest
{
	// Quest
	public static String qn = "Q10325_SearchingForNewPower";
	
	// NPC
	private int _gallint = 32980;
	private int _talbot = 32156;
	private int _cindet = 32148;
	private int _black = 32161;
	private int _herz = 32151;
	private int _kincaid = 32159;
	// TODO Kamael
	
	public Q10325_SearchingForNewPower(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(_gallint);
		addTalkId(_gallint);
		addTalkId(_talbot);
		addTalkId(_cindet);
		addTalkId(_black);
		addTalkId(_herz);
		addTalkId(_kincaid);
		// TODO Kamael
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		
		if (st == null)
			return htmltext;
		
		if (npc.getNpcId() == _gallint && event.equalsIgnoreCase("32980-03.htm"))
		{
			st.setState(State.STARTED);
			st.set("cond", "1");
			st.playSound("ItemSound.quest_accept");
			
			htmltext = "32980-0" + (3 + player.getRace().ordinal()) + ".htm";
			
			st.set("cond", String.valueOf(2 + player.getRace().ordinal()));
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		if (npc.getNpcId() == _gallint)
		{
			switch(st.getState())
			{
				case State.CREATED:
					if (canStart(player))
						htmltext = "32980-01.htm";
					else
						htmltext = "32980-00.htm"; // TODO
					break;
				case State.STARTED:
					htmltext = "32980-10.htm"; // TODO
					break;
				case State.COMPLETED:
					htmltext = "32980-11.htm"; // TODO
					break;
			}
			if (st.getInt("cond") > 7)
			{
				htmltext = "32980-09.htm";
				st.unset("cond");
				st.giveItems(57, 12000);
				st.addExpAndSp(3254, 2400);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(false);
				
				// Main quests state
				player.setGlobalQuestFlag(GlobalQuest.STARTING, 6);
			}
		}
		else if (npc.getNpcId() == _talbot && st.getInt("cond") == 2)
		{
			htmltext = "32156-01.htm";
			st.set("cond", "8");
			st.playSound("ItemSound.quest_middle");
		}
		else if (npc.getNpcId() == _cindet && st.getInt("cond") == 3)
		{
			htmltext = "32148-01.htm";
			st.set("cond", "9");
			st.playSound("ItemSound.quest_middle");
		}
		else if (npc.getNpcId() == _black && st.getInt("cond") == 4)
		{
			htmltext = "32161-01.htm";
			st.set("cond", "10");
			st.playSound("ItemSound.quest_middle");
		}
		else if (npc.getNpcId() == _herz && st.getInt("cond") == 5)
		{
			htmltext = "32151-01.htm";
			st.set("cond", "11");
			st.playSound("ItemSound.quest_middle");
		}
		else if (npc.getNpcId() == _kincaid && st.getInt("cond") == 6)
		{
			htmltext = "32159-01.htm";
			st.set("cond", "12");
			st.playSound("ItemSound.quest_middle");
		}
		// TODO Kamael
		return htmltext;
	}
	
	@Override
	public boolean canStart(L2PcInstance player)
	{
		return player.getGlobalQuestFlag(GlobalQuest.STARTING, 5) && player.getLevel() <= 20;
	}

	public static void main(String[] args)
	{
		new Q10325_SearchingForNewPower(10325, qn, "Obtaining information about skills from the Race Master.");
	}
}
