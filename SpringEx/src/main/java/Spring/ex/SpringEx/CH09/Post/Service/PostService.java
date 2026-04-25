package Spring.ex.SpringEx.CH09.Post.Service;

import Spring.ex.SpringEx.CH09.Post.DTO.PostDTO;
import Spring.ex.SpringEx.CH09.Post.Entity.Post;
import Spring.ex.SpringEx.CH09.Post.Repository.PostRepository;
import Spring.ex.SpringEx.CH09.User.Entity.User;
import Spring.ex.SpringEx.CH09.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 생성
    @Transactional
    public PostDTO create(Long userId, PostDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저 ID의 사용자가 없습니다."));
        Post post = convertToEntity(user, dto);

        Post savedPost = postRepository.save(post);

        return convertToDTO(savedPost);
    }

    // 전체 조회
    public List<PostDTO> getAll() {
        List<Post> posts = postRepository.findAll();

        List<PostDTO> dtos = new ArrayList<>();

        for (Post post : posts) {
            PostDTO dto = convertToDTO(post);
            dtos.add(dto);
        }

        return dtos;
    }

    // 게시글 조회
    public PostDTO findById(Long userId, Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 게시글 ID의 게시글이 없습니다."));

        if (!post.getUser().getId().equals(userId)) {
            throw new RuntimeException("해당 유저는 이 게시글에 대한 접근 권한이 없습니다.");
        }

        return convertToDTO(post);
    }

    // 업데이트
    @Transactional
    public void update(Long userId, long id, PostDTO dto) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 게시글 ID의 게시글이 없습니다."));

        if (!post.getUser().getId().equals(userId)) {
            throw new RuntimeException("해당 유저는 이 게시글에 대한 접근 권한이 없습니다.");
        }

        post.update(dto.getTitle(), dto.getContent());

        postRepository.save(post);
    }

    // 삭제
    @Transactional
    public void delete(Long userId, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 게시글 ID의 게시글이 없습니다."));

        if (!post.getUser().getId().equals(userId)) {
            throw new RuntimeException("해당 유저는 이 게시글에 대한 접근 권한이 없습니다.");
        }

        postRepository.delete(post);
    }

    private Post convertToEntity(User user, PostDTO dto) {
        return Post.builder()
                .id(dto.getId())
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }

    private PostDTO convertToDTO(Post post) {
        Long userId = post.getId() != null ? post.getUser().getId() : null;
        String username = post.getUser() != null ? post.getUser().getUsername() : "알 수 없음";

        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(userId)
                .username(username)
                .build();
    }

}
