package socialMediaCreational;

import socialMediaStructure.*;
import socialMediaObserver.*;
import java.util.*;

public class FeedManager {
    private static FeedManager instance;
    private Map<String, Account> accounts;
    private Map<String, PostComponent> posts;
    private int postIdCounter;
    // Keep a single follower instance per accountId
    private Map<String, Follower> followers; // added

    private FeedManager() {
        accounts = new HashMap<>();
        posts = new HashMap<>();
        postIdCounter = 1;
        followers = new HashMap<>(); // added
    }

    public static synchronized FeedManager getInstance() {
        if (instance == null) {
            instance = new FeedManager();
        }
        return instance;
    }

    public Account createAccount(String accountId, String name) {
        if (accounts.containsKey(accountId)) {
            throw new IllegalArgumentException("Account already exists: " + accountId);
        }
        Account account = new Account(accountId, name);
        accounts.put(accountId, account);
        // ensure a follower identity exists for this accountId
        followers.putIfAbsent(accountId, new Follower(accountId, name)); // added
        System.out.println("Account created: " + name + " (" + accountId + ")");
        return account;
    }

    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }

    // Convenience to get all accounts
    public Collection<Account> getAllAccounts() { // added
        return new ArrayList<>(accounts.values());
    }

    // Ensure we reuse one follower instance per accountId
    public Follower getOrCreateFollower(String accountId) { // added
        Follower f = followers.get(accountId);
        if (f == null) {
            Account acc = accounts.get(accountId);
            if (acc == null) {
                throw new IllegalArgumentException("Account not found: " + accountId);
            }
            f = new Follower(accountId, acc.getName());
            followers.put(accountId, f);
        }
        return f;
    }

    public Follower getFollower(String accountId) { // added
        return followers.get(accountId);
    }

    // Follow target account from followerAccountId. Backfill existing posts into
    // follower's feed.
    public boolean follow(String followerAccountId, String targetAccountId) { // added
        Account source = accounts.get(followerAccountId);
        Account target = accounts.get(targetAccountId);
        if (source == null)
            throw new IllegalArgumentException("Follower account not found: " + followerAccountId);
        if (target == null)
            throw new IllegalArgumentException("Target account not found: " + targetAccountId);

        Follower follower = getOrCreateFollower(followerAccountId);
        if (follower.isFollowing(targetAccountId)) {
            System.out.println(source.getName() + " already follows " + target.getName());
            return false;
        }

        target.attach(follower); // observer attach
        follower.follow(targetAccountId); // track following set

        // Backfill existing posts to the feed silently
        for (PostComponent pc : target.getPosts()) {
            follower.addHistory(target.getName(), pc);
        }
        return true;
    }

    public boolean unfollow(String followerAccountId, String targetAccountId) { // added
        Account source = accounts.get(followerAccountId);
        Account target = accounts.get(targetAccountId);
        if (source == null)
            throw new IllegalArgumentException("Follower account not found: " + followerAccountId);
        if (target == null)
            throw new IllegalArgumentException("Target account not found: " + targetAccountId);

        Follower follower = getOrCreateFollower(followerAccountId);
        if (!follower.isFollowing(targetAccountId)) {
            System.out.println(source.getName() + " is not following " + target.getName());
            return false;
        }

        target.detach(follower);
        follower.unfollow(targetAccountId);
        return true;
    }

    public Post createPost(String accountId, String content) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }

        String postId = "POST" + postIdCounter++;
        Post post = new Post(postId, content, accountId);
        posts.put(postId, post);
        account.addPost(post);

        System.out.println("Post created: " + postId + " by " + accountId);
        return post;
    }

    public Album createAlbum(String accountId, String albumName) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }

        String albumId = "ALBUM" + postIdCounter++;
        Album album = new Album(albumId, albumName, accountId);
        posts.put(albumId, album);
        account.addPost(album);

        System.out.println("Album created: " + albumName + " (" + albumId + ") by " + accountId);
        return album;
    }

    public boolean deletePost(String postId) {
        PostComponent post = posts.remove(postId);
        if (post != null) {
            Account account = accounts.get(post.getOwnerId());
            if (account != null) {
                account.removePost(post);
            }
            System.out.println("Post deleted: " + postId);
            return true;
        }
        return false;
    }

    public PostComponent getPost(String postId) {
        return posts.get(postId);
    }

    public void displayAllAccounts() {
        System.out.println("\n=== All Accounts ===");
        for (Account account : accounts.values()) {
            System.out.println(account.getAccountId() + ": " + account.getName() +
                    " (Followers: " + account.getFollowerCount() + ")");
        }
    }
}
