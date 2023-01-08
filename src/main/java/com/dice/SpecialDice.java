package com.dice;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import net.runelite.api.Item;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;


import java.util.Map;

@Getter
public enum SpecialDice
{
    // FISH
    SHRIMP(DiceType.FISHING, "Shrimp", ItemID.RAW_SHRIMPS),
    LOBSTER(DiceType.FISHING, "Lobster", ItemID.RAW_LOBSTER),
    MONKFISH(DiceType.FISHING, "Monkfish", ItemID.RAW_MONKFISH),
    SALMON(DiceType.FISHING, "Salmon", ItemID.RAW_SALMON),
    LAVA_EEL(DiceType.FISHING, "Lava eel", ItemID.LAVA_EEL),
    BARB_FISH(DiceType.FISHING, "Sturgeon", ItemID.LEAPING_STURGEON),
    ANGLERFISH(DiceType.FISHING, "Anglerfish", ItemID.RAW_ANGLERFISH),
    MINNOW(DiceType.FISHING, "Minnow", ItemID.MINNOW),
    HARPOONFISH(DiceType.FISHING, "Harpoonfish", ItemID.RAW_HARPOONFISH),
    INFERNAL_EEL(DiceType.FISHING, "Infernal Eel", ItemID.INFERNAL_EEL),
    KARAMBWAN(DiceType.FISHING, "Karambwan", ItemID.RAW_KARAMBWAN),
    KARAMBWANJI(DiceType.FISHING, "Karambwanji", ItemID.KARAMBWANJI),
    SACRED_EEL(DiceType.FISHING, "Sacred eel", ItemID.SACRED_EEL),
    CAVE_EEL(DiceType.FISHING, "Frog spawn", ItemID.RAW_CAVE_EEL),
    SLIMY_EEL(DiceType.FISHING, "Slimy eel", ItemID.RAW_SLIMY_EEL),
    DARK_CRAB(DiceType.FISHING, "Dark Crab", ItemID.RAW_DARK_CRAB),
    COMMON_TENCH(DiceType.FISHING, "Common tench", ItemID.COMMON_TENCH),
    QUEST_RUM_DEAL(DiceType.FISHING, "Sluglings", ItemID.SLUGLINGS),
    QUEST_FISHING_CONTEST(DiceType.FISHING, "Giant carp", ItemID.GIANT_CARP),

    // JESTER
    SILLY_JESTER_HAT(DiceType.JESTER, "Hat", ItemID.SILLY_JESTER_HAT),
    SILLY_JESTER_TOP(DiceType.JESTER, "Top", ItemID.SILLY_JESTER_TOP),
    SILLY_JESTER_TIGHTS(DiceType.JESTER, "Tights", ItemID.SILLY_JESTER_TIGHTS),
    SILLY_JESTER_BOOTS(DiceType.JESTER, "Boots", ItemID.SILLY_JESTER_BOOTS),
    A_JESTER_STICK(DiceType.JESTER, "Stick", ItemID.A_JESTER_STICK),
    JESTER_SCARF(DiceType.JESTER, "Scarf", ItemID.JESTER_SCARF),

    // METALS
    BRONZE_BAR(DiceType.METALS, "Bronze", ItemID.BRONZE_BAR),
    IRON_BAR(DiceType.METALS, "Iron", ItemID.IRON_BAR),
    STEEL_BAR(DiceType.METALS, "Steel", ItemID.STEEL_BAR),
    SILVER_BAR(DiceType.METALS, "Silver", ItemID.SILVER_BAR),
    GOLD_BAR(DiceType.METALS, "Gold", ItemID.GOLD_BAR),
    MITHRIL_BAR(DiceType.METALS, "Mithril", ItemID.MITHRIL_BAR),
    ADAMANTITE_BAR(DiceType.METALS, "Adamant", ItemID.ADAMANTITE_BAR),
    RUNITE_BAR(DiceType.METALS, "Rune", ItemID.RUNITE_BAR),
    PERFECT_GOLD_BAR(DiceType.METALS, "Perfect Gold", ItemID.PERFECT_GOLD_BAR),


    // COMBAT SKILLS
    COMBAT_SKILL_ATTACK(DiceType.COMBAT_SKILLS, "Attack", Skill.ATTACK),
    COMBAT_SKILL_STRENGTH(DiceType.COMBAT_SKILLS, "Strength", Skill.STRENGTH),
    COMBAT_SKILL_DEFENCE(DiceType.COMBAT_SKILLS, "Defence", Skill.DEFENCE),
    COMBAT_SKILL_RANGED(DiceType.COMBAT_SKILLS, "Ranged", Skill.RANGED),
    COMBAT_SKILL_MAGIC(DiceType.COMBAT_SKILLS, "Magic", Skill.MAGIC),

    // SKILLS
    SKILL_ATTACK(DiceType.SKILLS, "Attack", Skill.ATTACK),
    SKILL_STRENGTH(DiceType.SKILLS, "Strength", Skill.STRENGTH),
    SKILL_DEFENCE(DiceType.SKILLS, "Defence", Skill.DEFENCE),
    SKILL_RANGED(DiceType.SKILLS, "Ranged", Skill.RANGED),
    SKILL_PRAYER(DiceType.SKILLS, "Prayer", Skill.PRAYER),
    SKILL_MAGIC(DiceType.SKILLS, "Magic", Skill.MAGIC),
    SKILL_HITPOINTS(DiceType.SKILLS, "Hitpoints", Skill.HITPOINTS),
    SKILL_AGILITY(DiceType.SKILLS, "Agility", Skill.AGILITY),
    SKILL_HERBLORE(DiceType.SKILLS, "Herblore", Skill.HERBLORE),
    SKILL_THIEVING(DiceType.SKILLS, "Thieving", Skill.THIEVING),
    SKILL_CRAFTING(DiceType.SKILLS, "Crafting", Skill.CRAFTING),
    SKILL_FLETCHING(DiceType.SKILLS, "Fletching", Skill.FLETCHING),
    SKILL_MINING(DiceType.SKILLS, "Mining", Skill.MINING),
    SKILL_SMITHING(DiceType.SKILLS, "Smithing", Skill.SMITHING),
    SKILL_FISHING(DiceType.SKILLS, "Fishing", Skill.FISHING),
    SKILL_COOKING(DiceType.SKILLS, "Cooking", Skill.COOKING),
    SKILL_FIREMAKING(DiceType.SKILLS, "Firemaking", Skill.FIREMAKING),
    SKILL_WOODCUTTING(DiceType.SKILLS, "Woodcutting", Skill.WOODCUTTING),
    SKILL_RUNECRAFT(DiceType.SKILLS, "Runecraft", Skill.RUNECRAFT),
    SKILL_SLAYER(DiceType.SKILLS, "Slayer", Skill.SLAYER),
    SKILL_FARMING(DiceType.SKILLS, "Farming", Skill.FARMING),
    SKILL_HUNTER(DiceType.SKILLS, "Hunter", Skill.HUNTER),
    SKILL_CONSTRUCTION(DiceType.SKILLS, "Construction", Skill.CONSTRUCTION),


    ;

    private final DiceType diceType;
    private final String name;
    private final int specialSpriteId;
    private final Skill specialSkill;

    SpecialDice(DiceType diceType, String name, int specialSpriteId)
    {
        this.diceType = diceType;
        this.name = name;
        this.specialSpriteId = specialSpriteId;
        this.specialSkill = null;
    }

    SpecialDice(DiceType diceType, String name, Skill specialSkill)
    {
        this.diceType = diceType;
        this.name = name;
        this.specialSpriteId = 0;
        this.specialSkill = specialSkill;
    }
}
