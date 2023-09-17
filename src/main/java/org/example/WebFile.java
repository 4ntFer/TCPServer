package org.example;

import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WebFile implements Serializable {
    private String name;
    private double size;
    private  byte[] content;
    private byte[] hash;

    public WebFile(String dir){
        File file = new File(dir);

        //Tranformando o conteudo dos arquivos em bytes
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("Falha ao criar um FileInputStream!");
            throw new RuntimeException(e);
        }

        content = new byte[(int) file.length()];

        try {
            fis.read(content);
            fis.close();
        } catch (IOException e) {

            System.out.println("ERRO AO CONVERTER O ARQUIVO PARA BYTES!");
            throw new RuntimeException(e);
        }



        name = dir.substring(8, dir.length());
        size = file.length()/1024;
        try {
            hash = calcHash();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Falha ao calcular o hash do arquivo!");
            throw new RuntimeException(e);
        }

    }

    public String toString(){
        return name;
    }

    public byte[] getContent() {
        return content;
    }

    public double getSize() {
        return size;
    }

    public String getName(){
        return name;
    }

    public byte[] calcHash() throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

        messageDigest.update(content);
        return messageDigest.digest();
    }

    public byte[] getHash(){
        return hash;
    }

    // método retirado da internet
    public String getHashToString(){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            int parteAlta = ((hash[i] >> 4) & 0xf) << 4;
            int parteBaixa = hash[i] & 0xf;
            if (parteAlta == 0) s.append('0');
            s.append(Integer.toHexString(parteAlta | parteBaixa));
        }
        return s.toString();
    }
}
