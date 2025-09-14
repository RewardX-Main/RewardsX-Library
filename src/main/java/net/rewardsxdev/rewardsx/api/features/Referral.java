package net.rewardsxdev.rewardsx.api.features;

import net.rewardsxdev.rewardsx.api.player.RPlayer;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Locale;

public interface Referral {

    /** Function to generate Referral Code **/
    default String generateCode() {
        return RandomStringUtils.randomAlphanumeric(8).toUpperCase(Locale.ROOT);
    }

    /** Invite player to refer
     * @param inviter player to invite
     * @param args Commands args
     * **/
    boolean invite(RPlayer inviter, String[] args);

    /** Accept the referral
     * @param guestPlayer player that accept invite
     * @param args Commands args
     * **/
    boolean accept(RPlayer guestPlayer, String[] args);

    /** View info of referral
     * @param viewer player that perform command or specify name in command
     * @param args Commands args
     * **/
    boolean stats(RPlayer viewer, String[] args);


    /** Function to obtain rewards for host and guest player
     * @param host player that perform the invite
     * @param guest player that accept the referral
     * **/
    void rewardPlayers(RPlayer host, RPlayer guest);
}
