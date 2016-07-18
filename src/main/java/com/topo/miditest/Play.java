package com.topo.miditest;

/**
 * Created by yaf107 on 7/17/16.
 */
public class Play {

    public static void main(String[] args) {
        MusicFile f = new MusicFile("/Users/yaf107/Desktop/MeruKhand.txt");

        PlayMode mode = f.getMode();

        switch (mode.getType()) {
            case Normal:
                new PlayNormal().playSequence(f);
                break;

            case Combination:
                new PlayCombination().playSequence(f);
                break;

            default:
                new PlayNormal().playSequence(f);
                break;

        }

    }
}
