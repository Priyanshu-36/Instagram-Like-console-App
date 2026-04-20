package socialMediaStructure;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Album extends PostComponent {
    private String name;
    private List<PostComponent> posts;

    public Album(String id, String name, String ownerId) {
        super(id, ownerId);
        this.name = name;
        this.posts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void add(PostComponent component) {
        if (!posts.contains(component)) {
            posts.add(component);
            System.out.println("Added " + component.getId() + " to album " + name);
        }
    }

    @Override
    public void remove(PostComponent component) {
        if (posts.remove(component)) {
            System.out.println("Removed " + component.getId() + " from album " + name);
        }
    }

    @Override
    public List<PostComponent> getChildren() {
        return new ArrayList<>(posts);
    }

    @Override
    public String getContent() {
        return "Album: " + name + " (" + posts.size() + " items)";
    }

    @Override
    public void display(String indent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println(indent + "Album [" + id + "]: " + name);
        System.out.println(indent + "   Items: " + posts.size());
        System.out.println(indent + "   Created: " + createdAt.format(formatter));

        for (PostComponent post : posts) {
            post.display(indent + "   ");
        }
    }

    @Override
    public String toString() {
        return "Album{id='" + id + "', name='" + name + "', posts=" + posts.size() + "}";
    }
}
