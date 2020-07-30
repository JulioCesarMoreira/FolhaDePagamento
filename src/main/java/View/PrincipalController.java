package View;

import Model.Funcionario;
import Model.Administrativo;
import Model.Vendedor;
import Model.Gerente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import static javax.swing.UIManager.get;
import org.json.JSONObject;

public class PrincipalController implements Initializable {

    @FXML
    private Label lbFuncao;

    @FXML
    private RadioButton rdBtnVendedor;

    @FXML
    private VBox lbRS;

    @FXML
    private void rdBtnVendedorSelect(ActionEvent event) {
        vbBonifVenda.setVisible(true);
        txtFldBonifVenda.setVisible(true);
        lbBonifVenda.setText("Vendas:");
        lbRS.setVisible(true);
    }

    @FXML
    private RadioButton rdBtnGerente;

    @FXML
    private void rdBtnGerenteSelect(ActionEvent event) {
        vbBonifVenda.setVisible(true);
        txtFldBonifVenda.setVisible(true);
        lbBonifVenda.setText("Bonificação:");
        lbRS.setVisible(true);
    }

    @FXML
    private RadioButton rdBtnAdministrativo;

    @FXML
    private void rdBtnAdministrativoSelect(ActionEvent event) {
        vbBonifVenda.setVisible(false);
        lbRS.setVisible(false);
    }

    @FXML
    private Label lbBonifVenda;

    @FXML
    private TextField txtFldBonifVenda;

    @FXML
    private Label lbConvenio;

    @FXML
    private TextField txtFldConvenio;

    @FXML
    private Label lbSalario;

    @FXML
    private TextField txtFldSalario;

    @FXML
    private Label lbNome;

    @FXML
    private TextField txtFldNome;

    @FXML
    private Label lbID;

    @FXML
    private TextField txtFldID;

    @FXML
    private VBox vbBonifVenda;

    @FXML
    private StackPane pnIncluirAlterar;

    @FXML
    private Button btnIncluir;

    @FXML
    private VBox vbInicio;

    @FXML
    private Label lbEndereco;

    @FXML
    private VBox vbIncluirAlterar;

    @FXML
    private VBox vbinfo;

    @FXML
    private Label lbTotalProventos;

    @FXML
    private Label lbTotalDescontos;

    @FXML
    private Label lbTotalSalarioFinal;

    @FXML
    private Label lbTotalSalarios;

    @FXML
    private HBox boxGraph;

    @FXML
    private PieChart grafPizza;

    @FXML
    private StackPane pnGrafico;

    @FXML
    private Label lbSalarioT;

    List<Funcionario> listaGrafico = new ArrayList<>();

    @FXML
    void btnGraficoFuncionarioClick(Event event) {
        pnIncluirAlterar.setVisible(false);
        pnGrafico.setVisible(true);
        vbinfo.setVisible(false);
        vbInicio.setVisible(false);
        boxGraph.getChildren().clear();
        PieChart grafPizzaa = new PieChart();
        for (Funcionario f : lstFuncionario) {
            listaGrafico.add(f);
        }
        ordena(listaGrafico);
        int i = 0;
        double soma = 0, acumulaSalarioLista = 0;
        for (Funcionario f : listaGrafico) {
            if (i <= 4) {
                grafPizzaa.getData().add(new PieChart.Data(f.getNomeFormatado() + " " + f.getSalarioF(), f.getSalarioFinal()));
                acumulaSalarioLista = acumulaSalarioLista + f.getSalarioFinal();
            } else {
                soma = soma + f.getSalarioFinal();
            }
            i++;
        }
        if (soma > 0) {
            grafPizzaa.getData().add(new PieChart.Data("Outros " + String.format("%.2f", soma), soma));
        }
        lbSalarioT.setText(String.format("%.2f", soma + acumulaSalarioLista));
        grafPizza.setTitle("Funcionários com maiores salários");
        boxGraph.getChildren().add(grafPizzaa);
    }

    @FXML
    private void btnInclurClickView(Event event) {
        pnIncluirAlterar.setVisible(true);
        vbIncluirAlterar.setVisible(true);
        vbinfo.setVisible(false);
        vbInicio.setVisible(false);
        lbFuncao.setText("Inclusão");
        txtFldSalario.setText("");
        txtFldBonifVenda.setText("0");
        txtFldConvenio.setText("0");
        txtFldID.setText("");
        txtFldNome.setText("");
        txtFldID.setDisable(false);
        txtFldNome.setDisable(false);
    }

