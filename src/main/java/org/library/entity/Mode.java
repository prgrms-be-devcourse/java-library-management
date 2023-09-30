package org.library.entity;

import org.library.repository.ApplicationRepository;
import org.library.repository.Repository;
import org.library.repository.TestRepository;

public enum Mode {
    APPLICATION(1,"일반 모드", new ApplicationRepository()),
    TEST(2, "테스트 모드", new TestRepository());

    Mode(int num, String name, Repository repository) {
        this.num = num;
        this.name = name;
        this.repository = repository;
    }

    private int num;
    private String name;
    private Repository repository;

    public boolean isEqual(int inputNum){
        return this.num == inputNum;
    }

    public String getName() {
        return name;
    }

    public Repository getRepository() {
        return repository;
    }

    @Override
    public String toString() {
        return num + ". " + name;
    }
}
