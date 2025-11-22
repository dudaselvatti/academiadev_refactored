package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.UserRepository;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.entities.User;
import br.com.academiadev.domain.enums.SubscriptionPlan;

public class GerenciarPlanoAlunoUseCase {
    private final UserRepository repository;

    public GerenciarPlanoAlunoUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(String studentEmail, SubscriptionPlan newPlan) {
        User user = repository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (user instanceof Student) {
            ((Student) user).setSubscriptionPlan(newPlan);
            repository.save(user);
        } else {
            throw new RuntimeException("O email informado não pertence a um aluno.");
        }
    }
}