    @FXML
    private void btnVoltarClick(Event event) {
        pnIncluirAlterar.setVisible(false);
        pnGrafico.setVisible(false);
        vbInicio.setVisible(true);
    }

    private void atualiza() {
        tbVwFolha.refresh();
        tbVwFolha.setItems(FXCollections.observableList(lstFuncionario));
    }

    @FXML
    private void btnAlterarView(Event event) {
        if (!tbVwFolha.getSelectionModel().isEmpty()) {
            vbIncluirAlterar.setVisible(true);
            vbinfo.setVisible(false);
            int index = tbVwFolha.getSelectionModel().getFocusedIndex();
            pnIncluirAlterar.setVisible(true);
            vbInicio.setVisible(false);
            lbFuncao.setText("Alteração");
            txtFldSalario.setText(String.valueOf(lstFuncionario.get(index).getSalario()));
            txtFldConvenio.setText(String.valueOf(lstFuncionario.get(index).getConvenio()));
            txtFldID.setText(String.valueOf(lstFuncionario.get(index).getId()));
            txtFldNome.setText(lstFuncionario.get(index).getNome());
            txtFldID.setDisable(true);

            Funcionario aux = lstFuncionario.get(index);

            if (aux instanceof Administrativo) {
                vbBonifVenda.setVisible(false);
                lbRS.setVisible(false);
                rdBtnAdministrativo.requestFocus();
                rdBtnAdministrativo.setSelected(true);

            } else if (aux instanceof Gerente) {
                rdBtnGerente.requestFocus();
                rdBtnGerente.setSelected(true);
                vbBonifVenda.setVisible(true);
                lbRS.setVisible(true);
                lbBonifVenda.setText("Bonificação:");
                txtFldBonifVenda.setText(String.valueOf(((Gerente) aux).getBonificacao()));

            } else if (aux instanceof Vendedor) {
                rdBtnVendedor.requestFocus();
                rdBtnVendedor.setSelected(true);
                vbBonifVenda.setVisible(true);
                lbRS.setVisible(true);
                lbBonifVenda.setText("Vendas:");
                txtFldBonifVenda.setText(String.valueOf(((Vendedor) aux).getVendas()));
            }
        }
    }

