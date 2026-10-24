package com.oldworld.rosterbuilder.upgrade;

public enum ModifierOperation {

    SET(0) {
        @Override
        public int apply(int current, int value) {
            return value;
        }
    },

    ADD(1) {
        @Override
        public int apply(int current, int value) {
            return current + value;
        }
    },

    IMPROVE(1) {
        @Override
        public int apply(int current, int value) {
            return current - value;
        }
    },

    BEST_OF(2) {
        @Override
        public int apply(int current, int value) {
            return Math.min(current, value);
        }
    };

    private final int phase;

    ModifierOperation(int phase) {
        this.phase = phase;
    }

    public int getPhase() {
        return phase;
    }

    public abstract int apply(int current, int value);
}
