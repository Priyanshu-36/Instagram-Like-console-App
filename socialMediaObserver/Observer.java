package socialMediaObserver;

import socialMediaStructure.PostComponent;

public interface Observer {
    void update(String accountId, String accountName, PostComponent post);

    String getObserverId();
}
