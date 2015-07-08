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
package custom.HeroCloak;

import l2tserver.gameserver.model.actor.L2Npc;
import l2tserver.gameserver.model.actor.instance.L2PcInstance;
import l2tserver.gameserver.model.olympiad.Olympiad;
import l2tserver.gameserver.model.quest.Quest;
import l2tserver.gameserver.model.quest.QuestState;

public class HeroCloak extends Quest
{
	private final static int[] npcIds =
	{
		31690,31769,31770,31771,31772
	};

	private final static int HERO_CLOAK = 30372;
	private final static int GLORIOUS_CLOAK = 30373;
	
	public HeroCloak(int questId, String name, String descr)
	{
		super(questId, name, descr);
		for (int i : npcIds)
		{
			addStartNpc(i);
			addTalkId(i);
		}
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		if (st == null)
			st = newQuestState(player);
		
		if (player.isHero())
		{
			if (player.getInventory().getItemByItemId(HERO_CLOAK) == null)
				st.giveItems(HERO_CLOAK, 1);
			else
				htmltext = "already_have_cloak.htm";
		}
		else
		{
			int heroRank = Olympiad.getInstance().getPosition(player);
			if (heroRank > 0 && heroRank <= 3)
			{
				if (player.getInventory().getItemByItemId(GLORIOUS_CLOAK) == null)
					st.giveItems(GLORIOUS_CLOAK, 1);
				else
					htmltext = "already_have_cloak.htm";
			}
			else
				htmltext = "no_hero.htm";
		}
		
		st.exitQuest(true);
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new HeroCloak(-1, "HeroCloak", "custom");
	}
}