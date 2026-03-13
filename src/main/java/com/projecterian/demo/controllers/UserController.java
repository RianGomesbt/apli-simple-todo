package com.projecterian.demo.controllers;

import com.projecterian.demo.models.User;
import com.projecterian.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    //buscar dados
    @GetMapping("/{id}")
    public ResponseEntity<User> findByid(@PathVariable Long id){
        User userObj = this.userService.buscarPeloIndentificador(id);
        return ResponseEntity.ok().body(userObj);
    }

    //criar dados
    @PostMapping
    @Validated(User.CreateUser.class)
    public ResponseEntity<Void> create(@Valid @RequestBody User obj){
        this.userService.criarUsuario(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    //Atualizar dados
    @PutMapping("/{id}")
    @Validated(User.UpdateUser.class)
    public ResponseEntity<Void> update(@Valid @RequestBody User obj, @PathVariable Long id){
        obj.setId(id);
        this.userService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
