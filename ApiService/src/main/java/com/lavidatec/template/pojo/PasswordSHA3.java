/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lavidatec.template.pojo;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
/**
 * 
 * @author Mikey
 */
public class PasswordSHA3 {
    public String createHash(String input){
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
        byte[] digest = digestSHA3.digest(input.getBytes());

        System.out.println("SHA3-512 = " + Hex.toHexString(digest));
        return Hex.toHexString(digest);
    }
    
    public static void main(String[] args) throws Exception {
        System.out.print(PasswordHash.createHash("123"));
    }
}
