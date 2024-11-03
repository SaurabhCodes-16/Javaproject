import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TaskManagerApp extends Application {
    private ObservableList<Task> taskList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Task Manager");

        // Initialize the task list
        taskList = FXCollections.observableArrayList();

        // Create ListView to display tasks
        ListView<Task> taskListView = new ListView<>(taskList);
        taskListView.setCellFactory(param -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                } else {
                    setText(task.toString());
                }
            }
        });

        // Task description input
        TextField taskInput = new TextField();
        taskInput.setPromptText("Enter a new task description");

        // Add Task button
        Button addButton = new Button("Add Task");
        addButton.setOnAction(e -> {
            String description = taskInput.getText();
            if (!description.trim().isEmpty()) {
                taskList.add(new Task(description));
                taskInput.clear();
            }
        });

        // Mark Task as Completed button
        Button completeButton = new Button("Mark as Completed");
        completeButton.setOnAction(e -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                selectedTask.markAsCompleted();
                taskListView.refresh();
            }
        });

        // Delete Task button
        Button deleteButton = new Button("Delete Task");
        deleteButton.setOnAction(e -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                taskList.remove(selectedTask);
            }
        });

        // Layout for task input and add button
        HBox inputLayout = new HBox(10, taskInput, addButton);
        HBox.setHgrow(taskInput, Priority.ALWAYS);

        // Layout for complete and delete buttons
        HBox buttonLayout = new HBox(10, completeButton, deleteButton);

        // Main layout
        VBox layout = new VBox(10, taskListView, inputLayout, buttonLayout);
        layout.setPadding(new Insets(10));

        // Set the scene
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

