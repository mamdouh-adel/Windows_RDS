package utils;

import javafx.concurrent.Task;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbAuthException;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import org.omg.CORBA.TIMEOUT;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Helper {


    //  ---- detect OS ---

    private static final String OS = System.getProperty("os.name");

    public static final String SYS_FILE_MANAGER = getFileManager_cmd();

    public static final String FreeRDP_command = getFreeRDP_cmd();

    private static Helper helper;

    Map<String, Object> settingsMap;

    private FileInputStream fis;
    private ObjectInputStream ois;
    private FileOutputStream fos;
    private ObjectOutputStream oos;

    private Helper() {

        settingsMap = new HashMap<>();

        settingsMap = initSettingsMap();

       // System.out.println(OS);
    }

    public static Helper getInstance() {

        if (helper == null) {
            helper = new Helper();
        }

        return helper;

    }

    //------------execute command---------------


    public void kill_cmd(){

        try {

            //Runtime.getRuntime().exec("taskkill /F /IM <processname>.<extension>"); // windows

            Runtime.getRuntime().exec("killall " + FreeRDP_command); // linux


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String execCmd(String cmd) {

        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(cmd);

        } catch (IOException e) {
            e.printStackTrace();
        }
        java.io.InputStream is = proc.getInputStream();
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        String val = "";
        if (s.hasNext()) {
            val = s.next();

        } else {
            val = "";
        }

        return val;
    }

    //--- new cmd exec -----

    public int execCmd_2nd(String cmd) {

        Process proc = null;
        try {

            proc = Runtime.getRuntime().exec(cmd);

        } catch (IOException e) {
            e.printStackTrace();
        }


        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

// read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s = null;
        try {
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

// read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        try {
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return proc.exitValue();
    }

//======== exec script file =============

    public void exec_file(String script_file){

        Process p = null;

        try{

             p = Runtime.getRuntime().exec("scripts/" + script_file);

            System.out.println("scripts/" + script_file);

            p.waitFor();

        }catch( IOException ex ){
            //Validate the case the file can't be accesed (not enought permissions)

            System.out.println("io.....");

        }catch( InterruptedException ex ){
            //Validate the case the process is being stopped by some external situation

            System.out.println("Interr.....");
        }

        //------ output ----

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(p.getErrorStream()));

// read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s = null;
        try {
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

// read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        try {
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //--- exec test-------------------

    public void exec_test_file(){

        try{
            Process p = Runtime.getRuntime().exec("test/test_app_share");

            p.waitFor();

        }catch( IOException ex ){
            //Validate the case the file can't be accesed (not enought permissions)

        }catch( InterruptedException ex ){
            //Validate the case the process is being stopped by some external situation

        }
    }

    //------------------serialization------------------------

    public void saveSettings(String key, Object obj) {

        settingsMap.put(key, obj);

        try {

            fos = new FileOutputStream("app.settings");
            oos = new ObjectOutputStream(fos);

            oos.writeObject(settingsMap);

            oos.flush();
            fos.flush();

            oos.close();
            fos.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Object loadSettings(String key) {

        Map<String, Object> map = null;

        try {

            fis = new FileInputStream("app.settings");
            ois = new ObjectInputStream(fis);

            map = (Map<String, Object>) ois.readObject();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return map.get(key);
    }


    public void remove_host_from_Settings(String host) {

        Map<String, Object> map = null;

        try {

            fis = new FileInputStream("app.settings");
            ois = new ObjectInputStream(fis);

            map = (Map<String, Object>) ois.readObject();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }

        map.remove(host);

        save_after_remove_Settings(map);
    }

    private void save_after_remove_Settings(Object obj) {

        try {

            fos = new FileOutputStream("app.settings");
            oos = new ObjectOutputStream(fos);

            oos.writeObject(obj);

            oos.flush();
            fos.flush();

            oos.close();
            fos.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    private Map<String, Object> initSettingsMap() {

        Map<String, Object> map = null;

        try {

            FileInputStream fis = new FileInputStream("app.settings");
            ois = new ObjectInputStream(fis);

            map = (Map<String, Object>) ois.readObject();

        } catch (FileNotFoundException ex) {

            map = new HashMap<>();

            saveSettings("first run date", LocalDateTime.now());

            return map;

            //   Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return map;
    }


    //-------------------------------db part-------------------------------------
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
    //--------insert---------

    public boolean insert(String host,String user, String app_name, String app_script, String iconFile, String type) {

        String host_db = "apps_" + host +"_" + user;

        String sql = "INSERT INTO '"+host_db+"'(app_name, app_script, app_icon, type) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, app_name);
            pstmt.setString(2, app_script);

            pstmt.setBytes(3, readFile(iconFile));

            pstmt.setString(4, type);

            pstmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    //-------- update ---------

    public boolean update(String host, String user, int app_id, String app_name, String app_script, String iconFile, String type) {

        String host_db = "apps_" + host + "_" + user;

        String sql = "UPDATE '"+host_db+"' SET app_name = ?, app_script = ?, app_icon = ?, type = ? WHERE app_id =" + app_id;

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, app_name);
            pstmt.setString(2, app_script);

            pstmt.setBytes(3, readFile(iconFile));

            pstmt.setString(4, type);

            pstmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    //------------ delete ---------------------

    public boolean delete(String host, String user, String app_name) {

        String host_db = "apps_" + host + "_" + user;

        String sql = "DELETE FROM '"+host_db+"' WHERE app_name = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, app_name);
            // execute the delete statement
            pstmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;

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

    //------------ select ---------------

    public ArrayList<Map> select_apps(String host, String user,  String certin_app){

        String host_db = "apps_" + host + "_" + user;

        String where_part = "";

        if(certin_app != null){

            where_part = " WHERE app_name=\"" + certin_app + "\"";
        }

        ArrayList appArrList = new ArrayList();
        Map<String, Object> appMap = null;

        String app_script = null;
        String app_name = null;
        int app_id = 0;

        String sql = "SELECT app_id, app_name, app_script, app_icon, type FROM '"+host_db+"'"+ where_part;

        FileOutputStream fos = null;
        String iconsDir = "app_icons/";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {

                // write binary stream into file
                File file = new File(iconsDir + host + "_" + user + "_" + rs.getString("app_name"));
                fos = new FileOutputStream(file);

                InputStream input = rs.getBinaryStream("app_icon");
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {

                    fos.write(buffer);
                }

              appMap = new HashMap<>();

              appMap.put("app_id", rs.getInt("app_id"));
              appMap.put("app_name", rs.getString("app_name"));
              appMap.put("app_script", rs.getString("app_script"));

              appMap.put("type", rs.getString("type"));

            appArrList.add(appMap);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return appArrList ;
    }

    //----- add new host apps table-------------------------

    public void create_new_host_table(String host, String username){

        String host_db = "apps_" + host + "_" + username;

        String sql = "CREATE TABLE IF NOT EXISTS '"+host_db+"' (\n" +
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

    //----- drop new host apps table-------------------------

    public void drop_host_table(String host){

        String host_db = "apps_" + host;

        String sql = "DROP TABLE IF EXISTS '"+host_db+"';";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //---get hosts from table names -----------

    public ArrayList<String> get_hosts_users() {

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

    //------------------------------ Share Part ------------------------

    public Set<SmbFile> getShares(String url, String username, String password, String domain){

        Set<SmbFile> resultList = new HashSet<>();

        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domain, username, password);

        SmbFile dir = null;

        try {

            dir = new SmbFile(url, auth);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            for (SmbFile smbFile : dir.listFiles())
            {
                    if(smbFile.isDirectory()){

                        resultList.add(smbFile);
                    }
            }
        }
        catch (SmbAuthException ee){

            System.out.println("No Permission ..........");
        }
        catch (SmbException e) {
            e.printStackTrace();
        }

        //  System.out.println(resultList);

        return resultList;
    }

    //------------------ filemanager command part -----------

    private static String getFileManager_cmd(){

        return "dolphin";
    }


    private static String getFreeRDP_cmd(){

        File FreeRDP_file = null;

        if(OS.equalsIgnoreCase("Linux")){

        /*    FreeRDP_file = new File("FreeRDP/client/X11/xfreerdp");

            return FreeRDP_file.getAbsolutePath();*/

        return "xfreerdp";


        }else if(OS.equalsIgnoreCase("Windows")){

          /*  FreeRDP_file = new File("FreeRDP/client/Windows/wfreerdp");

            return FreeRDP_file.getAbsolutePath();*/

          return "wfreerdp";

        }

        return "";
    }
}

