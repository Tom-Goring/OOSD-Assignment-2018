package model;

import java.util.ArrayList;

public class Match {

    public class Set {

        public Team getWinningTeam() {
            return winningTeam;
        }

        public void setWinningTeam(Team winningTeam) {
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

        public Game getGame(int index) {

            return this.games.get(index);
        }

        public class Game {

            public Game(int gameNumber) {

                this.gameNumber = gameNumber + 1;
            }

            // Game attributes
            private int gameNumber;
            private int homeTeamScore;
            private int awayTeamScore;
            private Team winningTeam;

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
        private Team winningTeam;
        private Player homeTeamPlayer;
        private Player awayTeamPlayer;
        private ArrayList<Game> games;

        public Set() {
            this.games = new ArrayList<>();
        }

        public Set(int setNumber) {

            this.setNumber = setNumber;
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

        public Double() {

            super.games = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                super.games.add(new Game(i));
            }
        }
    }

    private Team homeTeam;
    private Team awayTeam;
    private Team winningTeam;

    private Player homeTeamPlayer1;
    private Player homeTeamPlayer2;
    private Player awayTeamPlayer1;
    private Player awayTeamPlayer2;

    private ArrayList<Set> sets;

    private int homeTeamSetsWon;
    private int awayTeamSetsWon;

    public Match(Team homeTeam, Team awayTeam) {

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.sets = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.sets.add(new Set(i + 1));
        }
        this.sets.add(new Double());
    }

    public Match() {

        this.sets = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.sets.add(new Set(i + 1));
        }
        this.sets.add(new Double());
    }

    public int getHomeTeamSetsWon() { return homeTeamSetsWon; }

    public int getAwayTeamSetsWon() { return awayTeamSetsWon; }

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

    public Set getSet(int setNumber) { return this.sets.get(setNumber); }

    public int getGameHomeScore(int setNumber, int gameNumber) {

        return this.sets.get(setNumber).games.get(gameNumber).homeTeamScore;
    }

    public int getGameAwayScore(int setNumber, int gameNumber) {

        return this.sets.get(setNumber).games.get(gameNumber).awayTeamScore;
    }

    public ArrayList<Player> getMatchPlayers() {

        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(this.homeTeamPlayer1);
        playerList.add(this.homeTeamPlayer2);
        playerList.add(this.awayTeamPlayer1);
        playerList.add(this.awayTeamPlayer2);

        return playerList;
    }

    public void fillInWinnerFields() {

        int homeTeamSetsWon = 0;
        int awayTeamSetsWon = 0;

        for (int i = 0; i < 5; i++) {

            int homeTeamGamesWon = 0;
            int awayTeamGamesWon = 0;
            for (int j = 0; j < 3; j++) {

                if (this.getSet(i).getGame(j).getHomeTeamScore() > this.getSet(i).getGame(j).getAwayTeamScore()) {

                    this.getSet(i).getGame(j).setWinningTeam(this.getHomeTeam());
                    homeTeamGamesWon++;
                }
                else {

                    this.getSet(i).getGame(j).setWinningTeam(this.getAwayTeam());
                    awayTeamGamesWon++;
                }
            }

            if (homeTeamGamesWon > awayTeamGamesWon) {
                this.getSet(i).setWinningTeam(this.getHomeTeam());
                homeTeamSetsWon++;
            }
            else {
                this.getSet(i).setWinningTeam(this.getAwayTeam());
                awayTeamSetsWon++;
            }
        }
        if (homeTeamSetsWon > awayTeamSetsWon) {

            this.setWinningTeam(this.getHomeTeam());
        }
        else {

            this.setWinningTeam(this.getAwayTeam());
        }

        this.homeTeamSetsWon = homeTeamSetsWon;
        this.awayTeamSetsWon = awayTeamSetsWon;
    }
}