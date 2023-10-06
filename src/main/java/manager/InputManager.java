package manager;

import exception.EmptyInputException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InputManager {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public String getInput() throws Exception {
        System.out.print("> ");
        String value = br.readLine().strip();
        if (value.isBlank()) throw new EmptyInputException();
        return value;
    }
}
