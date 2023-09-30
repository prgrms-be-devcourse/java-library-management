package com.programmers.app.file;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.programmers.app.timer.Timer;
import com.programmers.app.timer.dto.TimerJson;

public class TimerFileManager implements FileManager<Queue<Timer>, Queue<Timer>> {

    private final String filePath;
    private final Gson gson;

    public TimerFileManager(String filePath, Gson gson) {
        this.filePath = filePath;
        this.gson = gson;
    }

    @Override
    public Queue<Timer> loadDataFromFile() throws IOException {
        FileReader fileReader = new FileReader(filePath);

        TimerJson[] timersFromFile = Optional.ofNullable(gson.fromJson(fileReader, TimerJson[].class))
                .orElse(new TimerJson[]{});

        fileReader.close();

        return Arrays.stream(timersFromFile)
                .map(TimerJson::toTimer)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void save(Queue<Timer> timers) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);

            Queue<TimerJson> timerJsons = timers
                    .stream()
                    .map(Timer::toTimerJson)
                    .collect(Collectors.toCollection(LinkedList::new));

            Type type = new TypeToken<Queue<TimerJson>>(){}.getType();

            gson.toJson(timerJsons, type, fileWriter);
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to write timers.json for some reason. System exits");
            System.exit(1);
        }
    }
}
