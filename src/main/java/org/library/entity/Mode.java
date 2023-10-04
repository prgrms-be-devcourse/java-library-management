package org.library.entity;

import org.library.repository.ApplicationRepository;
import org.library.repository.Repository;
import org.library.repository.TestRepository;
import org.library.utils.RepositorySupplier;

import java.util.function.Supplier;

public enum Mode {
    APPLICATION(1,"일반 모드", ApplicationRepository::new),
    TEST(2, "테스트 모드", TestRepository::new);

    private int num;
    private String name;
    private RepositorySupplier repositorySupplier;

    Mode(int num, String name, RepositorySupplier repositorySupplier) {
        this.num = num;
        this.name = name;
        this.repositorySupplier = repositorySupplier;
    }

    public boolean isEqual(int inputNum){
        return this.num == inputNum;
    }

    public String getName() {
        return name;
    }

    public Repository getRepository(String path) {
        return repositorySupplier.create(path);
    }

    @Override
    public String toString() {
        return num + ". " + name;
    }
}
