import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LexicalAnalyzer {

    public static void printResult(String token, String lexeme)
    {
        System.out.println("Next token is " + token + "\tNext lexeme is " + lexeme);
    }

    public static void processLine(String sourceLine)
    {
        sourceLine = sourceLine.toLowerCase();  // convert all characters to lowercase since EtuLang is case insensitive
        sourceLine = sourceLine.replaceAll("%.*?%", "");    // get rid of words between comment characters
        sourceLine = sourceLine.replaceAll("\\s+", " ");    // remove duplicate whitespaces

        String[] tokens = sourceLine.split("(?<=[():=+-/*;<>,: ])|(?=[():=+-/*;<>,: ])"); // inclusively split string into special tokens

        for(int tokenIndex = 0; tokenIndex < tokens.length; ) {
            String token = tokens[tokenIndex];

//            System.out.println(token);
            if (token == " ")
            {
                // nothing to do, ingore that
            }
            else if (token.length() > 15)
            {
                printResult("UNKNOWN", token);
            }
            else if ((token.equals("begin")) || (token.equals("end")) || (token.equals("if")) ||
                    (token.equals("then")) || (token.equals("else")) || (token.equals("while")) ||
                    (token.equals("program")) || (token.equals("integer")) || (token.equals("var")))
            {
                printResult("RES_WORD", token);
            }
            else if (token.equals("("))
            {
                printResult("LPARANT", token);
            }
            else if (token.equals(")"))
            {
                printResult("RPARANT", token);
            }
            else if (token.equals(":"))
            {
                // check next symbol
                if (tokens[tokenIndex+1].equals("="))
                {
                    printResult("ASSIGNM", token+tokens[tokenIndex+1]);
                    tokenIndex++;
                }
                else
                {
                    printResult("COLON", token);
                }
            }
            else if (token.equals("+"))
            {
                printResult("ADD", token);
            }
            else if (token.equals("-"))
            {
                printResult("SUBT", token);
            }
            else if (token.equals("/"))
            {
                printResult("DIV", token);
            }
            else if (token.equals("*"))
            {
                printResult("MULT", token);
            }
            else if (token.equals(";"))
            {
                printResult("SEMICOLON", token);
            }
            else if (token.equals("<"))
            {
                // check next symbol
                if (tokens[tokenIndex+1].equals(">"))
                {
                    printResult("NOTEQ", token+tokens[tokenIndex+1]);
                    tokenIndex++;
                }
                else if (tokens[tokenIndex+1].equals("="))
                {
                    printResult("LESS_EQ", token+tokens[tokenIndex+1]);
                    tokenIndex++;
                }
                else
                {
                    printResult("LESS", token);
                }
            }
            else if (token.equals(">"))
            {
                // check next symbol
                if (tokens[tokenIndex+1].equals("="))
                {
                    printResult("GRE_EQ", token+tokens[tokenIndex+1]);
                    tokenIndex++;
                }
                else
                {
                    printResult("GREATER", token);
                }
            }
            else if (token.equals(","))
            {
                printResult("COMMA", token);
            }
            else if (token.equals(":"))
            {
                printResult("COLON", "token");
            }
            else
            {
                // check if string is numeric
                try
                {
                    Double.parseDouble(token);
                    printResult("INT_LIT", token);
                }
                catch(NumberFormatException e)
                {
                    if (Character.isLetter(token.charAt(0)))
                    {
                        printResult("identifier", token);
                    }
                    // it is not numeric
                }
            }

            tokenIndex++;
        }

        printResult("EOF", "end of file");
    }

    public static void main(String[] args)
    {
//        String inputFileName = args[0];
        String inputFileName = "C:\\Users\\bbuyu\\IdeaProjects\\LexicalAnalyzer\\out\\production\\LexicalAnalyzer\\Sample1.ETU"; // TODO: remove this
        String readLine = null;
        String lines = null;

        try {
            File inputFile = new File(inputFileName);
            StringBuilder sBuilder = new StringBuilder();
            BufferedReader bReader = new BufferedReader(new FileReader(inputFile));

            while ((readLine = bReader.readLine()) != null) {
                sBuilder.append(readLine);
                sBuilder.append(" ");   // put whitespace instead of new line
            }

            bReader.close();
            lines = sBuilder.toString();
            processLine(lines);  // TODO: pass by ref
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
