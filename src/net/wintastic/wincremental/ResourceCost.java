package net.wintastic.wincremental;

import java.math.BigInteger;

public class ResourceCost {

    BigInteger woodCost;
    BigInteger goldCost;

    public ResourceCost() {
        this(BigInteger.ZERO, BigInteger.ZERO);
    }

    public ResourceCost(BigInteger woodCost, BigInteger goldCost) {
        this.woodCost = woodCost;
        this.goldCost = goldCost;
    }

}
