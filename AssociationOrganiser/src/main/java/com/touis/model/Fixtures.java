package main.java.com.touis.model;

import main.java.com.touis.DB.DatabaseManager;

import java.util.ArrayList;

public class Fixtures {

    public static void generateFixtures() {

        ArrayList<Team> teamList = DatabaseManager.DB_Team.getAllTeamsFromDatabase();
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
