package com.dice;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import net.runelite.api.ItemID;

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
    ;

    private final DiceType diceType;
    private final String name;
    private final int specialSpriteId;

    SpecialDice(DiceType diceType, String name, int specialSpriteId)
    {
        this.diceType = diceType;
        this.name = name;
        this.specialSpriteId = specialSpriteId;
    }
}
