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
package ai.individual.GenesisStatues;

import java.util.HashMap;
import java.util.Map;

import l2tserver.gameserver.ai.CtrlIntention;
import l2tserver.gameserver.datatables.SkillTable;
import l2tserver.gameserver.model.L2Skill;
import l2tserver.gameserver.model.actor.L2Npc;
import l2tserver.gameserver.model.actor.instance.L2MonsterInstance;
import l2tserver.gameserver.model.actor.instance.L2PcInstance;
import ai.group_template.L2AttackableAIScript;

/**
 * @author LasTravel
 * 
 * Genesis Statues AI
 */

public class GenesisStatues extends L2AttackableAIScript
{
	private static final int[]	_statues 			= {33138, 33139, 33140};
	private static final int[]	_keepers			= {23038, 23039, 23040};
	private static final L2Skill _blessingOfGarden	= SkillTable.getInstance().getInfo(14200, 1);
	private static Map<Integer, Boolean> _spawns	= new HashMap<Integer, Boolean>(3);

	public GenesisStatues(int id, String name, String descr)
	{
		super(id, name, descr);
		
		for (int statues : _statues)
		{
			addTalkId(statues);
			addStartNpc(statues);
			
			_spawns.put(statues, false);
		}
		
		for (int keepers : _keepers)
		{
			addKillId(keepers);
		}
	}

	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		if (_spawns.get(npc.getNpcId()))
			return npc.getNpcId() + "-no.html";
		else
		{
			_spawns.put(npc.getNpcId(), true);
			
			L2MonsterInstance angelStatue = (L2MonsterInstance) addSpawn(npc.getNpcId() - 10100, player.getX(), player.getY(), player.getZ(), 0, false, 0, false);
			angelStatue.setTarget(player);
			angelStatue.addDamageHate(player, 500, 99999);
			angelStatue.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
		}
		return super.onTalk(npc, player);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		_spawns.put(npc.getNpcId() + 10100, false);
		
		_blessingOfGarden.getEffects(killer, killer);
		
		return super.onKill(npc, killer, isPet);
	}
	
	public static void main(String[] args)
	{
		new GenesisStatues(-1, "GenesisStatues", "ai/individual");
	}
}
