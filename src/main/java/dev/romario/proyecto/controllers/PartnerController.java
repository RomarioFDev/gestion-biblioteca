package dev.romario.proyecto.controllers;

import dev.romario.proyecto.exceptions.DuplicatePartnerException;
import dev.romario.proyecto.exceptions.PartnerNotFoundException;
import dev.romario.proyecto.models.Partner;
import dev.romario.proyecto.services.PartnerService;

import java.util.List;

public class PartnerController {
    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    public void register(Partner partner){
        try {
            this.partnerService.register(partner);
            System.out.println("El asociado fue registrado correctamente");
        } catch (IllegalArgumentException | DuplicatePartnerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void findAll(){
        List<Partner> partners = this.partnerService.findAll();
        if (partners.isEmpty()){
            System.out.println("No hay asociados agregados");
        }
        partners.forEach(p -> System.out.println(p.getId() + " " + p.getName() + " Email: " + p.getEmail()));
    }

    public void findById(String id){
        try {
            Partner partner = this.partnerService.findById(id);
            System.out.println(partner.getId() + " " + partner.getName() + " " + "(Email: " + partner.getEmail() + ")");
        } catch (IllegalArgumentException | PartnerNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void findByName(String name){
        try {
            List<Partner> partners = this.partnerService.findByName(name);
            partners.forEach(p -> System.out.println(p.getId() + " " + p.getName() + " Email: " + p.getEmail()));
        } catch (IllegalArgumentException | PartnerNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void update(Partner partner){
        try {
            this.partnerService.update(partner);
            System.out.println("Asociado actualizado correctamente");
        }catch (IllegalArgumentException | PartnerNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void delete(String id){
        try {
            this.partnerService.delete(id);
            System.out.println("Asociado eliminado correctamente");
        }catch (IllegalArgumentException | PartnerNotFoundException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
