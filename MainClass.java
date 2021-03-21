import java.sql.*;

class MainClass{ 
    public static void main(String[] args) {
        System.out.println("test");
        TestDBConnection();
    }

    public static void TestDBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stdmanagementsystem","root","qwe123");
            
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from stdTable");  
            while(rs.next())  
            System.out.println(rs.getInt(1)+"  "+rs.getString(2));  
            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
            
    }
}