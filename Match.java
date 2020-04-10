/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package footballmatchleague;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Milly
 */
public class Match {

    private static final String DRAWN = "drawn";

    private static final String LOST = "lost";

    private static final String WON = "won";
    private String homeTeam;
    private String awayTeam;
    private int homeScore;
    private int awayScore;


    public Match(final String homeTeam, final String awayTeam, final int homeScore, final int awayScore) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public String getResult() {
        String result = null;

        if (homeScore > awayScore) {
            result = WON;
        } else if (homeScore < awayScore) {
            result = LOST;
        } else if (homeScore == awayScore) {
            result = DRAWN;
        }
        return result;
    }
}
