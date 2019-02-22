package model;

import DB.DatabaseManager;

import java.util.ArrayList;

public class Fixtures {

    private ArrayList<Match> matchList;

    public Fixtures() { matchList = new ArrayList<>(); }

    public void addMatch(Match match) { this.matchList.add(match); }

    public Match getMatch(String homeTeamName, String awayTeamName) {

        for (Match match : matchList) {

            if (match.getHomeTeam().getTeamName().equals(homeTeamName) && match.getAwayTeam().getTeamName().equals(awayTeamName)) {

                return match;
            }
        }
        return null;
    }

    public static Fixtures generateFixtures() {

        ArrayList<Team> teamList = DatabaseManager.DB_Team.getTeamListFromDatabase();
        Fixtures fixtures = new Fixtures();

        if (teamList != null) {

            for (Team homeTeam: teamList) {

                for (Team awayTeam: teamList) {

                    if (homeTeam != awayTeam) {

                        fixtures.matchList.add(new Match(homeTeam, awayTeam));
                    }
                }
            }
        }
        else {

            return null;
        }

        return fixtures;
    }

    public ArrayList<Match> getMatchList() { return matchList; }
}
