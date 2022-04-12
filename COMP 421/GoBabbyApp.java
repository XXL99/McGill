import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class GoBabbyApp {
    public static void main(String[] args) throws SQLException, IOException {
        // Register the driver.  You must register the driver before you can use it.
        try {
            DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
        } catch (Exception cnfe) {
            System.out.println("Class not found");
        }
        // This is the url you must use for DB2.
        //Note: This url may not valid now ! Check for the correct year and semester and server name.
        String url = "jdbc:db2://winter2022-comp421.cs.mcgill.ca:50000/cs421";

        //REMEMBER to remove your user id and password before submitting your code!!
        String your_userid = null;
        String your_password = null;
        //AS AN ALTERNATIVE, you can just set your password in the shell environment in the Unix (as shown below) and read it from there.
        //$  export SOCSPASSWD=yoursocspasswd
        if (your_userid == null && (your_userid = System.getenv("SOCSUSER")) == null) {
            System.err.println("Error!! do not have a password to connect to the database!");
            System.exit(1);
        }
        if (your_password == null && (your_password = System.getenv("SOCSPASSWD")) == null) {
            System.err.println("Error!! do not have a password to connect to the database!");
            System.exit(1);
        }
        Connection con = DriverManager.getConnection(url, your_userid, your_password);
        Statement statement = con.createStatement();

        //Insert ID
        int sqlCode = 0;      // Variable to hold SQLCODE
        String sqlState = "00000";  // Variable to hold SQLSTATE
        try {
            Integer idl[] = {};
            ArrayList<Integer> idlist = new ArrayList<Integer>(Arrays.asList(idl));
            java.sql.ResultSet rs = statement.executeQuery("SELECT ID from  MIDWIFE");
            while (rs.next()) {
                int id = rs.getInt("ID");
                idlist.add(id);//check if the id in the list
            }
            rs.close();
            int parc_id;
            int mo_health_id;
            InputStreamReader input = new InputStreamReader(System.in);
            //Taking the input data using the BufferedReader class
            BufferedReader reader = new BufferedReader(input);
            System.out.println("Please enter your practitioner id [E] to exit:");
            String n = reader.readLine();
            parc_id = checkID(statement, con, idlist, n);
            System.out.println("Please enter the date for appointment list [E] to exit:");
            InputStreamReader input2 = new InputStreamReader(System.in);
            BufferedReader reader2 = new BufferedReader(input2);
            String datein = reader2.readLine();
            int app_id = checkDate(con, datein, parc_id);//the chosen appointment id
            java.sql.ResultSet rs2 = statement.executeQuery("SELECT m.health_id FROM ASSIGNMENT a,APPOINTMENTs ap, MOTHER m WHERE a.motherhealthid=m.health_id AND a.assignid=ap.assignid AND ap.ID=" + app_id);
            if (rs2.next()) {
                mo_health_id = rs2.getInt(1);
                switchcases(reader, statement, con, datein, parc_id, mo_health_id, app_id);
            }
        }catch (SQLException e) {
            sqlCode = e.getErrorCode(); // Get SQLCODE
            sqlState = e.getSQLState(); // Get SQLSTATE
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
        }
        statement.close();
        con.close();
    }

    static void switchcases(BufferedReader reader, Statement statement, Connection con, String datein, int parc_id, int mo_health_id, int app_id) throws SQLException, IOException {
        java.sql.ResultSet nm = statement.executeQuery("SELECT name from  MOTHER WHERE health_id= " + mo_health_id);
        if (nm.next()) {
            String nameofmom = nm.getString(1);
            System.out.println("For " + nameofmom + " " + mo_health_id);
            System.out.println("1. Review notes");
            System.out.println("2. Review tests");
            System.out.println("3. Add a note");
            System.out.println("4. Prescribe a test");
            System.out.println("5. Go back to the appointments.");
            System.out.println("Enter your choice:");
            String choice = reader.readLine();
            switch (Integer.parseInt(choice)) {
                case 1:
                    review_notes(reader, statement, con, datein, parc_id, mo_health_id, app_id);
                    break;
                case 2:
                    review_tests(reader, statement, con, datein, parc_id, mo_health_id, app_id);
                    break;
                case 3:
                    add_note(reader, statement, con, datein, parc_id, mo_health_id, app_id);
                    break;
                case 4:
                    prescribe(reader, statement, con, datein, parc_id, mo_health_id, app_id);
                    break;
                case 5:
                    int new_id = checkDate(con, datein, parc_id);//the chosen appointment id
                    java.sql.ResultSet rs2 = statement.executeQuery("SELECT m.health_id FROM ASSIGNMENT a,APPOINTMENTs ap, MOTHER m WHERE a.motherhealthid=m.health_id AND a.assignid=ap.assignid AND ap.ID=" + new_id);
                    if (rs2.next()) {
                        mo_health_id = rs2.getInt(1);
                        switchcases(reader, statement, con, datein, parc_id, mo_health_id, new_id);
                    }
                    break;
            }
        }
    }

    static int checkID(Statement statement, Connection con, ArrayList<Integer> idlist, String n) throws SQLException, IOException {
        int parc_id = 0;
        if (n.equals("E")) {//enter E to exit
            statement.close();
            con.close();
            System.exit(0);
        } else if (!(idlist.contains(Integer.parseInt(n)))) {
            InputStreamReader input = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(input);
            System.out.println("The ID is not valid");
            System.out.println("Please enter your practitioner id [E] to exit:");
            String x = reader.readLine();
            checkID(statement, con, idlist, x);
        } else {
            parc_id = Integer.parseInt(n);
        }
        return parc_id;
    }

    static int checkDate(Connection con, String datein, int parc_id) throws SQLException, IOException {
        int next = 0;
        int sqlCode = 0;      // Variable to hold SQLCODE
        String sqlState = "00000";  // Variable to hold SQLSTATE
        if (datein.equals("E")) {//enter E to exit
            con.close();
            System.exit(0);
        } else if (datein.equals("D")) {
            InputStreamReader input4 = new InputStreamReader(System.in);
            BufferedReader reader4 = new BufferedReader(input4);
            System.out.println("Please enter the date for appointment list [E] to exit:");
            String date4 = reader4.readLine();
            next = checkDate(con, date4, parc_id);
        } else {
            try {
                String querySQL1 = "SELECT ap.TIME, 'P' AS type, mo.name,mo.health_id,ap.ID\n" +
                        "FROM MIDWIFE m,ASSIGNMENT a,APPOINTMENTs ap, MOTHER mo\n" +
                        "WHERE m.id=? AND ap.DATE= ? AND m.id=a.primaryMF AND a.assignid=ap.assignid AND (a.motherhealthid=mo.health_id)\n" +
                        "UNION\n" +
                        "SELECT ap.TIME,'B' AS type, mo.name,mo.health_id,ap.ID\n" +
                        "FROM MIDWIFE m,ASSIGNMENT a,APPOINTMENTs ap, MOTHER mo\n" +
                        "WHERE m.id=? AND ap.DATE= ? AND m.id=a.BACKMF AND a.assignid=ap.assignid AND (a.motherhealthid=mo.health_id)\n" +
                        "ORDER BY TIME\n";
                Statement statement2 = con.prepareStatement(querySQL1);
                ((PreparedStatement) statement2).setInt(1, parc_id);
                ((PreparedStatement) statement2).setString(2, datein);
                ((PreparedStatement) statement2).setInt(3, parc_id);
                ((PreparedStatement) statement2).setString(4, datein);
                ResultSet rs1 = ((PreparedStatement) statement2).executeQuery();
                if (rs1.next() == false) {
                    System.out.println("There is no appointment for this date");
                    InputStreamReader input2 = new InputStreamReader(System.in);
                    BufferedReader reader2 = new BufferedReader(input2);
                    System.out.println("Please enter the date for appointment list [E] to exit:");
                    String date2 = reader2.readLine();
                    next = checkDate(con, date2, parc_id);
                } else {
                    ResultSet rs2 = ((PreparedStatement) statement2).executeQuery();
                    int count = 0;
                    while (rs2.next()) {
                        count += 1;
                        System.out.println(count + ": " + rs2.getTime(1) + " " + rs2.getString(2) + " " + rs2.getString(3) + " " + rs2.getInt(4));
                    }
                    next = check_insert(statement2, con, parc_id);
                }
            } catch (SQLException e) {
                sqlCode = e.getErrorCode(); // Get SQLCODE
                sqlState = e.getSQLState(); // Get SQLSTATE
                System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
                System.out.println(e);

            }
        }
        return next;
    }

    static int check_insert(Statement statement2, Connection con, int parc_id) throws IOException, SQLException {
        int next = 0;
        ResultSet rs2 = ((PreparedStatement) statement2).executeQuery();
        InputStreamReader input3 = new InputStreamReader(System.in);
        BufferedReader reader3 = new BufferedReader(input3);
        System.out.println(" Enter the appointment number that you would like to work on.");
        System.out.println("[E] to exit [D] to go back to another date :");
        String date3 = reader3.readLine();
        int rowCount = 0;
        while (rs2.next()) {
            rowCount++;
        }
        if (date3.equals("E") || date3.equals("D")) {
            next = checkDate(con, date3, parc_id);
        } else if (Integer.parseInt(date3) > rowCount) {
            System.out.println("Insert is not in the range, please chose another one");
            next = check_insert(statement2, con, parc_id);
        } else {
            ResultSet rs3 = ((PreparedStatement) statement2).executeQuery();
            int temp = 0;
            while (rs3.next()) {
                temp += 1;
                if (temp == Integer.parseInt(date3)) {
                    next = rs3.getInt(5);
                } else {
                    continue;
                }
            }
        }
        return next;
    }

    static void review_notes(BufferedReader reader, Statement statement, Connection con, String datein, int parc_id, int mo_health_id, int appid) throws SQLException, IOException {
        java.sql.ResultSet nm = statement.executeQuery("SELECT n.DATE,TIMESTAMP,CONTENT\n" +
                "FROM NOTE n, MOTHER m, ASSIGNMENT a, APPOINTMENTS ap\n" +
                "WHERE m.HEALTH_ID=" + mo_health_id + " AND a.MOTHERHEALTHID=m.HEALTH_ID AND a.ASSIGNID=ap.ASSIGNID AND ap.ID=n.APPID\n" +
                "ORDER BY DATE DESC ,TIMESTAMP DESC ");
        while (nm.next()) {
            System.out.println(nm.getDate(1) + " " + nm.getTime(2) + " " + nm.getString(3));
        }
        switchcases(reader, statement, con, datein, parc_id, mo_health_id, appid);
    }

    static void review_tests(BufferedReader reader, Statement statement, Connection con, String datein, int parc_id, int mo_health_id, int appid) throws SQLException, IOException {
        java.sql.ResultSet rs = statement.executeQuery("SELECT PREDATE,TYPE,RESULT FROM TEST t,APPLYTESTON a WHERE a.MOTHERHEALTHID=" +
                mo_health_id + " AND a.TESTID=t.ID ORDER BY PREDATE DESC");
        while (rs.next()) {
            String result = rs.getString(3);
            if (result != null) {
                System.out.println(rs.getDate(1) + " [" + rs.getString(2) + "] " + result);
            } else {
                System.out.println(rs.getDate(1) + " [" + rs.getString(2) + "] " + "PENDING");
            }
        }
        switchcases(reader, statement, con, datein, parc_id, mo_health_id, appid);
    }

    static void add_note(BufferedReader reader, Statement statement, Connection con, String datein, int parc_id, int mo_health_id, int appid) throws SQLException, IOException {
        System.out.println("Please type your observation: ");
        String written = reader.readLine();
        Time update = new Time(System.currentTimeMillis());
        PreparedStatement pre = con.prepareStatement("INSERT INTO Note (appID, TimeStamp, Date, content) VALUES (?,?,?,?)");
        pre.setString(4, written);
        pre.setTime(2, update);
        pre.setString(3, datein);
        pre.setInt(1, appid);
        pre.executeUpdate();
        switchcases(reader, statement, con, datein, parc_id, mo_health_id, appid);
    }

    static void prescribe(BufferedReader reader, Statement statement, Connection con, String datein, int parc_id, int mo_health_id, int appid) throws IOException, SQLException {
        System.out.println("Please enter the type of test: ");
        String written = reader.readLine();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int max = 9999;
        int min = 1000;
        int idoftest = (int) Math.floor(Math.random() * (max - min + 1) + min);
        PreparedStatement pre = con.prepareStatement("INSERT INTO Test (ID, preDate, sampleDate, labDate, Type, result, TechID, labID) VALUES (?,?,?,?,?,?,?,?)");
        pre.setInt(1, idoftest);
        pre.setDate(2, java.sql.Date.valueOf(formatter.format(date)));
        pre.setDate(3, java.sql.Date.valueOf(formatter.format(date)));
        pre.setDate(4, null);
        pre.setString(5, written);
        pre.setString(6, null);
        pre.setObject(7, null);
        pre.setObject(8, null);
        pre.executeUpdate();
        //update the test apply on
        java.sql.ResultSet rs = statement.executeQuery("SELECT EXPTIMEFRAME FROM ASSIGNMENT a,APPOINTMENTS ap WHERE a.ASSIGNID=ap.ASSIGNID AND ap.ID=" + appid);
        while (rs.next()) {
            Date exptime = rs.getDate(1);
            PreparedStatement pre2 = con.prepareStatement("INSERT INTO applyTestOn (testID, MotherHealthID, ExpTimeFrame) VALUES (?,?,?)");
            pre2.setInt(1, idoftest);
            pre2.setInt(2, mo_health_id);
            pre2.setDate(3, (java.sql.Date) exptime);
            pre2.executeUpdate();
        }
        switchcases(reader, statement, con, datein, parc_id, mo_health_id, appid);
    }
}


