package model;

import DB.DatabaseManager;

import java.util.ArrayList;

public class Fixtures {

    public static void generateFixtures() {

        ArrayList<Team> teamList = DatabaseManager.DB_Team.getTeamListFromDatabase();
        ArrayList<Match> matchList;

        if (teamList != null) {

            for (Team homeTeam: teamList) {

                for (Team awayTeam: teamList) {

                    if (homeTeam != awayTeam) {

                        DatabaseManager.DB_Match.sendNewMatchToDB(new Match(homeTeam, awayTeam));
                    }
                }
            }
        }
    }
}
