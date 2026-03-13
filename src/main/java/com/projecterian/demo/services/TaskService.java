package com.projecterian.demo.services;

import com.projecterian.demo.models.Task;
import com.projecterian.demo.models.User;
import com.projecterian.demo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task buscarPorId(Long id){
        Optional<Task> task = this.taskRepository.findById(id);
        return  task.orElseThrow(()-> new RuntimeException("Tarefa não encontrada!"));
    }

    @Transactional
    public Task criarTarefa(Task obj){
        User user = this.userService.buscarPeloIndentificador(obj.getUser().getId());
        obj.setId(obj.getId());
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj){
        Task newObj = buscarPorId(obj.getId());
        newObj.setDescricao();
        return this.taskRepository.save(newObj);
    }

    @Transactional
    public void deletar(Long id){
        buscarPorId(id);
        try{
            this.taskRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Não é possivel deletar pois a entidades relacionadas");
        }
    }

}
