package sample;

import com.mysql.cj.xdevapi.Table;
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

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import sample.Appointment;

import javax.swing.*;

import static java.sql.Timestamp.valueOf;

public class ModAppointmentController implements Initializable {

    @FXML
    private ScrollBar scrollBar;

    @FXML
    private TableView <Appointment> appointmentsTable;

    @FXML
    private TableColumn <Appointment, Integer> apptIDCol;
    @FXML
    private TableColumn <Appointment, String> titleCol;
    @FXML
    private TableColumn <Appointment, String> descriptionCol;
    @FXML
    private TableColumn <Appointment, String> locationCol;
    @FXML
    private TableColumn <Appointment, String> typeCol;
    @FXML
    private TableColumn <Appointment, LocalDateTime> startCol;
    @FXML
    private TableColumn <Appointment, LocalDateTime> endCol;
    @FXML
    private TableColumn <Appointment, Integer> customerIDCol;
    @FXML
    private TableColumn <Appointment, Integer> userIDCol;
    @FXML
    private TableColumn <Appointment, Integer> contactCol;
    @FXML
    private TableColumn <Appointment, String> contactNameCol;
    @FXML
    private Label lblWelcome;
    @FXML
    private Button selectButton;
    @FXML
    private Label lblStart;
    @FXML
    private Label lblEnd;
    @FXML
    private TextField tfApptID;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfDescription;
    @FXML
    private TextField tfLocation;
    @FXML
    private TextField tfContactID;
    @FXML
    private TextField tfType;
    @FXML
    private TextField tfStart;
    @FXML
    private TextField tfEnd;
    @FXML
    private Label lblUserID;
    @FXML
    private TextField tfUserID;
    ComboBox<Integer> userID_box;
    @FXML
    ComboBox<String> contactName_box;
    @FXML
    private Button saveChangesButton;
    @FXML
    private Label lblCustomerID;
    @FXML
    private TextField tfCustomerID;
    @FXML
    private Button deleteButton;

    @FXML
    private Button mainMenuButton;
    private Stage stage;
    private Scene scene;

    private LocalDate selectedDate;
    private LocalTime startTime;
    private LocalTime endTime;

    @FXML
    private Label lblDate;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<LocalTime> cbStartTime;
    @FXML
    private ComboBox<LocalTime> cbEndTime;


    public void showAppointments() {
        ObservableList<Appointment> list = MainMenuController.getAppointments();
        apptIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointment_id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("start_time"));
        endCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("end_time"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customer_id"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("user_id"));
        //contactCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contact_name"));
        contactCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contact_id"));
        //contactNameCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contact_name"));
        appointmentsTable.setItems(list);


    }

    //LocalDateTime startTimeObject;
    public void selectButtonClick(ActionEvent event) {



        //startTimeObject = startTime;


        if (appointmentsTable.getItems() != null) {
           // ObservableList<Appointment> appointment = appointmentsTable.getSelectionModel().getSelectedItems();
            //tfApptID =
            Appointment selectedAppointment;
            selectedAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();

            LocalDateTime startTime = selectedAppointment.getStart_time();
            LocalDate selectedDate = startTime.toLocalDate();
            LocalTime startClock = startTime.toLocalTime();

            LocalDateTime endTime = selectedAppointment.getEnd_time();
            LocalTime endClock =  endTime.toLocalTime();

            //Customer selectedCustomer;
            //selectedCustomer = (Customer) modCustomersTable.getSelectionModel().getSelectedItem();

            if (selectedAppointment == null){
                Alerts.modHandler2();
            }
            else {
                int appointmentID = selectedAppointment.getAppointment_id();
                //int divisionID = selectedCustomer.getDivision_id();

                // = (Customer) modCustomersTable.getSelectionModel().getSelectedItems();


                //tfApptID.setDisable(false);
               // tfApptID.setText(selectedAppointment.getAppointment_id());
               // tfApptID.setText(Integer.parseInt(selectedAppointment.getAppointment_id()));
               // tfApptID.setText(selectedAppointment.getAppointment_id());
                tfTitle.setDisable(false);
                tfDescription.setDisable(false);
                tfLocation.setDisable(false);
                tfContactID.setDisable(false);
                tfUserID.setDisable(false);
                //tfStart.setDisable(false);
                //tfEnd.setDisable(false);
                tfType.setDisable(false);
                tfCustomerID.setDisable(false);
                tfApptID.setText(String.valueOf(selectedAppointment.getAppointment_id()));
                tfTitle.setText(selectedAppointment.getTitle());
                tfDescription.setText(selectedAppointment.getDescription());
                tfLocation.setText(selectedAppointment.getLocation());
                tfContactID.setText(String.valueOf(selectedAppointment.getContact_id()));
                tfType.setText(selectedAppointment.getType());
                tfStart.setText(String.valueOf(Timestamp.valueOf(selectedAppointment.getStart_time())));
                tfEnd.setText(String.valueOf(Timestamp.valueOf(selectedAppointment.getEnd_time())));
                tfCustomerID.setText(String.valueOf(selectedAppointment.getCustomer_id()));
                tfUserID.setText(String.valueOf(selectedAppointment.getUser_id()));

                datePicker.setValue(selectedDate);
                cbStartTime.setValue(startClock);
                cbEndTime.setValue(endClock);

                setComboBoxStart();
                setComboBoxEnd();



               /* tfAddress.setDisable(false);
                tfAddress.setText(selectedCustomer.getAddress());
                tfPostal.setDisable(false);
                tfPostal.setText(selectedCustomer.getPostalCode());
                tfPhone.setDisable(false);
                tfPhone.setText(selectedCustomer.getPhoneNumber());
                tfDivision.setDisable(true);
                tfDivision.setText(String.valueOf(divisionID));
                country_box.setDisable(false);
                division2_box.setDisable(false);*/


                //country_box.setItems(selectedCustomer.getCountryName());
                //country_box.setItems(divisionID.get);

                // tfDivision.setInt(Integer.parseInt(tfDivision.getText()));


                //setText(Integer.parseInt(String.valueOf(selectedCustomer.getDivision_id())));

            }


        }
    }

