/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import junit.runner.Version;

/**
 *
 * @author User
 */
public class Administrativo extends Funcionario {

    public Administrativo(int id, String nome, double salario, double convenio) {
        super(id, nome, salario, convenio);
    }
  
    
    @Override
    public double getProventos() {
        return this.getSalario();
    }
    
}
