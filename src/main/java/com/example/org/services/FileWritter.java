package com.example.org.services;

import com.example.org.model.MyOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileWritter {
    private static ObjectOutputStream os;
    private static ObjectInputStream is;
    private static boolean value = false;
    private static File file = new File("log.bin");
    public static <D>void Write(D object) {
        fileExist();
        try {
            os.writeObject(object);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static <D>List<D> Read(){
        try {
            boolean validation = true;
            List<D> list = new ArrayList<>();
            FileInputStream fis = new FileInputStream(file);
            is = new ObjectInputStream(fis);
            while(fis.available()>0) {
                try{
                    D ob = (D) is.readObject();
                    list.add(ob);
                } catch (EOFException e){
                    e.printStackTrace();
                };
            }
            is.close();
            return list;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean fileExist(){
        try {
            if(file.length()>1){
                os = new MyOutputStream(new FileOutputStream(file, true));
            }else {
                os = new ObjectOutputStream(new FileOutputStream(file, true));
            }
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
