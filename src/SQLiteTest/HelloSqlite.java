package SQLiteTest;

import java.sql.*;

public class HelloSqlite {

    public static void main( String args[] )
    {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened database successfully");

            Statement statement = c.createStatement();
            HelloSqlite helloSqlite=new HelloSqlite();
            helloSqlite.drop(statement);
            helloSqlite.create(statement);

            statement.executeUpdate("insert into person values(1, 'leo')");
            statement.executeUpdate("insert into person values(2, 'yui')");
            ResultSet rs = statement.executeQuery("select * from person");
            while(rs.next())
            {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }finally {
                try {
                    if(c!=null)c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }




    }

    public void create(Statement statement) throws SQLException {
        statement.executeUpdate("create table person (id integer, name string)");
    }

    public void drop(Statement statement) throws SQLException {
        statement.executeUpdate("drop table if exists person");
    }

}
