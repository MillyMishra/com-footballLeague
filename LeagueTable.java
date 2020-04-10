/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package footballmatchleague;

/**
 *
 * @author Milly
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LeagueTable {

    private static final String LOST = "lost";
    private static final String WON = "won";
    private Set<LeagueTableEntry> leagueTableEntries = new HashSet<LeagueTableEntry>();

    public LeagueTable(final List<Match> matches) {
        generateLeagueTable(matches);
    }

    /**
     * Get the ordered list of league table entries for this league table.
     *     
* @return
     */
    public List<LeagueTableEntry> getTableEntries() {
        // Sorting on multiple fields using Java 8 lambda expressions
        Comparator<LeagueTableEntry> groupByComparator = Comparator.comparing(LeagueTableEntry::getPoints).thenComparing(LeagueTableEntry::getGoalDifference).thenComparing(LeagueTableEntry::getGoalsFor).thenComparing(LeagueTableEntry::getTeamName);
        List<LeagueTableEntry> leagueEntries = new ArrayList<>(leagueTableEntries);
        leagueEntries.sort(groupByComparator.reversed());
        return leagueEntries;

    }

    private void generateLeagueTable(final List<Match> matches) {
        for (Match match : matches) {
            String matchResult = match.getResult();

            LeagueTableEntry leagueTableEntry = null;

            if (!checkIfTeamExistsInTable(match.getHomeTeam())) {
                leagueTableEntry = createNewLeagueEntry(match, matchResult);
                leagueTableEntries.add(leagueTableEntry);
            } else {
                retrieveAndUpdateExistingLeagueEntry(match, matchResult);
            }

        }
    }

    private LeagueTableEntry createNewLeagueEntry(Match match, String matchResult) {
        int played = 1;
        int won = 0;
        int drawn = 0;
        int lost = 0;
        int goalsFor = match.getHomeScore();
        int goalsAgainst = match.getAwayScore();
        int goalDifference = goalsFor - goalsAgainst;
        int points = 0;

        // Populate match points as per the result of the match
        if (matchResult.equals(WON)) {
            won = 1;
            // Assume 10 points are assigned on winning a match
            points = 3;
        } else if (matchResult.equals(LOST)) {
            lost = 1;
        } else {     // case for drawn result
            drawn = 1;
            // Assume one point is assigned when match is drawn
            points = 1;
        }
        return new LeagueTableEntry(match.getHomeTeam(), played, won, drawn, lost, goalsFor, goalsAgainst, goalDifference, points);

    }

    private void retrieveAndUpdateExistingLeagueEntry(Match match, String matchResult) {
        // update an exist League Table Entry
        LeagueTableEntry leagueTableEntry = leagueTableEntries.stream().filter(leagueEntry -> leagueEntry.getTeamName().equalsIgnoreCase(match.getHomeTeam())).findFirst().get();

        if (matchResult.equals(WON)) {
            leagueTableEntry.setPoints(leagueTableEntry.getPoints() + 3);
            leagueTableEntry.setWon(leagueTableEntry.getWon() + 1);
        } else if (matchResult.equals(LOST)) {
            leagueTableEntry.setLost(leagueTableEntry.getLost() + 1);
        } else {
            leagueTableEntry.setPoints(leagueTableEntry.getPoints() + 1);
            leagueTableEntry.setDrawn(leagueTableEntry.getDrawn() + 1);
        }
        leagueTableEntry.setPlayed(leagueTableEntry.getPlayed() + 1);
        leagueTableEntry.setGoalsFor(leagueTableEntry.getGoalsFor() + match.getHomeScore());
        leagueTableEntry.setGoalsAgainst(leagueTableEntry.getGoalsAgainst() + match.getAwayScore());
        leagueTableEntry.setGoalDifference(leagueTableEntry.getGoalDifference() + (match.getHomeScore() - match.getAwayScore()));
    }

    public boolean checkIfTeamExistsInTable(String teamName) {
        return leagueTableEntries.stream().anyMatch(leagueEntry -> leagueEntry.getTeamName().equalsIgnoreCase(teamName));
    }
}
