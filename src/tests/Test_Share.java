package tests;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import utils.Helper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

public class Test_Share {

    static String url = "smb://10.0.0.55";



    private Set<SmbFile> shareTest(String url){

        Set<SmbFile> resultList = new HashSet<>();

        //  smb://mamdouh:pipo300@192.168.122.237/zShare

       // String url = "smb://10.0.0.55";

      //  Set<SmbFile> resultList = new HashSet<>();

        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, "douha", "pipo300");

        SmbFile dir = null;

        try {

            dir = new SmbFile(url, auth);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            for (SmbFile f : dir.listFiles())
            {
                if(!f.getName().contains("$") && !f.getName().contains("Users")){

                    resultList.add(f);

                    if(f.isDirectory()){

                        resultList.addAll(shareTest(f.getCanonicalPath()));

                       // shareTest(f.getCanonicalPath());
                    }

                }
            }
        } catch (SmbException e) {
            e.printStackTrace();
        }

      //  System.out.println(resultList);

        return resultList;

    }


    //---------

    public static List<SmbFile> list_share(String url) throws SmbException {

        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, "douha", "pipo300");

        SmbFile dir = null;

        try {

            dir = new SmbFile(url, auth);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

      //  File directory = new File(directoryName);

        List<SmbFile> resultList = new ArrayList<>();

        // get all the files from a directory

        SmbFile[] smb_List = dir.listFiles();

        resultList.addAll(Arrays.asList(smb_List));

        for (SmbFile file : smb_List) {

                if(!file.getName().contains("$")){

            if (file.isFile()) {

                System.out.println(file.getName());

            } else if (file.isDirectory()) {

                resultList.addAll(list_share(file.getName()));
            }

           }
        }

        //System.out.println(fList);

        return resultList;
    }

    public static void main(String[] args) throws SmbException {

        //Set<SmbFile> dirs = new Test_Share().shareTest(url);

        Set<SmbFile> dirs = Helper.getInstance().getShares(url, "douha", "pipo300", null);

        System.out.println(dirs);

        for(SmbFile sf : dirs){

            System.out.println(sf.getName());
        }

       /* for(SmbFile sf : dirs){

            if(sf.isFile()){

                System.out.println(sf.getCanonicalPath()+ " ---> File");

            }else{

                System.out.println(sf.getCanonicalPath());

            }

        }*/

      //  System.out.println(Test_Share.list_share(url));

        //Main.main(new String[]{""});
    }
}
