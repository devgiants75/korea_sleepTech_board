# korea_sleepTech_board

# Git 브랜치 및 커밋 컨벤션

본 프로젝트는 협업과 유지보수를 효율적으로 하기 위해 **브랜치 명명 규칙**과 **커밋 메시지 컨벤션**을 아래와 같이 정의합니다.

---

## 📌 브랜치 네이밍 규칙 (Branch Naming Convention)

브랜치는 다음과 같은 형식으로 생성합니다.


- 소문자 사용
- 단어는 `-`(하이픈)으로 구분
- 작업 내용은 구체적으로 작성

### ✅ 브랜치 Prefix 종류

| Prefix     | 설명                                     | 예시                              |
|------------|------------------------------------------|-----------------------------------|
| `feature/` | 새로운 기능 추가                          | `feature/login-api`               |
| `fix/`     | 버그 수정                                | `fix/signup-validation-error`     |
| `refactor/`| 코드 리팩토링 (기능 변경 없음)           | `refactor/user-service`           |
| `hotfix/`  | 운영 중 긴급 수정                         | `hotfix/token-expiration-error`   |
| `docs/`    | 문서 작업 및 README 수정                  | `docs/readme-update`              |
| `test/`    | 테스트 코드 추가 또는 수정                | `test/user-service-unit-test`     |
| `chore/`   | 기타 설정 파일 수정 등                    | `chore/package-update`            |
| `design/`  | UI/UX 관련 작업 (CSS 포함)                | `design/header-layout-update`     |

---

## 📌 커밋 메시지 컨벤션 (Commit Message Convention)

커밋 메시지는 다음 형식을 따릅니다:


### ✅ 커밋 타입 종류

| 타입      | 설명                                            |
|-----------|-------------------------------------------------|
| `feat`    | 새로운 기능 추가                                 |
| `fix`     | 버그 수정                                       |
| `refactor`| 리팩토링 (동작 변화 없이 구조 개선)              |
| `style`   | 코드 스타일 변경 (공백, 세미콜론 등)             |
| `docs`    | 문서 수정                                       |
| `test`    | 테스트 코드 추가 또는 수정                      |
| `chore`   | 빌드/설정 관련 작업, 패키지 매니저 설정 등       |
| `design`  | 프론트엔드 UI 작업 또는 스타일링 수정            |
| `ci`      | CI/CD 관련 설정 작업                            |

### ✅ 커밋 메시지 예시

```bash
feat: 회원가입 API 구현

fix: 로그인시 비밀번호 검증 오류 수정

refactor: 게시판 조회 로직 리팩토링

docs: README에 API 명세 추가

style: 코드 포맷팅 및 들여쓰기 수정

test: 댓글 서비스 단위 테스트 추가

chore: ESLint 설정 및 Prettier 적용

design: 반응형 네비게이션 바 구현

ci: GitHub Actions 배포 파이프라인 설정

main      # 운영 배포용 브랜치
dev       # 통합 개발 브랜치
feature/* # 기능 단위 개발 브랜치
fix/*     # 버그 수정 브랜치

---

모든 작업은 dev 브랜치를 기준으로 분기합니다.
작업 완료 후 Pull Request를 통해 dev에 병합합니다.
기능 테스트 완료 후 main 브랜치로 병합 및 배포합니다.