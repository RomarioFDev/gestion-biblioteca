package dev.romario.proyecto.services;

import dev.romario.proyecto.exceptions.DuplicatePartnerException;
import dev.romario.proyecto.exceptions.PartnerNotFoundException;
import dev.romario.proyecto.models.Partner;
import dev.romario.proyecto.repositories.Repositories;

import java.util.List;

public class PartnerService {
    private final Repositories<Partner> partnerRepositories;

    public PartnerService(Repositories<Partner> partnerRepositories) {
        this.partnerRepositories = partnerRepositories;
    }

    public void register(Partner partner){
        validateData(partner);
        if (existsById(partner.getId())){
            throw new DuplicatePartnerException("El asociado ya existe");
        }
        this.partnerRepositories.create(partner);
    }

    public List<Partner> findAll(){
        return this.partnerRepositories.findAll();
    }

    public Partner findById(String id){
        requiredNotBlank(id, "El id no puede estar vacio");
        Partner partner = this.partnerRepositories.findById(id);
        if (partner == null){
            throw new PartnerNotFoundException("El asociado no existe");
        }
        return this.partnerRepositories.findById(id);
    }

    public List<Partner> findByName(String name){
        requiredNotBlank(name, "El nombre no puede estar vacio");
        List<Partner> partners = this.partnerRepositories.findAll().stream().filter(p -> p.getName().toLowerCase().contains(name.toLowerCase())).toList();
        if (partners.isEmpty()){
            throw new PartnerNotFoundException("No hay asociados con ese nombre");
        }
        return partners;
    }

    public void update(Partner partner){
        validateData(partner);
        if (!existsById(partner.getId())){
            throw new PartnerNotFoundException("El asociado no existe");
        }
        this.partnerRepositories.update(partner);
    }

    public void delete(String id){
        requiredNotBlank(id, "El id no puede estar vacio");
        if (!existsById(id)){
            throw new PartnerNotFoundException("El asociado no existe");
        }
        this.partnerRepositories.deleteById(id);
    }

    private void validateData(Partner partner){
        if (partner == null){
            throw new IllegalArgumentException("El prestador no puede ser nulo");
        }
        requiredNotBlank(partner.getId(), "El id no puede estar vacio");
        requiredNotBlank(partner.getName(), "El nombre no puede estar vacio");
        requiredNotBlank(partner.getEmail(), "El email no puede estar vacio");
    }

    private void requiredNotBlank(String value, String menssage){
        if (value == null || value.isBlank()){
            throw new IllegalArgumentException(menssage);
        }
    }

    private boolean existsById(String id){
        return this.partnerRepositories.findAll().stream().anyMatch(l -> l.getId().equals(id));
    }

}
