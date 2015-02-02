package net.wintastic.wincremental;

public class Player {
    private int gold;
    private int wood;

    public Player() {
        gold = 0;
        wood = 0;
    }

    public int getGold() {
        return gold;
    }

    public boolean changeGold(int amount) {
        if (gold + amount < 0)
            return false;
        this.gold += amount;
        return true;
    }

    public int getWood() {
        return wood;
    }

    public boolean changeWood(int amount) {
        if (wood + amount < 0)
            return false;
        this.wood += amount;
        return true;
    }

}
