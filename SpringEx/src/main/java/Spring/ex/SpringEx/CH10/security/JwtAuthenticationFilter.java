package Spring.ex.SpringEx.CH10.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try {
            // req에서 token 꺼내오기
            String token = parseBearerToken(req); // 토큰 파싱

            // 토큰 검사
            if (token != null && !token.equalsIgnoreCase("null")) {
                // 사용자 아이디 추출
                String userId = tokenProvider.validateAndGetUserId(token);
                log.info("Authenticated user id: " + userId);

                // 직전에 추출한 userI로 인증한 객체 생성
                AbstractAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(
                        userId, null, AuthorityUtils.NO_AUTHORITIES);
                authenticationToken.setDetails((new WebAuthenticationDetailsSource().buildDetails(req)));

                // SecurityContextHolder: Spring security에서 인증된 사용자 정보를 저장하는 곳
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                // 컨텍스트에 authenticationToken로 심으면 이후부터는 인증된 사용자로 인식됨
                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception e) {
            // logger? spring security 필터 클래스에 기본 내장된 록
            logger.error("Could not set user authentication is security context", e);
        }

        // 다음 필터/컨트롤러로 넘김
        filterChain.doFilter(req, res);
    }
    /*
     * 1. 토큰을 가져온다.
     * 2. 토큰 유효한지 검사 토큰이 null 아니면서 토큰의 문자를 비교했을떄 null이 아닐떄
     * 3. 조건이 만족하면 유저아이디는 토큰을 얻는다.
     * 4. 유저아이디로 인증 객체를 생성
     * 5. 필터 체인 진행
     * */

    // 요청의 헤더에서 Bearer 토큰을 가져옴
    // http 요청의 헤더를 파싱해서 Bearer 토큰을 리턴
    private String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // 헤더 값이 'Bearer '로 시작하는지 검사
        // "Bearer" 문자열(7자리)을 제외한 실제 토큰 부분을 추출하여 반환
        // 7자리를 제외하는 이유는 JWT(JSON Web Token) 앞에 붙는 Bearer 접두사 떄문이다.
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 문자열의 길이가 7
        }
        return null;
    }
}
