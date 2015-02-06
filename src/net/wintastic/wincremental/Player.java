package net.wintastic.wincremental;

import java.math.BigInteger;

public class Player {

    public static enum ResourceType {
        GOLD, WOOD
    }

    private BigInteger gold;
    private BigInteger wood;

    public Player() {
        gold = BigInteger.ZERO;
        wood = BigInteger.ZERO;
    }

    public BigInteger getGold() {
        return gold;
    }

    public BigInteger getWood() {
        return wood;
    }

    public boolean changeResource(ResourceType type, BigInteger amount) {
        switch (type) {
            case GOLD:
                if (gold.add(amount).compareTo(BigInteger.ZERO) < 0)
                    return false;
                this.gold = this.gold.add(amount);
                return true;
            case WOOD:
                if (wood.add(amount).compareTo(BigInteger.ZERO) < 0)
                    return false;
                this.wood = this.wood.add(amount);
                return true;
        }
        return true;
    }

}
