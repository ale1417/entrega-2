package com.coderhouse.services;

import com.coderhouse.interfaces.CRUDInterface;
import com.coderhouse.models.Cliente;
import com.coderhouse.repositories.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class ClienteService implements CRUDInterface<Cliente, Long> {
    @Autowired private ClienteRepository repo;
    public List<Cliente> findAll(){ return repo.findAll(); }
    public Cliente findById(Long id){ return repo.findById(id).orElseThrow(()-> new IllegalArgumentException("Cliente no encontrado")); }
    public Cliente save(Cliente c){ return repo.save(c); }
    public Cliente update(Long id, Cliente c){ Cliente a = findById(id); a.setNombre(c.getNombre()); a.setEmail(c.getEmail()); a.setDni(c.getDni()); return repo.save(a); }
    public void deleteById(Long id){ if(!repo.existsById(id)) throw new IllegalArgumentException("Cliente no encontrado"); repo.deleteById(id); }
}
