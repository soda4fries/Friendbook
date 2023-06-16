package ExtraFile;

import com.wia1002g3.friendbook.entity.FriendshipGraph;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FriendshipGraphService {

    private final FriendshipGraphRepository friendshipGraphRepository;

    public FriendshipGraphService(FriendshipGraphRepository friendshipGraphRepository) {
        this.friendshipGraphRepository = friendshipGraphRepository;
    }

    public FriendshipGraph getSingletonFriendshipGraph() {
        Optional<FriendshipGraph> existingGraph = friendshipGraphRepository.findByGraphName("ONLY_GRAPH");
        if (existingGraph.isPresent()) {
            return existingGraph.get();
        } else {
            FriendshipGraph newGraph = new FriendshipGraph();
            newGraph.setGraphName("ONLYGRAPH");
            // Set other properties and perform necessary initialization
            return friendshipGraphRepository.save(newGraph);
        }
    }

}

