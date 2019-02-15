package DB;

import java.util.ArrayList;

public class Fixtures {

    static void generateFixtures() {

        ArrayList<Team> teamList = DatabaseManager.Team.getTeamList();
        ArrayList<Match> matchList;

        for (Team homeTeam: teamList) {

            for (Team awayTeam: teamList) {

                if (homeTeam != awayTeam) {

                    DatabaseManager.Match.sendNewMatchToDB(new Match(homeTeam, awayTeam));
                }
            }
        }
    }
}
