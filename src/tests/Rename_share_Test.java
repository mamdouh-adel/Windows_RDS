package tests;

public class Rename_share_Test {

    private String rename_share(String url){

        String tmp[] = url.split("/");

        return tmp[tmp.length - 1];
    }

    //--- main

    public static void main(String[] args) {

        String url = "10.0.0.55/zzzShare/sub1/gdsd/mbnbn/";

        String newName = new Rename_share_Test().rename_share(url);

        System.out.println(newName);

    }
}
