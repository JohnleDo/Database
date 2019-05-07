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
        String tablechoice2;
        int rowID;
        String input;
        Scanner keyboard = new Scanner(System.in);
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
                        PrintAnyTable(conn, tablechoice);
                        break;
                case 2:
                        tablechoice = PrintTableandGetResponse();
                        DatabaseDeleteQuery(conn, tablechoice);
                        PrintAnyTable(conn, tablechoice);
                        break;
                case 3:
                        tablechoice = PrintTableandGetResponse();
                        PrintAnyTable(conn, tablechoice);
                        tablechoice2 = PrintAtrributesandGetResponse(tablechoice);
                        System.out.print("Enter row ID of the desired change: ");
                        rowID = keyboard.nextInt();
                        System.out.print("Enter the new value to be updated: ");
                        input = keyboard.next();
                        DatabaseUpdateQuery(conn, tablechoice, tablechoice2, rowID, input);
                        PrintAnyTable(conn, tablechoice);
                        break;
                case 4:
                        PrintCoaches(conn);
                        break;
                case 5:
                        PlayerMost(conn);
                        break;
                case 6:
                        PlayerLeast(conn);
                        break;
                case 7:
                        tablechoice = PrintTableandGetResponse();
                        PrintAnyTable(conn, tablechoice);
                        break;
                case 8:
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
    System.out.println(" 7. Print Specific table.");
    System.out.println(" 8. Quit the program");
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
    System.out.println(" 8. Quit the program");
    System.out.print("Your choice ==> ");
    response = keyboard.nextInt();
    // Leave a blank line before printing the output response.
    System.out.println( );
    return response;
}

