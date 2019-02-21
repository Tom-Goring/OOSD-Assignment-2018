package model;

import DB.DatabaseManager;

import java.util.ArrayList;

public class Fixtures {

    private ArrayList<Match> matchList;

    public Fixtures() {

        matchList = new ArrayList<>();
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
