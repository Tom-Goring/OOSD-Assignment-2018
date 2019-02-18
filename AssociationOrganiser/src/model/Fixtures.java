package model;

import DB.DatabaseManager;

import java.util.ArrayList;

public class Fixtures {

    public static void generateFixtures() {

        ArrayList<Team> teamList = DatabaseManager.Team.getAllTeamsFromDatabase();
        ArrayList<Match> matchList;

        if (teamList != null) {

            for (Team homeTeam: teamList) {

                for (Team awayTeam: teamList) {

                    if (homeTeam != awayTeam) {

                        DatabaseManager.Match.sendNewMatchToDB(new Match(homeTeam, awayTeam));
                    }
                }
            }
        }
    }
}
