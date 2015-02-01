package net.wintastic.wincremental;

public class Player {
    private int gold;

    public Player() {
        gold = 0;
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
}
