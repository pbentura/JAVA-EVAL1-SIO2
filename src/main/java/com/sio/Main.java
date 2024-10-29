package com.sio;

import com.sio.apis.MockChrevTzyonApiClient;
import com.sio.models.Position;
import com.sio.models.Target;
import com.sio.repositories.PositionRepository;
import com.sio.repositories.TargetRepository;
import com.sio.services.TargetService;
import com.sio.services.TrackingService;
import org.json.simple.JSONObject;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TargetService targetService = new TargetService();
    private static final TrackingService trackingService = new TrackingService();
    private static final String GREEN = "\u001B[32m";
    private static String RESET = "\u001B[0m";

    public static void main(String[] args) {
        MockChrevTzyonApiClient mctAPI = new MockChrevTzyonApiClient();
        TargetRepository targetRepository = new TargetRepository();
        PositionRepository positionRepository = new PositionRepository();

        mctAPI.getTargets();
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        ArrayList<JSONObject> targets = mctAPI.getTargets();

                        for (JSONObject onetarget : targets) {
                            System.out.println(onetarget.toString());

                            Target t = new Target();

                            t.setHash((String) onetarget.get("hash"));
                            t.setName((String) onetarget.get("name"));
                            t.setCodeName((String) onetarget.get("code_name"));

                            Target existingTarget = targetRepository.findByHash(t.getHash());

                            if (existingTarget == null) {
                                targetRepository.create(t);
                                System.out.println("Nouvelle cible ajoutée : " + t.getName());
                            } else {
                                System.out.println("La cible existe déjà dans la base de données : " + t.getName());

                                Float latitude = ((Number) onetarget.get("latitude")).floatValue();
                                Float longitude = ((Number) onetarget.get("longitude")).floatValue();

                                String timestampString = (String) ((JSONObject) onetarget.get("updated_at")).get("Time");
                                Instant instant = Instant.parse(timestampString);
                                Timestamp timestamp = Timestamp.from(instant);

                                Position newPosition = new Position(existingTarget, latitude, longitude, timestamp);

                                positionRepository.create(newPosition);
                                System.out.println("Nouvelle position ajoutée pour la cible " + t.getName() + " : Lat=" + latitude + ", Lon=" + longitude);
                            }
                        }
                    }
                },
                0,
                60000
        );
        System.out.println(GREEN);
        printConnectBanner();

        while(true) {
            System.out.println("===============================================");
            System.out.println("Actions menu");
            System.out.println("===============================================");
            System.out.println("1. List targets");
            System.out.println("2. Acquire targets positions");
            System.out.println("3. Add target");
            System.out.println("4. Delete target");
            System.out.println("0. Exit");
            System.out.println("===============================================");

            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            ArrayList<Target> targets;
            PositionRepository pRepository = new PositionRepository();
            switch (option) {
                case 1:
                    System.out.println("List targets");
                    System.out.println("-----------------------------------------------");
                    //TODO : Get all targets from the database and print them
                    ArrayList<Target> lesTargets = targetService.getTargets();

                    for (Target target: lesTargets){
                        ArrayList<Position> lesPositions = pRepository.findByTargetHash(target.getHash());
                        System.out.println(
                                "Code Name: " + target.getCodeName() +
                                        ", Name: " + target.getName() +
                                        ", Positions: " + lesPositions.size()
                        );
                    }
                    System.out.println("-----------------------------------------------");
                    break;
                case 2:
                    System.out.println("Acquire targets positions");
                    System.out.println("-----------------------------------------------");
                    //TODO : Acquire all targets positions
                    trackingService.updateTargetsPositions();
                    System.out.println("-----------------------------------------------");
                    break;
                case 3:
                    System.out.println("Add target");
                    System.out.println("-----------------------------------------------");
                    //TODO : Add a target
                    System.out.println("input Codename:");
                    String codename = scanner.nextLine();
                    System.out.println("input Name:");
                    String name = scanner.nextLine();
                    targetService.addTarget(codename, name);
                    System.out.println("-----------------------------------------------");
                    break;
                case 4:
                    System.out.println("Delete target");
                    System.out.println("-----------------------------------------------");
                    //TODO : Delete a target
                    System.out.println("-----------------------------------------------");

                    ArrayList<Target> targetsy = targetService.getTargets();

                    for (int i = 0; i < targetsy.size(); i++) {
                        Target target = targetsy.get(i);
                        System.out.println((i + 1) + ". Code Name: " + target.getCodeName() +
                                ", Name: " + target.getName());
                    }

                    System.out.print("Please select a target by entering the corresponding number: ");
                    int selection = scanner.nextInt();

                    if (selection > 0 && selection <= targetsy.size()) {
                        Target selectedTarget = targetsy.get(selection - 1);
                        System.out.println("You selected:");
                        System.out.println("Code Name: " + selectedTarget.getCodeName() +
                                ", Name: " + selectedTarget.getName() +
                                ", Hash: " + selectedTarget.getHash() );
                        pRepository.deleteByTargetHash(selectedTarget.getHash());
                        targetService.deleteTarget(selectedTarget);

                    } else {
                        System.out.println("Invalid selection");
                    }

                    break;
                case 0:
                    printDisconnectBanner();
                    System.out.println(RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void printConnectBanner() {

        System.out.println("===============================================");
        System.out.println("    CHREV TZYON INTERFACE     ");
        System.out.println("===============================================");
        System.out.println("    Establishing connection to satellite...    ");
        System.out.println("===============================================");

        String[] progressIndicators = {"|", "/", "-", "\\"};
        int iterations = 10;

        for (int i = 0; i < iterations; i++) {
            System.out.print("\rConnecting " + progressIndicators[i % progressIndicators.length]);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        System.out.println("\rConnection established.        ");
        System.out.println("===============================================");
        System.out.println("Satellite link active. Ready to transmit data.");
        System.out.println("===============================================");

    }

    private static void printDisconnectBanner() {
        System.out.println("===============================================");
        System.out.println("    Closing connection to satellite...         ");
        System.out.println("===============================================");

        String[] progressIndicators = {"|", "/", "-", "\\"};
        int iterations = 10;

        for (int i = 0; i < iterations; i++) {
            System.out.print("\rDisconnecting " + progressIndicators[i % progressIndicators.length]);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        System.out.println("\rConnection Closed.        ");
        System.out.println("===============================================");

    }
}