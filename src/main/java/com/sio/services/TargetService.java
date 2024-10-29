package com.sio.services;

import com.sio.apis.MockChrevTzyonApiClient;
import com.sio.models.Position;
import com.sio.models.Target;
import com.sio.repositories.PositionRepository;
import com.sio.repositories.TargetRepository;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class TargetService {
    private final MockChrevTzyonApiClient mockChrevTzyonApiClient = new MockChrevTzyonApiClient();

    private final TargetRepository tRepository;
    private final PositionRepository pRepository;

    public TargetService() {
        this.tRepository = new TargetRepository();
        this.pRepository = new PositionRepository();
    }

    /**
     * Get all targets stored in database and their respective positions
     *
     * @return targets ArrayList
     */
    public ArrayList<Target> getTargets() {
      //TODO implements this method
        ArrayList<Target> targets = tRepository.findAll();
        for (Target t : targets) {
            ArrayList<Position> positions = pRepository.findByTargetHash(t.getHash());
            t.setPositions(positions);
        }
        return targets;
    }

    /**
     * Add a target to the API
     * Print the following message after the target is succesfully added to the API : Target succesfully added, you will now have to wait 60 seconds before the target is available for position acquisition
     * @param codename String
     * @param name String
     */
    public void addTarget(String codename, String name) {
        //TODO implements this method
            Target t = new Target();
            t.setHash(String.valueOf(System.currentTimeMillis()));
            t.setCodeName(codename);
            t.setName(name);

            MockChrevTzyonApiClient apiClient = new MockChrevTzyonApiClient();
            apiClient.addTarget(t);
            tRepository.create(t);
    }

    /**
     * Delete a target from the database and the API
     * @param t Target
     */
    public void deleteTarget(Target t) {
        //TODO implements this method
        MockChrevTzyonApiClient mctAPI = new MockChrevTzyonApiClient();
        TargetRepository targetRepository = new TargetRepository();

        mctAPI.deleteTarget(t);
        targetRepository.delete(t);
    }

}
