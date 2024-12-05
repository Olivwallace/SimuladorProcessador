/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChipSimulator.Uteis;

import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    
    public static String[] parseInstruction(String instruction) {
        // Regex para instruções do tipo R (ex: add $r1, $r2, $r3)
        String regexR = "(\\w+) \\$(\\w+), \\$(\\w+), \\$(\\w+)";
        // Regex para instruções do tipo I (ex: addi $r1, $r2, 100)
        String regexI = "(\\w+) \\$(\\w+), \\$(\\w+), (-?\\d+)";
        // Regex para instruções com offset (ex: lw $r1, 0($r2))
        String regexOffset = "(\\w+) \\$(\\w+), (-?\\d+)\\(\\$(\\w+)\\)";

        // Primeiro tenta combinar com o formato tipo R
        Pattern patternR = Pattern.compile(regexR);
        Matcher matcherR = patternR.matcher(instruction);
        if (matcherR.matches()) {
            String opCode = matcherR.group(1);
            String reg1 = matcherR.group(2);
            String reg2 = matcherR.group(3);
            String reg3 = matcherR.group(4);
            return new String[]{opCode, reg1, reg2, reg3};
        }

        // Depois tenta combinar com o formato tipo I
        Pattern patternI = Pattern.compile(regexI);
        Matcher matcherI = patternI.matcher(instruction);
        if (matcherI.matches()) {
            String opCode = matcherI.group(1);
            String reg1 = matcherI.group(2);
            String reg2 = matcherI.group(3);
            String immediate = matcherI.group(4);
            return new String[]{opCode, reg1, reg2, immediate};
        }

        // Por último tenta combinar com o formato offset
        Pattern patternOffset = Pattern.compile(regexOffset);
        Matcher matcherOffset = patternOffset.matcher(instruction);
        if (matcherOffset.matches()) {
            String opCode = matcherOffset.group(1);
            String reg1 = matcherOffset.group(2);
            String offset = matcherOffset.group(3);
            String reg2 = matcherOffset.group(4);
            return new String[]{opCode, reg1, offset, reg2};
        }

        // Se nenhum formato for compatível, retorna erro
        return null;
    }
}
