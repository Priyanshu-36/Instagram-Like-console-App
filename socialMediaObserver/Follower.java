package socialMediaObserver;

import socialMediaStructure.PostComponent;
import java.util.*; // added

public class Follower implements Observer {
    private String followerId;
    private String name;
    // Feed entries and following set
    private final List<String> feed = new ArrayList<>();
    private final Set<String> following = new HashSet<>();

    public Follower(String followerId, String name) {
        this.followerId = followerId;
        this.name = name;
    }

    @Override
    public void update(String accountId, String accountName, PostComponent post) {
        // Store to feed and print notification
        feed.add(accountName + " - " + post.getContent());
        System.out.println("[" + name + "] New notification: " +
                accountName + " posted - " + post.getContent());
    }

    @Override
    public String getObserverId() {
        return followerId;
    }

    public String getName() {
        return name;
    }

    // Following helpers
    public void follow(String accountId) {
        following.add(accountId);
    }

    public void unfollow(String accountId) {
        following.remove(accountId);
    }

    public boolean isFollowing(String accountId) {
        return following.contains(accountId);
    }

    // Add history entries without printing a notification
    public void addHistory(String accountName, PostComponent post) {
        feed.add(accountName + " - " + post.getContent());
    }

    // Print the feed
    public void printFeed() {
        System.out.println("\n=== Your feed (" + name + ") ===");
        if (feed.isEmpty()) {
            System.out.println("No posts in your feed yet.");
        } else {
            for (String entry : feed) {
                System.out.println(" - " + entry);
            }
        }
    }

    @Override
    public String toString() {
        return "Follower{id='" + followerId + "', name='" + name + "'}";
    }
}
