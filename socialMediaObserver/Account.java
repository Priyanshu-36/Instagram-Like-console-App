package socialMediaObserver;

import socialMediaStructure.PostComponent;
import java.util.ArrayList;
import java.util.List;

public class Account implements Subject {
    private String accountId;
    private String name;
    private List<Observer> followers;
    private List<PostComponent> posts;
    private PostComponent lastPost;

    public Account(String accountId, String name) {
        this.accountId = accountId;
        this.name = name;
        this.followers = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    @Override
    public void attach(Observer observer) {
        if (!followers.contains(observer)) {
            followers.add(observer);
            System.out.println(((Follower) observer).getName() + " is now following " + name);
        }
    }

    @Override
    public void detach(Observer observer) {
        if (followers.remove(observer)) {
            System.out.println(((Follower) observer).getName() + " unfollowed " + name);
        }
    }

    @Override
    public void notifyObservers() {
        if (lastPost != null) {
            for (Observer observer : followers) {
                observer.update(accountId, name, lastPost);
            }
        }
    }

    public void addPost(PostComponent post) {
        posts.add(post);
        lastPost = post;
        System.out.println(name + " added new content: " + post.getContent());
        notifyObservers();
    }

    public void removePost(PostComponent post) {
        posts.remove(post);
    }

    public List<PostComponent> getPosts() {
        return new ArrayList<>(posts);
    }

    public int getFollowerCount() {
        return followers.size();
    }

    public void displayPosts() {
        System.out.println("\n=== Posts by " + name + " ===");
        if (posts.isEmpty()) {
            System.out.println("No posts yet.");
        } else {
            for (PostComponent post : posts) {
                post.display("");
                System.out.println();
            }
        }
    }

    @Override
    public String toString() {
        return "Account{id='" + accountId + "', name='" + name +
                "', followers=" + followers.size() + ", posts=" + posts.size() + "}";
    }
}
