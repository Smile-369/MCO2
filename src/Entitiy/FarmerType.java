package Entitiy;

/**
 * the type of famer and the bonuses that come with it
 */
public class FarmerType {
    public final int bonusEarning;
    public final int seedCostReduc;
    final Player player;
    public int waterBonusLimit;
    public int ferBonusLimit;
    public int level = 0;
    public String title;

    public FarmerType(Player player, String title) {
        this.title = title;
        this.player = player;
        switch (title) {
            case "Registered" -> {
                bonusEarning = 1;
                seedCostReduc = 1;
                waterBonusLimit = 0;
                ferBonusLimit = 0;
                level = 1;
            }
            case "Distinguished" -> {
                bonusEarning = 2;
                seedCostReduc = 2;
                waterBonusLimit = 1;
                ferBonusLimit = 0;
                level = 2;
            }
            case "Legendary" -> {
                bonusEarning = 4;
                seedCostReduc = 3;
                waterBonusLimit = 2;
                ferBonusLimit = 1;
                level = 3;
            }
            default -> {
                bonusEarning = 0;
                seedCostReduc = 0;
                waterBonusLimit = 0;
                ferBonusLimit = 0;
            }
        }
    }

}