    public void preparedUpdate() {
        Appointment selectedAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
        int selectedAppointmentId = selectedAppointment.getAppointment_id();

        PreparedStatement pstatement;
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        try {
            // assert pstatement != null;
            pstatement = DBConnection.getConnection().prepareStatement(sql);
            pstatement.setString(1, tfTitle.getText());
            pstatement.setString(2, tfDescription.getText());
            pstatement.setString(3, tfLocation.getText());
            pstatement.setString(4, tfType.getText());

            selectedDate = datePicker.getValue();
            LocalTime startTime = cbStartTime.getValue();
            LocalTime endTime = cbEndTime.getValue();

            LocalDateTime startTimeAndDate = LocalDateTime.of(selectedDate,startTime);
            LocalDateTime endTimeAndDate = LocalDateTime.of(selectedDate,endTime);


            //pstatement.setTimestamp(5, valueOf(tfStart.getText()));
            pstatement.setTimestamp(5, Timestamp.valueOf(startTimeAndDate));
            pstatement.setTimestamp(6, Timestamp.valueOf(endTimeAndDate));
           // pstatement.setTimestamp(6, valueOf(tfEnd.getText()));

            pstatement.setInt(7, Integer.parseInt(tfCustomerID.getText()));
            pstatement.setInt(8, Integer.parseInt(tfUserID.getText()));
            pstatement.setInt(9, Integer.parseInt(tfContactID.getText()));
            pstatement.setInt(10, Integer.parseInt(tfApptID.getText()));


           /* ObservableList<Division> divisionsOL = AddCustomerController.getDivisionIDList();
            String tempVal = division2_box.getSelectionModel().getSelectedItem();
            int divisionID = 0;
            for (Division division : divisionsOL)

            {
                if (tempVal.equals(division.getDivision_name())) {
                    divisionID = division.getId();
                }


            }
            pstatement.setInt(5, Integer.parseInt(String.valueOf(divisionID)));
            pstatement.setInt(6, selectedAppointment.getId());
*/


            pstatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void preparedDelete() {
        Appointment selectedAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
        int selectedAppointmentID = selectedAppointment.getAppointment_id();

        PreparedStatement pstatement;
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try {
            pstatement = DBConnection.getConnection().prepareStatement(sql);
            pstatement.setInt(1,selectedAppointmentID);
            pstatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void deleteAppointment(ActionEvent event) throws IOException {
        Appointment selectedAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            preparedDelete();
            showAppointments();
            Alerts.deleteSuccessful2();
            //appointmentsTable.refresh();
        }
        else {
            Alerts.delHandler2();
        }

    }


    @FXML
    public void saveChanges(ActionEvent event) throws IOException {
        preparedUpdate();
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void setComboBoxStart(){
         cbStartTime.getItems().add(LocalTime.parse("00:00"));
        cbStartTime.getItems().add(LocalTime.parse("00:15"));
        cbStartTime.getItems().add(LocalTime.parse("00:30"));
        cbStartTime.getItems().add(LocalTime.parse("00:45"));
        cbStartTime.getItems().add(LocalTime.parse("01:00"));
        cbStartTime.getItems().add(LocalTime.parse("01:15"));
        cbStartTime.getItems().add(LocalTime.parse("01:30"));
        cbStartTime.getItems().add(LocalTime.parse("01:45"));
        cbStartTime.getItems().add(LocalTime.parse("02:00"));
        cbStartTime.getItems().add(LocalTime.parse("02:15"));
        cbStartTime.getItems().add(LocalTime.parse("02:30"));
        cbStartTime.getItems().add(LocalTime.parse("02:45"));
        cbStartTime.getItems().add(LocalTime.parse("03:00"));
        cbStartTime.getItems().add(LocalTime.parse("03:15"));
        cbStartTime.getItems().add(LocalTime.parse("03:30"));
        cbStartTime.getItems().add(LocalTime.parse("03:45"));
        cbStartTime.getItems().add(LocalTime.parse("04:00"));
        cbStartTime.getItems().add(LocalTime.parse("04:15"));
        cbStartTime.getItems().add(LocalTime.parse("04:30"));
        cbStartTime.getItems().add(LocalTime.parse("04:45"));
        cbStartTime.getItems().add(LocalTime.parse("05:00"));
        cbStartTime.getItems().add(LocalTime.parse("05:15"));
        cbStartTime.getItems().add(LocalTime.parse("05:30"));
        cbStartTime.getItems().add(LocalTime.parse("05:45"));
        cbStartTime.getItems().add(LocalTime.parse("06:00"));
        cbStartTime.getItems().add(LocalTime.parse("06:15"));
        cbStartTime.getItems().add(LocalTime.parse("06:30"));
        cbStartTime.getItems().add(LocalTime.parse("06:45"));
        cbStartTime.getItems().add(LocalTime.parse("07:00"));
        cbStartTime.getItems().add(LocalTime.parse("07:15"));
        cbStartTime.getItems().add(LocalTime.parse("07:30"));
        cbStartTime.getItems().add(LocalTime.parse("07:45"));
        cbStartTime.getItems().add(LocalTime.parse("08:00"));
        cbStartTime.getItems().add(LocalTime.parse("08:15"));
        cbStartTime.getItems().add(LocalTime.parse("08:30"));
        cbStartTime.getItems().add(LocalTime.parse("08:45"));
        cbStartTime.getItems().add(LocalTime.parse("09:00"));
        cbStartTime.getItems().add(LocalTime.parse("09:15"));
        cbStartTime.getItems().add(LocalTime.parse("09:30"));
        cbStartTime.getItems().add(LocalTime.parse("09:45"));
        cbStartTime.getItems().add(LocalTime.parse("10:00"));
        cbStartTime.getItems().add(LocalTime.parse("10:15"));
        cbStartTime.getItems().add(LocalTime.parse("10:30"));
        cbStartTime.getItems().add(LocalTime.parse("10:45"));
        cbStartTime.getItems().add(LocalTime.parse("11:00"));
        cbStartTime.getItems().add(LocalTime.parse("11:15"));
        cbStartTime.getItems().add(LocalTime.parse("11:30"));
        cbStartTime.getItems().add(LocalTime.parse("11:45"));
        cbStartTime.getItems().add(LocalTime.parse("12:00"));
        cbStartTime.getItems().add(LocalTime.parse("12:15"));
        cbStartTime.getItems().add(LocalTime.parse("12:30"));
        cbStartTime.getItems().add(LocalTime.parse("12:45"));
        cbStartTime.getItems().add(LocalTime.parse("13:00"));
        cbStartTime.getItems().add(LocalTime.parse("13:15"));
        cbStartTime.getItems().add(LocalTime.parse("13:30"));
        cbStartTime.getItems().add(LocalTime.parse("13:45"));
        cbStartTime.getItems().add(LocalTime.parse("14:00"));
        cbStartTime.getItems().add(LocalTime.parse("14:15"));
        cbStartTime.getItems().add(LocalTime.parse("14:30"));
        cbStartTime.getItems().add(LocalTime.parse("14:45"));
        cbStartTime.getItems().add(LocalTime.parse("15:00"));
        cbStartTime.getItems().add(LocalTime.parse("15:15"));
        cbStartTime.getItems().add(LocalTime.parse("15:30"));
        cbStartTime.getItems().add(LocalTime.parse("15:45"));
        cbStartTime.getItems().add(LocalTime.parse("16:00"));
        cbStartTime.getItems().add(LocalTime.parse("16:15"));
        cbStartTime.getItems().add(LocalTime.parse("16:30"));
        cbStartTime.getItems().add(LocalTime.parse("16:45"));
        cbStartTime.getItems().add(LocalTime.parse("17:00"));
        cbStartTime.getItems().add(LocalTime.parse("17:15"));
        cbStartTime.getItems().add(LocalTime.parse("17:30"));
        cbStartTime.getItems().add(LocalTime.parse("17:45"));
        cbStartTime.getItems().add(LocalTime.parse("18:00"));
        cbStartTime.getItems().add(LocalTime.parse("18:15"));
        cbStartTime.getItems().add(LocalTime.parse("18:30"));
        cbStartTime.getItems().add(LocalTime.parse("18:45"));
        cbStartTime.getItems().add(LocalTime.parse("19:00"));
        cbStartTime.getItems().add(LocalTime.parse("19:15"));
        cbStartTime.getItems().add(LocalTime.parse("19:30"));
        cbStartTime.getItems().add(LocalTime.parse("19:45"));
        cbStartTime.getItems().add(LocalTime.parse("20:00"));
        cbStartTime.getItems().add(LocalTime.parse("20:15"));
        cbStartTime.getItems().add(LocalTime.parse("20:30"));
        cbStartTime.getItems().add(LocalTime.parse("20:45"));
        cbStartTime.getItems().add(LocalTime.parse("21:00"));
        cbStartTime.getItems().add(LocalTime.parse("21:15"));
        cbStartTime.getItems().add(LocalTime.parse("21:30"));
        cbStartTime.getItems().add(LocalTime.parse("21:45"));
        cbStartTime.getItems().add(LocalTime.parse("22:00"));
        cbStartTime.getItems().add(LocalTime.parse("22:15"));
        cbStartTime.getItems().add(LocalTime.parse("22:30"));
        cbStartTime.getItems().add(LocalTime.parse("22:45"));
        cbStartTime.getItems().add(LocalTime.parse("23:00"));
        cbStartTime.getItems().add(LocalTime.parse("23:15"));
        cbStartTime.getItems().add(LocalTime.parse("23:30"));
        cbStartTime.getItems().add(LocalTime.parse("23:45"));
    }

    public void setComboBoxEnd(){
        cbEndTime.getItems().add(LocalTime.parse("00:00"));
        cbEndTime.getItems().add(LocalTime.parse("00:15"));
        cbEndTime.getItems().add(LocalTime.parse("00:30"));
        cbEndTime.getItems().add(LocalTime.parse("00:45"));
        cbEndTime.getItems().add(LocalTime.parse("01:00"));
        cbEndTime.getItems().add(LocalTime.parse("01:15"));
        cbEndTime.getItems().add(LocalTime.parse("01:30"));
        cbEndTime.getItems().add(LocalTime.parse("01:45"));
        cbEndTime.getItems().add(LocalTime.parse("02:00"));
        cbEndTime.getItems().add(LocalTime.parse("02:15"));
        cbEndTime.getItems().add(LocalTime.parse("02:30"));
        cbEndTime.getItems().add(LocalTime.parse("02:45"));
        cbEndTime.getItems().add(LocalTime.parse("03:00"));
        cbEndTime.getItems().add(LocalTime.parse("03:15"));
        cbEndTime.getItems().add(LocalTime.parse("03:30"));
        cbEndTime.getItems().add(LocalTime.parse("03:45"));
        cbEndTime.getItems().add(LocalTime.parse("04:00"));
        cbEndTime.getItems().add(LocalTime.parse("04:15"));
        cbEndTime.getItems().add(LocalTime.parse("04:30"));
        cbEndTime.getItems().add(LocalTime.parse("04:45"));
        cbEndTime.getItems().add(LocalTime.parse("05:00"));
        cbEndTime.getItems().add(LocalTime.parse("05:15"));
        cbEndTime.getItems().add(LocalTime.parse("05:30"));
        cbEndTime.getItems().add(LocalTime.parse("05:45"));
        cbEndTime.getItems().add(LocalTime.parse("06:00"));
        cbEndTime.getItems().add(LocalTime.parse("06:15"));
        cbEndTime.getItems().add(LocalTime.parse("06:30"));
        cbEndTime.getItems().add(LocalTime.parse("06:45"));
        cbEndTime.getItems().add(LocalTime.parse("07:00"));
        cbEndTime.getItems().add(LocalTime.parse("07:15"));
        cbEndTime.getItems().add(LocalTime.parse("07:30"));
        cbEndTime.getItems().add(LocalTime.parse("07:45"));
        cbEndTime.getItems().add(LocalTime.parse("08:00"));
        cbEndTime.getItems().add(LocalTime.parse("08:15"));
        cbEndTime.getItems().add(LocalTime.parse("08:30"));
        cbEndTime.getItems().add(LocalTime.parse("08:45"));
        cbEndTime.getItems().add(LocalTime.parse("09:00"));
        cbEndTime.getItems().add(LocalTime.parse("09:15"));
        cbEndTime.getItems().add(LocalTime.parse("09:30"));
        cbEndTime.getItems().add(LocalTime.parse("09:45"));
        cbEndTime.getItems().add(LocalTime.parse("10:00"));
        cbEndTime.getItems().add(LocalTime.parse("10:15"));
        cbEndTime.getItems().add(LocalTime.parse("10:30"));
        cbEndTime.getItems().add(LocalTime.parse("10:45"));
        cbEndTime.getItems().add(LocalTime.parse("11:00"));
        cbEndTime.getItems().add(LocalTime.parse("11:15"));
        cbEndTime.getItems().add(LocalTime.parse("11:30"));
        cbEndTime.getItems().add(LocalTime.parse("11:45"));
        cbEndTime.getItems().add(LocalTime.parse("12:00"));
        cbEndTime.getItems().add(LocalTime.parse("12:15"));
        cbEndTime.getItems().add(LocalTime.parse("12:30"));
        cbEndTime.getItems().add(LocalTime.parse("12:45"));
        cbEndTime.getItems().add(LocalTime.parse("13:00"));
        cbEndTime.getItems().add(LocalTime.parse("13:15"));
        cbEndTime.getItems().add(LocalTime.parse("13:30"));
        cbEndTime.getItems().add(LocalTime.parse("13:45"));
        cbEndTime.getItems().add(LocalTime.parse("14:00"));
        cbEndTime.getItems().add(LocalTime.parse("14:15"));
        cbEndTime.getItems().add(LocalTime.parse("14:30"));
        cbEndTime.getItems().add(LocalTime.parse("14:45"));
        cbEndTime.getItems().add(LocalTime.parse("15:00"));
        cbEndTime.getItems().add(LocalTime.parse("15:15"));
        cbEndTime.getItems().add(LocalTime.parse("15:30"));
        cbEndTime.getItems().add(LocalTime.parse("15:45"));
        cbEndTime.getItems().add(LocalTime.parse("16:00"));
        cbEndTime.getItems().add(LocalTime.parse("16:15"));
        cbEndTime.getItems().add(LocalTime.parse("16:30"));
        cbEndTime.getItems().add(LocalTime.parse("16:45"));
        cbEndTime.getItems().add(LocalTime.parse("17:00"));
        cbEndTime.getItems().add(LocalTime.parse("17:15"));
        cbEndTime.getItems().add(LocalTime.parse("17:30"));
        cbEndTime.getItems().add(LocalTime.parse("17:45"));
        cbEndTime.getItems().add(LocalTime.parse("18:00"));
        cbEndTime.getItems().add(LocalTime.parse("18:15"));
        cbEndTime.getItems().add(LocalTime.parse("18:30"));
        cbEndTime.getItems().add(LocalTime.parse("18:45"));
        cbEndTime.getItems().add(LocalTime.parse("19:00"));
        cbEndTime.getItems().add(LocalTime.parse("19:15"));
        cbEndTime.getItems().add(LocalTime.parse("19:30"));
        cbEndTime.getItems().add(LocalTime.parse("19:45"));
        cbEndTime.getItems().add(LocalTime.parse("20:00"));
        cbEndTime.getItems().add(LocalTime.parse("20:15"));
        cbEndTime.getItems().add(LocalTime.parse("20:30"));
        cbEndTime.getItems().add(LocalTime.parse("20:45"));
        cbEndTime.getItems().add(LocalTime.parse("21:00"));
        cbEndTime.getItems().add(LocalTime.parse("21:15"));
        cbEndTime.getItems().add(LocalTime.parse("21:30"));
        cbEndTime.getItems().add(LocalTime.parse("21:45"));
        cbEndTime.getItems().add(LocalTime.parse("22:00"));
        cbEndTime.getItems().add(LocalTime.parse("22:15"));
        cbEndTime.getItems().add(LocalTime.parse("22:30"));
        cbEndTime.getItems().add(LocalTime.parse("22:45"));
        cbEndTime.getItems().add(LocalTime.parse("23:00"));
        cbEndTime.getItems().add(LocalTime.parse("23:15"));
        cbEndTime.getItems().add(LocalTime.parse("23:30"));
        cbEndTime.getItems().add(LocalTime.parse("23:45"));

    }


    @FXML
    public void goToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MainMenuController.getAppointments();
        showAppointments();

        tfApptID.setDisable(true);
        tfTitle.setDisable(true);
        tfDescription.setDisable(true);
        tfLocation.setDisable(true);
        tfContactID.setDisable(true);
        tfType.setDisable(true);
        tfStart.setDisable(true);
        tfEnd.setDisable(true);
        tfCustomerID.setDisable(true);
        tfUserID.setDisable(true);

        //cbStartTime.getItems().add(LocalTime.parse("1:00"));
        //cbStartTime.setDisable(true);
       /* cbStartTime.getItems().add(LocalTime.parse("00:00"));
        cbStartTime.getItems().add(LocalTime.parse("00:15"));
        cbStartTime.getItems().add(LocalTime.parse("00:30"));
        cbStartTime.getItems().add(LocalTime.parse("00:45"));
        cbStartTime.getItems().add(LocalTime.parse("01:00"));
        cbStartTime.getItems().add(LocalTime.parse("01:15"));
        cbStartTime.getItems().add(LocalTime.parse("01:30"));
        cbStartTime.getItems().add(LocalTime.parse("01:45"));
        cbStartTime.getItems().add(LocalTime.parse("02:00"));
        cbStartTime.getItems().add(LocalTime.parse("02:15"));
        cbStartTime.getItems().add(LocalTime.parse("02:30"));
        cbStartTime.getItems().add(LocalTime.parse("02:45"));
        cbStartTime.getItems().add(LocalTime.parse("03:00"));
        cbStartTime.getItems().add(LocalTime.parse("03:15"));
        cbStartTime.getItems().add(LocalTime.parse("03:30"));
        cbStartTime.getItems().add(LocalTime.parse("03:45"));
        cbStartTime.getItems().add(LocalTime.parse("04:00"));
        cbStartTime.getItems().add(LocalTime.parse("04:15"));
        cbStartTime.getItems().add(LocalTime.parse("04:30"));
        cbStartTime.getItems().add(LocalTime.parse("04:45"));
        cbStartTime.getItems().add(LocalTime.parse("05:00"));
        cbStartTime.getItems().add(LocalTime.parse("05:15"));
        cbStartTime.getItems().add(LocalTime.parse("05:30"));
        cbStartTime.getItems().add(LocalTime.parse("05:45"));
        cbStartTime.getItems().add(LocalTime.parse("06:00"));
        cbStartTime.getItems().add(LocalTime.parse("06:15"));
        cbStartTime.getItems().add(LocalTime.parse("06:30"));
        cbStartTime.getItems().add(LocalTime.parse("06:45"));
        cbStartTime.getItems().add(LocalTime.parse("07:00"));
        cbStartTime.getItems().add(LocalTime.parse("07:15"));
        cbStartTime.getItems().add(LocalTime.parse("07:30"));
        cbStartTime.getItems().add(LocalTime.parse("07:45"));
        cbStartTime.getItems().add(LocalTime.parse("08:00"));
        cbStartTime.getItems().add(LocalTime.parse("08:15"));
        cbStartTime.getItems().add(LocalTime.parse("08:30"));
        cbStartTime.getItems().add(LocalTime.parse("08:45"));
        cbStartTime.getItems().add(LocalTime.parse("09:00"));
        cbStartTime.getItems().add(LocalTime.parse("09:15"));
        cbStartTime.getItems().add(LocalTime.parse("09:30"));
        cbStartTime.getItems().add(LocalTime.parse("09:45"));
        cbStartTime.getItems().add(LocalTime.parse("10:00"));
        cbStartTime.getItems().add(LocalTime.parse("10:15"));
        cbStartTime.getItems().add(LocalTime.parse("10:30"));
        cbStartTime.getItems().add(LocalTime.parse("10:45"));
        cbStartTime.getItems().add(LocalTime.parse("11:00"));
        cbStartTime.getItems().add(LocalTime.parse("11:15"));
        cbStartTime.getItems().add(LocalTime.parse("11:30"));
        cbStartTime.getItems().add(LocalTime.parse("11:45"));
        cbStartTime.getItems().add(LocalTime.parse("12:00"));
        cbStartTime.getItems().add(LocalTime.parse("12:15"));
        cbStartTime.getItems().add(LocalTime.parse("12:30"));
        cbStartTime.getItems().add(LocalTime.parse("12:45"));
        cbStartTime.getItems().add(LocalTime.parse("13:00"));
        cbStartTime.getItems().add(LocalTime.parse("13:15"));
        cbStartTime.getItems().add(LocalTime.parse("13:30"));
        cbStartTime.getItems().add(LocalTime.parse("13:45"));
        cbStartTime.getItems().add(LocalTime.parse("14:00"));
        cbStartTime.getItems().add(LocalTime.parse("14:15"));
        cbStartTime.getItems().add(LocalTime.parse("14:30"));
        cbStartTime.getItems().add(LocalTime.parse("14:45"));
        cbStartTime.getItems().add(LocalTime.parse("15:00"));
        cbStartTime.getItems().add(LocalTime.parse("15:15"));
        cbStartTime.getItems().add(LocalTime.parse("15:30"));
        cbStartTime.getItems().add(LocalTime.parse("15:45"));
        cbStartTime.getItems().add(LocalTime.parse("16:00"));
        cbStartTime.getItems().add(LocalTime.parse("16:15"));
        cbStartTime.getItems().add(LocalTime.parse("16:30"));
        cbStartTime.getItems().add(LocalTime.parse("16:45"));
        cbStartTime.getItems().add(LocalTime.parse("17:00"));
        cbStartTime.getItems().add(LocalTime.parse("17:15"));
        cbStartTime.getItems().add(LocalTime.parse("17:30"));
        cbStartTime.getItems().add(LocalTime.parse("17:45"));
        cbStartTime.getItems().add(LocalTime.parse("18:00"));
        cbStartTime.getItems().add(LocalTime.parse("18:15"));
        cbStartTime.getItems().add(LocalTime.parse("18:30"));
        cbStartTime.getItems().add(LocalTime.parse("18:45"));
        cbStartTime.getItems().add(LocalTime.parse("19:00"));
        cbStartTime.getItems().add(LocalTime.parse("19:15"));
        cbStartTime.getItems().add(LocalTime.parse("19:30"));
        cbStartTime.getItems().add(LocalTime.parse("19:45"));
        cbStartTime.getItems().add(LocalTime.parse("20:00"));
        cbStartTime.getItems().add(LocalTime.parse("20:15"));
        cbStartTime.getItems().add(LocalTime.parse("20:30"));
        cbStartTime.getItems().add(LocalTime.parse("20:45"));
        cbStartTime.getItems().add(LocalTime.parse("21:00"));
        cbStartTime.getItems().add(LocalTime.parse("21:15"));
        cbStartTime.getItems().add(LocalTime.parse("21:30"));
        cbStartTime.getItems().add(LocalTime.parse("21:45"));
        cbStartTime.getItems().add(LocalTime.parse("22:00"));
        cbStartTime.getItems().add(LocalTime.parse("22:15"));
        cbStartTime.getItems().add(LocalTime.parse("22:30"));
        cbStartTime.getItems().add(LocalTime.parse("22:45"));
        cbStartTime.getItems().add(LocalTime.parse("23:00"));
        cbStartTime.getItems().add(LocalTime.parse("23:15"));
        cbStartTime.getItems().add(LocalTime.parse("23:30"));
        cbStartTime.getItems().add(LocalTime.parse("23:45"));

        cbEndTime.getItems().add(LocalTime.parse("00:00"));
        cbEndTime.getItems().add(LocalTime.parse("00:15"));
        cbEndTime.getItems().add(LocalTime.parse("00:30"));
        cbEndTime.getItems().add(LocalTime.parse("00:45"));
        cbEndTime.getItems().add(LocalTime.parse("01:00"));
        cbEndTime.getItems().add(LocalTime.parse("01:15"));
        cbEndTime.getItems().add(LocalTime.parse("01:30"));
        cbEndTime.getItems().add(LocalTime.parse("01:45"));
        cbEndTime.getItems().add(LocalTime.parse("02:00"));
        cbEndTime.getItems().add(LocalTime.parse("02:15"));
        cbEndTime.getItems().add(LocalTime.parse("02:30"));
        cbEndTime.getItems().add(LocalTime.parse("02:45"));
        cbEndTime.getItems().add(LocalTime.parse("03:00"));
        cbEndTime.getItems().add(LocalTime.parse("03:15"));
        cbEndTime.getItems().add(LocalTime.parse("03:30"));
        cbEndTime.getItems().add(LocalTime.parse("03:45"));
        cbEndTime.getItems().add(LocalTime.parse("04:00"));
        cbEndTime.getItems().add(LocalTime.parse("04:15"));
        cbEndTime.getItems().add(LocalTime.parse("04:30"));
        cbEndTime.getItems().add(LocalTime.parse("04:45"));
        cbEndTime.getItems().add(LocalTime.parse("05:00"));
        cbEndTime.getItems().add(LocalTime.parse("05:15"));
        cbEndTime.getItems().add(LocalTime.parse("05:30"));
        cbEndTime.getItems().add(LocalTime.parse("05:45"));
        cbEndTime.getItems().add(LocalTime.parse("06:00"));
        cbEndTime.getItems().add(LocalTime.parse("06:15"));
        cbEndTime.getItems().add(LocalTime.parse("06:30"));
        cbEndTime.getItems().add(LocalTime.parse("06:45"));
        cbEndTime.getItems().add(LocalTime.parse("07:00"));
        cbEndTime.getItems().add(LocalTime.parse("07:15"));
        cbEndTime.getItems().add(LocalTime.parse("07:30"));
        cbEndTime.getItems().add(LocalTime.parse("07:45"));
        cbEndTime.getItems().add(LocalTime.parse("08:00"));
        cbEndTime.getItems().add(LocalTime.parse("08:15"));
        cbEndTime.getItems().add(LocalTime.parse("08:30"));
        cbEndTime.getItems().add(LocalTime.parse("08:45"));
        cbEndTime.getItems().add(LocalTime.parse("09:00"));
        cbEndTime.getItems().add(LocalTime.parse("09:15"));
        cbEndTime.getItems().add(LocalTime.parse("09:30"));
        cbEndTime.getItems().add(LocalTime.parse("09:45"));
        cbEndTime.getItems().add(LocalTime.parse("10:00"));
        cbEndTime.getItems().add(LocalTime.parse("10:15"));
        cbEndTime.getItems().add(LocalTime.parse("10:30"));
        cbEndTime.getItems().add(LocalTime.parse("10:45"));
        cbEndTime.getItems().add(LocalTime.parse("11:00"));
        cbEndTime.getItems().add(LocalTime.parse("11:15"));
        cbEndTime.getItems().add(LocalTime.parse("11:30"));
        cbEndTime.getItems().add(LocalTime.parse("11:45"));
        cbEndTime.getItems().add(LocalTime.parse("12:00"));
        cbEndTime.getItems().add(LocalTime.parse("12:15"));
        cbEndTime.getItems().add(LocalTime.parse("12:30"));
        cbEndTime.getItems().add(LocalTime.parse("12:45"));
        cbEndTime.getItems().add(LocalTime.parse("13:00"));
        cbEndTime.getItems().add(LocalTime.parse("13:15"));
        cbEndTime.getItems().add(LocalTime.parse("13:30"));
        cbEndTime.getItems().add(LocalTime.parse("13:45"));
        cbEndTime.getItems().add(LocalTime.parse("14:00"));
        cbEndTime.getItems().add(LocalTime.parse("14:15"));
        cbEndTime.getItems().add(LocalTime.parse("14:30"));
        cbEndTime.getItems().add(LocalTime.parse("14:45"));
        cbEndTime.getItems().add(LocalTime.parse("15:00"));
        cbEndTime.getItems().add(LocalTime.parse("15:15"));
        cbEndTime.getItems().add(LocalTime.parse("15:30"));
        cbEndTime.getItems().add(LocalTime.parse("15:45"));
        cbEndTime.getItems().add(LocalTime.parse("16:00"));
        cbEndTime.getItems().add(LocalTime.parse("16:15"));
        cbEndTime.getItems().add(LocalTime.parse("16:30"));
        cbEndTime.getItems().add(LocalTime.parse("16:45"));
        cbEndTime.getItems().add(LocalTime.parse("17:00"));
        cbEndTime.getItems().add(LocalTime.parse("17:15"));
        cbEndTime.getItems().add(LocalTime.parse("17:30"));
        cbEndTime.getItems().add(LocalTime.parse("17:45"));
        cbEndTime.getItems().add(LocalTime.parse("18:00"));
        cbEndTime.getItems().add(LocalTime.parse("18:15"));
        cbEndTime.getItems().add(LocalTime.parse("18:30"));
        cbEndTime.getItems().add(LocalTime.parse("18:45"));
        cbEndTime.getItems().add(LocalTime.parse("19:00"));
        cbEndTime.getItems().add(LocalTime.parse("19:15"));
        cbEndTime.getItems().add(LocalTime.parse("19:30"));
        cbEndTime.getItems().add(LocalTime.parse("19:45"));
        cbEndTime.getItems().add(LocalTime.parse("20:00"));
        cbEndTime.getItems().add(LocalTime.parse("20:15"));
        cbEndTime.getItems().add(LocalTime.parse("20:30"));
        cbEndTime.getItems().add(LocalTime.parse("20:45"));
        cbEndTime.getItems().add(LocalTime.parse("21:00"));
        cbEndTime.getItems().add(LocalTime.parse("21:15"));
        cbEndTime.getItems().add(LocalTime.parse("21:30"));
        cbEndTime.getItems().add(LocalTime.parse("21:45"));
        cbEndTime.getItems().add(LocalTime.parse("22:00"));
        cbEndTime.getItems().add(LocalTime.parse("22:15"));
        cbEndTime.getItems().add(LocalTime.parse("22:30"));
        cbEndTime.getItems().add(LocalTime.parse("22:45"));
        cbEndTime.getItems().add(LocalTime.parse("23:00"));
        cbEndTime.getItems().add(LocalTime.parse("23:15"));
        cbEndTime.getItems().add(LocalTime.parse("23:30"));
        cbEndTime.getItems().add(LocalTime.parse("23:45"));*/

    }




}