    @FXML
    private void acConfirmaClick() {
        if (txtFldID.isDisable()) {
            int index = tbVwFolha.getSelectionModel().getFocusedIndex();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Deseja realmente alterar " + lstFuncionario.get(index).getNome() + ", de ID: " + lstFuncionario.get(index).getId(),
                    ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirmação de alteração");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.NO) {
                alert.close();
            } else {
                Funcionario func = lstFuncionario.get(index);
                System.out.println(func.getClass().getSimpleName());
                if (rdBtnAdministrativo.isSelected()) {
                    if (func instanceof Administrativo) {
                        lstFuncionario.get(index).setId(Integer.valueOf(txtFldID.getText()));
                        lstFuncionario.get(index).setNome(String.valueOf(txtFldNome.getText()));
                        lstFuncionario.get(index).setSalario(Double.valueOf(txtFldSalario.getText()));
                        lstFuncionario.get(index).setConvenio(Double.valueOf(txtFldConvenio.getText()));
                    } else if (func instanceof Gerente) {
                        tbVwFolha.getItems().removeAll(lstFuncionario.get(index));
                        lstFuncionario.add(new Administrativo(Integer.valueOf(txtFldID.getText()), txtFldNome.getText(),
                                Double.valueOf(txtFldSalario.getText()), Double.valueOf(txtFldConvenio.getText())));
                    } else if (func instanceof Vendedor) {
                        tbVwFolha.getItems().removeAll(lstFuncionario.get(index));
                        lstFuncionario.add(new Administrativo(Integer.valueOf(txtFldID.getText()), txtFldNome.getText(),
                                Double.valueOf(txtFldSalario.getText()), Double.valueOf(txtFldConvenio.getText())));
                    }
                } else if (rdBtnVendedor.isSelected()) {
                    if (func instanceof Vendedor) {
                        lstFuncionario.get(index).setId(Integer.valueOf(txtFldID.getText()));
                        lstFuncionario.get(index).setNome(String.valueOf(txtFldNome.getText()));
                        lstFuncionario.get(index).setSalario(Double.valueOf(txtFldSalario.getText()));
                        lstFuncionario.get(index).setConvenio(Double.valueOf(txtFldConvenio.getText()));
                        ((Vendedor) lstFuncionario.get(index)).setVendas(Double.valueOf(txtFldBonifVenda.getText()));
                    } else if (func instanceof Administrativo) {
                        tbVwFolha.getItems().removeAll(lstFuncionario.get(index));
                        lstFuncionario.add(new Vendedor(Integer.valueOf(txtFldID.getText()), txtFldNome.getText(), Double.valueOf(txtFldSalario.getText()),
                                Double.valueOf(txtFldConvenio.getText()), Double.valueOf(txtFldBonifVenda.getText())));
                    } else if (func instanceof Gerente) {
                        tbVwFolha.getItems().removeAll(lstFuncionario.get(index));
                        lstFuncionario.add(new Vendedor(Integer.valueOf(txtFldID.getText()), txtFldNome.getText(), Double.valueOf(txtFldSalario.getText()),
                                Double.valueOf(txtFldConvenio.getText()), Double.valueOf(txtFldBonifVenda.getText())));
                    }
                } else if (rdBtnGerente.isSelected()) {
                    if (func instanceof Gerente) {
                        lstFuncionario.get(index).setId(Integer.valueOf(txtFldID.getText()));
                        lstFuncionario.get(index).setNome(String.valueOf(txtFldNome.getText()));
                        lstFuncionario.get(index).setSalario(Double.valueOf(txtFldSalario.getText()));
                        lstFuncionario.get(index).setConvenio(Double.valueOf(txtFldConvenio.getText()));
                        ((Gerente) lstFuncionario.get(index)).setBonificacao(Double.valueOf(txtFldBonifVenda.getText()));
                    } else if (func instanceof Vendedor) {
                        tbVwFolha.getItems().removeAll(lstFuncionario.get(index));
                        lstFuncionario.add(new Gerente(Integer.valueOf(txtFldID.getText()), txtFldNome.getText(), Double.valueOf(txtFldSalario.getText()),
                                Double.valueOf(txtFldConvenio.getText()), Double.valueOf(txtFldBonifVenda.getText())));
                    } else if (func instanceof Administrativo) {
                        tbVwFolha.getItems().removeAll(lstFuncionario.get(index));
                        lstFuncionario.add(new Gerente(Integer.valueOf(txtFldID.getText()), txtFldNome.getText(), Double.valueOf(txtFldSalario.getText()),
                                Double.valueOf(txtFldConvenio.getText()), Double.valueOf(txtFldBonifVenda.getText())));
                    }
                }
                atualiza();
                pnIncluirAlterar.setVisible(false);
                vbInicio.setVisible(true);
            }
        } else {

            for (Funcionario o : lstFuncionario) {
                if (o.getId() == Double.valueOf(txtFldID.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Esse Id: " + txtFldID.getText() + ", já foi cadastrado!",
                            ButtonType.OK);
                    alert.setTitle("Folha de pagamento");
                    alert.showAndWait();
                    txtFldID.setText("");
                    txtFldID.requestFocus();
                }
            }
            if (!txtFldID.getText().isEmpty() && !txtFldNome.getText().isEmpty() && !txtFldSalario.getText().isEmpty()
                    && Double.valueOf(txtFldSalario.getText()) > 0 && Double.valueOf(txtFldConvenio.getText()) >= 0) {
                if (rdBtnVendedor.isSelected()) {
                    lstFuncionario.add(new Vendedor(Integer.valueOf(txtFldID.getText()), txtFldNome.getText(), Double.valueOf(txtFldSalario.getText()),
                            Double.valueOf(txtFldConvenio.getText()), Double.valueOf(txtFldBonifVenda.getText())));

                } else if (rdBtnGerente.isSelected()) {
                    lstFuncionario.add(new Gerente(Integer.valueOf(txtFldID.getText()), txtFldNome.getText(), Double.valueOf(txtFldSalario.getText()),
                            Double.valueOf(txtFldConvenio.getText()), Double.valueOf(txtFldBonifVenda.getText())));

                } else if (rdBtnAdministrativo.isSelected()) {
                    lstFuncionario.add(new Administrativo(Integer.valueOf(txtFldID.getText()), txtFldNome.getText(),
                            Double.valueOf(txtFldSalario.getText()), Double.valueOf(txtFldConvenio.getText())));

                }
                pnIncluirAlterar.setVisible(false);
                vbInicio.setVisible(true);
                atualiza();
            }
        }
    }

    @FXML
    private void btnExclurClick(Event event) {
        if (!tbVwFolha.getSelectionModel().isEmpty()) {
            int index = tbVwFolha.getSelectionModel().getFocusedIndex();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Deseja realmente deletar " + lstFuncionario.get(index).getNome() + ", de ID: " + lstFuncionario.get(index).getId(),
                    ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirmação de exclusão");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.NO) {
                alert.close();
            } else {
                tbVwFolha.getItems().removeAll(tbVwFolha.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    private TableView tbVwFolha;

    private List<Funcionario> lstFuncionario = new ArrayList<>();

    private void adicionaListener(TextField texto) {
        texto.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (!newValue.matches(
                            "\\d*(\\"
                            + "."
                            + "\\d*)?")
                    && !newValue.isEmpty()) {
                        texto.setText(oldValue);
                    } else {
                        texto.setText(newValue);
                    }
                });
    }

    @FXML
    private void btnInfoClick(ActionEvent event) {
        pnIncluirAlterar.setVisible(true);
        vbIncluirAlterar.setVisible(false);
        vbInicio.setVisible(false);
        vbinfo.setVisible(true);
        double proventos = 0, descontos = 0, salarioFinal = 0, salario = 0;
        for (Funcionario f : lstFuncionario) {
            proventos = proventos + f.getProventos();
            descontos = descontos + f.getDescontos();
            salarioFinal = salarioFinal + f.getSalarioFinal();
            salario = salario + f.getSalario();
        }
        lbTotalProventos.setText(String.valueOf(proventos));
        lbTotalDescontos.setText(String.valueOf(descontos));
        lbTotalSalarioFinal.setText(String.valueOf(salarioFinal));
        lbTotalSalarios.setText(String.valueOf(salario));
    }

    @FXML
    private void funcaoSair(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Deseja realmente fechar o sistema",
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Folha de pagamento");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO) {
            alert.close();
        } else {
            gravaJson();
            System.exit(0);
        }
    }

    private String nomeArq = "/Piloto.json";

    private BufferedReader br = null;
    private Funcionario funLinha;

    @FXML
    public void salvaLista(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Deseja realmente salvar a lista de funcionários",
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Folha de pagamento");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO) {
            alert.close();
        } else {
            gravaJson();
        }
    }

    public List<Funcionario> LerJson() {
        String linha;
        try {
            br = new BufferedReader(new FileReader(nomeArq));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Funcionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while ((linha = br.readLine()) != null) {
                JSONObject jNovo = new JSONObject(linha);
                if (jNovo.getString("Cargo : ").equals("ADM")) {
                    lstFuncionario.add(new Administrativo(jNovo.getInt("ID: "), jNovo.getString("Nome: "),
                            jNovo.getDouble("Salário: "), jNovo.getDouble("Convênio: ")));
                } else if (jNovo.getString("Cargo : ").equals("GER")) {
                    lstFuncionario.add(new Gerente(jNovo.getInt("ID: "), jNovo.getString("Nome: "),
                            jNovo.getDouble("Salário: "), jNovo.getDouble("Convênio: "), jNovo.getDouble("Convênio: ")));
                } else if ("VEN".equals(jNovo.getString("Cargo : "))) {
                    lstFuncionario.add(new Vendedor(jNovo.getInt("ID: "), jNovo.getString("Nome: "),
                            jNovo.getDouble("Salário: "), jNovo.getDouble("Convênio: "), jNovo.getDouble("Vendas: ")));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Funcionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        lbEndereco.setText(nomeArq);
        return lstFuncionario;

    }

    public void gravaJson() {

        try {
            FileWriter fw = null;
            nomeArq = "C:\\dados\\Piloto.json";
            File file = new File(nomeArq);
            file.createNewFile();
            fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            for (Funcionario f : lstFuncionario) {
                if (f instanceof Administrativo) {
                    JSONObject eJSON = new JSONObject();
                    eJSON.put("Cargo : ", f.getClass().getSimpleName().substring(0, 3).toUpperCase());
                    eJSON.put("ID: ", f.getId());
                    eJSON.put("Nome: ", f.getNomeFormatado());
                    eJSON.put("Salário: ", f.getSalario());
                    eJSON.put("Convênio: ", f.getConvenio());
                    bw.write(eJSON.toString() + "\n");
                } else if (f instanceof Gerente) {
                    JSONObject eJSON = new JSONObject();
                    eJSON.put("Cargo : ", f.getClass().getSimpleName().substring(0, 3).toUpperCase());
                    eJSON.put("ID: ", f.getId());
                    eJSON.put("Nome: ", f.getNomeFormatado());
                    eJSON.put("Salário: ", f.getSalario());
                    eJSON.put("Convênio: ", f.getConvenio());
                    eJSON.put("Bonificação: ", ((Gerente) f).getBonificacao());
                    bw.write(eJSON.toString() + "\n");
                } else {
                    JSONObject eJSON = new JSONObject();
                    eJSON.put("Cargo : ", f.getClass().getSimpleName().substring(0, 3).toUpperCase());
                    eJSON.put("ID: ", f.getId());
                    eJSON.put("Nome: ", f.getNomeFormatado());
                    eJSON.put("Salário: ", f.getSalario());
                    eJSON.put("Convênio: ", f.getConvenio());
                    eJSON.put("Vendas: ", ((Vendedor) f).getVendas());
                    bw.write(eJSON.toString() + "\n");
                }
            }
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Funcionario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ordena(List<Funcionario> lstGraph) {
        int i, j;
        Funcionario aux;
        for (i = 1; i < lstGraph.size(); i++) {
            lstGraph.get(i);
            j = i;
            while (j > 0
                    && (lstGraph.get(j).getSalario() > lstGraph.get(j - 1).getSalario())) {
                aux = lstGraph.get(j - 1);
                lstGraph.set(j - 1, lstGraph.get(j));
                lstGraph.set(j, aux);
                j--;
            }
        }
    }

    public String simpNome(String nome) {
        String[] palavra = nome.split(" ");
        if (palavra.length < 2) {
            return palavra[0];
        } else {
            return palavra[0] + " " + palavra[palavra.length - 1];
        }
    }

    public String geraSenha(int n) {
        Random rand = new Random();
        String palavraSaida = "";
        if (n < 0) {
            n = n * (-1);
        }
        for (int i = 0; i <= n; i++) {
            palavraSaida = palavraSaida + String.valueOf(rand.nextInt(9));
        }
        return palavraSaida;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Timeline timeLine;
        timeLine = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {

                    LocalDateTime hora = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String agoraFormatado = hora.format(formatter);
                    lbEndereco.setText(agoraFormatado);
                })
        );
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
        try {
            adicionaListener(txtFldSalario);
            adicionaListener(txtFldBonifVenda);
            adicionaListener(txtFldConvenio);
            txtFldID.textProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        if (!newValue.matches(
                                "\\d*(\\"
                                + "\\d*)?")
                        && !newValue.isEmpty()) {
                            txtFldID.setText(oldValue);
                        } else {
                            txtFldID.setText(newValue);
                        }
                    });
            txtFldNome.textProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        if (!newValue.matches("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]*$")
                        && !newValue.isEmpty()) {
                            txtFldNome.setText(oldValue);
                        } else {
                            txtFldNome.setText(newValue);
                        }
                    });
            br = new BufferedReader(new FileReader(nomeArq));
            if (br == null) {
                tbVwFolha.setItems(FXCollections.observableList(lstFuncionario));
                lbEndereco.setText("Arquivo não encontrado!");
            } else {
                lstFuncionario = LerJson();
                tbVwFolha.setItems(FXCollections.observableList(lstFuncionario));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ordena(lstFuncionario);
        System.out.println(simpNome("Julio"));
        System.out.println(geraSenha(10));
    }
}
