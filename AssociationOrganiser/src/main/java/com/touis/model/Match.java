package main.java.com.touis.model;

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

        public Game getGame(int gameNumber) {
            return this.games.get(gameNumber - 1);
        }

        protected class Game {

            public Game(int gameNumber) {

                this.gameNumber = gameNumber + 1;
            }

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

            this.setNumber = setNumber + 1;
            this.games = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                this.games.add(new Game(i));
            }
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
    private Player awayTeamPlayer2;

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
        return this.homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return this.awayTeam;
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

    public void setAwayTeamPlayer1(Player awayTeamPlayer1) { this.awayTeamPlayer1 = awayTeamPlayer1; }

    public Player getAwayTeamPlayer2() {
        return this.awayTeamPlayer2;
    }

    public void setAwayTeamPlayer2(Player getAwayTeamPlayer2) {
        this.awayTeamPlayer2 = getAwayTeamPlayer2;
    }

    public Set getSet(int setNumber) { return this.sets.get(setNumber - 1); }

    public String getGameScore(int setNumber, int gameNumber) {

        int homeTeamScore = this.sets.get(setNumber - 1).games.get(gameNumber - 1).homeTeamScore;
        int awayTeamScore = this.sets.get(setNumber - 1).games.get(gameNumber - 1).awayTeamScore;

        return this.homeTeam.getTeamName() + ":  " + homeTeamScore + "\n"
                + this.awayTeam.getTeamName() + ": " + awayTeamScore;
    }

    public ArrayList<Player> getMatchPlayers() {

        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(this.homeTeamPlayer1);
        playerList.add(this.homeTeamPlayer2);
        playerList.add(this.awayTeamPlayer1);
        playerList.add(this.awayTeamPlayer2);

        return playerList;
    }
}