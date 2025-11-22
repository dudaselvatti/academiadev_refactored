package br.com.academiadev.infrastructure.persistence;

import br.com.academiadev.application.repositories.UserRepository;
import br.com.academiadev.domain.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private final Map<String, User> database = new HashMap<>();

    @Override
    public void save(User user) {
        database.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(database.get(email));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(database.values());
    }
}