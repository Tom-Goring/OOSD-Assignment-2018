package DB;

import java.util.ArrayList;

public class Match {

    protected class Set {

        public int getSetNumber() {
            return setNumber;
        }

        public void setSetNumber(int setNumber) {
            this.setNumber = setNumber;
        }

        public int getWinningTeam() {
            return winningTeam;
        }

        public void setWinningTeam(int winningTeam) {
            this.winningTeam = winningTeam;
        }

        public Player getHomeTeamPlayer() {
            return homeTeamPlayer;
        }

        public void setHomeTeamPlayer(Player homeTeamPlayer) {
            this.homeTeamPlayer = homeTeamPlayer;
        }

        public Player getAwayTeamPlayer() {
            return awayTeamPlayer;
        }

        public void setAwayTeamPlayer(Player awayTeamPlayer) {
            this.awayTeamPlayer = awayTeamPlayer;
        }

        public ArrayList<Game> getGames() {
            return games;
        }

        protected class Game {

            // Game attributes
            private int gameNumber;
            private int homeTeamScore;
            private int awayTeamScore;
            private Team winningTeam;

            public int getGameNumber() {
                return gameNumber;
            }

            public void setGameNumber(int gameNumber) {
                this.gameNumber = gameNumber;
            }

            public int getHomeTeamScore() {
                return homeTeamScore;
            }

            public void setHomeTeamScore(int homeTeamScore) {
                this.homeTeamScore = homeTeamScore;
            }

            public int getAwayTeamScore() {
                return awayTeamScore;
            }

            public void setAwayTeamScore(int awayTeamScore) {
                this.awayTeamScore = awayTeamScore;
            }

            public Team getWinningTeam() {
                return winningTeam;
            }

            public void setWinningTeam(Team winningTeam) {
                this.winningTeam = winningTeam;
            }
        }

        // Set attributes
        private int setNumber;
        private int winningTeam;
        private Player homeTeamPlayer;
        private Player awayTeamPlayer;
        private ArrayList<Game> games;

        public Set() {
            this.games = new ArrayList<>();
        }

        public Set(int setNumber) {

            this.setNumber = setNumber;
            this.games = new ArrayList<>();
        }
    }

    private class Double extends Set {

        int setNumber = 5;
        Player homeTeamPlayer2;
        Player awayTeamPlayer2;
    }

    private Team homeTeam;
    private Team awayTeam;
    private Team winningTeam;

    private Player homeTeamPlayer1;
    private Player homeTeamPlayer2;
    private Player awayTeamPlayer1;
    private Player getAwayTeamPlayer2;

    private ArrayList<Set> sets;

    public Match(Team homeTeam, Team awayTeam) {

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.sets = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.sets.add(new Set(i + 1));
        }
        this.sets.add(new Double());
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Team getWinningTeam() {
        return winningTeam;
    }

    public void setWinningTeam(Team winningTeam) {
        this.winningTeam = winningTeam;
    }

    public Player getHomeTeamPlayer1() {
        return homeTeamPlayer1;
    }

    public void setHomeTeamPlayer1(Player homeTeamPlayer1) {
        this.homeTeamPlayer1 = homeTeamPlayer1;
    }

    public Player getHomeTeamPlayer2() {
        return homeTeamPlayer2;
    }

    public void setHomeTeamPlayer2(Player homeTeamPlayer2) {
        this.homeTeamPlayer2 = homeTeamPlayer2;
    }

    public Player getAwayTeamPlayer1() {
        return awayTeamPlayer1;
    }

    public void setAwayTeamPlayer1(Player awayTeamPlayer1) {
        this.awayTeamPlayer1 = awayTeamPlayer1;
    }

    public Player getGetAwayTeamPlayer2() {
        return getAwayTeamPlayer2;
    }

    public void setGetAwayTeamPlayer2(Player getAwayTeamPlayer2) {
        this.getAwayTeamPlayer2 = getAwayTeamPlayer2;
    }

    public ArrayList<Set> getSets() {
        return sets;
    }

    public void setSets(ArrayList<Set> sets) {
        this.sets = sets;
    }
}