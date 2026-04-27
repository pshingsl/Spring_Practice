package Spring.ex.SpringEx.CH10.Service;

import Spring.ex.SpringEx.CH10.DTO.TodoDTO;
import Spring.ex.SpringEx.CH10.Entity.TodoEntity;
import Spring.ex.SpringEx.CH10.Repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    // 생성
    public List<TodoEntity> create(TodoEntity entity) {
        todoRepository.save(entity);
        return todoRepository.findByUserId(entity.getUserId());
    }

    // 조회
    public List<TodoEntity> retrieve(String userId) {
        return todoRepository.findByUserId(userId);
    }

    // 업데이트
    public TodoDTO update(TodoDTO dto, long id, String userId) {
        TodoEntity todoEntity = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo Not Found"));

        if (!todoEntity.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        todoEntity.update(dto.getTitle(), dto.isDone());

        return new TodoDTO(todoEntity);
    }

    // 삭제
    public void delete(long id, String userId) {
        TodoEntity todoEntity = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo Not Found"));

        if (!todoEntity.getId().equals(id)) {
            throw new RuntimeException("Unauthorized access");
        }

        todoRepository.delete(todoEntity);
    }

    /*
     * 유효성 검사
     * - 데이터의 무결성과 안정성을 확보하기 위해서이다.
     * - 단순히 회원가입뿐만 아니라, 데이터베이스에 새로운 데이터를 저장하거나 기존 데이터를 수정할 때 항상 필요
     * - TodoEntity가 데이터베이스의 저장되기 전에 두 가지 사항 확인
     *
     * 1. entity가 null인지 확인
     * - 클라이언트로부터  받은 데이터가 유효하지 않을 수 있다.
     * - 잘못된 형식 JSON을 보내거나, HTTP요청 본문이 비었을 경우 Entity 객체가 null이 될 수 있다.
     * - 객체가 null이 될 수 있다. -> 객체 그대로 리포지토리에
     *   전달하면 예외가 발생하면 애플리케이션이 비정상적인 종료 될 수 있다.
     * - 따라서 validate() 메서드는 예기치 못한 오류 방지
     * - 클라이언트에게 명확한 에러 메시지를 반환
     *
     * 2. entity.getUserId()가 null인지 확인
     * - Todo는 반드시 사용자에게 종속되어야 한다.
     *  - 만약 userId가 null인 상태로 저장된다면, Todo는 누구의 것인지 확인이 힘들다
     *  - 데이터베이스에 userId가 not null로 설정되면 저장시도 자체가 실패
     * */
    private void validate(TodoEntity entity) {
        if (entity == null) {
            throw new RuntimeException("Entity는 null을 사용할 수 없다.");
        }

        if(entity.getUserId() == null) {
            throw new RuntimeException("Unknown user");
        }
    }
}
