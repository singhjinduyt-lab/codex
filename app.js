const lessons = {
  Punjabi: ["Greetings: Sat Sri Akal", "Family words", "Ordering food"],
  Hindi: ["Namaste and introductions", "Daily routine verbs", "Travel phrases"],
  English: ["Confident self-introduction", "Workplace communication", "Storytelling basics"],
};

const speechPrompts = {
  Punjabi: ["ਮੇਰਾ ਨਾਮ ਅਮਨ ਹੈ", "ਤੁਸੀਂ ਕਿਵੇਂ ਹੋ"],
  Hindi: ["मेरा नाम आरव है", "आप कैसे हैं"],
  English: ["My name is Aarav", "How are you today"],
};

const challenges = [
  "Neighborhood challenge: complete 3 Hindi intros this week",
  "Family challenge: practice Punjabi greetings for 10 minutes",
  "Community hall challenge: English storytelling night on Saturday",
];

const languageSelect = document.getElementById("language");
const lessonList = document.getElementById("lessonList");
const targetPhrase = document.getElementById("targetPhrase");
const heardText = document.getElementById("heardText");
const scoreEl = document.getElementById("score");
const streakCount = document.getElementById("streakCount");
const streakMessage = document.getElementById("streakMessage");

function renderLanguages() {
  Object.keys(lessons).forEach((lang) => {
    const option = document.createElement("option");
    option.value = lang;
    option.textContent = lang;
    languageSelect.appendChild(option);
  });
}

function renderLessons(lang) {
  lessonList.innerHTML = "";
  lessons[lang].forEach((lesson) => {
    const li = document.createElement("li");
    li.textContent = lesson;
    lessonList.appendChild(li);
  });
}

function randomPrompt(lang) {
  const prompts = speechPrompts[lang];
  return prompts[Math.floor(Math.random() * prompts.length)];
}

function setPrompt() {
  targetPhrase.textContent = `Say: "${randomPrompt(languageSelect.value)}"`;
  heardText.textContent = "(waiting)";
  scoreEl.textContent = "0%";
}

function levenshtein(a, b) {
  const dp = Array.from({ length: a.length + 1 }, () => Array(b.length + 1).fill(0));
  for (let i = 0; i <= a.length; i += 1) dp[i][0] = i;
  for (let j = 0; j <= b.length; j += 1) dp[0][j] = j;
  for (let i = 1; i <= a.length; i += 1) {
    for (let j = 1; j <= b.length; j += 1) {
      const cost = a[i - 1] === b[j - 1] ? 0 : 1;
      dp[i][j] = Math.min(
        dp[i - 1][j] + 1,
        dp[i][j - 1] + 1,
        dp[i - 1][j - 1] + cost,
      );
    }
  }
  return dp[a.length][b.length];
}

function calculateScore(target, spoken) {
  const t = target.toLowerCase().trim();
  const s = spoken.toLowerCase().trim();
  if (!s) return 0;
  const distance = levenshtein(t, s);
  const maxLen = Math.max(t.length, s.length);
  return Math.max(0, Math.round(((maxLen - distance) / maxLen) * 100));
}

function startRecognition() {
  const Recognition = window.SpeechRecognition || window.webkitSpeechRecognition;
  const target = targetPhrase.textContent.replace('Say: "', "").replace('"', "");

  if (!Recognition) {
    heardText.textContent = "Speech recognition not supported in this browser.";
    return;
  }

  const recognition = new Recognition();
  recognition.lang = languageSelect.value === "Punjabi" ? "pa-IN" : languageSelect.value === "Hindi" ? "hi-IN" : "en-US";
  recognition.onresult = (event) => {
    const spoken = event.results[0][0].transcript;
    heardText.textContent = spoken;
    scoreEl.textContent = `${calculateScore(target, spoken)}%`;
  };
  recognition.start();
}

function renderStreak() {
  const streak = Number(localStorage.getItem("trilingoStreak") || 0);
  streakCount.textContent = String(streak);
}

function completeSession() {
  const today = new Date().toDateString();
  const lastDay = localStorage.getItem("trilingoLastDay");
  let streak = Number(localStorage.getItem("trilingoStreak") || 0);

  if (lastDay === today) {
    streakMessage.textContent = "Already counted today. Keep practicing!";
    return;
  }

  const yesterday = new Date(Date.now() - 86400000).toDateString();
  streak = lastDay === yesterday ? streak + 1 : 1;

  localStorage.setItem("trilingoLastDay", today);
  localStorage.setItem("trilingoStreak", String(streak));
  streakMessage.textContent = "Session complete. Streak updated!";
  renderStreak();
}

function renderChallenges() {
  const challengeList = document.getElementById("challengeList");
  challenges.forEach((challenge) => {
    const li = document.createElement("li");
    li.textContent = challenge;
    challengeList.appendChild(li);
  });
}

renderLanguages();
renderLessons(languageSelect.value || "Punjabi");
setPrompt();
renderStreak();
renderChallenges();

languageSelect.addEventListener("change", () => {
  renderLessons(languageSelect.value);
  setPrompt();
});

document.getElementById("newPrompt").addEventListener("click", setPrompt);
document.getElementById("recordBtn").addEventListener("click", startRecognition);
document.getElementById("completeSession").addEventListener("click", completeSession);
