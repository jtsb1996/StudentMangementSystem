import java.sql.*;
import java.util.Scanner;

class MainClass{ 
    static final String connectionName = "jdbc:mysql://localhost:3306/stdmanagementsystem";
    static final String connectionUser = "root";
    static final String connectionPassword = "qwe123";
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        TestDBConnection();
        String name = signIn();
        Menu(name);
        
    }

    public static void TestDBConnection() {
        try {
            /* optional statement since jdbc 4.0 */
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(connectionName, connectionUser, connectionPassword);
            if (con.isValid(10))
                System.out.println("Connection Successful!");

        }
        catch(Exception e) {
            System.out.println(e);
        }
            
    }

    public static String signIn() {
        while (true) {
            System.out.println("Please enter username: ");
            String username = sc.next();
            System.out.println("Please enter password");
            String password = sc.next();
            boolean authentication = authenticate(username, password);
            if (authentication) {
                return username;
            }
                
        }
    }

    public static boolean authenticate(String username, String password) {
        boolean authenticate = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection(connectionName, connectionUser, connectionPassword);
            Statement statement = connect.createStatement();
            ResultSet result = statement.executeQuery("select * from usertable");
            while(result.next())
            {
                if (result.getString("username").equals(username) && result.getString("pw").equals(password)) {
                    System.out.println("Authentication complete!");
                    authenticate = true;
                    connect.close();
                    break;
                }
                    
            }
        }

        catch(Exception e) {
            System.out.println(e);
        }
        
        return authenticate;
    }

    public static void Menu(String name) {
        boolean run = true;
        System.out.println("Welcome " + name + "!");
        System.out.println("-------Student Management System-------");
        while(run) {
            System.out.println("-----------------------------------");
            System.out.println("1.\t Add Student into System");
            System.out.println("2.\t Remove Student from System");
            System.out.println("3.\t Add Subject into System");
            System.out.println("4.\t Remove Subject from System");
            System.out.println("5.\t Enroll Student into Subject");
            System.out.println("V.\t View Student Profile");
            System.out.println("E.\t Exit");
            System.out.print("Please select a choice: ");
            String userchoice = sc.next();
            System.out.println("-----------------------------------");
            
            switch(userchoice) {
                case("1"):
                    addStudent();
                    break;
                case("2"):
                    removeStudent();
                    break;
                case("3"):
                    addSubject();
                    break;
                case("4"):
                    removeSubject();
                    break;
                case("5"):
                    enrollStudent();
                    break;
                case("v"):
                    viewStudentProfile();
                    break;
                case("E"):
                    System.out.println("Thank you for using Student Management System!");
                    run = false;
                    break;
            }
        }   
    }

    public static void addStudent() {

        System.out.print("Please enter the student name: ");
        String tempName = sc.next();

        try {
            Connection connect = DriverManager.getConnection(connectionName, connectionUser, connectionPassword);
            Statement statement = connect.createStatement();
            int num = statement.executeUpdate("insert into stdtable" + "(name)" +  "values(\"" + tempName + "\")");
            System.out.println(num + " records inserted!");
            connect.close();
        }
        
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void removeStudent() {

        System.out.print("Please enter the student name: ");
        String tempName = sc.next();

        try {
            Connection connect = DriverManager.getConnection(connectionName, connectionUser, connectionPassword);
            Statement statement = connect.createStatement();
            int num = statement.executeUpdate("delete from stdtable where name = \'" + tempName + "\'");
            System.out.println(num + " records removed!");
            connect.close();
        }
        
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void addSubject() {
        System.out.print("Please enter the subject name: ");
        String tempSub = sc.next();

        try {
            Connection connect = DriverManager.getConnection(connectionName, connectionUser, connectionPassword);
            Statement statement = connect.createStatement();
            int num = statement.executeUpdate("insert into subjecttable (subjecttitle)" +  "values(\"" + tempSub + "\")");
            System.out.println(num + " records inserted!");
            connect.close();
        }
        
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void removeSubject() {
        System.out.print("Please enter the subject name: ");
        String tempSub = sc.next();

        try {
            Connection connect = DriverManager.getConnection(connectionName, connectionUser, connectionPassword);
            Statement statement = connect.createStatement();
            int num = statement.executeUpdate("delete from subjecttable where subjecttitle = \'" + tempSub + "\'");
            System.out.println(num + " records inserted!");
            connect.close();
        }
        
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void enrollStudent() {
        System.out.print("Please enter student name: ");
        String tempStudent = sc.next();
        System.out.print("Please enter subject title: ");
        String tempSubject = sc.next();
        int tempStudentId = 0, tempSubjectId = 0;
        try{
            Connection connect = DriverManager.getConnection(connectionName, connectionUser, connectionPassword);
            Statement statement = connect.createStatement();
            ResultSet result = statement.executeQuery("select stdId from stdtable where name = \'" + tempStudent + "\'");
            while(result.next()) {
                tempStudentId = result.getInt("stdId");
            }
                
            result = statement.executeQuery("select subjectId from subjecttable where subjecttitle = \'" + tempSubject + "\'");
            while(result.next()){
                tempSubjectId = result.getInt("subjectId");
            }
            int num = statement.executeUpdate("insert into stdsubject (stdId, subjectId) values(\'" + tempStudentId + "\', \'" + tempSubjectId + "\')");
            System.out.println(num + " records inserted!");
            connect.close();

        }

        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void viewStudentProfile() {
        System.out.print("Please enter a student name: ");
        String tempStudent = sc.next();
        System.out.print(tempStudent + " is currently enrolled in: ");
        try {
            Connection connect = DriverManager.getConnection(connectionName, connectionUser, connectionPassword);
            Statement statement = connect.createStatement();
            ResultSet result = statement.executeQuery("select subjecttitle " + 
                            "from stdtable join stdsubject on stdtable.stdId = stdsubject.stdId " +
                            "join subjecttable on subjecttable.subjectId = stdsubject.subjectId " +
                            "where stdtable.name = \'" + tempStudent + "\'");
            while(result.next()) {
                System.out.print(result.getString("subjecttitle") + ",");
            }
            System.out.println();
        }

        catch(Exception e){
            System.out.println(e);
        }
    }
}