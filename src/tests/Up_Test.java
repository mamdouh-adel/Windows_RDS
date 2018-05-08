package tests;

public class Up_Test {

   // String url = "smb://10.0.0.55/zzzShare/sub1/sb33";

    String url_top_level = "smb://10.0.0.55";

    private String Up_Url(String current_url){

        String[] tmp = current_url.split("/");

        StringBuilder rest = new StringBuilder();

        for(int i=0; i<tmp.length; i++){

            if(i>2){

                if(i < tmp.length - 1){

                    rest.append("/" + tmp[i]);
                }
            }
        }

        current_url = url_top_level + rest + "/";

        return current_url;
    }


    //----main---

    public static void main(String[] args) {

        Up_Test app = new Up_Test();

        System.out.println(app.Up_Url("smb://10.0.0.55/zzzShare/sub1/sb33"));
    }
}
