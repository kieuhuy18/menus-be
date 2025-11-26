package com.app.user.controller;

import com.app.user.entity.Role;
import com.app.user.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users/roles")
public class RoleController {
    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Role> create(@Valid @RequestBody Role body) {
        Role created = service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<Role> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> get(@PathVariable String id) {
        Role found = service.findById(id);
        if (found == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(found);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> update(@PathVariable String id, @Valid @RequestBody Role update) {
        Role updated = service.update(id, update);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = service.delete(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}

