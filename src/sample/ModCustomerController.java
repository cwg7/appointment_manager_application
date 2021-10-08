package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import static sample.DBConnection.getConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ModCustomerController implements Initializable {

    @FXML
    private TableView modCustomersTable;
    @FXML
    private TableColumn<Customer, Integer> idCol;
    @FXML
    private TableColumn<Customer, String> nameCol;
    @FXML
    private TableColumn<Customer, String> addressCol;
    @FXML
    private TableColumn<Customer, String> postalCol;
    @FXML
    private TableColumn<Customer, String> phoneCol;
    @FXML
    private TableColumn<Customer, Integer> divisionCol;
    @FXML
    private Label lblName;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblPostal;
    @FXML
    private Label lblPhone;
    @FXML
    private Label lblDivision;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPostal;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfDivision;
    @FXML
    private Button saveButton;
    @FXML
    private Button modButton;
    @FXML
    private Button mainMenuButton;
    @FXML
    private Button deleteButton;

    private Stage stage;
    private Scene scene;


    public static ObservableList<Customer> getCustomerList() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        Connection conn = DBConnection.getConnection();
        String query = "SELECT * FROM customers";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Customer customer;
            while (rs.next()) {
                customer = new Customer(rs.getInt("Customer_ID"), rs.getString("Customer_Name"),
                        rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"), rs.getInt("Division_ID"));
                customerList.add(customer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    @FXML
    public void handleModClick(ActionEvent event) throws IOException {
        Customer selectedCustomer;
        selectedCustomer = (Customer) modCustomersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null){
            Alerts.modHandler();
        }
        else {
        int selectedCustomerID = selectedCustomer.getId();
        int divisionID = selectedCustomer.getDivision_id();

        // = (Customer) modCustomersTable.getSelectionModel().getSelectedItems();


            tfName.setDisable(false);
            tfName.setText(selectedCustomer.getName());
            tfAddress.setDisable(false);
            tfAddress.setText(selectedCustomer.getAddress());
            tfPostal.setDisable(false);
            tfPostal.setText(selectedCustomer.getPostalCode());
            tfPhone.setDisable(false);
            tfPhone.setText(selectedCustomer.getPhoneNumber());
            tfDivision.setDisable(false);
            tfDivision.setText(String.valueOf(divisionID));
            // tfDivision.setInt(Integer.parseInt(tfDivision.getText()));
            

                    //setText(Integer.parseInt(String.valueOf(selectedCustomer.getDivision_id())));
            modCustomersTable.setDisable(true);
            saveButton.setDisable(false);
            modButton.setDisable(true);
        }

    }

    @FXML
    public void handleDelete(ActionEvent event) throws IOException {
        Customer selectedCustomer = (Customer) modCustomersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alerts.delHandler();

        }
        else {
            preparedDelete();
            showCustomers();
            Alerts.deleteSuccessful();
        }

    }
    public void preparedDelete() {
        Customer selectedCustomer = (Customer) modCustomersTable.getSelectionModel().getSelectedItem();
        int selectedCustomerID = selectedCustomer.getId();

        PreparedStatement pstatement;
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        try {
            pstatement = DBConnection.getConnection().prepareStatement(sql);
            pstatement.setInt(1,selectedCustomerID);
            pstatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


        @FXML
    public void saveButtonClick(ActionEvent event) throws IOException {
        preparedUpdate();
        modCustomersTable.setDisable(false);
        showCustomers();
        saveButton.setDisable(true);
        tfName.clear();
        tfName.setDisable(true);
        tfAddress.clear();
        tfAddress.setDisable(true);
        tfPostal.clear();
        tfPostal.setDisable(true);
        tfPhone.clear();
        tfPhone.setDisable(true);
        tfDivision.clear();
        tfDivision.setDisable(true);
        modButton.setDisable(false);

    }



    public void preparedUpdate() {
        Customer selectedCustomer = (Customer) modCustomersTable.getSelectionModel().getSelectedItem();
        int selectedCustomerID = selectedCustomer.getId();

        PreparedStatement pstatement;
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        try {
            // assert pstatement != null;
            pstatement = DBConnection.getConnection().prepareStatement(sql);
            pstatement.setString(1, tfName.getText());
            pstatement.setString(2, tfAddress.getText());
            pstatement.setString(3, tfPostal.getText());
            pstatement.setString(4, tfPhone.getText());
            pstatement.setInt(5, Integer.parseInt(tfDivision.getText()));
            pstatement.setInt(6, selectedCustomer.getId());
            pstatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void showCustomers() {
        ObservableList<Customer> list = getCustomerList();
        idCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("division_id"));
        modCustomersTable.setItems(list);


    }
    @FXML
    public void backToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    
    }







    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showCustomers();
        tfName.setDisable(true);
        tfAddress.setDisable(true);
        tfPostal.setDisable(true);
        tfPhone.setDisable(true);
        tfDivision.setDisable(true);
        




       /* idCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));*/
       /* nameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));*/
       /* addressCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));*/
       /* postalCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));*/
       /* phoneCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));*/
       /* divisionCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("division_id"));*/
       /* modCustomersTable.setItems(AddCustomerController.getCustomerList());*/
    }
}
