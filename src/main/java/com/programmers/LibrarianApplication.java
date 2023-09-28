package com.programmers;

import com.programmers.app.App;
import com.programmers.app.instances.GeneralInstances;

public class LibrarianApplication {
    public static void main(String[] args) {

        GeneralInstances generalInstances = new GeneralInstances();
        App app = new App(generalInstances);

        app.run();
    }
}
