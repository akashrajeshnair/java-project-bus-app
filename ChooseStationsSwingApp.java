package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.List;

class Station {
    String name;
    String type;
    double lat;
    double lon;
    public Station(String name, String type, double lat, double lon) {
        this.name = name;
        this.type = type;
        this.lat = lat;
        this.lon = lon;
    }
}

class Edge {
    Station source;
    Station destination;
    double weight;

    public Edge(Station source, Station destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}

class Graph {
    private Map<Station, java.util.List<Edge>> adjacencyList;
    public java.util.List<Station> allStations;
    private java.util.List<Station> busOnlyStations;
    private java.util.List<Station> trainOnlyStations;
    private java.util.List<Station> transitStations;
    public Graph() {
        this.adjacencyList = new HashMap<>();
        this.busOnlyStations = new ArrayList<>();
        this.trainOnlyStations = new ArrayList<>();
        this.transitStations = new ArrayList<>();
        this.allStations = new ArrayList<>();
    }

    public void addStation(Station station) {
        adjacencyList.put(station, new ArrayList<>());
        allStations.add(station);
        System.out.println(allStations);
        if (station.type.equals("bus")) {
            busOnlyStations.add(station);
        } else if (station.type.equals("train")) {
            trainOnlyStations.add(station);
        }
        else{
            transitStations.add(station);
        }
        System.out.println("addStation");
    }

    public List<String> executePathCheck(){
        List<String> fastestRoute = new ArrayList<>();
        System.out.println("executecheck");
        for(Station s : allStations){
            Class<?> myClass1 = s.getClass();
            try {
                Field namefield1 = myClass1.getDeclaredField("name");
                namefield1.setAccessible(true);
                String name1 = (String) namefield1.get(s);
                if(Objects.equals(ChooseStationsSwingApp.selectedSource, name1)){
                    System.out.println(name1);
                    for (Station st: allStations){
                        Class<?> myClass2 = st.getClass();
                        Field namefield2 = myClass2.getDeclaredField("name");
                        namefield2.setAccessible(true);
                        String name2 = (String) namefield2.get(st);
                        if(Objects.equals(ChooseStationsSwingApp.selectedDestination, name2)){
                            fastestRoute = findShortestPath(s, st, s.type);
                            break;
                        }
                    }
                }
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return fastestRoute;
    }

    public void addConnection(Station source, Station destination, double distance) {
        Edge edge = new Edge(source, destination, distance);
        adjacencyList.get(source).add(edge);
        // Assuming a bidirectional connection
        Edge reverseEdge = new Edge(destination, source, distance);
        adjacencyList.get(destination).add(reverseEdge);
        System.out.println("addconnection");
    }

    public List<String> findShortestPath(Station start, Station end, String mode) {
        System.out.println("findpath");
        Map<Station, Double> distances = new HashMap<>();
        Map<Station, Station> previous = new HashMap<>();
        PriorityQueue<Station> priorityQueue = new PriorityQueue<>(Comparator.comparing(distances::get));

        distances.put(start, 0.0);
        priorityQueue.add(start);

        while (!priorityQueue.isEmpty()) {
            Station current = priorityQueue.poll();
            System.out.println(current.name);

            for (Edge edge : adjacencyList.get(current)) {
                Station neighbor = edge.destination;

                if (!mode.equals("transit") && ((mode.equals("bus") && !(busOnlyStations.contains(neighbor)) ||
                        (mode.equals("train") && !(trainOnlyStations.contains(neighbor)))))) {
                    continue; // Skip edges that don't match the specified mode
                }

                double newDistance = distances.get(current) + edge.weight;

                if (newDistance < distances.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, current);
                    priorityQueue.add(neighbor);
                }
            }
        }

        List<String> path = new ArrayList<>();
        Station current = end;
        while (previous.containsKey(current)) {
            path.add(current.name);
            current = previous.get(current);
        }
        path.add(start.name);
        Collections.reverse(path);
        System.out.println(path);
        return path;
    }
}


public class ChooseStationsSwingApp extends JFrame {
    private JComboBox<String> station1ComboBox;
    private JComboBox<String> station2ComboBox;
    private JButton submitButton;
    private JButton trackingButton;
    private JLabel selectedStationsLabel;
    public static String selectedSource;
    public static String selectedDestination;

    public ChooseStationsSwingApp() {
        super("Choose Stations");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel appPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(52, 152, 219);
                Color color2 = new Color(46, 204, 113);
                GradientPaint gp = new GradientPaint(0, 0, color1, width, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };

        appPanel.setLayout(new BoxLayout(appPanel, BoxLayout.Y_AXIS));
        appPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Choose Stations");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        appPanel.add(titleLabel);

        appPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel station1Label = new JLabel("Select Station 1:");
        appPanel.add(station1Label);


        station1ComboBox = new JComboBox<>();
        appPanel.add(station1ComboBox);

        appPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel station2Label = new JLabel("Select Station 2:");
        appPanel.add(station2Label);

        station2ComboBox = new JComboBox<>();
        appPanel.add(station2ComboBox);

        appPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitForm();
            }
        });
        appPanel.add(submitButton);

        appPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        trackingButton = new JButton("Track");
        trackingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        trackingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new BusTrackingSimulationApp());
            }
        });
        appPanel.add(trackingButton);

        appPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        selectedStationsLabel = new JLabel();
        selectedStationsLabel.setForeground(Color.RED);
        selectedStationsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        appPanel.add(selectedStationsLabel);

        add(appPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        getStations();
        setVisible(true);
    }

    void getStations(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "admin");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select sta_name from stations");
            while (rs.next()) {
                String value = rs.getString("sta_name");
                station1ComboBox.addItem(value);
                station2ComboBox.addItem(value);
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void submitForm() {
        Graph g = new Graph();
        selectedSource = (String) station1ComboBox.getSelectedItem();
        selectedDestination = (String) station2ComboBox.getSelectedItem();
        String errorMessage = "";

        if (selectedSource != null && selectedDestination != null) {
            if (selectedSource.equals(selectedDestination)) {
                errorMessage = "Error: Please choose different stations for Station 1 and Station 2.";
            }
            else {
                System.out.println(g.allStations);
                List<String> route = new ArrayList<>();
                route = g.executePathCheck();
                System.out.println(route);
                String mine = " " + selectedSource + " -> " + selectedDestination;
                errorMessage = "The Fastest Route is" + mine;
            }
        } else {
            errorMessage = "Error: Please choose both stations.";
        }

        selectedStationsLabel.setText(errorMessage);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChooseStationsSwingApp());
        Graph graph = new Graph();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "admin")) {
            String query = "SELECT sta_name, sta_type, sta_lat, sta_lon FROM stations";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    String name = resultSet.getString("sta_name");
                    String type = resultSet.getString("sta_type");
                    double lat = resultSet.getDouble("sta_lat");
                    double lon = resultSet.getDouble("sta_lon");
                    Station station = new Station(name, type, lat, lon);
                    graph.addStation(station);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(int i = 0; i<graph.allStations.size(); i++){
            Station currentStation = graph.allStations.get(i);
            if(i+1<graph.allStations.size()){
                Station nextStation = graph.allStations.get(i+1);
                if(Objects.equals(currentStation.type, nextStation.type) || Objects.equals(nextStation.type, "transit")) {
                    double distance = org.example.BengaluruLocation.haversine(currentStation.lat, currentStation.lon, nextStation.lat, nextStation.lon);
                    graph.addConnection(currentStation,nextStation,distance);
                    System.out.println("here");
                }
            }
        }

    }
}