package com.example.ui;

import com.example.util.HttpUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class MainFrame extends JFrame {
    private JTable radarTable;
    private JTable infractionTable;
    private DefaultTableModel radarTableModel;
    private DefaultTableModel infractionTableModel;
    private JLabel infractionCountLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;
    private JPanel mainPanel;
    private JPanel loginPanel;

    public MainFrame() {
        setTitle("Radar Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        showLoginPanel();
    }

    private void initComponents() {
        // Initialize tables and models
        radarTableModel = new DefaultTableModel(new String[]{"ID", "Name", "Latitude", "Longitude", "Type", "Status"}, 0);
        radarTable = new JTable(radarTableModel);

        infractionTableModel = new DefaultTableModel(new String[]{"ID", "License Plate", "Timestamp", "Speed", "Description"}, 0);
        infractionTable = new JTable(infractionTableModel);

        infractionCountLabel = new JLabel("Total Infractions: 0");

        // Create refresh button
        JButton refreshButton = new JButton("Refresh data");
        refreshButton.addActionListener(e -> refreshData());

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(refreshButton, BorderLayout.WEST);
        controlPanel.add(infractionCountLabel, BorderLayout.EAST);

        // Create split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(radarTable), new JScrollPane(infractionTable));
        splitPane.setDividerLocation(300);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Create login components
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        statusLabel = new JLabel("Please login");

        loginButton.addActionListener(e -> authenticateUser());

        loginPanel = new JPanel();
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(statusLabel);

        // Set initial visibility
        loginPanel.setVisible(true);
        mainPanel.setVisible(false);

        add(loginPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void showLoginPanel() {
        loginPanel.setVisible(true);
        mainPanel.setVisible(false);
    }

    private void showMainPanel() {
        loginPanel.setVisible(false);
        mainPanel.setVisible(true);
        startDataRefresh();
    }

    private void authenticateUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Intentar iniciar sesión con los valores de application.properties
        if ("alvaro".equals(username) && "alvaro".equals(password)) {
            statusLabel.setText("Login successful");
            showMainPanel();
        } else {
            statusLabel.setText("Login failed");
        }
    }

    private void startDataRefresh() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                refreshData();
            }
        }, 0, 5000);
    }

    private void refreshData() {
        SwingUtilities.invokeLater(() -> {
            try {
                // Fetch radars
                String radarResponse = HttpUtil.sendGetRequest("/api/radars");
                JSONTokener radarTokener = new JSONTokener(radarResponse);
                Object radarJson = radarTokener.nextValue();

                if (radarJson instanceof JSONArray) {
                    JSONArray radars = (JSONArray) radarJson;
                    radarTableModel.setRowCount(0);
                    for (int i = 0; i < radars.length(); i++) {
                        JSONObject radar = radars.getJSONObject(i);
                        radarTableModel.addRow(new Object[]{
                                radar.getLong("id"),
                                radar.getString("name"),
                                radar.getDouble("latitude"),
                                radar.getDouble("longitude"),
                                radar.getString("type"),
                                radar.getString("status")
                        });
                    }
                } else {
                    System.out.println("Unexpected response for radars: " + radarJson);
                }

                // Fetch infractions
                String infractionResponse = HttpUtil.sendGetRequest("/api/infractions");
                JSONTokener infractionTokener = new JSONTokener(infractionResponse);
                Object infractionJson = infractionTokener.nextValue();

                if (infractionJson instanceof JSONArray) {
                    JSONArray infractions = (JSONArray) infractionJson;
                    infractionTableModel.setRowCount(0);
                    for (int i = 0; i < infractions.length(); i++) {
                        JSONObject infraction = infractions.getJSONObject(i);
                        infractionTableModel.addRow(new Object[]{
                                infraction.getLong("id"),
                                infraction.getString("licensePlate"),
                                infraction.getString("timestamp"),
                                infraction.getDouble("speed"),
                                infraction.getString("description")
                        });
                    }
                } else {
                    System.out.println("Unexpected response for infractions: " + infractionJson);
                }

                // Fetch infraction count
                String infractionCountResponse = HttpUtil.sendGetRequest("/api/infractions/count");
                try {
                    long infractionCount = Long.parseLong(infractionCountResponse.trim());
                    infractionCountLabel.setText("Total Infractions: " + infractionCount);
                } catch (NumberFormatException e) {
                    System.out.println("Unexpected response for infraction count: " + infractionCountResponse);
                    infractionCountLabel.setText("Total Infractions: N/A");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}


