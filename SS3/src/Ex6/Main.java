package Ex6;

import java.util.List;
import java.util.stream.Collectors;

record Post(String title, List<String> tags) {}

public class Main {
    public static void main(String[] args) {
        List<Post> posts = List.of(
                new Post("Bài viết về Java", List.of("java", "backend")),
                new Post("Bài viết về Python", List.of("python", "data")),
                new Post("Bài viết về Spring", List.of("java", "spring", "backend"))
        );

        System.out.println("Danh sách tất cả các tag sau khi làm phẳng:");

        List<String> allTags = posts.stream()
                .flatMap(post -> post.tags().stream())

                .collect(Collectors.toList());

        System.out.println(allTags);
    }
}
