package socialMediaStructure;

import java.time.format.DateTimeFormatter;

public class Post extends PostComponent {
    private String content;
    private int likes;

    public Post(String id, String content, String ownerId) {
        super(id, ownerId);
        this.content = content;
        this.likes = 0;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void like() {
        likes++;
    }

    @Override
    public void display(String indent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println(indent + "Post [" + id + "]");
        System.out.println(indent + "   Content: " + content);
        System.out.println(indent + "   Likes: " + likes);
        System.out.println(indent + "   Posted: " + createdAt.format(formatter));
    }

    @Override
    public String toString() {
        return "Post{id='" + id + "', content='" + content + "', likes=" + likes + "}";
    }
}
