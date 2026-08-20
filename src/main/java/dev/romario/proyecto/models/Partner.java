package dev.romario.proyecto.models;

import dev.romario.proyecto.repositories.Identifiable;

import java.util.ArrayList;
import java.util.List;

public class Partner implements Identifiable {
        private String id;
        private String name;
        private String email;
        private List<Book> listActiveLoan;

        public Partner(String id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        @Override
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public List<Book> getListActiveLoan() {
            return listActiveLoan;
        }

        public void setListActiveLoan(List<Book> listActiveLoan) {
            this.listActiveLoan = listActiveLoan;
        }

}
