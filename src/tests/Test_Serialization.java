package tests;

import utils.Helper;

import java.util.HashMap;
import java.util.Map;

public class Test_Serialization {

    private void addSettings(){

        Map<String, Object> app_map = new HashMap<>();

        app_map.put("ip", "10.10.10.120");
        app_map.put("username", "kokowawa");
        app_map.put("password", "kokowawaPass");

        Helper.getInstance().saveSettings("host1", app_map);
    }

    private void loadSettings(String key){

        Map<String, Object> app_map;

        Object obj = Helper.getInstance().loadSettings(key);

        app_map = (Map<String, Object>) obj;

        System.out.println(app_map.get("username"));

    }

    public static void main(String[] args) {

        Test_Serialization test = new Test_Serialization();

      //  test.addSettings();

        Helper.getInstance().remove_host_from_Settings("host1");

        test.loadSettings("10.0.0.55");
        //test.loadSettings("host2");

    }
}
