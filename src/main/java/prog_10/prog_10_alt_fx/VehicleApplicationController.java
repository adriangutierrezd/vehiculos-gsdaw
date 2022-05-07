package prog_10.prog_10_alt_fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vehicles.Vehiculo;
import vehicles.utils.DNI;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class VehicleApplicationController implements Initializable {

    @FXML
    GridPane vehiclesTable;
    @FXML
    TextField marca;
    @FXML
    Text marcaError;
    @FXML
    TextField matricula;
    @FXML
    Text matriculaError;
    @FXML
    TextField kilometros;
    @FXML
    Text kilometrosError;
    @FXML
    DatePicker fechaMat;
    @FXML
    Text fechaError;
    @FXML
    TextField precio;
    @FXML
    Text precioError;
    @FXML
    TextField nombre;
    @FXML
    Text nombreError;
    @FXML
    TextField NIF;
    @FXML
    Text NIFError;
    @FXML
    TextArea descripcion;
    @FXML
    Text descripcionError;


    private Stage stage;
    private Scene scene;
    private Parent root;
    /* Contiene los errores de los formularios: Campo | Error */
    static Map<String, String> errors = new HashMap<String, String>();
    /** Contiene las etiquetas de error del formulario: Campo | Node **/
    static Map<String, Node> errorAlerts = new HashMap<String, Node>();
    /* Contiene los vehículos: Matrícula | Vehículo */
    static Map<String, Vehiculo> vehiculoList = new HashMap<String, Vehiculo>();

    /* Sirve para saber de forma sencilla en qué escena estamos */
    static String currentScene = "main";

    /* Almacena el vehículo que se quiere editar */
    static Vehiculo vEdit;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        // Guardamos los campos de error
        errorAlerts.put("marca", marcaError);
        errorAlerts.put("matricula", matriculaError);
        errorAlerts.put("kilometros", kilometrosError);
        errorAlerts.put("fechaMat", fechaError);
        errorAlerts.put("NIF", NIFError);
        errorAlerts.put("precio", precioError);
        errorAlerts.put("nombre", nombreError);
        errorAlerts.put("descripcion", descripcionError);


        if(currentScene.equals("main")){

            // Seteamos el tamaño del header de la tabla
            RowConstraints tmpRowCa = new RowConstraints(35);
            vehiclesTable.getRowConstraints().add(0, tmpRowCa);

            // Añadimos los vehículos a la tabla
            vehiculoList.forEach((key, vehiculo)-> {

                Text tmpMat = new Text(key);
                Text tmpNif = new Text(vehiculo.getNif());
                Text tmpMarca = new Text(vehiculo.getMarca());
                Text tmpAnio = new  Text(String.valueOf(vehiculo.getFechaDeMatriculacion().getYear()));
                Text tmpKms = new Text(String.valueOf(vehiculo.getNumeroDeKilometros()));
                Text tmpPrecio = new Text(String.valueOf(vehiculo.getPrecio()));


                // Contenedor de botones
                HBox tmpBtnContainer = new HBox();
                tmpBtnContainer.setStyle("-fx-font-size: 12;");
                tmpBtnContainer.setSpacing(5);
                tmpBtnContainer.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
                
                // Botón de eliminar
                Button tmpDelBtn = new Button("X");
                tmpDelBtn.getStyleClass().add("del-btn");
                tmpDelBtn.setOnAction(ActionEvent -> {
                    deleteV(ActionEvent, key);
                });

                // Botón de editar
                Button tmpEditBtn = new Button("E");
                tmpEditBtn.getStyleClass().add("edit-btn");
                tmpEditBtn.setOnAction(ActionEvent -> {
                    editVehicle(ActionEvent, key);
                });

                // Añadir botones a su container
                tmpBtnContainer.getChildren().addAll(tmpEditBtn, tmpDelBtn);
                tmpBtnContainer.setAlignment(Pos.CENTER);

                // Añadimos los datos
                Node[] nodos = {tmpMat, tmpNif, tmpMarca, tmpAnio, tmpKms, tmpPrecio, tmpBtnContainer};
                vehiclesTable.addRow(vehiclesTable.getRowCount(), nodos);

                // Posicionamos los datos
                for(Node n : nodos){
                    vehiclesTable.setValignment(n, VPos.CENTER);
                    vehiclesTable.setHalignment(n, HPos.CENTER);
                }

                // Seteamos el tamaño de las celdas
                RowConstraints tmpRowC = new RowConstraints(35);
                vehiclesTable.getRowConstraints().add(tmpRowC);

            });

        }else if(currentScene.equals("edit")){

            // Poblar el formulario
            marca.setText(vEdit.getMarca());
            matricula.setText(vEdit.getMatricula());
            kilometros.setText(String.valueOf(vEdit.getNumeroDeKilometros()));
            fechaMat.setValue(vEdit.getFechaDeMatriculacion());
            precio.setText(String.valueOf(vEdit.getPrecio()));
            nombre.setText(vEdit.getNombreDelPropietario());
            NIF.setText(vEdit.getNif());
            descripcion.setText(vEdit.getDescripcion());

        }


    }

    /**
     * Elimina un vehículo de la lista y recarga la escena
     * @param e Evento click que desencadena la función
     * @param key Matrícula del coche a eliminar
     */
    private void deleteV(ActionEvent e, String key){
        vehiculoList.remove(key);
        try{
            toSceneMain(e);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Obtiene el vehículo que se quiere editar y cambia a la vista de edición
     * @param e Evento click que desencadena la función
     * @param key Matrícula del coche a editar
     */
    public void editVehicle(ActionEvent e, String key){
        vEdit = vehiculoList.get(key);
        try {
            toSceneEditVehicle(e);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Cambia a la escena principal
     * @param event
     * @throws IOException
     */
    public void toSceneMain(ActionEvent event) throws IOException {
        currentScene = "main";
        root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(VehicleApplication.class.getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cambia a la escena para añadir vehículos
     * @param event
     * @throws IOException
     */
    public void toSceneAddVehicle(ActionEvent event) throws IOException{
        currentScene = "add";
        root = FXMLLoader.load(getClass().getResource("add-vehicle.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(VehicleApplication.class.getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cambia a la escena para editar vehículos
     * @param event
     * @throws IOException
     */
    private void toSceneEditVehicle(ActionEvent event) throws IOException{
        currentScene = "edit";
        root = FXMLLoader.load(getClass().getResource("edit-vehicle.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(VehicleApplication.class.getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Crea un nuevo vehículo o actualiza uno ya existente
     * @param event
     */
    @FXML
    public void addVehicle(ActionEvent event){
        /* Comprobamos que no haya errores en el formulario */
        if(!chekFields()){
           showFormErrors();
        }else{
            String marc = marca.getText();
            String mat = matricula.getText();
            int kms = Integer.parseInt(kilometros.getText());
            float prec = Float.parseFloat(precio.getText().replace(",", "."));
            String desc = descripcion.getText();
            String nom = nombre.getText();
            String nifP = NIF.getText();
            LocalDate fechaM = fechaMat.getValue();

            Vehiculo vehiculo = new Vehiculo(marc, mat, kms, fechaM, desc, prec, nom, nifP);

            // Guardamos o actualizamos
            if(vehiculoList.containsKey(mat)){
                vehiculoList.replace(mat, vehiculo);
            }else{
                vehiculoList.put(mat, vehiculo);
            }

            try{
                toSceneMain(event);
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
            // TODO Posible conexion a una BBDD
        }

    }


    /**
     * Recorre el HashMap de errores, y si encuentra alguno, lo muestra en su etiqueta correspondiente
     */
    private void showFormErrors(){
        errors.forEach((key, value) -> {
            switch (key){
                case "marca":
                    marcaError.setText(value);
                    marcaError.setFill(Color.RED);
                    break;
                case "matricula":
                    matriculaError.setText(value);
                    matriculaError.setFill(Color.RED);
                    break;
                case "kilometros":
                    kilometrosError.setText(value);
                    kilometrosError.setFill(Color.RED);
                    break;
                case "fecha":
                    fechaError.setText(value);
                    fechaError.setFill(Color.RED);
                    break;
                case "nombre":
                    nombreError.setText(value);
                    nombreError.setFill(Color.RED);
                    break;
                case "NIF":
                    NIFError.setText(value);
                    NIFError.setFill(Color.RED);
                    break;
                case "descripcion":
                    descripcionError.setText(value);
                    descripcionError.setFill(Color.RED);
                    break;
                case "precio":
                    precioError.setText(value);
                    precioError.setFill(Color.RED);
            }
        });
    }


    /**
     * Comprueba que no haya errores en el formulario, si los hay, los guarda en un HashMap
     * @return true si el formulario no tiene errores, false si contiene uno o más errores
     */
    private boolean chekFields(){
        /* Eliminamos los errores del formulario para hacer el chekeo de nuevo */
        errorAlerts.forEach((key, value) -> {
            Text tmpText = (Text)value;
            tmpText.setText("");
        });

        /* Limpiamos el HashMap de errores */
        errors.clear();

        /* Validaciones marca */
        if(marca.getText().trim().isEmpty()) errors.put("marca", "Debes introducir una marca");

        /* Validaciones matricula */
        if(matricula.getText().trim().isEmpty()){
            errors.put("matricula", "Debes introducir una matrícula");
        }else if(!matricula.getText().matches("[0-9]{4}[A-Z]{3}")){
            errors.put("matricula", "Formato no válido (NNNNLLL)");
        }else if(!currentScene.equals("edit") && vehiculoList.containsKey(matricula.getText().trim())){
            errors.put("matricula", "Esta matrícula ya está en uso");
        }

        /* Validaciones fecha */
        if(fechaMat.getValue() == null) errors.put("fecha", "La fecha de matriculación es obligatoria");

        /* Validaciones kilometros */
        if(kilometros.getText().trim().isEmpty()){
            errors.put("kilometros", "Debes introducir un kilometraje");
        }else if(!kilometros.getText().matches("[0-9]*")){
            errors.put("kilometros", "Debes introducir un número");
        }


        /* Validaciones precio */
        if(precio.getText().trim().isEmpty()){
            errors.put("precio", "Debes introducir un precio");
        }else if(!precio.getText().replace(",", ".").matches("\\d*\\.?\\d*")){
            errors.put("precio", "Formato no válido (N*.N*)");
        }

        /* Validaciones nombre */
        if(nombre.getText().trim().isEmpty()) errors.put("nombre", "Debes introducir un nombre");

        /* Validaciones NIF */
        if(NIF.getText().trim().isEmpty()){
            errors.put("NIF", "Debes introducir un NIF");
        }else if(!DNI.validarNIF(NIF.getText().trim())){
            errors.put("NIF", "NIF inválido");
        }

        /* Validaciones descripcion*/
        if(descripcion.getText().trim().isEmpty()) errors.put("descripcion", "Debes introducir una descripción");


        return errors.isEmpty();
    }



}