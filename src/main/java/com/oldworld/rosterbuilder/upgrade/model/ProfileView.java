package com.oldworld.rosterbuilder.upgrade.model;

import com.oldworld.rosterbuilder.upgrade.UpgradeProfile;

public record ProfileView(
        String name,
        String m,
        String ws,
        String bs,
        String s,
        String t,
        String w,
        String i,
        String a,
        String ld,
        String basicSave,
        String wardSave
) {

    public static ProfileView from(UpgradeProfile profile) {
        return new ProfileView(profile.getName(), profile.getM(), profile.getWs(), profile.getBs(), profile.getS(),
                profile.getT(), profile.getW(), profile.getI(), profile.getA(), profile.getLd(),
                profile.getBasicSave(), profile.getWardSave());
    }
}
