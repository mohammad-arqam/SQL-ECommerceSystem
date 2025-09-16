import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class project {
    public static Scanner myObj = new Scanner(System.in);
    public static String connectionString = null;
    public static Connection connection = null;

    public static void main(String[] args) {

        connectionString = config();

    }

    public static String config() {
        Properties prop = new Properties();
        String fileName = "auth.cfg";
        try {
            FileInputStream configFile = new FileInputStream(fileName);
            prop.load(configFile);
            configFile.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find config file.");
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Error reading config file.");
            System.exit(1);
        }
        String username = (prop.getProperty("username"));
        String password = (prop.getProperty("password"));

        String connectionUrl = "jdbc:sqlserver://uranium.cs.umanitoba.ca:1433;"
                + "database=cs3380;"
                + "user=" + username + ";"
                + "password=" + password + ";"
                + "encrypt=false;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";

        try {
            connection = DriverManager.getConnection(connectionUrl);

        } catch (Exception e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }

        if (username == null || password == null) {
            System.out.println("Username or password not provided.");
            System.exit(1);
        } else {
            mainPage();
        }

        return connectionUrl;

    }

    public static int getInput(int min, int max, String input) {
        int index = -1;

        try {
            do {
                System.out.print("Input " + input + ": ");
                index = myObj.nextInt();
                myObj.nextLine(); // consumes the new line character

                if (index < min || index > max) {
                    System.out.println("Input a valid index\n");

                }

            } while (index < min || index > max);

        } catch (Exception e) {
            System.out.println("ERROR: Invalid index. Input a valid index");
        }
        return index;

    }

    public static void mainPage() {
        System.out.println("\nWelcome to soccer analytics");
        System.out.println("\nMAIN MENU");
        System.out.println("\n*Match and type the appropriate keys given below to view appropriate categories*");
        System.out.println("\nKEY        category");
        System.out.println("\n1          Club related queries");
        System.out.println("\n2          Player related queries");
        System.out.println("\n3          Manager related queries");
        System.out.println("\n4          Stadium related queries");
        System.out.println("\n5          Drop all tables");
        System.out.println("\n              ***INPUT -1 TO END PROGRAM***\n");
        int min = -1;
        int max = 5;
        int index = getInput(min, max, "index");
        if (index == -1) {
            System.exit(0);
        } else {
            second(index);
        }

    }

    public static void second(int index) {
        int min = 1;
        int max = -1;

        if (index == 1) {
            max = 2;
            System.out.println("\nClub related queries: ");
            System.out.println("\n1          NUMBER OF CLEAN SHEETS BY CLUBS IN A HOME GAME");
            System.out.println("\n2          HOME GOALS vs AWAY GOALS");
            System.out.println("\n3          GET LIST OF ALL CLUBS\n");

        } else if (index == 2) {
            max = 4;
            System.out.println("\nPlayer related queries: \n");
            System.out.println("\nKEY        QUERY\n");
            System.out.println("\n1          NUMBER OF GOALS IN A SPECIFIC SEASON BY A PLAYER");
            System.out.println("\n2          AVERAGE AGE OF PLAYERS OF EACH CLUB");
            System.out.println("\n3          TOP 10 GOAL SCORERS ACROSS ALL CLUBS");
            System.out.println("\n4          PLAYER APPEARANCES");
            System.out.println("\n5          GET A LIST OF 250 PLAYERS\n");

        } else if (index == 3) {
            max = 1;
            System.out.println("\n Manager related queries: ");
            System.out.println("\nKEY        QUERY\n");
            System.out.println("\n1          WINS AND LOSSES UNDER A MANAGER ");
            System.out.println("\n2          GET LIST OF ALL MANAGERS\n");

        } else if (index == 4) {
            max = 1;
            System.out.println("\n Stadium related queries: ");
            System.out.println("\nKEY        QUERY\n");
            System.out.println("\n1          REQUIRED STADIUM CAPACITY\n");

        } else if (index == 5) {
            dropTables();

        }
        System.out.println("\n              *INPUT 0 TO GO BACK TO THE MAIN MENU*\n");
        System.out.println("\n              *INPUT -1 TO END PROGRAM*\n");
        third(index);

    }

    public static void third(int index) {
        int min = -1;
        int max = -1;

        int indexTwo = -1;
        if (index == 1) {
            max = 3;
            indexTwo = getInput(min, max, "index");

            if (indexTwo == 1) {
                cleanSheets();

            } else if (indexTwo == 2) {
                homeVsaway();

            } else if (indexTwo == 3) {
                getClubList();

            } else if (indexTwo == 0) {
                mainPage();
            } else if (indexTwo == -1) {
                System.exit(0);
            }

        } else if (index == 2) {
            max = 5;
            indexTwo = getInput(min, max, "index");

            if (indexTwo == 1) {
                goalByplayer();

            } else if (indexTwo == 2) {
                playersAge();

            } else if (indexTwo == 3) {
                topScorers();
            } else if (indexTwo == 4) {
                playerAppearance();
            } else if (indexTwo == 5) {
                getPlayerList();
            } else if (indexTwo == 0) {
                mainPage();
            } else if (indexTwo == -1) {
                System.exit(0);
            }

        } else if (index == 3) {
            max = 2;
            indexTwo = getInput(min, max, "index");

            if (indexTwo == 1) {
                managerStats();

            } else if (indexTwo == 2) {
                getManagerList();

            } else if (indexTwo == 0) {
                mainPage();
            } else if (indexTwo == -1) {
                System.exit(0);
            }

        } else if (index == 4) {
            max = 1;
            indexTwo = getInput(min, max, "index");

            if (indexTwo == 1) {
                stadiumCapacity();

            } else if (indexTwo == 0) {
                mainPage();
            } else if (indexTwo == -1) {
                System.exit(0);
            }

        }
        mainPage();
        myObj.close();

    }

    public static void cleanSheets() {
        try {

            String sql = "SELECT c.club_id, c.club_name, COUNT(cg.game_id) AS clean_sheets " +
                    "FROM clubs c " +
                    "JOIN club_games cg ON c.club_id = cg.club_id " +
                    "WHERE cg.opponent_goals = 0 " +
                    "GROUP BY c.club_id, c.club_name";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            // Print column headers with tabs
            String cID = "Club ID", cName = "Club Name", cSheets = "Clean Sheets";
            System.out.printf("\n%-15s %35s %35s%n", cID, cName, cSheets);
            System.out.println("-".repeat(100));

            while (resultSet.next()) {
                int clubID = resultSet.getInt("club_ID");
                String clubName = resultSet.getString("club_name");
                if (clubName.length() > 30) {
                    clubName = clubName.substring(0, 30) + "...";
                }
                int cleanSheets = resultSet.getInt("clean_sheets");
                // Format and print each row with tabs
                System.out.printf("\n%-15d %35s %35d%n", clubID, clubName, cleanSheets);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

    }

    public static void homeVsaway() {
        int clubId = getInput(0, 100000, "club ID");
        try {
            String sql = "SELECT  " +
                    "clubs.club_name,SUM(home_club_goals) AS home_goals_total,clubs.club_name, " +
                    "SUM(away_club_goals) AS away_goals_total FROM " +
                    "games,clubs WHERE home_club_id = ? and clubs.club_id=home_club_id group by home_club_id,clubs.club_name;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, clubId);

            ResultSet resultSet = statement.executeQuery();

            String hcName = "Home Club Name", hGoals = "Home Goals Total", aTotal = "Away Goals Total";
            System.out.printf("\n%-30s %20s %20s%n", hcName, hGoals, aTotal);
            System.out.println("-".repeat(75));

            while (resultSet.next()) {
                String homeClubName = resultSet.getString("club_name");
                if (homeClubName.length() > 30) {
                    homeClubName = homeClubName.substring(0, 32) + "...";
                }
                int homeGoalsTotal = resultSet.getInt("home_goals_total");
                int awayGoalsTotal = resultSet.getInt("away_goals_total");
                // Print each row
                System.out.printf("%-30s %20d %20d%n", homeClubName, homeGoalsTotal, awayGoalsTotal);

            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public static void goalByplayer() {
        int playerID = getInput(0, 1000000, "player ID");
        int season = getInput(2019, 2020, "season");
        // String seasonString=Integer.toString(season);
        try {
            String sql = "SELECT " +
                    "p.player_id, ge.game_event_date AS EventDate, p.player_name, COUNT(ge.player_id) AS num_Goals " +
                    "FROM players p " +
                    "JOIN game_events ge ON p.player_id = ge.player_id " +
                    "WHERE ge.game_event_type = 'Goals' " +
                    "AND ge.player_id = ? " +
                    "AND ge.game_event_date LIKE ? " +
                    "GROUP BY p.player_id, ge.game_event_date, p.player_name " +
                    "ORDER BY EventDate ASC;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playerID);
            statement.setString(2, "%" + season + "%");
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            String sn = "Dates", nGoals = "Num Goals";
            System.out.println("\nPlayer Name :  " + resultSet.getString("player_name"));
            System.out.printf("\n%-25s %15s%n", sn, nGoals);
            System.out.println("-".repeat(125));
            System.out.printf("\n%-25s %15d%n", resultSet.getString("EventDate"), resultSet.getInt("num_Goals"));
            while (resultSet.next()) {
                String eventDate = resultSet.getString("EventDate");
                int numGoals = resultSet.getInt("num_Goals");
                System.out.printf("%-25s %15d%n", eventDate, numGoals);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

    }

    public static void playersAge() {
        try {
            String sql = "SELECT clubs.club_name, AVG(DATEDIFF(day, CAST(date_of_birth as date), GetDate()) / 365.25) AS average_age "
                    +
                    "FROM players JOIN clubs ON players.current_club_id = clubs.club_id " +
                    "GROUP BY clubs.club_name;";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Print column headers using tabs

            String cName = "Club Name", cAge = "Average Age";
            System.out.printf("\n%-60s %35s%n", cName, cAge);
            System.out.println("-".repeat(100));
            while (resultSet.next()) {
                String clubName = resultSet.getString("club_name");
                if (clubName.length() > 35) {
                    clubName = clubName.substring(0, 32) + "...";
                }
                double averageAge = resultSet.getDouble("average_age");
                System.out.printf("%-35s %35.2f%n", clubName, averageAge);
            }
            // This is incase we need the full names of the clubs
            /*
             * while (resultSet.next()) {
             * String clubName = resultSet.getString("club_name");
             * double averageAge = resultSet.getDouble("average_age");
             * System.out.printf("%-60s %35.2f%n", clubName, averageAge);
             * // Print each row using tabs for separation
             * //System.out.println(clubName + "\t" + averageAge);
             * }
             */

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

    }

    public static void topScorers() {
        int limit = 10; // Set the limit to 10 for top 10 scorers

        try {
            String sql = "SELECT TOP " + limit +
                    "p.player_id, p.player_name, c.club_name AS clubName, COUNT(ge.player_id) AS num_Goals " +
                    "FROM players p " +
                    "JOIN clubs c ON p.current_club_id = c.club_id " +
                    "JOIN game_events ge ON p.player_id = ge.player_id AND ge.game_event_type = 'Goals' " +
                    "GROUP BY p.player_id, p.player_name, c.club_name " +
                    "ORDER BY num_Goals DESC";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Print column headers using tabs

            String pID = "Player ID", pName = "Player Name", cName = "Club Name", nGoals = "Num Goals";
            System.out.printf("\n%-25s %-35s %-35s %15s%n", pID, pName, cName, nGoals);
            System.out.println("-".repeat(125));
            // Print each row using tabs for separation
            while (resultSet.next()) {
                int playerID = resultSet.getInt("player_id");
                String playerName = resultSet.getString("player_name"); // assuming the column name is "name"
                String clubName = resultSet.getString("clubName");
                int numGoals = resultSet.getInt("num_Goals");
                System.out.printf("%-25d %-35s %-35s %15d%n", playerID, playerName, clubName, numGoals);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public static void playerAppearance() {
        int playerID = getInput(0, 1000000, "player ID");

        try {
            String sql = "SELECT p.player_name, " +
                    "SUM(ISNULL(app.goals, 0)) AS total_goals, " + // converts null to 0
                    "SUM(ISNULL(app.assists, 0)) AS total_assists, " +
                    "SUM(ISNULL(app.red_cards, 0)) AS total_red_cards, " +
                    "SUM(ISNULL(app.yellow_cards, 0)) AS total_yellow_cards, " +
                    "SUM(ISNULL(app.minutes_played, 0)) AS total_minutes_played " +
                    "FROM players p " +
                    "LEFT JOIN appearances app ON p.player_id = app.player_id " +
                    "WHERE p.player_id = ? " +
                    "GROUP BY p.player_name";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playerID);
            ResultSet resultSet = statement.executeQuery();
            String pName = "Player Name", gs = "Goals", asts = "Assists", yCards = "Yellow Cards", rCards = "Red Cards",
                    mPlayed = "Minutes Played";
            System.out.printf("\n%-20s %10s %10s %15s %15s %20s%n", pName, gs, asts, yCards, rCards, mPlayed);
            System.out.println("-".repeat(125));

            while (resultSet.next()) {
                System.out.printf("%-20s %10d %10d %15d %15d %20d%n",
                        resultSet.getString("player_name"),
                        resultSet.getInt("total_goals"),
                        resultSet.getInt("total_assists"),
                        resultSet.getInt("total_yellow_cards"),
                        resultSet.getInt("total_red_cards"),
                        resultSet.getInt("total_minutes_played"));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

    }

    public static void managerStats() {
        System.out.print("Input manager name: ");
        String managerName = "%" + myObj.nextLine() + "%";
        System.out.println(" ");

        try {
            String sql = "SELECT own_manager_name, " +
                    "COUNT(*) AS total_games, " +
                    "SUM(CASE WHEN is_win = 1 THEN 1 ELSE 0 END) AS wins, " + // if is_win is 1 , then adds to the wins
                    "SUM(CASE WHEN is_win != 1 THEN 1 ELSE 0 END) AS losses " + // is is_win is 0, doesnt add to the
                                                                                // losses
                    "FROM club_games " +
                    "WHERE own_manager_name LIKE ? " +
                    "GROUP BY own_manager_name";
            // Frank de Boer
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, managerName);
            ResultSet resultSet = statement.executeQuery();

            String mName = "Manager Name", tGames = "Total Games", ws = "Wins", ls = "Losses";
            System.out.printf("\n%-25s %10s %10s %10s%n", mName, tGames, ws, ls);
            System.out.println("-".repeat(60));

            while (resultSet.next()) {
                managerName = resultSet.getString("own_manager_name");
                int totalGames = resultSet.getInt("total_games");
                int wins = resultSet.getInt("wins");
                int losses = resultSet.getInt("losses");
                // Print each row
                System.out.printf("%-25s %10d %10d %10d%n", managerName, totalGames, wins, losses);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

    }

    public static void stadiumCapacity() {
        int numSeats = getInput(0, 100000, "number of seats required");
        try {
            String sql = "SELECT TOP 5 stadium_name, stadium_seats " +
                    "FROM clubs " +
                    "WHERE stadium_seats > ? " +
                    "ORDER BY stadium_seats ASC; ";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, numSeats);
            ResultSet resultSet = statement.executeQuery();

            // Print column headers using tabs
            // System.out.println("Stadium Name\tStadium Seats");
            String sName = "Stadium Name", sSeats = "Stadium Seats";
            System.out.printf("\n%-40s %-40s%n", sName, sSeats);
            System.out.println("-".repeat(50));

            while (resultSet.next()) {
                String stadiumName = resultSet.getString("stadium_name");
                int stadiumSeats = resultSet.getInt("stadium_seats");
                // Print each row using tabs for separation
                System.out.printf("%-40s %-40s%n", stadiumName, stadiumSeats);
                // System.out.println(stadiumName + "\t" + stadiumSeats);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

    }

    public static void getClubList() {
        try {
            String sql = "SELECT Distinct club_ID, club_name " +
                    "FROM clubs " +
                    "ORDER BY club_ID ASC";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            String cID = "Club ID", cName = "Club Name";
            System.out.printf("\n%-15s %-15s%n", cID, cName);
            System.out.println("-".repeat(50));

            while (resultSet.next()) {
                int clubID = resultSet.getInt("Club_ID");
                String clubName = resultSet.getString("club_name");
                System.out.printf("%-15s %-15s%n", clubID, clubName);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    // Print all Manager - distinct
    public static void getManagerList() {
        try {
            String sql = "SELECT distinct own_manager_name " +
                    "FROM club_games";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Manager Names:\n");

            while (resultSet.next()) {
                String ownManagerName = resultSet.getString("own_manager_name");
                System.out.println(ownManagerName); // print results in columns

            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public static void getPlayerList() {
        try {
            String sql = "SELECT distinct TOP 250 player_id, player_name " +
                    "FROM players ORDER BY player_ID ASC";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            String pID = "player_id", pName = "player_name";
            System.out.printf("\n%-15s %-15s%n", pID, pName);
            System.out.println("-".repeat(50));

            while (resultSet.next()) {
                String playerID = resultSet.getString("player_id");
                String firstName = resultSet.getString("player_name");
                System.out.printf("%-15s %-15s%n", playerID, firstName);

            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public static void dropTables() {
        try {

            String dropTablesSQL = "DROP TABLE IF EXISTS game_lineups;" +
                    "DROP TABLE IF EXISTS game_events;" +
                    "DROP TABLE IF EXISTS club_games;" +
                    "DROP TABLE IF EXISTS appearances;" +
                    "DROP TABLE IF EXISTS games;" +
                    "DROP TABLE IF EXISTS players;" +
                    "DROP TABLE IF EXISTS clubs;" +
                    "DROP TABLE IF EXISTS competitions;";

            PreparedStatement statement = connection.prepareStatement(dropTablesSQL);

            statement.executeUpdate();

            System.out.println("Tables dropped successfully.");

            statement.close();
        } catch (SQLException e) {
            // Handle any SQL exceptions
            System.out.println("Error dropping tables: " + e.getMessage());
        }
    }

}