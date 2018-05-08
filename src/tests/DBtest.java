package tests;

import utils.Helper;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class DBtest {

    /**
     * Connect to the test.db database
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:db/windows_rds.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    /**
     * select all rows in the warehouses table
     */
    public void selectAll(){
        String sql = "SELECT app_id, app_name, app_script, app_icon FROM apps";

        FileOutputStream fos = null;
        String filename = "tmp/tmp_icon";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // write binary stream into file
            File file = new File(filename);
            fos = new FileOutputStream(file);

            // loop through the result set
            while (rs.next()) {

                InputStream input = rs.getBinaryStream("app_icon");
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    fos.write(buffer);
                }

                System.out.println(rs.getInt("app_id") +  "\t" +
                        rs.getString("app_name") + "\t" +
                        rs.getString("app_script")+ "\t" );


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //--------insert---------

    public void insert(String app_name, String app_script, String iconFile) {

        String sql = "INSERT INTO apps(app_name, app_script, app_icon) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, app_name);
            pstmt.setString(2, app_script);

            pstmt.setBytes(3, readFile(iconFile));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //------------ delete ---------------------

    public void delete(String app_name) {
        String sql = "DELETE FROM apps WHERE app_name = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, app_name);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    //---------------- read icon----------------

    private byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }

    //-------------- upload icon ------------------

    public void updatePicture(int materialId, String filename) {
        // update sql
        String updateSQL = "UPDATE materials "
                + "SET picture = ? "
                + "WHERE id=?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            // set parameters
            pstmt.setBytes(1, readFile(filename));
            pstmt.setInt(2, materialId);

            pstmt.executeUpdate();
            System.out.println("Stored the file in the BLOB column.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

 /*   private void mySelect(){

        ArrayList appsList;

        appsList = Helper.getInstance().select_apps("", null);

        System.out.println(appsList);

        System.out.println(appsList.get(appsList.size()-1));
    }*/

    private void add_new_host_table(String host){

        host = "apps_" + host;

        String sql = "CREATE TABLE IF NOT EXISTS '"+host+"' (\n" +
                "\tapp_id INTEGER NULL,\n" +
                "\tapp_name VARCHAR(2000000000) NULL,\n" +
                "\tapp_script VARCHAR(2000000000) NULL,\n" +
                "\tapp_icon BLOB NULL,\n" +
                "\t\"type\" TEXT(2000000000) NULL,\n" +
                "\tCONSTRAINT APPS_PK PRIMARY KEY (app_id)\n" +
                ") ;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    //---get tables -----------

    private ArrayList<String> get_hosts() {

        ArrayList<String> tables_list = new ArrayList<>();

        try {

            Connection conn = this.connect();

            DatabaseMetaData dbmd = conn.getMetaData();

            String[] types = {"TABLE"};

            ResultSet rs = dbmd.getTables(null, null, "%", types);

            while (rs.next()) {

                tables_list.add(rs.getString("TABLE_NAME").replace("apps_", ""));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        tables_list.remove("sqlite_sequence");

        return tables_list;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBtest app = new DBtest();

       // ArrayList<String> list = app.getDatabaseMetaData();

     //   System.out.println(list);


    }

}