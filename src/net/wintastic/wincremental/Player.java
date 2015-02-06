package net.wintastic.wincremental;

public class Player {

    public static enum ResourceType {
        GOLD, WOOD
    }

    private int gold;
    private int wood;

    public Player() {
        gold = 11156;
        wood = 568722567;
    }

    public int getGold() {
        return gold;
    }

    public int getWood() {
        return wood;
    }

    public boolean changeResource(ResourceType type, int amount) {
        switch (type) {
            case GOLD:
                if (gold + amount < 0)
                    return false;
                this.gold += amount;
                return true;
            case WOOD:
                if (wood + amount < 0)
                    return false;
                this.wood += amount;
                return true;
        }
        return true;
    }

}
