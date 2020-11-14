
package Entidad;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Elemento extends RecursiveTreeObject<Elemento> { 
            private int ID;
            private String nombre;
            private String descripción;
            private int Estado;

            public int getID() {
                return ID;
            }

            public int getCantidadMax() {
                return Estado;
            }

            public void setCantidadMax(int Estado) {
                this.Estado = Estado;
            }

            public String getNombre() {
                return nombre;
            }

            public void setNombre(String nombre) {
                this.nombre = nombre;
            }

            public String getDescripción() {
                return descripción;
            }

            public void setDescripción(String descripción) {
                this.descripción = descripción;
            }
       }
