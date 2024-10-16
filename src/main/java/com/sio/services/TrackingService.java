package com.sio.services;

import com.sio.apis.MockChrevTzyonApiClient;
import com.sio.repositories.PositionRepository;
import com.sio.repositories.TargetRepository;

public class TrackingService {

    private final MockChrevTzyonApiClient mockChrevTzyonApiClient = new MockChrevTzyonApiClient();
    private final TargetRepository targetRepository;
    private final PositionRepository positionRepository;

    public TrackingService() {
        this.targetRepository = new TargetRepository();
        this.positionRepository = new PositionRepository();
    }

    /**
     * Update the positions of all targets
     * If the target does not exist in the database, create it
     * If the target exists in the database, add its new position
     * Print the following message after the position is successfully acquired : - {target code name} : Position successfully acquired
     * Print the following message after the position is not acquired (API has not yet acquired the position : - {target code name} : Position not acquired
     */
    public void updateTargetsPositions() {
        //TODO implements this method
    }


}
