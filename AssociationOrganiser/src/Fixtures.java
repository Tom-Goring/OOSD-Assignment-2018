public class Fixtures {
//Woah //you made me merge u bastard

    // TODO: finish generateFixtures
    static void generateFixtures() {

        // Every team plays 2 matches against every other team - there should be n(n-1) matches (if my maths is ok)

        // first obtain all teams
        for (Team team_outer : Team.getTeamList()) {

            // iterate over every team, once per team - ignore when passing over self
            for (Team team_inner : Team.getTeamList()) {

                // create a match every time
                if (team_inner.getTeamID() != team_outer.getTeamID()) {

                    Match.createMatch(team_inner.getTeamName(), team_outer.getTeamName());
                }
            }
        }
    }
}
