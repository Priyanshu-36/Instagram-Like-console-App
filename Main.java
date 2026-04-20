import socialMediaCreational.FeedManager;
import socialMediaObserver.*;
import socialMediaStructure.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FeedManager feedManager = FeedManager.getInstance();
        String loggedIn = null;
  
        System.out.println("=== Instagram-like Console App ===\n");

        boolean running = true;
        while (running) {
            try {
                if (loggedIn == null) {
                    // Guest menu
                    printGuestMenu();
                    System.out.print("Enter choice: ");
                    String line = scanner.nextLine().trim();
                    if (line.isEmpty())
                        continue;
                    int choice = Integer.parseInt(line);

                    switch (choice) {
                        case 1: // Create account
                            System.out.print("Account ID: ");
                            String accId = scanner.nextLine().trim();
                            System.out.print("Name: ");
                            String accName = scanner.nextLine().trim();
                            feedManager.createAccount(accId, accName);
                            break;
                        case 2: // Login
                            System.out.print("Account ID to login: ");
                            String loginId = scanner.nextLine().trim();
                            Account acc = feedManager.getAccount(loginId);
                            if (acc == null) {
                                System.out.println("Account not found: " + loginId);
                            } else {
                                loggedIn = loginId;
                                // ensure follower identity exists
                                feedManager.getOrCreateFollower(loggedIn);
                                System.out.println("Logged in as: " + acc.getName() + " (" + acc.getAccountId() + ")");
                            }
                            break;
                        case 3: // Display all accounts
                            feedManager.displayAllAccounts();
                            break;
                        case 0:
                            running = false;
                            break;
                        default:
                            System.out.println("Unknown option.");
                    }
                } else {
                    // User menu
                    Account me = feedManager.getAccount(loggedIn);
                    Follower meFollower = feedManager.getOrCreateFollower(loggedIn);
                    System.out.println("\n--- User: " + me.getName() + " ---");
                    printUserMenu();
                    System.out.print("Enter choice: ");
                    String line = scanner.nextLine().trim();
                    if (line.isEmpty())
                        continue;
                    int choice = Integer.parseInt(line);

                    switch (choice) {
                        case 1: // Show all accounts to follow
                            System.out.println("\n=== Accounts ===");
                            for (Account a : feedManager.getAllAccounts()) {
                                if (a.getAccountId().equals(loggedIn))
                                    continue;
                                boolean following = meFollower.isFollowing(a.getAccountId());
                                System.out.println(a.getAccountId() + ": " + a.getName() +
                                        " (Followers: " + a.getFollowerCount() + ") " +
                                        (following ? "[Following]" : "[Follow]"));
                            }
                            break;
                        case 2: // Follow
                            System.out.print("Account ID to follow: ");
                            String targetFollow = scanner.nextLine().trim();
                            if (targetFollow.equals(loggedIn)) {
                                System.out.println("You cannot follow yourself.");
                                break;
                            }
                            feedManager.follow(loggedIn, targetFollow);
                            break;
                        case 3: // Unfollow
                            System.out.print("Account ID to unfollow: ");
                            String targetUnfollow = scanner.nextLine().trim();
                            feedManager.unfollow(loggedIn, targetUnfollow);
                            break;
                        case 4: // Create post
                            System.out.print("Content: ");
                            String content = scanner.nextLine().trim();
                            feedManager.createPost(loggedIn, content);
                            break;
                        case 5: // Create album
                            System.out.print("Album name: ");
                            String albumName = scanner.nextLine().trim();
                            feedManager.createAlbum(loggedIn, albumName);
                            break;
                        case 6: // Add post to album
                            System.out.print("Album ID: ");
                            String albumId = scanner.nextLine().trim();
                            System.out.print("Post ID to add: ");
                            String postId = scanner.nextLine().trim();
                            PostComponent albumComp = feedManager.getPost(albumId);
                            PostComponent postComp = feedManager.getPost(postId);
                            if (albumComp == null)
                                System.out.println("Album not found: " + albumId);
                            else if (postComp == null)
                                System.out.println("Post not found: " + postId);
                            else {
                                try {
                                    albumComp.add(postComp);
                                } catch (UnsupportedOperationException ex) {
                                    System.out.println("Cannot add to that component: " + albumId);
                                }
                            }
                            break;
                        case 7: // Like post
                            System.out.print("Post ID to like: ");
                            String likeId = scanner.nextLine().trim();
                            PostComponent toLike = feedManager.getPost(likeId);
                            if (toLike == null)
                                System.out.println("Post not found: " + likeId);
                            else if (toLike instanceof Post) {
                                ((Post) toLike).like();
                                System.out.println("Post liked: " + likeId);
                            } else
                                System.out.println("Only simple posts can be liked.");
                            break;
                        case 8: // Show my posts
                            me.displayPosts();
                            break;
                        case 9: // Show my feed
                            meFollower.printFeed();
                            break;
                        case 10: // Show post/album details
                            System.out.print("Post ID: ");
                            String showId = scanner.nextLine().trim();
                            PostComponent show = feedManager.getPost(showId);
                            if (show == null)
                                System.out.println("Post not found: " + showId);
                            else
                                show.display("");
                            break;
                        case 0: // Logout
                            System.out.println("Logged out: " + me.getName());
                            loggedIn = null;
                            break;
                        default:
                            System.out.println("Unknown option.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
            System.out.println();
        }

        System.out.println("Exiting. Goodbye.");
        scanner.close();
    }

    // Guest menu: create account -> login -> show accounts
    private static void printGuestMenu() {
        System.out.println("Menu (Guest):\n" +
                " 1) Create account\n" +
                " 2) Login\n" +
                " 3) Display all accounts\n" +
                " 0) Exit");
    }

    // User menu: show all accounts to follow -> feed and content actions
    private static void printUserMenu() {
        System.out.println("Menu (User):\n" +
                " 1) Show all accounts to follow\n" +
                " 2) Follow an account\n" +
                " 3) Unfollow an account\n" +
                " 4) Create post\n" +
                " 5) Create album\n" +
                " 6) Add post to album\n" +
                " 7) Like post\n" +
                " 8) Show my posts\n" +
                " 9) Show my feed\n" +
                "10) Show post/album details\n" +
                " 0) Logout");
    }
}
