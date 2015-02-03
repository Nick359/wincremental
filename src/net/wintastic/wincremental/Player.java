package net.wintastic.wincremental;

public class Player {

    public static enum ResourceType {
        GOLD, WOOD
    }

    private int gold;
    private int wood;

    public Player() {
        gold = 0;
        wood = 0;
    }

    public int getGold() {
        return gold;
    }

    public int getWood() {
        return wood;
    }

    public boolean changeResource(ResourceType type, int amount) {
        if (type == ResourceType.GOLD) {
            if (gold + amount < 0)
                return false;
            this.gold += amount;
            return true;

        } else if (type == ResourceType.WOOD) {
            if (wood + amount < 0)
                return false;
            this.wood += amount;
            return true;
        }
        return true;
    }

}
