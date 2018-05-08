package tests;

import java.net.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

class TelnetClient {

    /*private boolean check_server_connection(String host, int port) {

        Socket rdp_socket = null;

        try {

              rdp_socket = new Socket(host, port);

             if(rdp_socket.isConnected()){

                 System.out.println(host + " is Online");

                 return true;

             }

        }
        catch (NoRouteToHostException e){

            System.out.println(host + " is Offline");

            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }*/

    //---------- timeout method ----------

    private boolean check_server_connection(String host, int port, int timeout) {

        timeout = timeout * 1000;

        TimerTask task = new TimerTask(){
            public void run(){

                if (isFastConnected(host,port)){

                    System.out.println("Online...");

                   return;

                }else{

                    System.out.println("Offline...");

                    return;
                }

            }
        };

        Timer timer = new Timer();
        timer.schedule( task, timeout );

        //--------- connection test here -----------------

        Socket rdp_socket = null;

        try {

            rdp_socket = new Socket(host, port);

            if(rdp_socket.isConnected()){

                System.out.println(host + " is Online");

                return true;

            }

        }
        catch (NoRouteToHostException e){

            System.out.println(host + " is Offline");

            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //--------- end of connection test ----------------

        timer.cancel();

        return false;

    }

    //------- simple test ----

    private boolean isFastConnected(String host, int port){

        Socket rdp_socket = null;

        try {

            rdp_socket = new Socket(host, port);

            if(rdp_socket.isConnected()){

                System.out.println(host + " is Online");

                return true;

            }

        }
        catch (NoRouteToHostException e){

            System.out.println(host + " is Offline");

            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    // ----- main ----------
    public static void main(String args[]) {

        new TelnetClient().check_server_connection("192.168.122.5", 3389, 5);

    }
}