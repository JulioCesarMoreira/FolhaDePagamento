/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author User
 */
public class Vendedor extends Funcionario {

    private double vendas;

    public Vendedor(int id, String nome, double salario, double convenio, double vendas) {
        super(id, nome, salario, convenio);
        setVendas(vendas);
    }

    public double getVendas() {
        return vendas;
    }

    public void setVendas(double vendas) {
        if (vendas > 0) {
            this.vendas = vendas;
        } else {
            this.vendas = 0;
        }
    }

    @Override
    public double getProventos() {
        return this.getSalario() + (this.getVendas()*0.03);
    }

}
