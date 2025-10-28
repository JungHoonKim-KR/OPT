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
    //5
    {
      path: "/DEIA",
      name: "deia",
      component: () => import("../views/qrView.vue"),
    },
    //6
    {
      path: "/DEIP",
      name: "deip",
      component: () => import("../views/qrView.vue"),
    },
    //7
    {
      path: "/DMIA",
      name: "dmia",
      component: () => import("../views/qrView.vue"),
    },
    //8
    {
      path: "/DEFA",
      name: "defa",
      component: () => import("../views/qrView.vue"),
    },
    //9
    {
      path: "/NMFA",
      name: "nmfa",
      component: () => import("../views/qrView.vue"),
    },
    //10
    {
      path: "/DEFP",
      name: "defp",
      component: () => import("../views/qrView.vue"),
    },
    //11
    {
      path: "/DMFA",
      name: "dmfa",
      component: () => import("../views/qrView.vue"),
    },
    //12
    {
      path: "/NEFP",
      name: "nefp",
      component: () => import("../views/qrView.vue"),
    },
    //13
    {
      path: "/NEIA",
      name: "neia",
      component: () => import("../views/qrView.vue"),
    },
    //14
    {
      path: "/NEFA",
      name: "nefa",
      component: () => import("../views/qrView.vue"),
    },
    //15
    {
      path: "/NMFP",
      name: "nmfp",
      component: () => import("../views/qrView.vue"),
    },
    //16
    
  ],
});

export default router;
