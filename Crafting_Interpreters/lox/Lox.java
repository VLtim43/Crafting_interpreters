package lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
    static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox <script>");
            System.exit(0);
        } else if (args.length == 1) {
            readFile(args[0]);
        } else {
            readPrompt();
        }
    }

    private static void readFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hadError)
            System.exit(65);
    }

    private static void readPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader read = new BufferedReader(input);

        for (;;) {
            System.out.println("> ");
            String line = read.readLine();
            if (line == null)
                break;
            run(line);
        }
    }

    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String location, String message) {
        System.err.println("[line " + line + "] Error" + location + ": " + message);
        hadError = true;
    }
}