package Model;

/**
 * @author Julio Cesar Paes Moreira
 */
public abstract class Funcionario {

    // protected double alt;   ela manda o valor pela herança e não pelos geters and seters
    private String nome;
    private int id;
    private double salario;
    private double convenio;

    abstract public double getProventos();

    public Funcionario(int id, String nome, double salario, double convenio) {
        setId(id);
        setNome(nome);
        setSalario(salario);
        setConvenio(convenio);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        if (salario > 0) {
            this.salario = salario;
        } else {
            this.salario = 0.1;
        }
    }

    public double getConvenio() {
        return convenio;
    }

    public void setConvenio(double convenio) {
        if (convenio > 0) {
            this.convenio = convenio;
        } else {
            this.convenio = 0;
        }
    }

    public double getInss() {
        return this.getProventos() * 0.11;
    }

    public double getIR() {
        if (getSalario() <= 5000) {
            return 0;
        } else if (getSalario() <= 10000) {
            return this.getSalario() * 0.075;
        } else if (getSalario() <= 20000) {
            return this.getSalario() * 0.09;
        } else {
            return this.getSalario() * 0.2;
        }
    }

    public double getSalarioFinal() {
        return this.getProventos() - this.getDescontos();
    }

    public String getTipo() {
        return this.getClass().getSimpleName().substring(0, 3);
    }

    public double getDescontos() {
        if ((getConvenio() + getIR() + getInss()) > getProventos()) {
            return this.getIR() + this.getInss();
        } else {
            return this.getConvenio() + this.getIR() + this.getInss();
        }
    }

    public String getSalarioF() {
        return String.format("%.2f", getSalarioFinal());
    }

    public String getDescontosF() {
        return String.format("%.2f", getDescontos());
    }

    public String getProventosF() {
        return String.format("%.2f", getProventos());
    }

    public String getNomeFormatado() {
        return this.getNome().toUpperCase();
    }

}
