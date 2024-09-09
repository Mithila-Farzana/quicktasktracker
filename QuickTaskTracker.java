import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Task {
    private String title;
    private String description;
    private boolean isCompleted;

    
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.isCompleted = false;  
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return "Title: " + title + " | Description: " + description + " | Status: " + (isCompleted ? "Completed" : "Pending");
    }
}

class TaskRenderer extends JLabel implements ListCellRenderer<Task> {
    public TaskRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Task> list, Task task, int index, boolean isSelected, boolean cellHasFocus) {
        
        setText("<html><b>Title:</b> " + task.getTitle() + "<br/><b>Description:</b> " + task.getDescription() + "<br/><b>Status:</b> " + (task.isCompleted() ? "Completed" : "Pending") + "</html>");

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}

public class QuickTaskTracker extends JFrame {
    private ArrayList<Task> tasks = new ArrayList<>();
    private DefaultListModel<Task> listModel;
    private JList<Task> taskList;
    private JTextField titleField;
    private JTextArea descriptionField;

    public QuickTaskTracker() {
        setTitle("Quick Task Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

       
        Color primaryColor = new Color(50, 50, 50); // Dark grey or navy blue
        Color secondaryColor = new Color(240, 240, 240); // Light grey
        Color accentColor = new Color(173, 216, 230); // Light blue for buttons

        getContentPane().setBackground(secondaryColor);

       
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
        JLabel headerLabel = new JLabel("Quick Task Tracker");
        headerLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 1, 10, 10));
        inputPanel.setBackground(secondaryColor);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        titleField = new JTextField();
        titleField.setFont(new Font("Georgia", Font.PLAIN, 14));
        titleField.setBorder(BorderFactory.createTitledBorder("Task Title"));
        inputPanel.add(titleField);

        descriptionField = new JTextArea(3, 20);
        descriptionField.setFont(new Font("Georgia", Font.PLAIN, 14));
        descriptionField.setBorder(BorderFactory.createTitledBorder("Task Description"));
        inputPanel.add(new JScrollPane(descriptionField));

       
        JButton addButton = new JButton("Add Task");
        addButton.setBackground(accentColor);
        addButton.setFont(new Font("Georgia", Font.BOLD, 14));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.WEST);

        JPanel taskPanel = new JPanel(new BorderLayout());
        taskPanel.setPreferredSize(new Dimension(getWidth() - 200, getHeight()));  
        taskPanel.setBackground(secondaryColor);

        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setFont(new Font("Georgia", Font.PLAIN, 14));
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setCellRenderer(new TaskRenderer()); 

        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        taskPanel.add(scrollPane, BorderLayout.CENTER);

        add(taskPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.setBackground(secondaryColor);

        JButton removeButton = new JButton("Remove Task");
        removeButton.setBackground(accentColor);
        removeButton.setFont(new Font("Georgia", Font.BOLD, 14));
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeTask();
            }
        });
        buttonPanel.add(removeButton);

        JButton completeButton = new JButton("Mark as Completed");
        completeButton.setBackground(accentColor);
        completeButton.setFont(new Font("Georgia", Font.BOLD, 14));
        completeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markTaskAsCompleted();
            }
        });
        buttonPanel.add(completeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addTask() {
        String title = titleField.getText();
        String description = descriptionField.getText();

        if (title.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both title and description.");
        } else {
            Task newTask = new Task(title, description);
            tasks.add(newTask);
            listModel.addElement(newTask);  
            titleField.setText("");  
            descriptionField.setText("");
        }
    }

   
    private void removeTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            tasks.remove(selectedIndex);
            listModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to remove.");
        }
    }

    
    private void markTaskAsCompleted() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            tasks.get(selectedIndex).markAsCompleted();
            taskList.repaint();  
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to mark as completed.");
        }
    }

    public static void main(String[] args) {
        new QuickTaskTracker();
    }
}
