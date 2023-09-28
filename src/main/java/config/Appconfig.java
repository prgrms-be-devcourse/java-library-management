package config;

import input.ConsoleInput;
import input.Input;
import output.ConsoleOutput;
import output.Output;
import repository.MemoryRepository;
import repository.Repository;

public class Appconfig {

    public Input getInput(){
        return new ConsoleInput();
    }
    public Output getOutput(){
        return new ConsoleOutput();
    }
    public Repository getRepository(){
        return new MemoryRepository();
    }
}
