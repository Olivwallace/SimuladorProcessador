/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChipSimulator.Uteis;

import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author Walla
 */
public class StringUteis {
    public static String randomID(int length){
        String values = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(values.length());
            sb.append(values.charAt(index));
        }
        return sb.toString();
    }
}
