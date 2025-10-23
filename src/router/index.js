import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/quiz",
      name: "quiz",
      component: () => import("../views/QuizView.vue"), // 이런 식으로 연결
    },
    {
      path: "/quiz-intro",
      name: "quiz-intro",
      component: () => import("../views/QuizIntro.vue"), // 이런 식으로 연결
    },
    {
      path: "/loading",
      name: "loading",
      component: () => import("../views/LoadingView.vue"),
    },
    {
      path: "/result",
      name: "result",
      component: () => import("../views/ResultView.vue"),
    },
  ],
});

export default router;
