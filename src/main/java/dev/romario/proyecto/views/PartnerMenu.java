package dev.romario.proyecto.views;

import dev.romario.proyecto.controllers.PartnerController;
import dev.romario.proyecto.models.Partner;

import java.util.Scanner;

public class PartnerMenu {
    private final PartnerController partnerController;
    private final Scanner scanner;

    public PartnerMenu(PartnerController partnerController, Scanner scanner) {
        this.partnerController = partnerController;
        this.scanner = scanner;
    }

    public void show(){
        while (true){
            System.out.println("------- MENU DE SOCIOS ------");
            System.out.println("1. Registrar socio");
            System.out.println("2. Listar todos los socios");
            System.out.println("3. Listar socios por nombre");
            System.out.println("4. Listar socios por ID");
            System.out.println("5. Actualizar socio");
            System.out.println("6. Eliminar socio");
            System.out.println("7. Salir");

            System.out.print("Ingrese una opcion: ");
            String option = this.scanner.nextLine();
            System.out.println();

            switch (option){
                case "1" -> register();
                case "2" -> this.partnerController.findAll();
                case "4" -> findById();
                case "3" -> findByName();
                case "5" -> update();
                case "6" -> delete();
                case "7" -> {
                    return;
                }
                default -> System.out.println("Opcion no disponible");
            }
        }
    }

    private void register(){
        System.out.print("Ingrese el ID");
        String id = this.scanner.nextLine();
        System.out.print("Ingrese el nombre");
        String name = this.scanner.nextLine();
        System.out.print("Ingrese el email");
        String email = this.scanner.nextLine();

        this.partnerController.register(new Partner(id, name, email));
    }

    private void findById(){
        System.out.print("Ingrese el ID: ");
        String id = this.scanner.nextLine();
        this.partnerController.findById(id);
    }
    private void findByName(){
        System.out.print("Ingrese el nombre: ");
        String name = this.scanner.nextLine();
        this.partnerController.findByName(name);
    }

    private void update(){
        System.out.print("Ingrese el ID");
        String id = this.scanner.nextLine();
        System.out.print("Ingrese el nombre");
        String name = this.scanner.nextLine();
        System.out.print("Ingrese el email");
        String email = this.scanner.nextLine();

        this.partnerController.update(new Partner(id, name, email));

    }

    private void delete(){
        System.out.print("Ingrese el ID");
        String id = this.scanner.nextLine();

        this.partnerController.delete(id);
    }

}
