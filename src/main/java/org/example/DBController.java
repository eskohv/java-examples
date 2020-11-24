package org.example;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DBController {

    private static final String url = System.getenv("SQL_URL");
    private static final String user = System.getenv("SQL_USER");
    private static final String password = System.getenv("SQL_PSW");

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }

    public static void read() throws SQLException {
        try (
            Connection myCon = getConnection();
            Statement stmt = myCon.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("select * from user_info");
            )
        {
            while(rs.next()) {
                System.out.println(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public static int getId(){
        int id;
        try (
                Connection myCon = getConnection();
                Statement stmt = myCon.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery("select * from user_info");
        )
        {
                rs.last();
                id = (rs.getInt("user_id"));
                System.out.println(id);
                return id;

        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return 1;
    }


    public static void write(String fname, String lname, String username, String password) throws SQLException {
        String sql = "INSERT INTO account(user_id,fname,lname,username,password) VALUES ( ?, ?, ?, ?, ?)";

        try(
            Connection myCon = getConnection();
            PreparedStatement stmt = myCon.prepareStatement(sql)
            )
        {
            int account_id = getId()+1;
            stmt.setInt(1,account_id);
            stmt.setString(2,fname);
            stmt.setString(3,lname);
            stmt.setString(4,username);
            stmt.setString(5,password);

            stmt.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param psw
     * @return SHA-256 hashed password
     */

    protected static String hash(String psw){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] byteCodeHash = digest.digest(psw.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();

            for (int b : byteCodeHash) {
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
