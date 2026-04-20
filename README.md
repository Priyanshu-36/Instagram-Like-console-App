# Instagram-like Console App

A console-based social media application built in **Java**. This project demonstrates the practical application of key software design patterns to manage complex system interactions, content structuring, and user updates.

## 🛠 Tech Stack
- **Language**: Java
- **Environment**: Console / Command Line Interface (CLI)

## 🏗 Design Patterns Implemented
The core architecture relies on three primary patterns:

1.  **Singleton Pattern** (`socialMediaCreational.FeedManager`)
    -   **Purpose**: Ensures a single instance of the `FeedManager` handles all global operations like account creation, authentication, and data retrieval.
    -   **Benefit**: Centralized control over application state and data consistency.

2.  **Observer Pattern** (`socialMediaObserver` package)
    -   **Purpose**: Manages the "Follow" feature. When a user posts content, all followers (Observers) are automatically notified and their feeds are updated.
    -   **Benefit**: Decouples the content creator (Subject) from the content consumers (Observers), allowing for real-time feed updates.

3.  **Composite Pattern** (`socialMediaStructure` package)
    -   **Purpose**: Treating individual `Post` objects and `Album` objects (collections of posts) uniformly as `PostComponent`.
    -   **Benefit**: Simplifies client code by allowing operations (like `display()`) to be called on both single posts and complex albums without distinguishing between them.

## ✨ Features
-   **User System**:
    -   **Guest Mode**: Create accounts, login, or view public user lists.
    -   **User Mode**: Authenticated access to specialized features.
-   **Feed & Social Graph**:
    -   **Follow/Unfollow**: Build a network of connections.
    -   **News Feed**: View a consolidated feed of posts from followed accounts.
-   **Content Creation**:
    -   **Posts**: Create simple text-based posts.
    -   **Albums**: Create named albums and add existing posts to them (Recursive content structure).
    -   **Likes**: Like individual posts.

## 🚧 Obstacles Faced & Design Decisions
During development, several key challenges were addressed:
1.  **Complex State Management**: Coordinating the state between the global `FeedManager` and individual `Account` instances required careful synchronization (solved via Singleton).
2.  **Recursive Data Structures**: Implementing `Albums` that can contain `Posts` (or potentially other Albums) required a robust structure. The **Composite Pattern** was chosen to handle this hierarchy gracefully.
3.  **Real-time Updates**: Ensuring that when User A posts, User B's feed updates immediately without manual polling. The **Observer Pattern** provided an efficient event-driven solution.

## 🚀 Setup & Execution Instructions

### Prerequisites
-   Java Development Kit (JDK) installed (Java 8 or higher recommended).

### Compilation
Navigate to the project root directory (containing `Main.java`) and compile the source code:

```bash
javac Main.java
```
*Note: This will automatically look for and compile dependent files in the subdirectories (`socialMediaCreational`, `socialMediaObserver`, `socialMediaStructure`).*

### Running the Application
Run the execution command:

```bash
java Main
```

Follow the on-screen prompts to navigate the menus.

## 📂 Project Structure
```
DP_Project/
├── Main.java                 # Entry point
├── socialMediaCreational/    # Singleton & Factory logic
│   └── FeedManager.java
├── socialMediaObserver/      # Observer pattern logic
│   ├── Subject.java
│   ├── Observer.java
│   ├── Account.java
│   └── Follower.java
└── socialMediaStructure/     # Composite pattern logic
    ├── PostComponent.java
    ├── Post.java
    └── Album.java
```
