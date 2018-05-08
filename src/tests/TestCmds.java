package tests;

import utils.Helper;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class TestCmds {

    private void exec(){

        String cmd = "xfreerdp +clipboard /u:mamdouh /p:pipo300 /app:\"||1Password\" /app-name:1Password /v:Mamdouh-PC";
     //   String cmd = "gedit";
        Runtime run = Runtime.getRuntime();
        Process pr = null;
        try {
            pr = run.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            pr.waitFor();

           // pr.wait(30000);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = "";
        try {
            while ((line=buf.readLine())!=null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exec_file(){

        try{
            Process p = Runtime.getRuntime().exec("patches/1password");

            p.waitFor();

        }catch( IOException ex ){
            //Validate the case the file can't be accesed (not enought permissions)

        }catch( InterruptedException ex ){
            //Validate the case the process is being stopped by some external situation

        }
    }

    private void createFile(){

        File appFile = new File("patches/test");

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(appFile), "utf-8"))) {
            try {
                writer.write("ls -la");

                appFile.setExecutable(true);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    //--- process builder ---



    private void process_builder(){

        String[] args = new String[] {"/bin/bash", "-c", "xterm", "", ""};
        try {
            Process proc = new ProcessBuilder(args).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        TestCmds app = new TestCmds();


        app.process_builder();

        //  Helper.getInstance().execCmd_2nd("xfreerdp +clipboard /cert-tofu /t:\"192.168.122.96 user hammada\" /u:hammada /p:PiPo3000 /d:arab.local /v:192.168.122.96:3389");
    }

}
