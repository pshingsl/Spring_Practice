package Spring.ex.SpringEx.CH09.Post.Controller;

import Spring.ex.SpringEx.CH09.Post.DTO.PostDTO;
import Spring.ex.SpringEx.CH09.Post.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 생성
    @PostMapping("/{userId}")
    public PostDTO create(@PathVariable Long userId, @RequestBody PostDTO dto) {
        return postService.create(userId, dto);
    }

    // 조회
    @GetMapping
    public List<PostDTO> getAll() {
        return postService.getAll();
    }

    // 상세 조회
    @GetMapping("/{userId}/{id}")
    public PostDTO getById(@PathVariable Long userId, @PathVariable Long id) {
        return postService.findById(userId, id);
    }

    // 업데이트
    @PutMapping("/{userId}/{id}")
    public String update(@PathVariable Long userId, @PathVariable Long id, @RequestBody PostDTO dto) {
        postService.update(userId, id, dto);
        return "수정 되었습니다.";
    }

    // 삭제
    @DeleteMapping("/{userId}/{id}")
    public String delete(@PathVariable Long userId, @PathVariable Long id){
        postService.delete(userId, id);
        return "삭제 되었습니다.";
    }
}
