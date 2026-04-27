package Spring.ex.SpringEx.CH10.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
    // error 발생 시 에러 메시지를 반환
    private String error;

    // Generic을 이용해서 2xx 대 응답 시 해당 Type의 리스트를 반환
    private List<T> data;
}
