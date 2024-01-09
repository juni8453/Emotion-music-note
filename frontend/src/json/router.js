import {createRouter, createWebHistory} from "vue-router";
import store from "./store"
import UserLogin from "@/components/UserLogin";
import KakaoJoin from "@/components/KakaoJoin";
import HomePage from "@/components/HomePage";
import SaveNote from "@/components/SaveNote";
import NoteDetail from "@/components/NoteDetail";

const routes = [
  {
    path: '/',
    component: HomePage,
  },
  {
    path: '/login',
    component: UserLogin,
  },
  {
    // kakao redirect uri (인가 코드 받는 즉시 컴포넌트 생성)
    path: '/oauth/kakao',
    component: KakaoJoin,
  },
  {
    path: "/note/:noteId",
    component: NoteDetail,
    beforeEnter: (to, from, next) => {
      if (store.state.sessionId === null) {
        next('/login');
        alert('세션이 만료되어 로그인이 필요합니다.');
      } else {
        next();
      }
    }
  },
  {
    path: '/note/new',
    component: SaveNote,
    beforeEnter: (to, from, next) => {
      if (store.state.sessionId === null) {
        next('/login');
        alert('세션이 만료되어 로그인이 필요합니다.');
      } else {
        next();
      }
    }
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;