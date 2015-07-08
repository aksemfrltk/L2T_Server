package transformations;

import l2tserver.gameserver.datatables.SkillTable;
import l2tserver.gameserver.instancemanager.TransformationManager;
import l2tserver.gameserver.model.L2Transformation;

public class OnyxBeast extends L2Transformation
{
	private static final int[] SKILLS = {584,585,5491,619};
	public OnyxBeast()
	{
		// id, colRadius, colHeight
		super(1, 14, 14.5);
	}
	
	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 1 || getPlayer().isCursedWeaponEquipped())
			return;
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Power Claw
		getPlayer().addSkill(SkillTable.getInstance().getInfo(584, 1), false);
		// Fast Moving
		getPlayer().addSkill(SkillTable.getInstance().getInfo(585, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	@Override
	public void onUntransform()
	{
		removeSkills();
	}
	
	public void removeSkills()
	{
		// Power Claw
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(584, 1), false);
		// Fast Moving
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(585, 1), false, false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new OnyxBeast());
	}
}
