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

                    Match.createMatch(team_inner.getTeamID(), team_outer.getTeamID());
                }

                // fill in information about who plays later -> see page 4 spec
                // then create the 5 sets for each game - should thus be 10n(n-1) sets total
                //then create the 3 games per set - so 30n(n-1) games total (wowsers)
            }
        }
    }
}
