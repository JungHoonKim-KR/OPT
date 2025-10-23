// 질문 데이터 (변경 없음)
const questions = [
    {
        id: 1,
        q: 'Q1.',
        text: '디지털 공간에서 가장 활발한 시간은 언제인가요?',
        answers: ['DAY', 'Night'],
    },
    {
        id: 2,
        q: 'Q2.',
        text: '온라인 속 당신의 커뮤니케이션 스타일?',
        answers: ['MINIMAL', 'EXPRESSIVE'],
    },
    {
        id: 3,
        q: 'Q3.',
        text: '당신이 끌리는 콘텐츠는 어떤 유형인가요?',
        answers: ['INFORMATIVE', 'FUN'],
    },
    {
        id: 4,
        q: 'Q4.',
        text: '온라인 속 당신의 행동 방식은?',
        answers: ['ACTIVE', 'PASSIVE'],
    },
];

// 현재 질문의 순서를 기억할 변수
let currentQuestionIndex = 0;
// 사용자의 답변(0 또는 1)을 순서대로 저장할 배열
let userAnswers = [];

// HTML 요소를 가져옴
const questionCard = document.getElementById('question-card');

// 화면에 현재 질문을 그려주는 함수
function renderQuestion() {
    const question = questions[currentQuestionIndex];

    const questionHTML = `
    <div class="progress-dots">
      ${questions.map((_, index) =>
        `<div class="dot ${index === currentQuestionIndex ? 'active' : ''}"></div>`
    ).join('')}
    </div>

    <div class="question-content">
      <h2>${question.q}</h2>
      <p>${question.text}</p>
    </div>

    <div class="answers-container">
      ${question.answers.map((answer, index) =>
        // 각 버튼에 data-index 속성으로 0 또는 1을 부여
        `<button class="answer-button" data-index="${index}">${answer}</button>`
    ).join('')}
    </div>
  `;

    questionCard.innerHTML = questionHTML;

    const answerButtons = document.querySelectorAll('.answer-button');
    answerButtons.forEach(button => {
        // 클릭 이벤트 핸들러에 event 객체를 넘겨주도록 수정
        button.addEventListener('click', handleAnswerSelect);
    });
}

// 답변을 선택했을 때 처리할 함수
function handleAnswerSelect(event) {
    // 클릭된 버튼의 data-index 값을 숫자로 변환하여 가져옴 (0 또는 1)
    const selectedIndex = parseInt(event.target.dataset.index);
    // userAnswers 배열에 선택한 답변(0 또는 1)을 추가
    userAnswers.push(selectedIndex);

    console.log('현재까지의 답변:', userAnswers); // 중간 결과 확인용 로그

    // 다음 질문으로 순서를 넘김
    currentQuestionIndex++;

    if (currentQuestionIndex < questions.length) {
        renderQuestion();
    } else {
        // 모든 질문에 답했다면, 서버로 데이터를 전송
        sendSurveyData();
    }
}

// 서버로 설문 데이터를 전송하는 함수
function sendSurveyData() {
    // 1. 사용자에게 전송 중임을 알림
    questionCard.innerHTML = `
    <div class="question-content">
        <h2>전송 중...</h2>
        <p>설문 결과를 서버로 보내고 있습니다.</p>
    </div>
  `;

    // 2. fetch API를 사용하여 서버에 POST 요청 보내기
    fetch('/question/survey', {
        method: 'POST', // HTTP 요청 메소드
        headers: {
            'Content-Type': 'application/json', // 보내는 데이터의 타입
        },
        body: JSON.stringify({
            gender : "남",
            age : 25,
            questionList : userAnswers
        })
    })
        .then(response => {
            // 서버 응답이 정상이 아닐 경우 에러 처리
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // 서버가 보낸 json 데이터를 파싱
        })
        .then(data => {
            // 3. 서버로부터 성공 응답을 받으면 완료 화면 표시
            console.log('서버 응답:', data);
            showCompletionScreen(true, "성공적으로 제출되었습니다.");
        })
        .catch(error => {
            // 4. 전송 중 에러가 발생하면 에러 화면 표시
            console.error('데이터 전송 실패:', error);
            showCompletionScreen(false, "제출 중 오류가 발생했습니다.");
        });
}


// 완료 화면을 보여주는 함수
function showCompletionScreen(isSuccess, message) {
    const title = isSuccess ? "완료!" : "오류";
    const completionHTML = `
        <div class="question-content">
            <h2>${title}</h2>
            <p>${message}</p>
        </div>
    `;
    questionCard.innerHTML = completionHTML;
}

// 스크립트가 처음 실행될 때 첫 번째 질문을 화면에 표시
renderQuestion();