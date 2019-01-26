import java.sql.*;

// kill me what am i doing with my life REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE

// TODO: finish Player class
public class Player {

    private int player_id;
    private String player_name;
    private int team_id;

    //maybe a constructor that auto pulls a player with just the id? or an id with a name?
    public Player(int id, String name, int team_id) {

        this.player_id = id;
        this.player_name = name;
        this.team_id = team_id;
    }

    public Player(String name, int team_id) {

        this.player_name = name;
        this.team_id = team_id;
    }


}
