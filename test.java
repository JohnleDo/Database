// File: Menu.java
//
// This program illustrates the use of a menu, which would be the basis
// for constructing a larger program by adding more options, where each
// option is handled by a separate function.
//
import java.sql.*;
import java.util.Scanner;

public class Menu
{
    public static void main(String[] args)
    {
        int choice;
        int tablechoice;
        Connection conn = null;
        try
        {
            // Step 1: connect to the database server using a connection string.
            String host = "cslab-db.cs.wichita.edu";
            int port = 3306;
            String database = "dbuser21_database";
            String user = "dbuser21";
            String password = "cZXzvxNLKNyS";
            String url = String.format("jdbc:mariadb://%s:%s/%s?user=%s&password=%s", host, port, database, user, password);
            conn = DriverManager.getConnection(url);

            choice = PrintMenuAndGetResponse();

            switch (choice)
            {
                case 1:
                        tablechoice = PrintTableandGetResponse();
                        DatabaseInsertQuery(conn, tablechoice);
                        break;
                case 2:
                        break;
                case 3:
                        System.out.println("Something");
                case 4:
                        break;
                case 5:
                        break;
                case 6:
                        break;
                case 7:
                        System.out.println("Exiting Program");
                        break;
                default: // Illegal choice for integers other than 1, 2 and 3.
                        System.out.println("Illegal choice");
                        break;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
        // Step 4: Disconnect from the database server.
            try
            {
                if (conn != null)
                    conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
// This function controls the user interaction with the menu.
public static int PrintMenuAndGetResponse()
{
    Scanner keyboard = new Scanner(System.in);
    int response;
    System.out.println("Choose from one of the following options:");
    System.out.println(" 1. Insertion");
    System.out.println(" 2. Deletion");
    System.out.println(" 3. Update");
    System.out.println(" 4. List all coaches along with their teams and roles.");
    System.out.println(" 5. Player who has the most points.");
    System.out.println(" 6. Player who has the least points.");
    System.out.println(" 7. Quit the program%n");
    System.out.print("Your choice ==> ");
    response = keyboard.nextInt();
    // Leave a blank line before printing the output response.
    System.out.println( );
    return response;
}

public static int PrintTableandGetResponse()
{
    Scanner keyboard = new Scanner(System.in);
    int response;
    System.out.println("Choose from one of the following options:");
    System.out.println(" 1. Team");
    System.out.println(" 2. Player");
    System.out.println(" 3. Player Stats");
    System.out.println(" 4. General Manager");
    System.out.println(" 5. Owner");
    System.out.println(" 6. Coach");
    System.out.println(" 7. Home");
    System.out.println(" 8. Quit the program%n");
    System.out.print("Your choice ==> ");
    response = keyboard.nextInt();
    // Leave a blank line before printing the output response.
    System.out.println( );
    return response;
}

public static void DatabaseInsertQuery(Connection conn, int tablechoice) throws SQLException
{
    // When inserting make sure to the database has that home and team in that table before entering other tables due to constraints.
    Scanner keyboard = new Scanner(System.in);
    Statement stmt = conn.createStatement();
    String qry;
    ResultSet rs;

    // Variables for Team Table
    int TID;
    String TName;
    int THome;

    // Variables for Player Table
    int PID;
    String PName;
    String Position;
    int PTeam;

    // Variables for Player Stat Table
    int PSID;
    int PlayerID;
    String PlayerName;
    int FGMFPA;
    int FGMFPA3;
    int GP;
    int PTS;
    int FTMFTA;

    // Variables for General Manager Table
    int GMID;
    String GName;
    int TimeOnTeam;
    int GTeam;

    // Variables for Owner Table
    int OID;
    String OName;
    int TimeOwned;
    int OTeam;

    // Variables for Coach Table
    int CID;
    String CName;
    String CRole;
    int CTeam;

    // Variables for Home Table
    int HID;
    String State;
    String City;
    String Arena;


    switch (tablechoice)
    {
        case 1:
                System.out.print("Please enter a TID: ");
                TID = keyboard.nextInt();
                System.out.print("Please enter a Team Name: ");
                TName = keyboard.next();
                System.out.print("Please enter a Home ID: ");
                THome = keyboard.nextInt();

                qry = String.format("Insert Into Team (TID, TName, Home) Values (%d, '%s', %d)", TID, TName, THome);
                rs = stmt.executeQuery(qry);

                // Printing table after insertion
                rs = stmt.executeQuery("Select * From Team");

                System.out.format("%-4s %-20s %-20s%n", "TID", "TName", "Home");
                while(rs.next())
                {
                  TID = rs.getInt("TID");
                  TName = rs.getString("TName");
                  THome = rs.getInt("Home");

                  System.out.format("%-4d %-20s %-20d%n", TID, TName, THome);
                }
                System.out.println();
                rs.close();
                break;
        case 2:
                System.out.print("Please enter a PID: ");
                PID = keyboard.nextInt();
                System.out.print("Please enter a Player Name: ");
                PName = keyboard.next();
                keyboard = new Scanner(System.in);
                System.out.print("Please enter the Player's Position: ");
                keyboard = new Scanner(System.in);
                Position = keyboard.next();
                System.out.print("Please enter the Player's Team ID: ");
                PTeam = keyboard.nextInt();

                qry = String.format("Insert Into Player (PID, PName, Position, Team) Values (%d, '%s', '%s', %d)", PID, PName, Position, PTeam);
                rs = stmt.executeQuery(qry);

                // Printing table after insertion
                rs = stmt.executeQuery("Select * From Player");

                System.out.format("%-4s %-20s %-20s %-20s%n", "PID", "PName", "Position", "Team");
                while(rs.next())
                {
                  PID = rs.getInt("PID");
                  PName = rs.getString("PName");
                  Position = rs.getString("Position");
                  PTeam = rs.getInt("Team");

                  System.out.format("%-4d %-20s %-20s %-20d%n", PID, PName, Position, PTeam);
                }
                System.out.println();
                rs.close();

                break;
        case 3:
                System.out.print("Please enter a PSID: ");
                PSID = keyboard.nextInt();
                System.out.print("Please enter a Player ID: ");
                PlayerID = keyboard.nextInt();
                System.out.print("Please enter a Player Name: ");
                PlayerName = keyboard.next();
                System.out.print("Please enter a FGM/FPA: ");
                FGMFPA = keyboard.nextInt();
                System.out.print("Please enter a 3FGM/3FPA: ");
                FGMFPA3 = keyboard.nextInt();
                System.out.print("Please enter a GP: ");
                GP = keyboard.nextInt();
                System.out.print("Please enter a PTS: ");
                PTS = keyboard.nextInt();
                System.out.print("Please enter a FTM/FTA: ");
                FTMFTA = keyboard.nextInt();
                break;
        case 4:
                System.out.print("Please enter a GMID: ");
                GMID = keyboard.nextInt();
                System.out.print("Please enter a General Manager Name: ");
                GName = keyboard.next();
                System.out.print("Please enter the amount of time they spent on team in years: ");
                TimeOnTeam = keyboard.nextInt();
                System.out.print("Please enter the Team ID the belong to: ");
                GTeam = keyboard.nextInt();
                break;
        case 5:
                System.out.print("Please enter a OID: ");
                OID = keyboard.nextInt();
                System.out.print("Please enter Owner's Name: ");
                OName = keyboard.next();
                System.out.print("Please enter the amount of time they owned the team in years: ");
                TimeOwned = keyboard.nextInt();
                System.out.print("Please enter Team ID they belond to: ");
                OTeam = keyboard.nextInt();
                break;
        case 6:
                System.out.print("Please enter a CID: ");
                CID = keyboard.nextInt();
                System.out.print("Please enter Coach Name: ");
                CName = keyboard.next();
                System.out.print("Please enter Coach's role: ");
                CRole = keyboard.next();
                System.out.print("Please enter Team ID they belong to: ");
                CTeam = keyboard.nextInt();
                break;
        case 7:
                System.out.print("Please enter a HID: ");
                HID = keyboard.nextInt();
                System.out.print("Please enter the name of State: ");
                State = keyboard.next();
                System.out.print("Please enter the name of City: ");
                City = keyboard.next();
                System.out.print("Please enter name of Arena they play in: ");
                Arena = keyboard.next();

                qry = String.format("Insert Into Home (HID, State, City, Arena) Values (%d, '%s', '%s', '%s')", HID, State, City, Arena);
                rs = stmt.executeQuery(qry);

                // Printing table after insertion
                rs = stmt.executeQuery("Select * From Home");

                System.out.format("%-4s %-20s %-20s %-20s%n", "HID", "State", "City", "Arena");
                while(rs.next())
                {
                  HID = rs.getInt("HID");
                  State = rs.getString("State");
                  City = rs.getString("City");
                  Arena = rs.getString("Arena");

                  System.out.format("%-4d %-20s %-20s %-20s%n", HID, State, City, Arena);
                }
                System.out.println();
                rs.close();
                break;
        case 8:
                System.out.println("Exiting Program");
                break;
        default:
                System.out.println("Illegal choice");
                break;
    }
}

public static void PlayerMost(Connection conn) throws SQLException
{
    Statement stmt = conn.createStatement();
    String qry = "select PName, Position, MAX(PTS) " +
    "from Player, PlayerStats"
    +
    "where PID = PlayerID";
    ResultSet rs = stmt.executeQuery(qry);

    System.out.format("%n");
    System.out.format("%-12s %4s %-20s%n", "Player", "Position", "Points");

    while (rs.next())
    {
       String pname = rs.getString("PName");
       String position = rs.getString("Position");
       String points = rs.getString("MAX(PTS)");
       System.out.format("%-12s %4d %-20s%n", pname, position, points);
     }
     System.out.println();
     rs.close();
}

// Function for listing player who has the least points
public static void PlayerLeast(Connection conn) throws SQLException
{
    Statement stmt = conn.createStatement();
    String qry = "select PName, Position, MIN(PTS) "
    +
    "from Player, PlayerStats"
    +
    "where PID = PlayerID";
    ResultSet rs = stmt.executeQuery(qry);

    System.out.format("%n");
    System.out.format("%-12s %4s %-20s%n", "Player", "Position", "Points");

    while (rs.next())
    {
       String pname = rs.getString("PName");
       String position = rs.getString("Position");
       String points = rs.getString("MAX(PTS)");
       System.out.format("%-12s %4d %-20s%n", pname, position, points);
     }
     System.out.println();
     rs.close();
}


}
