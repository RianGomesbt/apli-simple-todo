package com.projecterian.demo.services;

import com.projecterian.demo.models.User;
import com.projecterian.demo.repositories.TaskRepository;
import com.projecterian.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public User buscarPeloIndentificador(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("Usuario nao encontrado " + id + "Tipo: " + User.class.getName()));
    }

    @Transactional
    public User criarUsuario(User obj){
        obj.setId(null);
        obj = this.userRepository.save(obj);
        this.taskRepository.saveAll(obj.getTask());
        return obj;
    }

    @Transactional
    public User update(User obj){
        User newObj = buscarPeloIndentificador(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    @Transactional
    public void delete(Long id){
        buscarPeloIndentificador(id);
        try {
            this.userRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Não é possivel excluir, há entidades relacionadas!");
        }

    }
}
