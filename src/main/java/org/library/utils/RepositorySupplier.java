package org.library.utils;

import org.library.repository.Repository;

@FunctionalInterface
public interface RepositorySupplier {

    Repository create(String path);
}