public static String PrintAtrributesandGetResponse(int tablechoice2)
{
  Scanner keyboard = new Scanner(System.in);
  String response;

  System.out.println("Please type the attributes as shown, is case sensetive");
  switch(tablechoice2)
  {
    case 1:
          System.out.println("Choose from one of the following attributes for Team");
          System.out.println("TName");
          System.out.println("Home");
          break;
    case 2:
          System.out.println("Choose from one of the following attributes for Player");
          System.out.println("PName");
          System.out.println("Position");
          System.out.println("Team");
          break;
    case 3:
          System.out.println("Choose from one of the following attributes for PlayerStats");
          System.out.println("Player ID");
          System.out.println("FGMFPA");
          System.out.println("3FGMFPA");
          System.out.println("GP");
          System.out.println("PTS");
          System.out.println("FTMFTA");
          break;
    case 4:
          System.out.println("Choose from one of the following attributes for GenManager");
          System.out.println("GMName");
          System.out.println("TimeOnTeam");
          System.out.println("Team");
          break;
    case 5:
          System.out.println("Choose from one of the following attributes for Owner");
          System.out.println("OName");
          System.out.println("TimeOwned");
          System.out.println("Team");
          break;
    case 6:
          System.out.println("Choose from one of the following attributes for Coach");
          System.out.println("CName");
          System.out.println("CRole");
          System.out.println("Team");
          break;
    case 7:
          System.out.println("Choose from one of the following attributes for Home");
          System.out.println("State");
          System.out.println("City");
          System.out.println("Arena");
          break;
    case 8:
            System.out.println("Exiting Program");
            break;
    default:
            System.out.println("Illegal choice");
            break;
  }
  // Leave a blank line before printing the output response.
  System.out.print("Your choice ==> ");
  response = keyboard.next();
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

                qry = String.format("Insert Into PlayerStats (PSID, PlayerID, PName, FGMFPA, 3FGMFPA, GP, PTS, FTMFTA) Values (%d, %d, '%s', %d, %d, %d, %d, %d)", PSID, PlayerID, PlayerName, FGMFPA, FGMFPA3, GP, PTS, FTMFTA);
                rs = stmt.executeQuery(qry);

                rs.close();
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

                qry = String.format("Insert Into GenManager (GMID, GMName, TimeOnTeam, Team) Values (%d, '%s', %d, %d)", GMID, GName, TimeOnTeam, GTeam);
                rs = stmt.executeQuery(qry);

                rs.close();
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

                qry = String.format("Insert Into Owner (OID, OName, TimeOwned, Team) Values (%d, '%s', %d, %d)", OID, OName, TimeOwned, OTeam);
                rs = stmt.executeQuery(qry);

                rs.close();
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

                qry = String.format("Insert Into Coach (CID, CName, CRole, Team) Values (%d, '%s', '%s', %d)", CID, CName, CRole, CTeam);
                rs = stmt.executeQuery(qry);

                rs.close();
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

public static void DatabaseDeleteQuery(Connection conn, int tablechoice) throws SQLException
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
                System.out.print("Please enter a TID to delete: ");
                TID = keyboard.nextInt();

                qry = String.format("Delete From Team Where TID = %d", TID);
                rs = stmt.executeQuery(qry);

                rs.close();
                break;
        case 2:
                System.out.print("Please enter a PID: ");
                PID = keyboard.nextInt();

                qry = String.format("Delete From Player Where PID = %d", PID);
                rs = stmt.executeQuery(qry);

                rs.close();

                break;
        case 3:
                System.out.print("Please enter a PSID: ");
                PSID = keyboard.nextInt();

                qry = String.format("Delete From PlayerStats Where PSID = %d", PSID);
                rs = stmt.executeQuery(qry);

                rs.close();
                break;
        case 4:
                System.out.print("Please enter a GMID: ");
                GMID = keyboard.nextInt();

                qry = String.format("Delete From GenManager Where GMID = %d", GMID);
                rs = stmt.executeQuery(qry);

                rs.close();
                break;
        case 5:
                System.out.print("Please enter a OID: ");
                OID = keyboard.nextInt();

                qry = String.format("Delete From Owner Where OID = %d", OID);
                rs = stmt.executeQuery(qry);

                rs.close();
                break;
        case 6:
                System.out.print("Please enter a CID: ");
                CID = keyboard.nextInt();

                qry = String.format("Delete From Coach Where CID = %d", CID);
                rs = stmt.executeQuery(qry);

                rs.close();
                break;
        case 7:
                System.out.print("Please enter a HID: ");
                HID = keyboard.nextInt();

                qry = String.format("Delete From Home Where HID = %d", HID);
                rs = stmt.executeQuery(qry);

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

public static void DatabaseUpdateQuery(Connection conn, int tablechoice, String tablechoice2, int rowID, String input) throws SQLException
{
  Statement stmt = conn.createStatement();
  String qry;
  ResultSet rs;

  switch (tablechoice)
  {
      case 1:
              if (input.matches("[0-9]+") && input.length() > 2)
              {
                qry = String.format("Update Team Set %s=%d Where TID = %d", tablechoice2, Integer.parseInt(input), rowID);
              }
              else
              {
                qry = String.format("Update Team Set %s='%s' Where TID = %d", tablechoice2, input, rowID);
              }

              rs = stmt.executeQuery(qry);
              rs.close();
              break;
      case 2:
              if (input.matches("[0-9]+") && input.length() > 2)
              {
                qry = String.format("Update Player Set %s=%d Where PID = %d", tablechoice2, Integer.parseInt(input), rowID);
              }
              else
              {
                qry = String.format("Update Player Set %s='%s' Where PID = %d", tablechoice2, input, rowID);
              }

              rs = stmt.executeQuery(qry);
              rs.close();
              break;
      case 3:
              if (input.matches("[0-9]+") && input.length() > 2)
              {
                qry = String.format("Update PlayerStats Set %s=%d Where PSID = %d", tablechoice2, Integer.parseInt(input), rowID);
              }
              else
              {
                qry = String.format("Update PlayerStats Set %s='%s' Where PSID = %d", tablechoice2, input, rowID);
              }

              rs = stmt.executeQuery(qry);
              rs.close();
              break;
      case 4:
              if (input.matches("[0-9]+") && input.length() > 2)
              {
                qry = String.format("Update GenManager Set %s=%d Where GMID = %d", tablechoice2, Integer.parseInt(input), rowID);
              }
              else
              {
                qry = String.format("Update GenManager Set %s='%s' Where GMID = %d", tablechoice2, input, rowID);
              }

              rs = stmt.executeQuery(qry);
              rs.close();
              break;
      case 5:
              if (input.matches("[0-9]+") && input.length() > 2)
              {
                qry = String.format("Update Owner Set %s=%d Where OID = %d", tablechoice2, Integer.parseInt(input), rowID);
              }
              else
              {
                qry = String.format("Update Owner Set %s='%s' Where OID = %d", tablechoice2, input, rowID);
              }

              rs = stmt.executeQuery(qry);
              rs.close();
              break;
      case 6:
              if (input.matches("[0-9]+") && input.length() > 2)
              {
                qry = String.format("Update Coach Set %s=%d Where CID = %d", tablechoice2, Integer.parseInt(input), rowID);
              }
              else
              {
                qry = String.format("Update Coach Set %s='%s' Where CID = %d", tablechoice2, input, rowID);
              }

              rs = stmt.executeQuery(qry);
              rs.close();
              break;
      case 7:
              if (input.matches("[0-9]+") && input.length() > 2)
              {
                qry = String.format("Update Home Set %s=%d Where HID = %d", tablechoice2, Integer.parseInt(input), rowID);
              }
              else
              {
                qry = String.format("Update Home Set %s='%s' Where HID = %d", tablechoice2, input, rowID);
              }

              rs = stmt.executeQuery(qry);
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
    String qry = "Select Player.Pname, Position, PTS From PlayerStats, Player Where PTS=(Select MAX(PTS) from PlayerStats) and PID=PlayerID";
    ResultSet rs = stmt.executeQuery(qry);

    System.out.format("%n");
    System.out.format("%-20s %-20s %-20s%n", "Player", "Position", "Points");

    while (rs.next())
    {
       String Pname = rs.getString("PName");
       String Position = rs.getString("Position");
       int PTS = rs.getInt("PTS");
       System.out.format("%-20s %-20s %-20d%n", Pname, Position, PTS);
     }
     System.out.println();
     rs.close();
}

// Function for listing player who has the least points
public static void PlayerLeast(Connection conn) throws SQLException
{
    Statement stmt = conn.createStatement();
    String qry = "Select Player.Pname, Position, PTS From PlayerStats, Player Where PTS=(Select MIN(PTS) from PlayerStats) and PID=PlayerID";
    ResultSet rs = stmt.executeQuery(qry);

    System.out.format("%n");
    System.out.format("%-20s %-20s %-20s%n", "Player", "Position", "Points");

    while (rs.next())
    {
       String Pname = rs.getString("PName");
       String Position = rs.getString("Position");
       int PTS = rs.getInt("PTS");
       System.out.format("%-20s %-20s %-20d%n", Pname, Position, PTS);
     }
     System.out.println();
     rs.close();
}

public static void PrintCoaches(Connection conn) throws SQLException
{
  ResultSet rs;
  Statement stmt = conn.createStatement();
  // Printing table after insertion
  rs = stmt.executeQuery("Select CName, CRole, TName From Coach, Team Where Coach.Team = TID");

  System.out.format("%-20s %-20s %-20s%n", "Name", "Role", "Team");
  while(rs.next())
  {
    String CName = rs.getString("CName");
    String CRole = rs.getString("CRole");
    String Team = rs.getString("TName");

    System.out.format("%-20s %-20s %-20s%n", CName, CRole, Team);
  }
  System.out.println();
  rs.close();
}

public static void PrintAnyTable(Connection conn, int tablechoice) throws SQLException
{
  ResultSet rs;
  Statement stmt = conn.createStatement();

  switch (tablechoice)
  {
      case 1:
              // Printing table after insertion
              rs = stmt.executeQuery("Select * From Team");

              System.out.format("%-4s %-20s %-20s%n", "TID", "TName", "Home");
              while(rs.next())
              {
                int TID = rs.getInt("TID");
                String TName = rs.getString("TName");
                int THome = rs.getInt("Home");

                System.out.format("%-4d %-20s %-20d%n", TID, TName, THome);
              }
              System.out.println();
              rs.close();
              break;
      case 2:
              // Printing table after insertion
              rs = stmt.executeQuery("Select * From Player");

              System.out.format("%-4s %-20s %-20s %-20s%n", "PID", "PName", "Position", "Team");
              while(rs.next())
              {
                int PID = rs.getInt("PID");
                String PName = rs.getString("PName");
                String Position = rs.getString("Position");
                int PTeam = rs.getInt("Team");

                System.out.format("%-4d %-20s %-20s %-20d%n", PID, PName, Position, PTeam);
              }
              System.out.println();
              rs.close();

              break;
      case 3:
              // Printing table after insertion
              rs = stmt.executeQuery("Select * From PlayerStats");

              System.out.format("%-4s %-20s %-20s %-20s %-20s %-20s %-20s %-20s%n", "PSID", "PlayerID", "PName", "FGMFPA", "3FGMFPA", "GP", "PTS", "FTMFTA");
              while(rs.next())
              {
                int PSID = rs.getInt("PSID");
                int PlayerID = rs.getInt("PlayerID");
                String PlayerName = rs.getString("PName");
                int FGMFPA = rs.getInt("FGMFPA");
                int FGMFPA3 = rs.getInt("3FGMFPA");
                int GP = rs.getInt("GP");
                int PTS = rs.getInt("PTS");
                int FTMFTA = rs.getInt("FTMFTA");

                System.out.format("%-4d %-20d %-20s %-20d %-20d %-20d %-20d %-20d%n", PSID, PlayerID, PlayerName, FGMFPA, FGMFPA3, GP, PTS, FTMFTA);
              }
              System.out.println();
              rs.close();
              break;
      case 4:
              rs = stmt.executeQuery("Select * From GenManager");

              System.out.format("%-4s %-20s %-20s %-20s%n", "GMID", "GMName", "TimeOnTeam", "Team");
              while(rs.next())
              {
                int GMID = rs.getInt("GMID");
                String GName = rs.getString("GMName");
                int TimeOnTeam= rs.getInt("TimeOnTeam");
                int GTeam = rs.getInt("Team");

                System.out.format("%-4d %-20s %-20d %-20d%n", GMID, GName, TimeOnTeam, GTeam);
              }
              System.out.println();
              rs.close();
              break;
      case 5:
              rs = stmt.executeQuery("Select * From Owner");

              System.out.format("%-4s %-20s %-20s %-20s%n", "OID", "OName", "TimeOwned", "Team");
              while(rs.next())
              {
                int OID = rs.getInt("OID");
                String OName = rs.getString("OName");
                int TimeOwned= rs.getInt("TimeOwned");
                int OTeam = rs.getInt("Team");

                System.out.format("%-4d %-20s %-20d %-20d%n", OID, OName, TimeOwned, OTeam);
              }
              System.out.println();
              rs.close();
              break;
      case 6:
              rs = stmt.executeQuery("Select * From Coach");

              System.out.format("%-4s %-20s %-20s %-20s%n", "CID", "CName", "CRole", "Team");
              while(rs.next())
              {
                int CID = rs.getInt("CID");
                String CName = rs.getString("CName");
                String CRole = rs.getString("CRole");
                int CTeam = rs.getInt("Team");

                System.out.format("%-4d %-20s %-20s %-20d%n", CID, CName, CRole, CTeam);
              }
              System.out.println();
              rs.close();
              break;
      case 7:
              rs = stmt.executeQuery("Select * From Home");

              System.out.format("%-4s %-20s %-20s %-20s%n", "HID", "State", "City", "Arena");
              while(rs.next())
              {
                int HID = rs.getInt("HID");
                String State = rs.getString("State");
                String City = rs.getString("City");
                String Arena = rs.getString("Arena");

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
}
