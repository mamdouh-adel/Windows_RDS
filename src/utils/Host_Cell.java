package utils;

import AppStart.Main;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Host_Cell extends HBox {

    Label host = new Label();
    Label status = new Label();

    Label user = new Label();

    int port;

    public Label getHost() {
        return host;
    }

    public Label getUser() {
        return user;
    }

    public Host_Cell(String host_text, String username, String status_text) {
        super();

        host.setText(host_text);

        host.setMaxWidth(100.0);

        HBox.setHgrow(host, Priority.ALWAYS);

        user.setText(username);

        user.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(user, Priority.ALWAYS);

        status.setText(status_text);

        this.getChildren().addAll(host, user, status);

        port = getPort();

        user.setTextFill(Color.valueOf("#D89A5B"));

     //   user.setTextFill(Color.valueOf("#5BCDD8"));

        check_server_connection();
    }


    private int getPort(){

        Map<String, Object> map = (Map<String, Object>) Helper.getInstance().loadSettings(host.getText() + "_" + user.getText());

        return (int) map.get("port");
    }

    //--------- check server connection auto connect -----------------

    private void check_server_connection() {

        Task check_rdp_task = new Task() {
            @Override
            protected Object call() throws Exception {

                Socket rdp_socket = null;

                while (Main.main_thread.isAlive()){

                    //--------- time task part --------

                    TimerTask timeTask = new TimerTask(){
                        public void run(){

                            Platform.runLater(()->{

                                status.setText("Offline");

                                status.setTextFill(Color.valueOf("#D12525"));
                            });

                            return;
                        }
                    };

                    Timer timer = new Timer();
                    timer.schedule( timeTask, 10*1000 );

                    //----------------connection test part --------------------

                    try {

                        rdp_socket = new Socket(host.getText(), port);

                        if(rdp_socket.isConnected()){

                            Platform.runLater(()->{

                                status.setText("Online");

                                status.setTextFill(Color.valueOf("#12D134"));
                            });

                         //   System.out.println(host + " is Online");
                        }

                    }

                    catch (ConnectException e){

                    //    System.out.println("ConnectException.........");

                        Platform.runLater(()->{

                            status.setText("Offline");

                            status.setTextFill(Color.valueOf("#D12525"));
                        });
                    }
                    catch (NoRouteToHostException e){

                     //   System.out.println(host + " is Offline");

                        Platform.runLater(()->{

                            status.setText("Offline");

                            status.setTextFill(Color.valueOf("#D12525"));
                        });
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                    Thread.sleep(5000);

                    timer.cancel();
                }

                //------------ end of connection test part -----------

                return null;
            }
        };

        new Thread(check_rdp_task).start();
    }
}

