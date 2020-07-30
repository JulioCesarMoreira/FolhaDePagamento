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
public class Gerente extends Funcionario {

    private double bonificacao;
    
    public Gerente(int id,String nome, double salario, double convenio, double bonificacao) {
        super(id, nome, salario, convenio);
        setBonificacao(bonificacao);
    }

    public double getBonificacao() {
        return bonificacao;
    }

    public void setBonificacao(double bonificacao) {
        if (this.bonificacao < 0) {
            this.bonificacao = 0;
        } else {
            this.bonificacao = bonificacao;
        }
    }

    @Override
    public double getProventos() {
        return (this.getSalario() + this.getBonificacao());
    }

}
